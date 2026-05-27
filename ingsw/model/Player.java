package it.polimi.ingsw.model;

import it.polimi.ingsw.network.ConnectionInterface;
import it.polimi.ingsw.network.socket.SocketConnection;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.rmi.RemoteException;

public class Player implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected final String name;
    transient private ConnectionInterface connection;
    transient private int gamePreference;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, int gamePreference, ConnectionInterface connection) {
        this.name = name;
        this.connection = connection;
        this.gamePreference = gamePreference;
    }

    public String getName() {
        return name;
    }

    public ConnectionInterface getConnection() throws IOException {
        if(connection == null){
            throw new IOException("Connection object of Player is not initialized");
        }else{return connection;}
    }

    public void setConnection(ConnectionInterface connection) throws RemoteException {
        this.connection=connection;
        this.connection.setConnected(true);
    }

    public int getGamePreference() {
        return gamePreference;
    }

    public boolean isConnected(){
        try {
            return connection.isConnected();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                '}';
    }
}
