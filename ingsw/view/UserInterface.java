package it.polimi.ingsw.view;

import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.network.message.ChatMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.view.cli.CLI;
import jdk.jshell.spi.ExecutionControl;

import java.util.List;

/**Interface used to abstract the concept of User Interface.<br />
 * Useful for the implementation of distinct user interface modes and for the capability to switch between them.
 * @see CLI
 * //@see GUI
 * @since 1.0
 * */

public interface UserInterface {

    /**
     * Initialize the specific user interface.<br />
     * Network independent, client specific functionality.
     *
     * @return
     */
    int startInterface();


    /**Display a message of informative type.<br />
     * Used in the following example contexts:<ul>
     * <li>Inform the user of non-existent games after try-loading.</li>
     * <li>Inform the user of a found game.</li>
     * <li>Inform the user that a new player entered the game.</li>
     * <li>Inform the user that is his/her turn to play.</li></ul>
     * @param output the string to be displayed as informative message.*/
    void infoMessage(String output);


    /**Alert the user with an error message.<br />
     * @param output the string to be displayed as error message.*/
    void errorMessage(String output);


    /**Yellow Message for debugging purposes.
     *
     * */
    public void debugMessage(String output);



    /**Display an Easter egg.<br />
     * @param type integer representing the type of Easter egg to be displayed.*/
    void easterEgg(int type) throws ExecutionControl.NotImplementedException;


    /**Gracefully exit from the user interface.<br />
     * @param status exit status.*/
    void exitInterface(int status);


    /**Display the Opening Interface with welcoming message.<br />
     * The interface must ask the user for the user's name and the game's number of players.<br />
     * @return an array with 2 elements:<br />
     * 1) Name of the player (String).<br />
     * 2) Number of players for the game (int).*/
    Object[] welcome();


    /*TODO - Option1: Network bound: there is maximum waiting time, if a game is found the timer is stopped and the game starts, else ask the user to retry or create a new game.
    *        Option2: Network independent: the waiting time is fixed, if no game is found we pass to the createGame screen*/
    /**Display a waiting room for few seconds<br />
     * @param message Message to Show
     * @param waitingTime Time in seconds to wait
     * */
    void waitingRoom(String message,long waitingTime);


    /**Display the appropriate user interface for creating a game.<br />
     * The interface must ask the user for Game's name and num of players.
     * @return String Name of the game .<br />*/
    String createGame();


    /*TODO - Syncable Objects?  */
    /**Start the game interface.<br />
     * The interface must ask the user for Game's name and num of players.
     * @param game an array with all the information to start a game:<br />*/
    void startGame(Object[] game);


    /**
     * Display the appropriate user interface for picking Tiles.<br />
     *
     * @return List of Position of the tiles to be picked from board.
     */
    List<Position> pickTilesFromBoard();


    /**
     * Display the appropriate user interface for sorting Tiles.<br />
     *
     * @param tiles List of Tiles to be ordered.
     * @return List of ordered Tiles.
     * */
    List<Tile> sortTilesToInsert(List<Tile> tiles);


    /**Display the appropriate user interface for choosing column.<br />
     * @return the column number */
    int chooseColumn();


    Boolean confirmChoice(String output);


    /*TODO - Consider creating a Message Class*/
    /**
     * Ask the user for a message to be sent.<br />
     *
     * @return array containing:<br />
     * 1) Author of the message.<br />
     * 2) Recipient of the message.<br />
     * 3) Body of the message.<br />
     * 4) Metadata.<br />
     */
    Object[] writeMessage();


    /**Write the .<br />
     * @param messages arrays of messages each one containing:<br />
     *                1) Author of the message.<br />
     *                2) Recipient of the message.<br />
     *                3) Body of the message.<br />
     *                4) Metadata.<br />*/
    void readMessage(ChatMessage[] messages);


    /**
     * Display the appropriate user interface for the end game
     *
     * @param winner string containing name of the winner
     * @param numOfPoints string containing total points of the winner
     */
    void closeGame(String winner, int numOfPoints);

    /**
     * GUI exclusive method.
     * @param model modelView of the game.
     */
    void updateModel(GameView model, String nickname);

    /**Setter to set internal waiting room interrupt attribute.</br>
     * Used to signal the waiting room method to interrupt.
     * */
    void setWaitingRoomInterrupt(boolean interrupt);

     void endgame (String winner);
}
