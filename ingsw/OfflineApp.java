package it.polimi.ingsw;

import it.polimi.ingsw.controller.Patrick;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.view.cli.CLI;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OfflineApp {

    public static void main(String[] args){
 
        Logger logger = Logger.getLogger("Logger");
        logger.setLevel(Level.FINE);

        CLI cli = new CLI();

        //List<String> names = cli.offlineIntroduceYourself();
        String[] names = new String[]{"Buba", "Paolo", "Don", "Matteo"};

        List<Player> players = new ArrayList<>();
        for(String name: names){
            players.add(new Player(name));
        }
        int numOfPlayers = players.size();
        if(numOfPlayers>4){System.err.println("Wrong num of players");System.exit(-1);}


        Patrick patrick = new Patrick(players, "OfflineGame");
        patrick.start();

    }
}
