package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.ClientInterface;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.MoveRequestMessage;
import it.polimi.ingsw.network.message.PickMoveMessage;
import it.polimi.ingsw.network.message.PingMessage;

import java.io.*;
import java.net.*;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;

public class SocketClient implements ClientInterface{

    Logger logger = Logger.getLogger("Logger");
    private Socket socket;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private final String hostAddress;
    private final int hostPort;

    /**Simple constructor
     * */
    public SocketClient(String hostAddress, int hostPort){
        this.hostAddress = hostAddress;
        this.hostPort = hostPort;
    }

    /**
     * */
    @Override
    public void connect() throws IOException {
        logger.info("Connection to "+hostAddress+":"+hostPort);
        this.socket = new Socket(hostAddress, hostPort);
        this.objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.flush();
        this.objectInputStream = new ObjectInputStream(socket.getInputStream());
    }

    @Override
    public void sendMessageFromClientToServer(Message message) throws IOException {
            this.objectOutputStream.writeUnshared(message);
            this.objectOutputStream.flush();
            logger.info("Wrote message "+message.toString());
    }

    @Override
    public Message waitForMessageFromServer(Class<? extends Message> messageToWaitFor, int waitingTime) throws IOException, TimeoutException{

        long t= System.currentTimeMillis();
        long end = t+waitingTime* 1000L;

        String string = messageToWaitFor.getName();
        String messageName = string.substring(string.lastIndexOf('.') + 1);
        logger.info("Looking for message "+messageName);
        while(System.currentTimeMillis() < end){

            try {

                Thread.sleep(500);
                logger.info("Looking for message "+messageName+".");


                   Message message = (Message) this.objectInputStream.readUnshared();
                   logger.info("pass readObj");


                   if(message instanceof PingMessage){ continue; }

                   if(messageToWaitFor.isInstance(message)) {
                       logger.info("Found the message you are looking for");
                       logger.info(String.valueOf(message.getClass()));
                       return message;
                   }

            } catch ( ClassNotFoundException | InterruptedException | ClassCastException e) {
                logger.info("Interruped or ClassNotFoundExc or ClassCastException ");
            }
        }
        logger.info("Throwing TimeoutExc");
        throw new TimeoutException();
    }

    @Override
    public void disconnect() throws IOException{
        this.socket.close();
        this.objectOutputStream.close();
        this.objectInputStream.close();
        logger.info("Socket Connection closed.");
    }


}
