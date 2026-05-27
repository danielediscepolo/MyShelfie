package it.polimi.ingsw;

import it.polimi.ingsw.exception.BadNumberOfPlayersException;
import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.model.GameView;
import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.model.Tile;
import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.network.rmi.RMIClient;
import it.polimi.ingsw.network.socket.SocketClient;
import it.polimi.ingsw.util.LocalFormatter;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.view.gui.GUI;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.TimeoutException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClientApp {

    static Logger logger;
    private static Board board;

    static {
        Logger mainLogger = Logger.getLogger("Logger");
        mainLogger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new LocalFormatter());
        mainLogger.addHandler(handler);
        logger = Logger.getLogger("Logger");
    }
    static UserInterface ui;
    static String hostname;
    static ClientInterface network;
    static int port;
    static String connectionType;
    static LinkedTransferQueue<Message> messageQueue = new LinkedTransferQueue<>();
    static GameView model;

    public static void main(String[] args) {


        ui = new CLI();
        port = 2308;
        hostname = "localhost";

        logger.setLevel(Level.SEVERE);
        connectionType = "socket";

        //Arguments parsing
        List<String> argsList = Arrays.asList(args);
        for (String flag : args) {
            switch (flag) {
                case "--help", "-h" -> {
                    printUsage();
                    System.exit(0);
                }
                case "--enable-logging", "--log", "-g" -> logger.setLevel(Level.FINE);
                case "--network", "-n" -> {
                    if (args[argsList.indexOf(flag) + 1].equals("rmi")) {
                        connectionType = "rmi";
                    } else if (!(args[argsList.indexOf(flag) + 1].equals("socket"))) {
                        logger.severe("Wrong --network flag");
                        printUsage();
                        System.exit(-1);
                    }
                }
                case "--interface", "-i" -> {
                    if (args[argsList.indexOf(flag) + 1].equals("gui")) {
                        ui = new GUI();
                    } else if (!args[argsList.indexOf(flag) + 1].equals("cli")) {
                        logger.severe("Wrong --interface flag");
                        printUsage();
                        System.exit(-1);
                    }
                }
                case "--server-ip", "--address", "-l" -> hostname = args[argsList.indexOf(flag) + 1];
                case "--server-port", "--port", "-p" -> {
                    try {
                        port = Integer.parseInt(args[argsList.indexOf(flag) + 1]);
                    } catch (NumberFormatException e) {
                        logger.severe("Wrong Port number: " + port);
                        printUsage();
                        System.exit(-1);
                    }

                    if (port < 1 || port > 65535) {
                        logger.severe("Wrong Port number, not in range: " + port);
                        printUsage();
                        System.exit(-1);
                    }
                }
            }
        }

        if (connectionType.equals("socket")) {
            network = new SocketClient(hostname, port);
        } else {
            network = new RMIClient(hostname);
        }

        //CLI -> GUI switch
        int rst = ui.startInterface();

        if ( CLI.class.isInstance(ui) && rst == 3 ) {

            new Thread(()->GUI.main(args)).start();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            ui = GUI.gui;

            ui.startInterface();
        }
        String RmiClientName = null;

        //Login Loop
        boolean loginRetry = true;
        while (loginRetry) {
            try {
                Thread.sleep(5000);


                connectionToServer();

                LoginMessage loginMessage = loginRequestForger();
                ui.infoMessage("Login request has been sent.");
                ui.waitingRoom("Looking for game...", 3000);
                logger.info("Sending" + loginMessage + " to " + loginMessage.getName());
                network.sendMessageFromClientToServer(loginMessage);
                NameAvailabilityMessage nameAvailabilityMessage = (NameAvailabilityMessage) network.waitForMessageFromServer(NameAvailabilityMessage.class, 30000);
                ui.setWaitingRoomInterrupt(true);
                logger.info("Message received");


                if(nameAvailabilityMessage.isAvailable()){
                    ui.infoMessage("Available name!");
                    RmiClientName = loginMessage.getName();
                    loginRetry=false;
                }
                else{
                    ui.infoMessage("The chosen name is not available!.");
                    loginRetry=true;
                }
            } catch (IOException e){

                logger.info("Connection FAILURE 1 to '"+hostname+":"+port+"'");
                e.printStackTrace();
                loginRetry=true;

            }catch(TimeoutException ex) {
                logger.info("Connection FAILURE 2 to '"+hostname+":"+port+"'");
                loginRetry=true;

            }catch (InterruptedException exc) {logger.info("INTERRUPTED");}
        }

        ui.waitingRoom("Waiting for the game to start...", 30000);

        //Match Loop
        while(true){
            try {

                Object message = network.waitForMessageFromServer(Message.class, 30000);
                ui.setWaitingRoomInterrupt(true);



                if(message instanceof UpdateGameMessage updateGameMessage){
                    logger.info("Received UpdateGameMessage: Updating model.");
                    model = updateGameMessage.getModel();
                    ui.updateModel(model,RmiClientName);
                    continue;
                }

                if(message instanceof EndGameMessage){
                    logger.info("Received EndGameMessage: Starting endgame routine.");
                    endgameRoutine((EndGameMessage) message);
                    ui.exitInterface(0);
                    System.exit(0);
                }

                if(message instanceof PingMessage){
                    logger.info("Received PingMessage: Ignoring it.");
                    if (RMIClient.class.isInstance(network)){
                        logger.warning("Sending PingMessage to server");
                        network.sendMessageFromClientToServer(new PingMessage());
                    }
                    continue;
                }


                if(message instanceof MoveRequestMessage && ((MoveRequestMessage) message).getChoice().equals(MoveRequestMessage.Choice.PICKING)){

                    logger.info("Received MoveRequestMessage-Pick. Starting turn handling.");

                    ui.infoMessage("\nIt's Your Turn!");
                    boolean isConfirmed = false;
                    boolean passAction = false;

                    while(!isConfirmed){

                        //PICK MOVE
                        List<Position> posix = new ArrayList<>();
                        boolean retryMove = true;
                        while (retryMove) {


                            logger.info("Waiting for the user to pick tiles.");
                            posix = ui.pickTilesFromBoard();
                            if(posix.size() == 0){

                                logger.info("The user passed an empty list of Positions. Considering it a pass.");
                                PickMoveMessage pickMoveMessage = new PickMoveMessage();
                                logger.info("Sending PickMoveMessage with PassedTurn = TRUE.");
                                network.sendMessageFromClientToServer(pickMoveMessage);
                                passAction = true;
                                break;

                            }else{
                                logger.info("Sending PickMoveMessage with PassedTurn = FALSE.");
                                PickMoveMessage pickMoveMessage = new PickMoveMessage(posix);
                                network.sendMessageFromClientToServer(pickMoveMessage);
                            }
                            // TODO lui qui si aspetta una risposta di tipo response
                            ResponseMessage responseMessage = (ResponseMessage) network.waitForMessageFromServer(ResponseMessage.class, 30000);

                            if(responseMessage.getResponseType().equals(ResponseMessage.ResponseType.PICKCONFIRMATION)){
                                logger.info("Received PICK-CONFIRMATION. Moving to Sorting.");
                                retryMove=false;
                            }else if(responseMessage.getResponseType().equals(ResponseMessage.ResponseType.NONPICKABLETILES)){
                                logger.info("Received NON-PICKABLE-TILES error. Repeat pick.");
                                retryMove=true;
                            }else {
                                logger.info("Received "+responseMessage.getResponseType()+" ResponseMessage while Waiting for Pick confirmation or pick error: Non consistent state, resetting the turn.");
                                throw new IOException();
                            }

                        }

                        if(passAction){break;}

                        //SORT MOVE
                        MoveRequestMessage moveRequestMessage = (MoveRequestMessage) network.waitForMessageFromServer(MoveRequestMessage.class, 30000);
                        if(moveRequestMessage.getChoice().equals(MoveRequestMessage.Choice.SORTING)){

                            retryMove = true;
                            while (retryMove) {

                                logger.info("Waiting for the user to sort tiles.");
                                List<Tile> sortedTiles = ui.sortTilesToInsert(getTilesFromPosix(posix));
                                if(sortedTiles.size() == 0){
                                    logger.info("The user passed an empty list of Tiles. Considering it a pass.");
                                    SortMoveMessage sortMoveMessage = new SortMoveMessage();
                                    logger.info("Sending SortMoveMessage with PassedTurn = TRUE.");
                                    network.sendMessageFromClientToServer(sortMoveMessage);
                                    passAction = true;
                                    break;

                                }else{
                                    logger.info("Sending SortMoveMessage with PassedTurn = FALSE.");
                                    SortMoveMessage sortMoveMessage = new SortMoveMessage(sortedTiles);
                                    network.sendMessageFromClientToServer(sortMoveMessage);
                                }

                                ResponseMessage responseMessage = (ResponseMessage) network.waitForMessageFromServer(ResponseMessage.class, 30000);

                                if(responseMessage.getResponseType().equals(ResponseMessage.ResponseType.SORTCONFIRMATION)){
                                    logger.info("Received SORT-CONFIRMATION. Moving to Column choosing.");
                                    retryMove=false;
                                }else if(responseMessage.getResponseType().equals(ResponseMessage.ResponseType.BADSORTEDTILESLIST)){
                                    logger.info("Received BAD-SORTED-TILES-LIST error. Repeat sort.");
                                    retryMove=true;
                                }else {
                                    logger.info("Received "+responseMessage.getResponseType()+" ResponseMessage while Waiting for Sort confirmation or sort error: Non consistent state, resetting the turn.");
                                    throw new IOException();
                                }

                            }
                        }else{
                            logger.info("Received "+moveRequestMessage.getChoice()+" MoveRequestMessage while Waiting for SORTING MoveRequestMessage: Non consistent state, resetting the turn.");
                            throw new IOException();
                        }

                        if(passAction){break;}

                        //COLUMN MOVE
                        moveRequestMessage = (MoveRequestMessage) network.waitForMessageFromServer(MoveRequestMessage.class, 30000);
                        if(moveRequestMessage.getChoice().equals(MoveRequestMessage.Choice.COLUMNCHOICE)){

                            retryMove = true;
                            while (retryMove) {

                                logger.info("Waiting for the user to chose column.");
                                int column = ui.chooseColumn();
                                ColumnMoveMessage columnMoveMessage = new ColumnMoveMessage(column);
                                logger.info("Sending ColumnMoveMessage with PassedTurn = FALSE.");
                                network.sendMessageFromClientToServer(columnMoveMessage);

                                ResponseMessage responseMessage = (ResponseMessage) network.waitForMessageFromServer(ResponseMessage.class, 30000);

                                if(responseMessage.getResponseType().equals(ResponseMessage.ResponseType.COLCONFIRMATION)){
                                    logger.info("Received COLUMN-CONFIRMATION. Moving to confirmation phase.");
                                    retryMove=false;
                                }else if(responseMessage.getResponseType().equals(ResponseMessage.ResponseType.NOTENOUGHFREEBOXESINCOLUMN)){
                                    logger.info("Received NOT-ENOUGH-FREE-BOXES-IN-COLUMN error. Repeat column choosing.");
                                    retryMove=true;
                                }else {
                                    logger.info("Received "+responseMessage.getResponseType()+" ResponseMessage while Waiting for Column chose confirmation or column choose error: Non consistent state, resetting the turn.");
                                    throw new IOException();
                                }

                            }
                        }else{
                            logger.info("Received "+moveRequestMessage.getChoice()+" MoveRequestMessage while Waiting for COLUMNCHOICE MoveRequestMessage: Non consistent state, resetting the turn.");
                            throw new IOException();
                        }


                        //CONFIRMATION MOVE
                        moveRequestMessage = (MoveRequestMessage) network.waitForMessageFromServer(MoveRequestMessage.class, 30000);
                        if(moveRequestMessage.getChoice().equals(MoveRequestMessage.Choice.CONFIRMATION)){

                            logger.info("Waiting for the user to confirm the state of the turn.");
                            isConfirmed = ui.confirmChoice("Do you want to confirm the turn?");

                            if (isConfirmed) {

                                TurnConfirmationMessage turnConfirmationMessage = new TurnConfirmationMessage(true);
                                logger.info("Sending POSITIVE TurnConfirmationMessage.");
                                //Restarting the loop in case of IOException.
                                network.sendMessageFromClientToServer(turnConfirmationMessage);


                            } else {

                                TurnConfirmationMessage turnConfirmationMessage = new TurnConfirmationMessage(false);
                                network.sendMessageFromClientToServer(turnConfirmationMessage);

                                moveRequestMessage = (MoveRequestMessage) network.waitForMessageFromServer(MoveRequestMessage.class, 30000);

                                if(!moveRequestMessage.getChoice().equals(MoveRequestMessage.Choice.PICKING)){

                                    logger.info("Received "+moveRequestMessage.getChoice()+" ResponseMessage while Waiting for Pick confirmation or pick error: Non consistent state, resetting the turn.");
                                    throw new IOException();
                                }

                            }
                        }else{
                            logger.info("Received "+moveRequestMessage.getChoice()+" MoveRequestMessage while Waiting for CONFIRMATION MoveRequestMessage: Non consistent state, But considering the turn valid.");
                            isConfirmed = true;
                        }
                    }
                }

            } catch (IOException e ) {
                logger.info("Caught IOException while waiting for message. Restarting the loop.");
                reconnectRoutine(300);

            } catch (TimeoutException e){
                logger.info("Caught TimeoutException while waiting for message. Restarting the loop.");
                reconnectRoutine(300);
            }
        }
    }

    private static void printUsage(){
        String usage= """
                MyShelfie Client - 1.0
                
                Usage: ClientApp [arguments]
                               
                Arguments:
                                
                --server-ip  <address>        Set server address to <address>.
                or --address <address>
                or        -l <address>
                
                --server-port <port>          Set socket server port on <port>.
                  or   --port <port>
                  or       -p <port>
                
                --enable-logging              Enable Logging from the FINE level.
                       or  --log
                       or     -g
                
                --interface {cli, gui}        Set CLI or GUI as User Interface.
                   or    -i {cli, gui}
                          
                --network {rmi, socket}       Set RMI or Socket as network layer.
                   or  -n {rmi, socket}
                             
                --help                        Print Help (this message) and exit
                or  -h
               
              """;
        System.out.println(usage);
    }

    private static void connectionToServer(){
        boolean retry = true;
        while (retry) {

            try{
                logger.info("Connecting to '"+hostname+":"+port+"'");
                network.connect();
                retry=false;
                logger.info("Connection SUCCESS to '"+hostname+":"+port+"'");
            }catch (IOException e){
                logger.info("Connection FAILURE to '"+hostname+":"+port+"'");
                retry = ui.confirmChoice("Failed Connection to '"+hostname+"' on port"+port+" via "+connectionType+": Do you want to retry?");
                if(!retry){
                    logger.info("Closing application");
                    ui.exitInterface(0);
                    System.exit(0);
                }
            }
        }

    }

    private static LoginMessage loginRequestForger(){
        Object[] loginRequest;
        LoginMessage loginMessage = null;
        boolean success=false;
        while (!success) {
            loginRequest = ui.welcome();
            try {
                loginMessage = new LoginMessage((String) loginRequest[0], (Integer) loginRequest[1]);
                success= true;
            } catch (BadNumberOfPlayersException e) { ui.errorMessage("The number of players preference must be between 2 and 4. Retry.");}
        }
        return loginMessage;
    }

    private static List<Tile> getTilesFromPosix(List<Position> posix){
        List<Tile> tiles = new ArrayList<>();
        for(Position pos: posix){
            tiles.add(model.getBoard().getBoxes()[pos.getRow()][pos.getColumn()]);
        }
        return tiles;
    }

    private static void endgameRoutine(EndGameMessage endGameMessage){
       ui.endgame(endGameMessage.getWinner());
    }

    public static void reconnectRoutine(int waitingTime){

        PingMessage pingMessage = new PingMessage();
        boolean retry;
        do {

            long t = System.currentTimeMillis();
            long end = t + waitingTime * 1000L;

            ui.waitingRoom("Trying to reconnect after connection failure ...", waitingTime+10);
            while(System.currentTimeMillis() < end){

                try {
                    network.sendMessageFromClientToServer(pingMessage);
                    return;

                } catch (IOException e) {
                    try { Thread.sleep(30000);} catch (InterruptedException ex) {/*NOP*/}

                    try {
                        network.disconnect();
                        network.connect();
                    } catch (IOException ex) {/*NOP*/}

                }
            }
            ui.setWaitingRoomInterrupt(true);
            retry=ui.confirmChoice("Connection Failure. Do you want to retry? Clicking NO, will exit from interface.");

        } while (retry);

        ui.exitInterface(-1);
        System.exit(-1);
    }


}