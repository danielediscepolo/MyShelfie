package it.polimi.ingsw.network;

import java.io.IOException;

public interface ServerInterface {

    /**Starter for listening thread.
     * @throws IOException to handle failed connections.
     * */
    void startServer() throws IOException;

}
