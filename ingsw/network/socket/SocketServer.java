package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.ServerApp;
import it.polimi.ingsw.network.ServerInterface;
import it.polimi.ingsw.network.message.LoginMessage;
import it.polimi.ingsw.network.rmi.RMIServer;

import java.io.*;
import java.net.*;
import java.util.logging.Logger;

/** Interface for Strategy pattern. It acts as a separator for the network layer.<br/>
 * Implementing classes such as RMI Server and Socket Server should provide the following capabilities: <br/>
 * 1. A first part provides a way to listen for incoming Login messages and update the ServerApp's queue of incoming login requests.<br/>
 *  - Start a thread listening for incoming connections. <br/>
 *  - For each successful connection a new Thread should listen for incoming login messages and update the central queue. <br/>
 *  - The update process should use a static method in the ServerApp.<br/>
 *
 * 2. A second part should provide a blocking way for Controller and Client to wait for messages (wait for message method) and to send messages.<br/>
 *
 * @see ServerApp
 * @see SocketServer
 * @see RMIServer
 * */

public class SocketServer implements ServerInterface, Runnable {

    static Logger logger = Logger.getLogger("Logger");
    ServerSocket server;


    public SocketServer(String addr, int port) throws IOException{
        logger.info("Socket server binding on "+addr+":"+port);
        this.server = new ServerSocket();
        server.bind(new InetSocketAddress(addr,port));
    }


    @Override
    public void startServer() {

        logger.info("Starting socket thread for accepting incoming connection.");
        Thread thread = new Thread(this);
        thread.start();
    }


    @Override
    public void run(){
        //noinspection InfiniteLoopStatement
        while (true) {
            try {
                server.setSoTimeout(60000);
                Socket connection = server.accept();
                logger.info("Accepted connection from "+connection.getRemoteSocketAddress().toString()+". Starting dedicated listening thread");
                Thread task = new SocketListener(connection, LoginMessage.class);
                task.start();
            } catch (SocketTimeoutException e) { logger.info("Caught SocketTimeoutException. The Socket server is Listening.");
            } catch (IOException ex) { logger.warning("Caught IOException. An error occurred while establishing a new connection.");
            }
        }
    }

}


