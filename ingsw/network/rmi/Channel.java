package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.message.LoginMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.util.LocalFormatter;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;


/** Wrapper class for RMI Networking.</br>
 * Remote object, accessible from the client-side, used as a logical channel for communication.
 * This is the single channel into which all communication from all users connecting to the server for the first time
 * is routed. The channel is a fixed registry entry created by the RmiServer. It is used to handle login
 * requests by creating a specific channel (a playerChannel) as a registry entry with the player's nickname
 * if it has not already been done.
 * */

public  class Channel extends UnicastRemoteObject  implements Channel_Interface {

    static Logger logger = Logger.getLogger("Logger");

    public  final List<String> notAvailableNames = new ArrayList<>();

    private boolean isConnected;


    Channel() throws RemoteException {
        logger.info("Channel created and working");

    }

    @Override
    public void setConnected(boolean connected) throws RemoteException {
        logger.warning("The software has reached a non possible situation. PlayerChannel should use this method");
    }

    @Override
    public boolean isConnected() throws RemoteException {
        logger.warning("The software has reached a non possible situation. PlayerChannel should use this method");
        return false;
    }

    @Override
    public void sendMessageFromServerToClient(Message message, String nickname) throws IOException, RemoteException {
        logger.warning("The software has reached a non possible situation. PlayerChannel should use this method");
    }

    @Override
    public Object waitForMessageFromClient(Class<? extends Message> messageToWaitFor, int timeout, String nickname) throws TimeoutException, IOException, RemoteException {
        logger.warning("The software has reached a non possible situation. PlayerChannel should use this method");
        return null;
    }


    @Override
    public void disconnect() throws IOException, RemoteException {
        logger.warning("The software has reached a non possible situation. PlayerChannel should use this method");

    }


    public synchronized void sendLoginMessageToChannel(Message message, String nickname) throws RemoteException {
        //noinspection RedundantClassCall
        if (LoginMessage.class.isInstance(message)) {
                notAvailableNames.add(nickname);
            System.out.println(notAvailableNames);
            RMIServer.createChannelForPlayer(nickname);
        }
    }

    @Override
    public void sendLoginMessageToPlayerChannel(Message message, String nickname) throws RemoteException {
        logger.warning("The software has reached a non possible situation. PlayerChannel should use this method");
    }


    @Override
    public Object takeMessageFromClientToServerQueueOfMessagesOfPlayer(String nickname) throws RemoteException {
        logger.warning("The software has reached a non possible situation. PlayerChannel should use this method");
        return null;
    }


    public synchronized boolean checkNameAvailability(String nickname) throws RemoteException {
        return !notAvailableNames.contains(nickname);
    }

    public String getNickname() throws RemoteException{
        logger.warning("The software has reached a non possible situation. PlayerChannel should use this method");
        return null;
    }
   public void depositMessageFromClientToServerQueueOfMessagesOfPlayer(Message message, String nickname) throws IOException, RemoteException{
       logger.warning("The software has reached a non possible situation. PlayerChannel should use this method");
   }

    public Object takeMessageFromServerToClientQueueOfMessagesOfPlayer(String nickname) throws RemoteException{
        logger.warning("The software has reached a non possible situation. PlayerChannel should use this method");
        return null;
    }


}
