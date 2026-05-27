package it.polimi.ingsw.network;

import it.polimi.ingsw.exception.BadNumberOfPlayersException;
import it.polimi.ingsw.network.message.LoginMessage;
import it.polimi.ingsw.network.socket.SocketClient;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.Random;

/**JUnit Test methods for CLI class
 * @see it.polimi.ingsw.view.cli.CLI
 * @since 1.0
 * */

public class ServerStresser {

    @Test
    void stresser(){
        try (BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir")+"/src/test/java/it/polimi/ingsw/resources/ServerAppStresserInput.txt"))){
            Random ran = new Random();
            for( String line = reader.readLine(); line != null; line = reader.readLine()){
                SocketClient socketClient = new SocketClient("localhost",2308);
                socketClient.connect();
                int num = ran.nextInt(2) + 2;
                LoginMessage loginMessage = new LoginMessage(line, num);
                socketClient.sendMessageFromClientToServer(loginMessage);
            }
        } catch (IOException | BadNumberOfPlayersException e) {
            throw new RuntimeException(e);
        }
    }
}
