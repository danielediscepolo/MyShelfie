package it.polimi.ingsw.network.rmi;

import it.polimi.ingsw.model.Board;
import it.polimi.ingsw.network.ServerInterface;
import it.polimi.ingsw.network.message.LoginMessage;
import it.polimi.ingsw.network.socket.SocketListener;
import it.polimi.ingsw.util.LocalFormatter;

import java.io.IOException;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;


public class RMIServer implements ServerInterface {

    static Logger logger = Logger.getLogger("Logger");

    static Registry registry;
    private final Channel channel = new Channel();
    static int port = 1099;

    public RMIServer(String hostname) throws IOException{
        logger.info("Creating a Rmi server with hostname: " + hostname);
    }

    @Override
    public void startServer()  throws IOException {
        try {
            logger.info("Creating the registry with port:" + port);
             registry = LocateRegistry.createRegistry(port);
            try {
                logger.info("Binding Channel with the registry");
                registry.bind("Channel", channel);
                logger.info("Binding done");
            } catch (AlreadyBoundException e) {
                logger.info(e + " while creating the server");
            }
        } catch (RemoteException e) {
            logger.warning(e + " while creating Rmi server");

        }
    }

    public static void createChannelForPlayer(String nickname){

        try {
            logger.info("Binding " + nickname + " channel in the registry");
            PlayerChannel playerChannel = new PlayerChannel(nickname);
            registry.bind(nickname,playerChannel);
            logger.info("Binding done");
        } catch (RemoteException | AlreadyBoundException e) {
            logger.warning(e + " while creating Rmi server");
        }

    }


}
