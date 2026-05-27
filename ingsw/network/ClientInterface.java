package it.polimi.ingsw.network;


import it.polimi.ingsw.ServerApp;
import it.polimi.ingsw.network.message.Message;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/** Interface for Strategy pattern. It acts as a separator for the network layer.<br/>
 * Implementing classes such as RMI Client and Socket Client should provide the following capabilities: <br/>
 * 1. A first part provides a way to listen for incoming Login messages and update the ServerApp's queue of incoming login requests.<br/>
 *  - Start a thread listening for incoming connections. <br/>
 *  - For each successful connection a new Thread should listen for incoming login messages and update the central queue. <br/>
 *  - The update process should use a static method in the ServerApp.<br/>
 *
 * 2. A second part should provide a blocking way for Controller and Client to wait for messages (wait for message method) and to send messages.<br/>
 *
 * @see ServerApp
 * @see it.polimi.ingsw.network.socket.SocketClient
 * @see it.polimi.ingsw.network.rmi.RMIClient
 * */
public interface ClientInterface{

    void connect() throws RuntimeException, IOException;

    void sendMessageFromClientToServer(Message message) throws IOException;

    Object waitForMessageFromServer(Class<? extends Message> messageToWaitFor, int timeout) throws TimeoutException, IOException;

    void disconnect() throws IOException;

}
