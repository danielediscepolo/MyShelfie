package it.polimi.ingsw.view;

import it.polimi.ingsw.ServerApp;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.message.*;
import it.polimi.ingsw.util.Observer;
import it.polimi.ingsw.view.cli.CLI;
import it.polimi.ingsw.exception.PassedMoveException;
import it.polimi.ingsw.controller.Patrick;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**VirtualView is a server-side class intended to dispatch messages from client to server.<br />
 * So it acts as a network handler, ensuring player connections are active.
 * It acts like a Virtual View in respect to the server-side Controller and Model.<br />
 * It has an Observer role in respect to GameView, and it sends update messages to the clients.<br />
 * View uses UserInterface class to realize the user interface in a CLI or GUI fashion.<br />
 * <br />
 * N.B. Every method in VirtualView can handle directly a match played in Local mode on the Server.
 * In this mode, Virtual View acts as an actual View.
 * This feature is implemented to debug and stress test the MVC portion on the project.
 * For this reason, and to keep the arch simple, this feature is implemented in VirtualView and not in a dedicated class.<br />
 * @see ServerApp
 * @see Patrick
 * @see UserInterface
 * @see ConnectionInterface
 *
 * @since 1.0
 * @version 1.0
 * */

 public class VirtualView implements Observer<GameView, Game.Event>{

    static Logger logger = Logger.getLogger("Logger");
    CLI localModeUI;
    Boolean localMode;
    List<String> playersNames = new ArrayList<>();

    /** Constructor for Local mode, used by OfflineApp to set VirtualView in Local Mode*/
    public VirtualView(){
        this.localMode = true;
        this.localModeUI = new CLI();
    }


    /** Constructor for Remote mode, used by Patrick to initialize the view.
     * @param players List of players for the "send-to-everybody" feature.*/
    public VirtualView(List<Player> players){
        this.localMode = false;
        this.playersNames = players.stream().map(Player::getName).collect(Collectors.toCollection(ArrayList::new));
    }


    /** Simple getter, returning the UserInterface object used in the Local mode of the game.
     * @return UserInterface object*/
    public UserInterface getLocalModeUI() {
        return localModeUI;
    }


    /** Observer method reacting to Game event built for the communication between Observer and Observable classes:<ul>
     *      <li>STARTGAME: Signal the View to initialize the Client-side Model.</li>
     *      <li>STARTTURN: Signal to update the Client-side Model.</li>
     *      <li>ENDTURN: Signal deprecated to keep the network communication at the bare minimum.</li>
     *      <li>ENDGAME: Signal the View the end of the game and the winner.</li>
     *      <li>UPDATE: Signal deprecated to keep the network communication at the bare minimum.</li>
     *</ul>
     * @param model Game model wrapped in a class as a final attribute.
     * @param event Game event to distinguish the different actions.
     * */
    @Override
    public void update(GameView model, Game.Event event) {
        if (localMode) {
            switch (event) {
                case STARTTURN -> {
                    localModeUI.infoMessage(model.getTurnPlayerName());
                    localModeUI.updateModel(model, "");
                }
                case ENDTURN -> {

                }
                case ENDGAME -> {
                    localModeUI.closeGame(model.getWinner().getName(),model.getWinner().getTotalPoints());
                }
                default -> logger.warning("Ignoring event from " + model + ": " + event);
            }
        }else{
            switch (event) {
                case STARTTURN -> {
                    logger.info("Caught STARTGAME/STARTTURN event on model update.");
                    UpdateGameMessage updateGameMessage = new UpdateGameMessage(model);
                    logger.info("UpdateGameMessage for START event is packed, sending it to every player.");
                    this.sendMessageToEverybody(playersNames, updateGameMessage);
                }
                case STARTGAME -> logger.severe("Caught DEPRECATED STARTGAME event.");
                case ENDTURN -> logger.severe("Caught DEPRECATED ENDTURN event.");

                case ENDGAME -> {
                    logger.info("Caught ENDGAME event on model update.");
                    EndGameMessage endgameMessage = new EndGameMessage(model.getWinner().getName(), "");
                    logger.info("UpdateGameMessage for ENDGAME event is packed, sending it to every player.");
                    this.sendMessageToEverybody(playersNames, endgameMessage);

                }
                default -> logger.warning("Caught unhandled event: "+event);
            }
        }
    }


    /** Internal method, main gate to the network connection, for outgoing communications.<br />
     * It accesses the shared Map of connections, based in ServerApp. Take the Connection obj and send the message.
     * @param turnPlayerName Recipient of the message to be sent.
     * @param message Message to be sent.
     * @throws IOException On failed connection.
     * */
    public void sendMessage(String turnPlayerName, Message message) throws IOException {

        ConnectionInterface connection = ServerApp.getPlayerConnectionFromPlayersInGameMap(turnPlayerName);
        logger.info("Got connection obj for "+turnPlayerName+".");
        try{
            connection.sendMessageFromServerToClient(message,turnPlayerName);
            logger.info("Message of type "+message.getClass()+" sent to "+turnPlayerName+".");
        }catch(IOException e){
            throw new IOException(turnPlayerName);
        }

    }


    /** Send same message for each specified player.<br />
     * @param players List of the recipients of the message to be sent.
     * @param message Message to be sent.
     * */
    private void sendMessageToEverybody(List<String> players, Message message) {

        for (String player : players) {
            try {
                this.sendMessage(player, message);
            } catch (IOException e) {
                //NOTHING TO DO - We do not handle this case
            }
        }
    }


    /** Controller driven, notify the client of the need to make a move.<br />
     * The available moves are the following:<ul>
     *      <li>PICKING: Signal the view to pick tiles from board.</li>
     *      <li>SORTING: Signal the view to sort picked tiles.</li>
     *      <li>COLUMNCHOICE: Signal the view to chose a column to insert tiles into.</li>
     *      <li>CONFIRMATION: Signal the View to confirm the played turn.</li>
     *</ul>
     * @param turnPlayerName Name of the player who has to move.
     * @param choice Move that the player has to do.
     * @throws IOException On network error.
     * */
    public void moveNotification(String turnPlayerName, MoveRequestMessage.Choice choice) throws IOException {
        if(localMode){
            switch (choice){
                case SORTING -> localModeUI.infoMessage("It's time to sort");
                case PICKING -> localModeUI.infoMessage("It's time to pick");
                case COLUMNCHOICE -> localModeUI.infoMessage("It's time to chose column");
                case CONFIRMATION -> localModeUI.infoMessage("It's time to confirm");
            }

        }else{

            Message message = new MoveRequestMessage(choice);
            logger.info("Packed message for "+choice+".");
            this.sendMessage(turnPlayerName, message);


        }

    }


    /** Controller driven, notify the client  of the result of the played move.
     * The available responses are the following:<ul>
     *      <li>NON-PICKABLE-TILES: Error on picking move.</li>
     *      <li>BAD-SORTED-TILES-LIST: Error on sorting move.</li>
     *      <li>NOT-ENOUGH-FREE-BOXES-IN-COLUMN: Error on column choosing move.</li>
     *      <li>PICK-CONFIRMATION: Confirmation for picking move.</li>
     *      <li>SORT-CONFIRMATION: Confirmation for sorting move.</li>
     *      <li>COL-CONFIRMATION: Confirmation for column choosing move.</li>
     * </ul>
     * @param turnPlayerName Recipient of the notification.
     * @param responseType Result of the move to communicate.
     * @throws IOException On network error.
     * */
    public void responseNotification(String turnPlayerName , ResponseMessage.ResponseType responseType) throws IOException{

        if(localMode){
            localModeUI.errorMessage(responseType.toString());
        }else{

            ResponseMessage responseMessage = new ResponseMessage(responseType);
            this.sendMessage(turnPlayerName, responseMessage);

        }
    }


    /** Wait for PickMoveMessage from a specific player.
     * @param turnPlayerName Name of the player to listen for.
     * @param timeout Time is seconds to wait for incoming messages.
     * @return List of Position objects representing tiles in the board.
     * @throws IOException On network error.
     * @throws PassedMoveException On received "Passed" as move.
     * @throws TimeoutException On Timeout.
     * */
    public List<Position> pickTilesMove(String turnPlayerName, int timeout) throws  IOException, PassedMoveException, TimeoutException{
        List<Position> posix;
        if(localMode){
            posix = localModeUI.pickTilesFromBoard();
        }else{

            ConnectionInterface connection = ServerApp.getPlayerConnectionFromPlayersInGameMap(turnPlayerName);
            logger.info("Got connection object for "+turnPlayerName+".");
            PickMoveMessage pickMessage = (PickMoveMessage) connection.waitForMessageFromClient(PickMoveMessage.class, timeout,turnPlayerName);

            if (!pickMessage.isPassedTurn()) {
                posix = pickMessage.getPosix();
            } else {
                throw new PassedMoveException();
            }
        }

        return posix;
    }


    /** Wait for SortMoveMessage from a specific player.
     * @param turnPlayerName Name of the player to listen for.
     * @param timeout Time is seconds to wait for incoming messages.
     * @return List of sorted Tiles.
     * @throws IOException On network error.
     * @throws PassedMoveException On received "Passed" as move.
     * @throws TimeoutException On Timeout.
     * */
    public List<Tile> sortTilesMove(String turnPlayerName, List<Tile> tiles, int timeout) throws  IOException, PassedMoveException, TimeoutException{
        List<Tile> sortedTiles;
        if(localMode){
            sortedTiles = localModeUI.sortTilesToInsert(tiles);
        }else{

            ConnectionInterface connection = ServerApp.getPlayerConnectionFromPlayersInGameMap(turnPlayerName);
            logger.info("Got connection object for "+turnPlayerName+".");
            SortMoveMessage sortMessage = (SortMoveMessage) connection.waitForMessageFromClient(SortMoveMessage.class, timeout,turnPlayerName);
            if(!sortMessage.isPassedTurn()) {
                sortedTiles = sortMessage.getSortedTiles();
            }else{
                throw new PassedMoveException();
            }

        }

        return sortedTiles;
    }


    /** Wait for ColumnMoveMessage from a specific player.
     * @param turnPlayerName Name of the player to listen for.
     * @param timeout Time is seconds to wait for incoming messages.
     * @throws IOException On network error.
     * @throws PassedMoveException On received "Passed" as move.
     * @throws TimeoutException On Timeout.
     * */
    public int chooseColumnMove(String turnPlayerName, int timeout) throws IOException, PassedMoveException, TimeoutException{
        int column;
        if(localMode){
            column = localModeUI.chooseColumn();
        }else{

            ConnectionInterface connection = ServerApp.getPlayerConnectionFromPlayersInGameMap(turnPlayerName);
            logger.info("Got connection object for "+turnPlayerName+".");
            ColumnMoveMessage columnMoveMessage = (ColumnMoveMessage) connection.waitForMessageFromClient(ColumnMoveMessage.class, timeout,turnPlayerName);
            if (!columnMoveMessage.isPassedTurn()) {
                column = columnMoveMessage.getColumn();
            } else {
                throw new PassedMoveException();
            }
        }

        return column;
    }


    /** Wait for TurnConfirmationMessage from a specific player.
     * @param turnPlayerName Name of the player to listen for.
     * @param timeout Time is seconds to wait for incoming messages.
     * @throws IOException On network error.
     * @throws TimeoutException On Timeout.
     * */
    public boolean turnConfirmation(String turnPlayerName, int timeout) throws IOException, TimeoutException{
        Boolean confirm;
        if(localMode){
            confirm = localModeUI.confirmChoice("Do you want to confirm your choices?");
        }else{

            ConnectionInterface connection = ServerApp.getPlayerConnectionFromPlayersInGameMap(turnPlayerName);
            logger.info("Got connection object for "+turnPlayerName+".");
            TurnConfirmationMessage turnConfirmationMessage = (TurnConfirmationMessage) connection.waitForMessageFromClient(TurnConfirmationMessage.class, timeout,turnPlayerName);

            confirm = turnConfirmationMessage.getConfirmation();
        }
        return confirm;
    }


    /** Send a Win message to the only player that is still online.
     * @param playerName Name of the player to send the message to.
     * */
    public void sendMessageForAnticipatedEndgame(String playerName) throws IOException {
        Message message = new EndGameMessage(playerName, "You are the only one left!");
        logger.info("Packed message for anticipated endgame.");
        this.sendMessage(playerName, message);
    }


    /** Check connection of the players. If there are no online players, it starts a countdown.
     * If not player connect for the end of the countdown, the
     * */
    public List<String> checkConnection(List<String> playerNames, int waitingTime) {

        List<String> connected = ServerApp.getConnectedPlayersFromPlayersInGameMap(playerNames);
        logger.info("Starting connection check for all players.");
        if(connected.size()==0){
            long t = System.currentTimeMillis();
            long end = t + waitingTime * 1000L;

            while(System.currentTimeMillis() < end ){
                try { //noinspection BusyWait
                    Thread.sleep(120000);} catch (InterruptedException e) {/*NOP*/}
                ServerApp.getConnectedPlayersFromPlayersInGameMap(playerNames);
                if(connected.size()!=0){
                    logger.info("Found at least a connected player. ");
                    return connected;}
            }
        }

        return connected;

    }

}
