package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.model.Position;
import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.network.message.LoginMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.NameAvailabilityMessage;
import it.polimi.ingsw.network.message.PickMoveMessage;
import it.polimi.ingsw.util.LocalFormatter;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.concurrent.TimeoutException;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

public class  RMIClient implements ClientInterface {

    Logger logger = Logger.getLogger("Logger");
    private Registry registry;
    private Channel_Interface channel;
    private Channel_Interface playerChannel;


    static int port = 1099;
    private final String hostname;

    private String playerNickname = null;

    private boolean nicknameNotAvailable;




    public RMIClient(String hostname) {
        this.hostname = hostname;
        logger.info("Creating a Rmi client with hostname:" + hostname);
    }

    @Override
    public void connect() throws RuntimeException {
        try {
            logger.info("Getting the registry created by the RmiServer");
            registry = LocateRegistry.getRegistry(hostname, port);
            try {
                logger.info("Obtaining the communication channel from the registry");
               channel = (Channel_Interface) (registry.lookup("Channel"));
            }catch (ClassCastException e ){
            logger.warning(e + " occurred while searching for the channel");
            }
        } catch (RemoteException | NotBoundException e) {
            logger.warning(e + " occurred while searching for the registry");
        }
    }


    @Override
    public void sendMessageFromClientToServer(Message message) throws IOException {
        try {
            //noinspection RedundantClassCall
            if (LoginMessage.class.isInstance(message)) {
                LoginMessage loginMessage = (LoginMessage) message;
                playerNickname = loginMessage.getName();
                if (channel.checkNameAvailability(playerNickname)) {
                    nicknameNotAvailable = false;
                    logger.info("Creating " + playerNickname + " channel");
                    channel.sendLoginMessageToChannel(message,playerNickname);

                    playerChannel = (Channel_Interface) (registry.lookup(playerNickname));
                    logger.info("Created  " + playerNickname + " channel");
                    playerChannel.sendLoginMessageToPlayerChannel(message,playerNickname);
                    logger.info("Sent " + loginMessage + " to " + playerNickname + " channel");
                } else if(checkReconnections()) {
                    playerChannel = (Channel_Interface) (registry.lookup(playerNickname));
                    logger.info("Created  " + playerNickname + " channel");
                    playerChannel.sendLoginMessageToPlayerChannel(message,playerNickname);
                    logger.info("Sent " + loginMessage + " to " + playerNickname + " channel");
                }else  {
                    setPlayerNickname();
                    logger.info("Name not available. Resetting nickname attribute.");
                    nicknameNotAvailable = true;
                }
            } else {
                playerChannel.depositMessageFromClientToServerQueueOfMessagesOfPlayer(message, playerNickname);
                logger.info("Sending " + message + " through the channel");
            }
        } catch (RemoteException | NotBoundException e) {
            logger.info(e + " occurred while sending " + message);
        }
    }


    @Override
    public Message waitForMessageFromServer(Class<? extends Message> messageToWaitFor, int timeout) throws TimeoutException, IOException {


        long t= System.currentTimeMillis();
        long end = t+timeout* 1000L;

        logger.info("Looking for message " + messageToWaitFor.toString());

            if (nicknameNotAvailable){
                NameAvailabilityMessage nameAvailabilityMessage = new NameAvailabilityMessage(false);
                logger.info("Sending nameNotAvailableMessage");
                return nameAvailabilityMessage;
            }


        while(System.currentTimeMillis() < end) {


            try {

                Message message = (Message) playerChannel.takeMessageFromServerToClientQueueOfMessagesOfPlayer(playerNickname);



                if (message == null){

                    logger.info("discarding message");

                }else if (messageToWaitFor.isInstance(message)) {
                    logger.info(message.toString() + " message taken");
                    logger.info("Found the message you are looking for");
                    logger.info(String.valueOf(message.getClass()));
                    return message;
                }

            } catch (IOException exc) {
                logger.warning(exc.toString());
                throw new RuntimeException();
            }

        }
        logger.info("Throwing TimeoutExc");
        throw new TimeoutException();
    }

    @Override
    public void disconnect() {
        logger.info("Rmi connection cannot be close. RMI's TCP connections are managed invisibly under the hood" +
                "The connection between an RMI client and server is implicit and it\n" +
                "closes itself automatically via a short idle timeout.");
    }

    public void setPlayerNickname() {
        this.playerNickname = null;
    }


    private boolean checkReconnections(){
        boolean flag = true;
        try {
            playerChannel = (Channel_Interface) (registry.lookup(playerNickname));
            if (playerChannel.isConnected()){
                flag = false;
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        } catch (NotBoundException e) {
            throw new RuntimeException(e);
        }
    return flag;
    }
}
