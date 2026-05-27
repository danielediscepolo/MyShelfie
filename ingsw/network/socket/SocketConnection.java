package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.PingMessage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;
import java.util.logging.Logger;


public class SocketConnection implements ConnectionInterface {

    static Logger logger = Logger.getLogger("Logger");
    private Socket connection;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;
    private boolean isConnected;

    public SocketConnection(Socket connection, ObjectInputStream objectInputStream, ObjectOutputStream objectOutputStream, boolean isConnected) {
        this.connection = connection;
        this.objectOutputStream = objectOutputStream;
        this.objectInputStream = objectInputStream;
        this.isConnected = isConnected;
    }

    public SocketConnection(){
        this.isConnected = false;
    }

    @Override
    public void sendMessageFromServerToClient(Message message, String playerName) throws IOException {
            String string = message.toString();
            this.objectOutputStream.reset();
            this.objectOutputStream.writeUnshared(message);
            this.objectOutputStream.flush();
            logger.info("Wrote message "+string);
    }

    @Override
    public Message waitForMessageFromClient(Class<? extends Message> messageToWaitFor, int timeout, String playerName) throws IOException, TimeoutException {
        Message message;
        try {
            this.connection.setSoTimeout(timeout*1000);
            Object message1 = this.objectInputStream.readObject();

            System.out.println(message1);

            message = (Message) message1;
            logger.info("Read message:"+message.toString());

        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SocketTimeoutException e){
            throw new TimeoutException(e.getMessage());
        }
        return message;
    }

    @Override
    public void disconnect() throws IOException{
        this.connection.close();
        this.objectOutputStream.close();
        this.objectInputStream.close();
        this.isConnected = false;
        logger.info("Socket Connection closed.");
    }

    @Override
    public void setConnected(boolean connected) {
        this.isConnected = connected;
    }

    @Override
    public boolean isConnected() {
        if(isConnected){
            try {
                this.sendMessageFromServerToClient(new PingMessage(),"placeHolderName");
                logger.info("PingMessage Sent");
                this.isConnected=true;
            } catch (IOException e) {
                this.isConnected=false;
                logger.info("Ping Failure");
            }
        }
        return isConnected;
    }

}
