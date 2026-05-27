package it.polimi.ingsw;

import it.polimi.ingsw.controller.Patrick;
import it.polimi.ingsw.model.Player;
import it.polimi.ingsw.util.NullPrintStream;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import it.polimi.ingsw.view.VirtualView;
import org.junit.jupiter.api.Test;

import it.polimi.ingsw.view.cli.*;

public class OfflineAppTest {

    @Test
    public void testingMain() {

        VirtualView view = new VirtualView();
        CLI cli = (CLI) view.getLocalModeUI();
        cli.setActiveDebug(true);

        Logger logger = Logger.getLogger("Logger");
        logger.setLevel(Level.FINE);

        for(int i = 0; i<10; i++){


            try (InputStream inputStream = new FileInputStream(System.getProperty("user.dir")+"/src/test/java/it/polimi/ingsw/resources/offlineAppTestInput2Players.txt")){
                cli.setOutputStream(new NullPrintStream());
                cli.setInputStream(inputStream);
                List<String> names = cli.offlineIntroduceYourself();

                List<Player> players = new ArrayList<>();
                for (String name : names) {
                    players.add(new Player(name));
                }
                int numOfPlayers = players.size();
                if (numOfPlayers > 4) {
                    logger.warning("Wrong num of players");
                    System.exit(-1);
                }
                logger.info("Cicle number"+(i+1));
                Patrick patrick = new Patrick(players, "OfflineGame");
                patrick.run();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }catch (NullPointerException ex){
                logger.info("Cicle number"+(i+1));
            }
        }
    }
}

