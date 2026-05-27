package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.controller.*;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.network.message.ChatMessage;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.UpdateGameMessage;
import it.polimi.ingsw.util.LocalFormatter;
import it.polimi.ingsw.view.UserInterface;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import jdk.jshell.spi.ExecutionControl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.ConsoleHandler;
import java.util.logging.Logger;

/**
 * The gui is implemented, again to maintain the strategy pattern, in a manner similar to the cli with a few differences.
 * Each method that is invoked loads a different scene, which depending on whether it expects client input,
 * returns a value or not, encapsulating the logic and controls on the player's action within each individual scene,
 * making each scene atomic and independent of each other, being connected only through individual invocations of
 * clientApps. To do this, we implemented a CallbackRunner class in order not to conflict with the JavaFx thread owner
 * by scheduling through lamba functions each scene and function of the game.
 */
public class GUI extends Application implements UserInterface {

    private Boolean sync;

    static Logger logger;

    static {
        Logger mainLogger = Logger.getLogger("Logger");
        mainLogger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new LocalFormatter());
        mainLogger.addHandler(handler);
        logger = Logger.getLogger("Logger");
    }

    private Stage primaryStage;
    private Scene waitingRoom;

    private Scene startingScene;

    private GameView gameView;

    private List<CommonGoalCard> commonGoalCards;

    private PersonalGoalCard personalGoalCard;

    private List<PlayerInGame> players;

    private String nicknamePlayer;

    private Bookshelf bookshelf;

    private Board board;

    private EndGameToken endGameToken;

    private static String[] args;

    private boolean b;

    private List<Tile> orderedTiles = new ArrayList<>();
    public static GUI gui;

    public GUI() {
    }

    /**
     * Start-up method derived from Application.
     * It creates the initial stage of the project by loading an initial scene without a controller, showing only a
     * quick screen of the game. It also handles the logic of closing the stage in case the user clicks to close the
     * JavaFx pop-up.
     */
    @Override
    public void start(Stage stage) {
        try {
            this.primaryStage = stage;
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/StartingScene.fxml")));
            Parent root = loader.load();
            this.primaryStage.setTitle("MyShelfie board game");
            this.primaryStage.setScene(new Scene(root));
            this.primaryStage.setResizable(false);

            primaryStage.setOnCloseRequest(event -> {
                event.consume();
                boolean userChoice = confirmChoice("Do you want to disconnect from the game?");

                if (userChoice){

                    exitInterface(-1);
                }
            });

            this.primaryStage.show();
            gui = this;
        } catch (IOException exception) {
            logger.info("it is not possible to load /StartingScene.fxml");
            System.exit(1);
        }
    }


    /**
     * Method that launches the Gui.
     *
     * @param args parameters from configuration file.
     */
    public static void main(String[] args) {
        GUI.args = args;
        launch(args);
    }


    /**
     * Used by ClientApp to trigger the Gui.
     *
     * @return an integer to ensure that the interface has started.
     */
    @Override
    public int startInterface() {

        logger.info("Starting GUI interface");
        logger.info("GUI interface started");
        return 1;
    }

    /**
     * Method used to take information of nickname and number of players the player would prefer to play with.
     *
     * @return Object[] where in the first position is the string with the nickname selected by the user and in the
     * second position the int with the number of players.
     */
    @Override
    public Object[] welcome() {

        WelcomeController welcomeController = CallbackRunner.runAndWait(() -> {
            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Welcome.fxml")));

            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                this.primaryStage.setScene(scene);
                this.primaryStage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return loader.getController();
        });
            Object[] loginMessage;
            try {
                loginMessage = welcomeController.welcome();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            return loginMessage;

    }


    /**
     * Method that loads the waiting screen for entry into a game. The controller associated with the scene
     * only handles the rotation of a scene element.
     *
     * @param message Message to Show not used here in GUI
     * @param waitingTime Time in seconds to wait not used as the next scene (the update) appears at the request of
     *                    ClientApp.
     */
    @Override
    public void waitingRoom(String message, long waitingTime) {


        WaitingRoomController waitingRoomController = CallbackRunner.runAndWait(() -> {

            FXMLLoader loader = new FXMLLoader((getClass().getResource("/WaitingRoom.fxml")));

            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                this.primaryStage.setScene(scene);
                WaitingRoomController waitingRoomController1 = loader.getController();
                waitingRoomController1.rotation();
                this.primaryStage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return loader.getController();
        });

    }


    /**
     * A method that loads the scene used to take in the player's choices regarding the drawing of tiles from the board.
     * The controller associated with the scene takes care of understanding if the move is a pass (i.e. a list of
     * positions of size 0) or if the client choice is possible by assisting the player with graphical elements such
     * as the lighting of various elements and messages on the screen.
     *
     * @return a list of positions chosen by the client.
     */
    @Override
    public List<Position> pickTilesFromBoard() {
        GameView model = this.gameView;
        PickTilesController pickTilesController = CallbackRunner.runAndWait(() -> {

            FXMLLoader loader = new FXMLLoader((getClass().getResource("/PickTiles2.fxml")));

            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                this.primaryStage.setScene(scene);
                PickTilesController pickTilesController1 = loader.getController();
                pickTilesController1.setGameView(model,this.nicknamePlayer);
                this.primaryStage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return loader.getController();
        });

        try {
            return pickTilesController.pickTilesFromBoard();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * Method that loads the scene that sets the client's view of the game when it is not his turn. The controller
     * associated with the scene prevents any move by the player, who must wait for his turn to begin.
     *
     * @param model modelView of the game.Each client turn sets the model to the current state of the game by updating it.
     * @param nickname nome del client nella partita.Used to extract information concerning specific client elements to
     * be displayed on the screen as for the bookshelf or personalGoalCard.
     */
    @Override
    public void updateModel(GameView model, String nickname) {

        this.gameView = model;
        this.nicknamePlayer = nickname;

         UpdateModelController updateModelController = CallbackRunner.runAndWait(() -> {

            FXMLLoader loader = new FXMLLoader((getClass().getResource("/UpdateModel2.fxml")));

            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                this.primaryStage.setScene(scene);
                UpdateModelController updateModelController1 = loader.getController();
                updateModelController1.setGameView(model,nickname);
                this.primaryStage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
           return loader.getController();
        });


    }

    /**
     * A method associated with the scene that is used to take input from the client that orders the tiles chosen
     * during the pick. The controller associated with the scene handles and controls the player's choices
     * through the use of specific animations.
     *
     * @param tiles List of Tiles to be ordered.
     * @return the list of tiles ordered by the player.
     */
    @Override
    public List<Tile> sortTilesToInsert(List<Tile> tiles) {

        GameView model = this.gameView;


        SortTilesController sortTilesController = CallbackRunner.runAndWait(() -> {

            FXMLLoader loader = new FXMLLoader((getClass().getResource("/SortTiles2.fxml")));

            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                this.primaryStage.setScene(scene);
                SortTilesController sortTilesController1 = loader.getController();
                sortTilesController1.setGameView(model,this.nicknamePlayer,tiles);
                this.primaryStage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return loader.getController();
        });


        try {
            this.orderedTiles = sortTilesController.sortTilesToInsert();
            return orderedTiles;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * A method that loads the scene to take as input the player's choice of which column to place the tiles ordered in
     * the sort. The controller associated with the scene takes care of controlling the choice by guiding it
     * through the use of colours, figures and dynamic on-screen graphics.
     *
     * @return an integer representing the index of the chosen column.
     */
    @Override
    public int chooseColumn() {


        InsertTilesController insertTilesController = CallbackRunner.runAndWait(() -> {

            FXMLLoader loader = new FXMLLoader((getClass().getResource("/InsertTiles.fxml")));

            try {
                GameView model = this.gameView;

                Parent root = loader.load();
                Scene scene = new Scene(root);
                this.primaryStage.setScene(scene);
                InsertTilesController insertTilesController1 = loader.getController();
                insertTilesController1.setGameView(model,this.nicknamePlayer,this.orderedTiles);
                this.primaryStage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return loader.getController();
        });
        try {
            return insertTilesController.chooseColumn();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


    }

    /**
     * Method that creates a choiceBox which is used to take input from the player who can choose whether to confirm or
     * cancel the action. Used in various parts of the code including the message confirming his move, which in the
     * event of a negative response is sent back to repeat his turn from the pick.
     *
     * @param output the question to be displayed on the screen.
     * @return a Boolean, true if the user confirms the choice, false otherwise.
     */
    @Override
    public Boolean confirmChoice(String output) {

        Boolean confirmed;

        confirmed = CallbackRunner.runAndWait(() -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Move");
            alert.setHeaderText(null);
            alert.setContentText(output);
            javafx.scene.control.DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN, CornerRadii.EMPTY,
                    javafx.geometry.Insets.EMPTY)));

            dialogPane.setStyle("-fx-font-size: 14px; -fx-text-fill: red; -fx-font-family: Verdana;");
            ButtonType confirmButton = new ButtonType("Confirm");
            ButtonType retryButton = new ButtonType("Retry");
            alert.getButtonTypes().setAll(confirmButton, retryButton);


            Optional<ButtonType> result = alert.showAndWait();

            boolean confirmed2 = result.orElse(retryButton) == confirmButton;
            if (confirmed2) {
                logger.info("User Confirmed");
            } else {
                logger.info("User wants to retry");
            }
            return confirmed2;
        });

            return confirmed;

    }

    /**
     * Method used at the end of the game to display the winner's screen. The controller associated with the scene only
     * handles the winner's name and makes it appear on the screen with a fade-out animation.
     *
     * @param winner name of the winner of the match.
     */
    public void endgame (String winner){


        EndgameController endgameController = CallbackRunner.runAndWait(() -> {

            FXMLLoader loader = new FXMLLoader((getClass().getResource("/Endgame.fxml")));

            try {
                Parent root = loader.load();
                Scene scene = new Scene(root);
                this.primaryStage.setScene(scene);
                EndgameController endgameController1 = loader.getController();
                endgameController1.setWinnerLabel(winner);
                this.primaryStage.show();

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return loader.getController();
        });



    }

    /**
     * It closes the GUI and also causes the client to disconnect from the server.
     *
     * @param status exit status.
     */
    @Override
    public void exitInterface(int status) {
            logger.info("Closing Gui Interface");
            primaryStage.close();
            logger.info("Client disconnected");
            System.exit(status);
    }


    /**
     * Part of the methods used only Cli-side and not implemented in Gui. Left to maintain consistency of the
     * strategy pattern.
     * @return
     */
    @Override
    public Object[] writeMessage() {
        return new Object[0];
    }

    /**
     *
     * @param messages arrays of messages each one containing:<br />
     *                1) Author of the message.<br />
     *                2) Recipient of the message.<br />
     *                3) Body of the message.<br />
     *                4) Metadata.<br />
     */
    @Override
    public void readMessage(ChatMessage[] messages) {

    }

    /**
     *
     * @param winner string containing name of the winner
     * @param numOfPoints string containing total points of the winner
     */
    @Override
    public void closeGame(String winner, int numOfPoints) {

    }

    /**
     * Part of the methods used only Cli-side and not implemented in Gui. Left to maintain consistency of the
     * strategy pattern.
     * @param b
     */
    @Override
    public void setWaitingRoomInterrupt(boolean b) {
        this.b = b;
    }

    /**
     * Part of the methods used only Cli-side and not implemented in Gui. Left to maintain consistency of the
     * strategy pattern.
     * @param output the string to be displayed as informative message.
     */
    @Override
    public void infoMessage(String output) {

    }

    /**
     * Part of the methods used only Cli-side and not implemented in Gui. Left to maintain consistency of the
     * strategy pattern.
     * @param output the string to be displayed as error message.
     */
    @Override
    public void errorMessage(String output) {

    }

    /**
     * Part of the methods used only Cli-side and not implemented in Gui. Left to maintain consistency of the
     * strategy pattern.
     * @param output
     */
    @Override
    public void debugMessage(String output) {

    }

    /**
     * Part of the methods used only Cli-side and not implemented in Gui. Left to maintain consistency of the
     * strategy pattern.
     * @param type integer representing the type of Easter egg to be displayed.
     * @throws ExecutionControl.NotImplementedException
     */
    @Override
    public void easterEgg(int type) throws ExecutionControl.NotImplementedException {

    }

    /**
     * Part of the methods used only Cli-side and not implemented in Gui. Left to maintain consistency of the
     * strategy pattern.
     * @return
     */
    @Override
    public String createGame() {
        return null;

     }

    /**
     * Part of the methods used only Cli-side and not implemented in Gui. Left to maintain consistency of the
     *   strategy pattern.
     * @param game an array with all the information to start a game:<br />
     */
    @Override
    public void startGame(Object[] game) {

    }

}
