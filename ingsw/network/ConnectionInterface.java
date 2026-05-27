package it.polimi.ingsw.network;

import it.polimi.ingsw.exception.PassedMoveException;
import it.polimi.ingsw.network.message.Message;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.concurrent.TimeoutException;

public interface ConnectionInterface {

    void setConnected(boolean connected) throws RemoteException;

    boolean isConnected() throws RemoteException;

    /**Given a Message object, it pass it through the network.<br/>
     * @param message obj Message.
     * @throws IOException on writing error. Useful for controller to catch.
     * */
    void sendMessageFromServerToClient(Message message, String playerName) throws IOException, RemoteException;

    /**Blocking method waiting for new Messages.</br>
     * @param messageToWaitFor Class<?> type. It should be a class of the Message group.<br/>
     * @return the first message of the specified type.
     * @throws TimeoutException if the timeout is exceeded.
     * @throws PassedMoveException if the method receive a MoveResponseMessage with passed turn in it.
     * @throws IOException for all connection problems.
     * */
    Object waitForMessageFromClient(Class<? extends Message> messageToWaitFor, int timeout, String playerName) throws TimeoutException, IOException, RemoteException;

    /**Utility method to gracefully disconnect a client.<br/>
     * @throws IOException
     * */
    void disconnect() throws IOException, RemoteException;

}
