package it.polimi.ingsw.client;

import it.polimi.ingsw.view.cli.CLI;
import jdk.jshell.spi.ExecutionControl;

public class RunCli{
    public static void main(String[] args) {
        CLI cli = new CLI();

        //cli.writeMessage();

        //cli.welcome();

        //cli.infoMessage("This is a test");

        //cli.errorMessage("   ");

        /*try {
            cli.easterEgg(0);
        } catch (ExecutionControl.NotImplementedException e) {
            throw new RuntimeException(e);
        }*/

        //cli.exitInterface(0);

        //cli.welcome();

        //cli.interactiveMainMenu();

        //cli.waitingRoom("Looking for a Game...",5);

        //cli.createGame();*/

        //cli.startGame();

        cli.pickTilesFromBoard();

        //cli.sortTilesToInsert();

        //cli.chooseColumn ();

        //cli.showCommonGoalCards();

        //cli.showPlayerStatus();

        //cli.showPersonalGoalCard();

        //cli.showEndgameToken();

        //cli.SendMessage();

        //cli.receiveMessage();

        //cli.closeGame();

    }
}
