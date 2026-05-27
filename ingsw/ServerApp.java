package it.polimi.ingsw;

import it.polimi.ingsw.controller.Patrick;
import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.model.WaitingRoom;
import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.message.NameAvailabilityMessage;
import it.polimi.ingsw.network.socket.SocketServer;
import it.polimi.ingsw.network.socket.SocketListener;
import it.polimi.ingsw.network.rmi.RMIServer;
import it.polimi.ingsw.util.LocalFormatter;
import it.polimi.ingsw.view.VirtualView;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.rmi.RemoteException;
import java.util.*;
import java.util.concurrent.*;
import java.util.logging.*;
import java.time.Instant;

/**Main Process for MyShelfie Server.<br/>
 * The following structure is fundamental to implement in an elegant way Resilience, Persistence and Multi-match features.
 * ServerApp is primarily based on the use of 3 data structures:<br/>
 * 3 lists of players representing the 3 waiting rooms (2,3 and 4 players) enclosed in the WaitingRoom object;<br/>
 * 1 BlockingQueue representing the shared resource in a Consumer Producer Pattern.<br/>
 * ServerApp plays the role of a Consumer of Login requests. These are processed one at a time to maintain robustness in
 * execution.<br/>
 * Elements from the queue are taken with the take method, blocking to ensure correct execution if the queue is empty.
 * The login name is the only discriminator to distinguish different players, they must therefore be unique.<br/>
 * Requests are processed in the following way:<ul>
 *      <li>If a player with the same name exists and is in the game:
 *               The incoming request is discarded with notification.</li>
 *      <li>If a player with the same name exists and is in the game but is logged out:
 *               Since the login name is the only authenticating element, the incoming request is assumed to be an
 *               attempt to reconnect.
 *               The connection object of the disconnected player is then updated.</li>
 *      <li>If a player with the same name exists and is in waitingRoom:
 *               The incoming request is discarded with notification.</li>
 *      <li>If there are no players with the same name:
 *               At this point an attempt is made to assign the newly connected player to a random waiting room that has
 *               at least one player (to speed up the start). If this is not possible, it means that all 3 are empty.
 *               Then the waiting room chosen by the player in the preferences is used.
 *               If the waiting room is full the game is initialised in a new thread, players are added to the list of
 *               players in the game and the waiting room is emptied.
 *</ul>
 * ServerApp also maintain a HashMap of Playing players. This Data Structure is a centralized store for valid Connection
 * objects.<br/>
 * Whenever VirtualView needs to send a message to a player, it refers to the connections contained here. Whenever
 * ServerApp needs to update a connection, the corresponding player in the map is updated.<br/>
 * <br/>
 * Note that ServerApp skims unconnected players each time it checks waiting rooms.<br/>
 * <br/>
 * ServerApp, as like as Patrick class, implements a Persistence functionality. At the start of a match, it passes to
 * Patrick constructor a unique filename. The uniqueness of the filename is entirely based on timestamp,
 * given that the thread generating the filename is unique in the context of a Server process,
 * and assuming there are no other Servers with which exchange save-files, we can firmly state that for the scope of
 * this project the generate filenames have 1-to-1 relationship with matches.<br/>
 * When the server is restarted after a shutdown, it checks how many save files are in directory specified in the arguments.
 * For each one try to deserialize the content in a Game obj. Then it checks for consistency in players and start the Game.
 *
 * @see SocketServer
 * @see SocketListener
 * @see VirtualView
 * @see WaitingRoom
 * @see Patrick
 * @since 1.0
 * @version 1.1
 * */
public class ServerApp {

