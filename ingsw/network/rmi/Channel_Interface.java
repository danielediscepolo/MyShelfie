package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.message.Message;

import java.io.IOException;
import java.rmi.Remote;
import java.rmi.RemoteException;


/**
 *  Common interface to the client and server extending Remote, and implementing ConnectionInterface, used to
 *  define remote access methods from the client to the server.
 */

public interface Channel_Interface extends Remote, ConnectionInterface {


    /**
     * Used server-side. Takes, via the poll() method, the first message from the concurrent queue of messages
     * that have been deposited from the client to the server.
     *
     * @param nickname used to be printed as log info.
     * @return the first message (FIFO logic) of the client -> server concurrent queue.
     * @throws RemoteException
     */
     Object takeMessageFromClientToServerQueueOfMessagesOfPlayer(String nickname) throws RemoteException;

    /**
     * Used client-side. Takes, via the poll() method, the first message from the concurrent queue of messages
     * that have been deposited from the server to the client.
     *
     * @param nickname used to be printed as log info.
     * @return the first message (FIFO logic) of the server -> client concurrent queue.
     * @throws RemoteException
     */
     Object takeMessageFromServerToClientQueueOfMessagesOfPlayer(String nickname) throws RemoteException;

    /**
     * Used by the client. Deposits a message in the client -> server concurrent queue.
     *
     * @param message to deposit.
     * @param nickname used to be printed as log info.
     * @throws IOException
     * @throws RemoteException
     */
     void depositMessageFromClientToServerQueueOfMessagesOfPlayer(Message message, String nickname) throws IOException, RemoteException;

    /**
     * Used by the Rmi server to register a new user in the registry. The method calls another ServerApp method that
     * creates a new specific channel (a playerChannel) with the player's nickname.
     *
     * @param message LoginMessage containing the information required to create a new player.
     * @param nickname used to be added to the list of channel names already created.
     * @throws RemoteException
     */
    void sendLoginMessageToChannel(Message message, String nickname) throws RemoteException;

    /**
     * Method which is invoked after its counterpart on channel. After channel creates a specific channel for the player,
     *  this channel takes care of registering the player on the competing serverApp map.
     * @param message LoginMessage containing the information required to create a new player.
     *
     * @param nickname
     * @throws RemoteException
     */
    void sendLoginMessageToPlayerChannel(Message message, String nickname) throws RemoteException;

    /**
     * It checks whether the player's name has already been used for the creation of a playerChannel on the registry.
     *
     * @param nickname to search within the concurrent list of name
     * @return a Boolean, true if the name is not present, false otherwise.
     * @throws RemoteException
     */
     boolean checkNameAvailability(String nickname) throws RemoteException;


    /**
     * Method used to know the actual status of the player during the game. Useful for logouts and logins, it is
     * implemented by sending ping messages every 5 seconds, which in the event of a missed pong message
     * throws an exception notifying Patrick that the client has logged out.
     * @return un booleano, true se è connesso, falso altrimenti.
     *
     * @throws RemoteException
     */
    public boolean isConnected() throws RemoteException;

}


