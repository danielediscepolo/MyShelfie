package it.polimi.ingsw.network.socket;

import it.polimi.ingsw.ServerApp;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.network.message.LoginMessage;
import it.polimi.ingsw.network.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.logging.Logger;

// https://stackoverflow.com/questions/20289772/checking-if-an-object-from-objectinputstream-is-a-particular-type
//https://stackoverflow.com/questions/4294844/check-if-an-object-belongs-to-a-class-in-java
//https://stackoverflow.com/questions/5734720/test-if-object-is-instanceof-a-parameter-type
// https://stackoverflow.com/questions/2572348/java-casting-base-class-to-derived-class
// https://stackoverflow.com/questions/8422842/in-java-syntax-class-extends-something


public class SocketListener extends Thread{
    private final Socket socket;
    private final Class<? extends Message> requestedMessageType;
    static Logger logger = Logger.getLogger("Logger");
    long timeout;
    private ObjectOutputStream objectOutputStream;
    private ObjectInputStream objectInputStream;

    public SocketListener(Socket socket, Class<? extends Message> requestedMessageType){
        this.requestedMessageType = requestedMessageType;
        this.socket = socket;


    }

    //TODO ADD TIMEOUT
    @Override
    public void run() {
        logger.info("Socket Listener for "+socket.getRemoteSocketAddress().toString()+": Running");
        //long t= System.currentTimeMillis();
        //long end = t+timeout*1000;

        //TODO MOVE THIS
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.flush();
            objectInputStream  = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while (true) {
            try {

                Message incomingMessage = (Message) objectInputStream.readObject();
                objectOutputStream.flush();

                logger.info("Socket Listener for "+socket.getRemoteSocketAddress().toString()+": Running");

                if(this.requestedMessageType.isInstance(incomingMessage) ) {

                    if (LoginMessage.class.isInstance(incomingMessage)) {
                        LoginMessage loginMessage = (LoginMessage) incomingMessage;
                        SocketConnection connection = new SocketConnection(socket, objectInputStream, objectOutputStream, true);
                        Player newLoginPlayer = new Player(loginMessage.getName(),loginMessage.getWaitingRoomPreference(), connection);
                        ServerApp.addPlayerToQueue(newLoginPlayer);

                        logger.info("Socket Listener for "+socket.getRemoteSocketAddress().toString()+": Interrupting");
                        this.interrupt();
                        return;
                    }
                }else {
                    logger.info("Wrong Message, discarding");
                }
            } catch (IOException | ClassNotFoundException e) {
                //throw new RuntimeException(e);
            }
        }

    }

}