    static Logger logger;
    static {
        Logger mainLogger = Logger.getLogger("Logger");
        mainLogger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new LocalFormatter());
        mainLogger.addHandler(handler);
        logger = Logger.getLogger("Logger");
    }
    static WaitingRoom waitingRoom2 = new WaitingRoom(2);
    static WaitingRoom waitingRoom3 = new WaitingRoom(3);
    static WaitingRoom waitingRoom4 = new WaitingRoom(4);
    static List<WaitingRoom> waitingRooms = Arrays.asList(waitingRoom2, waitingRoom3, waitingRoom4);
    static final BlockingQueue<Player> incomingPlayersQueue = new ArrayBlockingQueue<>(100);
    static final ConcurrentHashMap<String, Player> playersInGame = new ConcurrentHashMap<>();
    static int serverSocketPort = 2308;
    static String hostname;
    static SocketServer socketServer;
    static RMIServer rmiServer;

    static String directoryPath = System.getProperty("user.dir")+"\\src\\main\\resources\\MemoryCard8MB";

    public static void main(String[] args) {

        logger.setLevel(Level.SEVERE);

        //Arguments parsing
        List<String> argsList = Arrays.asList(args);
        for (String flag : args) {
            switch (flag) {
                case "--help", "-h" -> {
                    printUsage();
                    System.exit(0);
                }
                case "--enable-logging", "--log", "-g" -> logger.setLevel(Level.FINE);
                case "--socket-port", "--port", "-p" -> {
                    try {
                        serverSocketPort = Integer.parseInt(args[argsList.indexOf(flag) + 1]);
                    } catch (NumberFormatException e) {
                        logger.severe("Wrong Port number: " + serverSocketPort);
                        printUsage();
                        System.exit(-1);
                    }

                    if (serverSocketPort < 1 || serverSocketPort > 65535) {
                        logger.severe("Wrong Port number, not in range: " + serverSocketPort);
                        printUsage();
                        System.exit(-1);
                    }
                }
                case "--ip-address", "--address", "-l" -> hostname = args[argsList.indexOf(flag) + 1];
                case "--directory", "-d" -> directoryPath = flag;

            }
        }


        //Recovering closed games
        logger.info("Recovering matches from Memory Card: ");
        File[] files = new File(directoryPath).listFiles();
        if(files == null){
            logger.severe("Wrong filename path: "+directoryPath+". Closing");
            printUsage();
            System.exit(-1);

        }else if(files.length!=0){

            Arrays.sort(files, Comparator.comparingLong(File::lastModified));
            Arrays.sort(files, Collections.reverseOrder());

            for (File file : files) {

                if (file.isFile()) {
                    logger.info("Reading file: "+file.getAbsolutePath());

                    FileInputStream fileInputStream = null;
                    ObjectInputStream objectInputStream = null;
                    Game game = null;
                    try {
                        fileInputStream = new FileInputStream(file);
                        objectInputStream = new ObjectInputStream(fileInputStream);
                        game = (Game) objectInputStream.readObject();
                        logger.info("Reading object");

                        List<Player> players = new ArrayList<>();
                        int count=0;
                        for(String name :game.getPlayersNames()){
                            Player player = new Player(name);
                            if(!playersInGame.containsKey(player.getName())){
                                players.add(player);
                                count++;
                            }
                        }

                        if(count == game.getPlayersNames().size()){
                            addPlayersToPlayersInGameMap(players);
                            Patrick controller = new Patrick(players, file.getAbsolutePath(), game);
                            Thread controllerThread = new Thread(controller);
                            controllerThread.start();
                        }else{

                            //noinspection ResultOfMethodCallIgnored
                            file.delete();
                        }


                    } catch (IOException | ClassNotFoundException e) {logger.warning("Caught exception while handling file: "+file.getName()+". Ignoring it.");
                    } finally {
                        try { if(objectInputStream != null) objectInputStream.close();} catch (IOException e) {logger.warning("Caught exception while closing file stream for: "+file.getName());}
                    }

                }
            }
        }else{ logger.info("There are no files in: "+directoryPath+".Skipping restoring phase."); }


        //Starting servers, they listen for new connections and put logging-in players in Queue

        logger.info("Starting up Servers");

        try { socketServer = new SocketServer(hostname, serverSocketPort); } catch (IOException e) {
            logger.severe("Cannot bind to the specified interface. Closing.");
            printUsage();
            System.exit(-1);
        }

       try { rmiServer = new RMIServer(hostname); } catch (IOException e) {
            logger.severe("IOException caught on RMI server starting");
            printUsage();
            System.exit(-1);
        }
        socketServer.startServer();
        try {
            rmiServer.startServer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        //Processing players in queue
        int roomCapacityPreference;
        int indexOfDesignatedWaitingRoom;
        int indexOfFirstNonEmptyWaitingRoom;
        WaitingRoom designatedWaitingRoom;
        //noinspection InfiniteLoopStatement
        while(true){

            Player newPlayer;

            //Wait for an element to be available in the queue, then take it.
            try {
                newPlayer = incomingPlayersQueue.take();
                logger.info("Processing login request from player:"+newPlayer.getName());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            //Getting player preferences
            String newPlayerName = newPlayer.getName();
            roomCapacityPreference = newPlayer.getGamePreference();

            //Checking if a player with same name exists.
            Player playerInWaitingRoom = existingPlayerWithSameNameInWaitingRooms(newPlayerName);
            Player playerInGame = existingPlayerWithSameNameInPlayersInGameMap(newPlayerName);

            if(playerInGame != null && playerInWaitingRoom != null){
                //Exception for Robustness and debugging purposes
                logger.severe("The program has reached a non consistent state.\n 2 Player, with same name have been" +
                        " found in the playersInGame list and in a waiting room.");
                throw new RuntimeException();
            }

            if(playerInGame != null){

                boolean connectedPlayerInGame;
                synchronized (playersInGame) {
                    connectedPlayerInGame = playerInGame.isConnected();
                }
                if(connectedPlayerInGame){
                    logger.info("A player with the specified Name ("+newPlayer.getName()+") is connected and is " +
                            "playing, discard the Login request and notify the requester.");
                    try { newPlayer.getConnection().sendMessageFromServerToClient(new NameAvailabilityMessage(false),newPlayer.getName());
                    } catch (IOException e) {/*DISCONNECTED WHILE TRYING TO NOTIFY THE PLAYER, DO NOTHING*/}

                }else{
                    logger.info("A player with the specified Name ("+newPlayer.getName()+") exists and is " +
                            "playing but is not connected, updating the connection.");
                    try {
                        setPlayerConnectionOnPlayersInGameMap(playerInGame.getName(), newPlayer.getConnection());
                        newPlayer.getConnection().sendMessageFromServerToClient(new NameAvailabilityMessage(true),newPlayer.getName());
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    } catch (IOException ex){ /*NOP*/ }
                }
            }

            if(playerInWaitingRoom != null){
             //If there is a player in waiting room we assume it is connected. The check is handled by
                // existingPlayerWithSameNameInWaitingRooms()
                logger.info("A player with the specified Name ("+newPlayer.getName()+") is connected in a " +
                        "waiting room, discard the new Login request and notify the requester.");
                try {
                    newPlayer.getConnection().sendMessageFromServerToClient(new NameAvailabilityMessage(false),newPlayer.getName());
                } catch (IOException e) {/*DISCONNECTED WHILE TRYING TO NOTIFY THE PLAYER, DO NOTHING*/}

            }

            if(playerInGame == null && playerInWaitingRoom == null){

                logger.info("The Name '"+newPlayer.getName()+"' is available, registering the user as a new " +
                        "player.");


                //Collecting player preferences and looking for the first non-empty waiting room.
                //If there are only empty rooms, use player preference to assign it.
                indexOfFirstNonEmptyWaitingRoom = findFirstNonEmptyWaitingRoom();
                indexOfDesignatedWaitingRoom = (indexOfFirstNonEmptyWaitingRoom==-1 ) ? (roomCapacityPreference-2) :
                        indexOfFirstNonEmptyWaitingRoom;
                designatedWaitingRoom = waitingRooms.get(indexOfDesignatedWaitingRoom);

                //Add player to designated waiting room
                try {
                    newPlayer.getConnection().sendMessageFromServerToClient(new NameAvailabilityMessage(true),newPlayer.getName());
                    newPlayer.setConnection(newPlayer.getConnection());
                    designatedWaitingRoom.addPlayer(newPlayer);

                } catch (IOException e) {
                    logger.severe("Caught network exception while trying to send positive availability Message." +
                            "\n Skipping the user");
                    continue;
                } catch (RuntimeException ex){
                    logger.severe("A Waiting Room has reached a non consistent state.\n Flushing it to prevent " +
                            "further errors.");
                    designatedWaitingRoom.flush();
                    continue;
                }

                //Connection check to be sure to start only consistent games
                //TODO controllare la waiting room
                designatedWaitingRoom.everybodyInWaitingRoomIsConnected();

                if (designatedWaitingRoom.isFull()) {

                    synchronized (playersInGame) {
                        logger.info("The waiting room is full and everyone is connected. Starting the game");
                        List<Player> players = designatedWaitingRoom.getPlayers();
                        Patrick controller = new Patrick(players, nameGenerator());
                        Thread controllerThread = new Thread(controller);
                        controllerThread.start();
                        for(Player player: players){ playersInGame.put(player.getName(), player);}
                        designatedWaitingRoom.flush();
                    }


                }else if(!designatedWaitingRoom.isEmpty()){
                    logger.info("The Waiting Room has "+designatedWaitingRoom.size()+" the game could not be " +
                            "started. The player must wait.");
                }else{
                    logger.severe("The program has reached a status that should not be reachable.\n At this " +
                            "point it should not be possible to have empty rooms.");
                    throw new RuntimeException();
                }

            }

        }
    }

    /**Utility method, it looks for a non-empty non-full room, in the following order: 4-players, 3-players, 2-players.<br/>
     * Given the structure, in the phase this method is called it should not exist a Room that is full.
     * @return the index of the first non-empty non-full room in the WaitingRooms List.
     * */
    private static int findFirstNonEmptyWaitingRoom() {
        for(int i = 2; i>-1; i--){
            if(waitingRooms.get(i).size()!=0){
                if(waitingRooms.get(i).size()==i+2) {
                    logger.severe("The program has reached a status that should not be reachable.\n Every " +
                            "full room must be processed before the processing of a new element in the queue.");
                    throw new RuntimeException();
                }
                return i;
            }
        }
        return -1;
    }


    /**Utility method, it checks if it exists a Logged-in player with same name in waiting rooms. If it exists
     * but is disconnected, it is kicked.
     * @param newPlayerName Name to check for
     * @return The existing player of Null
     * */
    private static Player existingPlayerWithSameNameInWaitingRooms(String newPlayerName){
        Player existingPlayer = null;
        for(WaitingRoom room: waitingRooms){
            existingPlayer = room.existingPlayerWithSameName(newPlayerName);
            if (existingPlayer != null){
                return existingPlayer;
            }
        }
        return existingPlayer;
    }


    /**Utility method, it checks if it exists a Logged-in player with same name in the HashMap of players in Game.
     * @param newPlayerName Name to check for
     * @return The existing player of Null
     * */
    private static synchronized Player existingPlayerWithSameNameInPlayersInGameMap(String newPlayerName){
        return playersInGame.get(newPlayerName);
    }


    /** Callback method, called by Listener thread. Must be synchronized.
     * @param player Temporary player Object ot be put in queue
     * */
    public static synchronized void addPlayerToQueue(Player player){
        try {
            incomingPlayersQueue.put(player);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /** Access method for the PlayerInGame HashMap: Used by VirtualView to retrieve a valid connection object.
     * @param playerName Name of the player from whom we are to take the connection.
     * */
    public synchronized static ConnectionInterface getPlayerConnectionFromPlayersInGameMap(String playerName) throws IOException{
        ConnectionInterface connection = playersInGame.get(playerName).getConnection();
        return connection;
    }


    /** Access method for the PlayerInGame HashMap: Used by VirtualView to retrieve a list of connected players.
     * @param players List of players to check the connection.
     * */
    public synchronized static List<String> getConnectedPlayersFromPlayersInGameMap(List<String> players){
        List<String> connected = new ArrayList<>();
        for(String name: players){
            try {
                    boolean isConnected = playersInGame.get(name).getConnection().isConnected();
                    if(isConnected){connected.add(name);}
            } catch (RemoteException e) {
                logger.info(e + "while obtaining connection info");
            } catch (IOException e){ /*NOP*/}
        }
        return connected;

    }


    /** Access method for the PlayerInGame HashMap: Used to set new connection for reconnection players.
     * @param playerName Name of the player whose connection we need to modify.
     * @param connection New connection to be set.
     * */
    public synchronized static void setPlayerConnectionOnPlayersInGameMap(String playerName, ConnectionInterface connection) throws RemoteException {
        playersInGame.get(playerName).setConnection(connection);
    }


    /** Access method for the PlayerInGame HashMap: Used by the controller to remove players who have finished playing.
     * @param names list of player to remove from the HashMap.
     * */
    public synchronized static void removePlayersFromPlayersInGameMap(List<String> names){
        for(String name: names){
            playersInGame.remove(name);
        }

    }


    /** Access method for the PlayerInGame HashMap: Used by the controller to add players at Game restart.
     * @param players list of players to add to the HashMap.
     * */
    private synchronized static void addPlayersToPlayersInGameMap(List<Player> players){
        if (players!=null) {
            for(Player player: players){
                playersInGame.put(player.getName(), player);
            }

        }
    }


    /**Print the Usage for command line arguments
     * */
    private static void printUsage(){
        String usage= """
                MyShelfie Server - 1.0
                
                Usage: ServerApp [arguments]
                               
                Arguments:
                                
                --ip-address <address>        Bind Socket and RMI server on interface <address>
                or --address <address>
                or        -l <address>
                
                --socket-port <port>          Bind Socket server on port <port>
                  or   --port <port>
                  or       -p <port>
                
                --enable-logging              Enable Logging from the FINE level.
                       or  --log
                          or  -g
                          
                --directory <dir_path>        Use this directory path to save uncompleted matches.
                      or -d <dir_path>          <dit_path> must adhere Windows path (C:\\Users\\user).
                                                or Unix path (/home/user). Please make sure you have rw rigths to access
                                                the chosen directory.
                          
                --help                        Print Help (this message) and exit
                or  -h
                
              """;
        System.out.println(usage);
    }


    /** Generate a unique filename based on time instant.
     * */
    private static String nameGenerator(){
        Instant instant = Instant.now();
        String sanitized = instant.toString().replaceAll(":", "-");
        if(System.getProperty("os.name").startsWith("Windows")){
            return directoryPath+"\\"+sanitized+".ser";
        }else{
            return directoryPath+"/"+sanitized+".ser";
        }

    }
}
