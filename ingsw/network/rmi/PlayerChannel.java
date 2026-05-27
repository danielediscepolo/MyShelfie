package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.ServerApp;
import it.polimi.ingsw.model.Player;

import it.polimi.ingsw.network.message.LoginMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.PingMessage;


import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Specific channel, which implements Channel_Interface, created by the RmiServer at the request of the Channel
 * (unique for all clients and the Server). And specific for the player using the same channel even in the event
 * of disconnection or restart in the event of persistence. And at the same time used by both server and client in
 * a very similar way. It has two concurrent message queues (one from client to server and one from server to client)
 * in which messages are deposited according to the user and taken with FIFO logic. The player channel then takes
 * care of the two-sided communication management by acting as a shared message relay and incorporating the same
 * (or nearly the same) logic for both sides.
 */
public class PlayerChannel extends Channel implements Channel_Interface {


    private String nickname;

    private final BlockingQueue<Message> fromServerToClientQueueOfPlayerMessages = new ArrayBlockingQueue<>(100);
    private final BlockingQueue<Message> fromClientToServerQueueOfPlayerMessages = new ArrayBlockingQueue<>(100);

    private boolean isConnected;


    PlayerChannel(String nickname) throws RemoteException {
        logger.info(nickname + " channel created");
    }

    @Override
    public void setConnected(boolean connected) throws RemoteException {
        this.isConnected = connected;
    }

    @Override
    public boolean isConnected() throws RemoteException {

        if(isConnected){
            try {
                this.sendMessageFromServerToClient(new PingMessage(),nickname);
                logger.info("PingMessage Sent");
                try {
                    PingMessage pingMessage = (PingMessage) waitForMessageFromClient(PingMessage.class,5000,nickname);
                    logger.warning("Ping message received !");
                    this.isConnected=true;
                } catch (TimeoutException e) {
                    this.isConnected = false;
                    logger.info("Ping Failure");
                }
            } catch (IOException e) {
                this.isConnected=false;
                logger.info("Ping Failure");
            }
        }
        return isConnected;
    }


    @Override
    public void sendLoginMessageToPlayerChannel(Message message, String nickname) throws RemoteException {
        //noinspection RedundantClassCall
        if (LoginMessage.class.isInstance(message)) {
            LoginMessage loginMessage = (LoginMessage) message;
            this.nickname = loginMessage.getName();
            Player newPlayer = new Player(loginMessage.getName(), loginMessage.getWaitingRoomPreference(), this);
            ServerApp.addPlayerToQueue(newPlayer);
            logger.info(nickname + " added to ServerApp concurrentMap");
        }
    }


    public void sendMessageFromServerToClient(Message message, String nickname) throws IOException, RemoteException {


        if (message != null) {
            logger.info(message + " added to " + nickname + " from Server to Client queue of messages");
            try {
                fromServerToClientQueueOfPlayerMessages.put(message);
            } catch (InterruptedException e) {
                logger.warning(e + " while sending " + message);
            }
            for (Message m : fromServerToClientQueueOfPlayerMessages) {
                logger.info("Message " + m + " of " + nickname);
            }
        } else {
            logger.warning("Invalid message sent to the channel. Discarding...");
        }

    }


    public void depositMessageFromClientToServerQueueOfMessagesOfPlayer(Message message, String nickname) throws IOException, RemoteException {

        if (message != null) {
            logger.info(message + " added to " + nickname + " from Client to Server queue of messages");
            try {
                fromClientToServerQueueOfPlayerMessages.put(message);
            } catch (InterruptedException e) {
                logger.warning(e + " while sending " + message);
            }
            for (Message m : fromClientToServerQueueOfPlayerMessages) {
                logger.info("Message " + m + " of " + nickname);
            }
        } else {
            logger.warning("Invalid message sent to the channel. Discarding...");
        }
    }

    public Object takeMessageFromClientToServerQueueOfMessagesOfPlayer(String nickname) throws RemoteException {

        Message message;

        logger.info(" Taking message from " + nickname + " from Client to Server queue of messages");
        try {
            message = fromClientToServerQueueOfPlayerMessages.poll(10000, TimeUnit.MILLISECONDS);


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        logger.info(message + " taken from " + nickname + " from Client to Server queue of messages");

        return message;


    }


    public Object takeMessageFromServerToClientQueueOfMessagesOfPlayer(String nickname) throws RemoteException {

        Message message = null;

        logger.info(" Taking message from " + nickname + " from Server to Client queue of messages");
        try {
            message = fromServerToClientQueueOfPlayerMessages.poll(10000, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        logger.info(message + " taken from " + nickname + " from Server to Client queue of messages");

        return message;


    }

    public Object waitForMessageFromClient(Class<? extends Message> messageToWaitFor, int timeout, String nickname) throws RemoteException, TimeoutException {
        String string = messageToWaitFor.getName();

        long t = System.currentTimeMillis();
        long end = t + timeout + 20000;

        String messageName = string.substring(string.lastIndexOf('.') + 1);
        logger.info("Looking for message " + messageName);
        while(System.currentTimeMillis() < end) {
        try {

            Message message =  (Message) takeMessageFromClientToServerQueueOfMessagesOfPlayer(nickname);

            if (message == null){

                logger.info("discarding message");

            }else if (messageToWaitFor.isInstance(message)) {
                logger.info("Found the message you are looking for");
                logger.info(String.valueOf(message.getClass()));
                return message;
            } else {
                return null;
            }

        } catch (IOException exc) {
            logger.warning(exc.toString());
            throw new RuntimeException();
        }
    }
        logger.warning("Throwing TimeoutExc");
        throw new TimeoutException();

    }


    public void disconnect() throws IOException, RemoteException {
        logger.info("Rmi connection cannot be close. RMI's TCP connections are managed invisibly under the hood" +
                "The connection between an RMI client and server is implicit and it\n" +
                "closes itself automatically via a short idle timeout.");
    }


}


