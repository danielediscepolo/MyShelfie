package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoalCard.C6;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.view.gui.CallbackRunner;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;


import javax.naming.Context;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class PickTilesController implements Initializable {

    @FXML
    private ImageView commonToken1;
    @FXML
    private ImageView commonToken2;

    @FXML
    private Label playerName1;
    @FXML
    private Label playerName2;
    @FXML
    private Label playerName3;
    @FXML
    private Label playerName4;

    @FXML
    private Button button;
    @FXML
    private Button button2;
    @FXML
    private Button MyPlayer1;
    @FXML
    private Button MyPlayer2;
    @FXML
    private Button MyPlayer3;
    @FXML
    private Button MyPlayer4;
    @FXML
    private ImageView MyBoard;

    @FXML
    private ImageView commonGoalCard1;
    @FXML
    private ImageView commonGoalCard2 ;

    @FXML
    private ImageView personalGoalCard ;

    @FXML
    private Label  commonGoalCard1Description;
    @FXML
    private Label  commonGoalCard2Description;

    @FXML
    private GridPane gridPaneBookshelf;
    @FXML
    private GridPane gridPaneBoard;

    @FXML
    private ImageView endgameToken;


    private Board board;

    private List<List<Position>> combinationOfPickableTilesForTurnPlayer;

    private Bookshelf bookshelf;

    private GameView gameView;

    private List<Position> posix = new ArrayList<>();

    private boolean passedAction = false;


    private Map<Position,ImageView> tilesImageView = new HashMap<>();


    /**
     * Initializes the scene, setting mouse event handlers for buttons representing players.
     * When the mouse cursor is over a player button, the button's background color changes to green.
     * When the mouse cursor exits a player button, the button's background color changes back to white.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



            MyPlayer1.setOnMouseMoved(event ->{
                MyPlayer1.setStyle("-fx-background-color: green;");
            }); //the button turns green when the cursor is over it

            MyPlayer1.setOnMouseExited(event->{
                MyPlayer1.setStyle("-fx-background-color: white;");
            });
            MyPlayer2.setOnMouseMoved(event ->{
                MyPlayer2.setStyle("-fx-background-color: green;");
            }); //the button turns green when the cursor is over it

            MyPlayer2.setOnMouseExited(event->{
                MyPlayer2.setStyle("-fx-background-color: white;");
            });
            MyPlayer3.setOnMouseMoved(event ->{
                MyPlayer3.setStyle("-fx-background-color: green;");
            }); //the button turns green when the cursor is over it

            MyPlayer3.setOnMouseExited(event->{
                MyPlayer3.setStyle("-fx-background-color: white;");
            });
            MyPlayer4.setOnMouseMoved(event ->{
                MyPlayer4.setStyle("-fx-background-color: green;");
            }); //the button turns green when the cursor is over it

            MyPlayer4.setOnMouseExited(event->{
                MyPlayer4.setStyle("-fx-background-color: white;");
            });

    }


    /**
     * Sets the game view for a player and initializes all related UI components.
     *
     * @param gameView The GameView object containing all game-related information
     * @param nickname The nickname of the player for whom the game view is being set
     */
    public void setGameView(GameView gameView, String nickname) {
        this.gameView = gameView;

        for (PlayerInGame p: gameView.getPlayers()) {

            if (p.getName().equals(nickname)){

                setBookshelf(p.getBookshelf());
                setPersonalGoalCard(p.getPersonalGoalCard());
            }
        }

        setCommonGoalCard1(this.gameView.getCommonGoalCards().get(0));
        setCommonGoalCard2(this.gameView.getCommonGoalCards().get(1));
        setCommonGoalCard1Description(this.gameView.getCommonGoalCards().get(0).getDescription());
        setCommonGoalCard2Description(this.gameView.getCommonGoalCards().get(1).getDescription());
        setBoard(this.gameView.getBoard());
        setEndGameToken(this.gameView.getPlayers());
        setPlayerNames(this.gameView.getPlayers());
        setCommonTokens(this.gameView.getCommonGoalCards());
    }

    /**
     * Sets the board representation for the game, including initializing all tile images on the user interface.
     * The method takes a Board object as an input and updates the graphical user interface to reflect the current state
     * of the game board. It displays each tile on the board by creating an ImageView for each tile and adds it to
     * a GridPane. If a tile on the board is not null, it sets up an ImageView with the image corresponding to the tile.
     * It also sets up a click event for each tile image.
     *
     * @param board The Board object representing the current state of the game board. The Board object contains the
     * information about the layout of tiles on the board.
     */
    private void setBoard(Board board) {
        this.board = board;

        combinationOfPickableTilesForTurnPlayer = board.combinationOfTakeableTilesForTurnPlayer(this.bookshelf);
        int NUM_ROWS = board.getRows();
        int NUM_COLS = board.getColumns();

        Tile[][] boardBoxes = board.getBoxes();
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {

                if (boardBoxes[row][col] != null){
                    Tile tile = boardBoxes[row][col];
                    int rowTile = row;
                    int colTile = col;
                    String tileGuiReference = tile.getGuiReference();
                    ImageView imageView = new ImageView(new Image(tileGuiReference));
                    imageView.setFitWidth(49);
                    imageView.setFitHeight(49);
                    tilesImageView.put(new Position(row,col),imageView);
                    imageView.setEffect(new DropShadow());
                    imageView.setOnMouseClicked(event -> pickTile(imageView,tile,rowTile,colTile));
                    gridPaneBoard.add(imageView, col, row);

                }
            }
        }

    }


    /**
     * Handles the event when the player chooses to pass their turn.
     * It sets the flag "passedAction" to true, indicating that the player has passed their turn.
     * If the number of selected tiles (posix) is zero, it notifies all waiting threads.
     * Otherwise, it displays an error alert indicating that the player has already selected a tile and cannot pass anymore.
     *
     * @param actionEvent The ActionEvent object representing the event of the player clicking the "Pass Turn" button.
     */
    public synchronized void passTurn(ActionEvent actionEvent) {
        passedAction = true;
        if (posix.size() == 0) {
            notifyAll();
        }else {
            showAlert(Alert.AlertType.ERROR, "Wrong Pass Move", "You have already selected a tile. You can't " +
                    "pass anymore");
        }
    }

    /**
     * Checks the validity of the picked tiles and handles the corresponding actions.
     * If no tiles have been selected for the pick and the "passedAction" flag is false, it displays an error alert.
     * If more than three tiles have been selected, it displays an error alert.
     * If the positions of the selected tiles are not correct according to the available pickable tiles for the turn player,
     * it displays an error alert.
     * Otherwise, it notifies the waiting thread(s).
     *
     * @param actionEvent The ActionEvent object representing the event of the player clicking the "Check Pick" button.
     */
    public synchronized void checkPickTiles(ActionEvent actionEvent) {

        if (posix.size() == 0 && !passedAction ){
            showAlert(Alert.AlertType.ERROR, "Wrong Pick Move", "No tiles have been selected for the pick. " +
                    "If you want to pass the round, press the 'Pass' button.");
        }else if (posix.size() > 3 ){
            showAlert(Alert.AlertType.ERROR, "Wrong Pick Move", "Too many tiles selected.");
        } else if (!checkIfPositionsAreCorrect(combinationOfPickableTilesForTurnPlayer,posix)) {
            showAlert(Alert.AlertType.ERROR, "Wrong Pick Move", "Positions chosen are not correct");
        }else{
            notify();
        }
    }


    /**
     * Waits until the positions of the picked tiles are correct or the player has passed the action.
     * If the positions are correct or the player has passed, it returns the list of selected positions.
     *
     * @return The list of positions representing the picked tiles from the board.
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    public synchronized List<Position> pickTilesFromBoard() throws InterruptedException {

        while ((!checkIfPositionsAreCorrect(combinationOfPickableTilesForTurnPlayer,posix) && !passedAction)){
            wait();
        }

        return posix;

    }

    /**
     * Handles the selection of a tile from the board.
     * If the tile is takeable and has not been previously selected, it adds the position to the selected positions list.
     * It also applies visual effects to the selected tile and other tiles accordingly.
     *
     * @param imageView The ImageView representing the selected tile.
     * @param tile      The Tile object representing the selected tile.
     * @param row       The row index of the selected tile.
     * @param col       The column index of the selected tile.
     */
    private void pickTile(ImageView imageView,Tile tile,int row, int col){

        if (isTakeable(row,col)){


            if (posix.stream().filter((Position p) -> p.getColumn() == col && p.getRow() == row).count() == 0) {


                if (posix.size() < 3) {

                    List<Position> posixChosenUntilNow = new ArrayList<>(posix);

                    posixChosenUntilNow.add(new Position(row, col));

                    if (checkIfPositionsAreCorrect(combinationOfPickableTilesForTurnPlayer, posixChosenUntilNow)) {

                        posix.add(new Position(row, col));
                        imageView.setEffect(new Glow());
                        imageView.setEffect(new Bloom());
                        setEffectOnOtherTiles();


                    }else{
                        showAlert(Alert.AlertType.ERROR,"Wrong Pick Move ", " You can't make this sequence " +
                                "of tiles.");
                    }
                }else {
                    showAlert(Alert.AlertType.ERROR, "Wrong Pick Move ", " You have already selected as " +
                            "many tiles as you can.");
                    tile.setHasBeenClicked(true);
                }
            } else{
                /*discarding action*/
            }
        }else {
            /*discarding action*/
        }


    }

    /**
     * Sets the bookshelf and updates the GUI representation of the bookshelf.
     *
     * @param bookshelf The Bookshelf object to set.
     */
    public void setBookshelf(Bookshelf bookshelf) {
        this.bookshelf = bookshelf;

        int NUM_ROWS = bookshelf.getRows();
        int NUM_COLS = bookshelf.getColumns();

        Tile[][] playerMatrix = this.bookshelf.getMatrix();
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {

                if (playerMatrix[row][col] != null){
                    String tileGuiReference = playerMatrix[row][col].getGuiReference();
                    ImageView imageView = new ImageView(new Image(tileGuiReference));
                    imageView.setFitWidth(44); // Set width to 44 pixels
                    imageView.setFitHeight(44); // Set height to 44 pixels
                    gridPaneBookshelf.add(imageView, col, row); // Add to cell (col, row)
                }
            }
        }
    }

    /**
     * Sets the personal goal card and updates the GUI representation of the personal goal card.
     *
     * @param personalGoalCard The PersonalGoalCard object to set.
     */
    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {

        String personalGoalCardGuiReference = personalGoalCard.getGuiReference();

        ImageView personalCardImageView = new ImageView(personalGoalCardGuiReference);

        this.personalGoalCard.setImage(personalCardImageView.getImage());
    }


    /**
     * Sets the first common goal card and updates the GUI representation of the card.
     *
     * @param commonGoalCard1 The CommonGoalCard object to set as the first common goal card.
     */
    public void setCommonGoalCard1(CommonGoalCard commonGoalCard1){

        String commonGoalCardGuiReference = commonGoalCard1.guiReference;

        ImageView commonCard1ImageView = new ImageView(commonGoalCardGuiReference);

        this.commonGoalCard1.setImage(commonCard1ImageView.getImage());
    }


    /**
     * Sets the second common goal card and updates the GUI representation of the card.
     *
     * @param commonGoalCard2 The CommonGoalCard object to set as the second common goal card.
     */
    public void setCommonGoalCard2(CommonGoalCard commonGoalCard2) {

        String commonGoalCardGuiReference = commonGoalCard2.guiReference;

        ImageView commonCard2ImageView = new ImageView(commonGoalCardGuiReference);

        this.commonGoalCard2.setImage(commonCard2ImageView.getImage());
    }

    /**
     * Sets the description for the first common goal card and updates the GUI label displaying the description.
     *
     * @param commonGoalCard1Description The description to set for the first common goal card.
     */
    public void setCommonGoalCard1Description(String commonGoalCard1Description) {
        this.commonGoalCard1Description.setText(commonGoalCard1Description);

    }

    /**
     * Sets the description for the second common goal card and updates the GUI label displaying the description.
     *
     * @param commonGoalCard2Description The description to set for the second common goal card.
     */
    public void setCommonGoalCard2Description(String commonGoalCard2Description) {
        this.commonGoalCard2Description.setText(commonGoalCard2Description);
    }

    /**
     * Sets the end game token image based on the presence of the token in the players' list.
     *
     * @param players The list of players in the game.
     */
    public void setEndGameToken(List<PlayerInGame> players){

        boolean flag = false;

        for (PlayerInGame p: players) {

            if (p.getEndGameToken() != null){
                flag = true;
                this.endgameToken.setImage(null);
                break;
            }
        }


    }





    /**
     * Checks if the chosen positions by the turn player are correct according to the list of combinations of takeable tiles.
     *
     * @param combinationsOfTakeableTiles The list of combinations of takeable tiles.
     * @param positionsChosenByTurnPlayer The positions chosen by the turn player.
     * @return True if the positions are correct, false otherwise.
     */
    public boolean checkIfPositionsAreCorrect (List<List<Position>> combinationsOfTakeableTiles, List<Position> positionsChosenByTurnPlayer){

        boolean correctChoice = false;
        int count;
        for (List<Position> x: combinationsOfTakeableTiles) {
            count = 0;
            for (Position position : x) {
                for (Position value : positionsChosenByTurnPlayer) {
                    if (position.equals(value)) {
                        count++;
                    }
                }
                if (count == x.size() && count == positionsChosenByTurnPlayer.size()) {

                    correctChoice = true;
                }
            }
        }
        return correctChoice;
    }

    /**
     * Converts the positions chosen by the turn player into a list of tiles.
     *
     * @param positions The positions chosen by the turn player.
     * @return The list of tiles corresponding to the chosen positions.
     */
    public List<Tile> convertPositionsToTiles(List<Position> positions){

        Tile matrix[][] = board.getBoxes();

        List<Tile> tilesChosenByTurnPlayer = new ArrayList<>();

        for (Position x: positions) {

            Tile tile = matrix[x.getRow()][x.getColumn()];

            tilesChosenByTurnPlayer.add(new Tile(tile.getColor(),tile.getFigure()));

        }

        return tilesChosenByTurnPlayer;

    }

    /**
     * Checks if the tile at the specified position can be taken.
     *
     * @param i The row index of the tile.
     * @param j The column index of the tile.
     * @return {@code true} if the tile can be taken, {@code false} otherwise.
     */
    private boolean isTakeable(int i, int j){

        Tile matrix[][] = board.getBoxes();

        if ( i == 0 || i == 8 || j == 0 || j == 8) {

            return true;

        }else return matrix[i + 1][j] == null || matrix[i - 1][j] == null || matrix[i][j - 1] == null || matrix[i][j + 1] == null;


    }

    /**
     * Shows an alert dialog with the specified type, title, and message.
     *
     * @param alertType The type of the alert.
     * @param title The title of the alert.
     * @param message The message of the alert.
     */
    private  void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        javafx.scene.control.DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.LIGHTCORAL, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
        PauseTransition delay = new PauseTransition(Duration.seconds(1.3));
        delay.setOnFinished(event -> alert.close());
        delay.play();
    }




    /**
     * Sets the effect on other tiles on the board based on the positions chosen by the turn player.
     * Tiles that are not part of a correct combination will have the effect set to Lighting.
     */
    public void setEffectOnOtherTiles(){

        int NUM_ROWS = board.getRows();
        int NUM_COLS = board.getColumns();
        Tile[][] boardBoxes = board.getBoxes();
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {

                Position position = new Position(row,col);
                List<Position> posixChosen = new ArrayList<>(posix);
                posixChosen.add(position);

                if (boardBoxes[row][col] != null && !checkIfPositionsAreCorrect(combinationOfPickableTilesForTurnPlayer,posixChosen)){

                    ImageView imageView = tilesImageView.get(position);

                    imageView.setEffect(new Lighting());
                }
            }
        }
    }

    /**
     * Receives a list of players and sets their names in the corresponding labels in the graphical user interface.
     * The names of the first two players are set in the playerName1 and playerName2 labels.
     * If the list of players has a size greater than 2, the name of the third player is set in the playerName3 label.
     * If the list has a size greater than 3, the name of the fourth player is set in the playerName4 label.
     *
     * @param players The list of players
     */
    public void setPlayerNames(List<PlayerInGame> players){


        playerName1.setText(players.get(0).getName());
        playerName2.setText(players.get(1).getName());

        if (players.size() > 2){
            playerName3.setText(players.get(2).getName());
        }


        if (players.size() > 3 ){
            playerName4.setText(players.get(3).getName());
        }

    }

    /**
     * Sets the common tokens for the common goal cards in the graphical user interface.
     * It expects a list of common goal cards as input.
     * The method checks if the first common goal card has a non-null common token at index 0 and a non-zero number of points.
     * If these conditions are met, it retrieves the GUI reference of the common token, creates an ImageView with the corresponding image, and sets it as the image of commonToken1.
     * Similarly, the method performs the same checks and operations for the second common goal card, setting the image in commonToken2.
     *
     * @param commonGoalCards The list of common goal cards
     */
    public void setCommonTokens(List<CommonGoalCard> commonGoalCards) {


        if (commonGoalCards.get(0).getCommonTokens().get(0) != null ||
                commonGoalCards.get(0).getCommonTokens().get(0).getPoints() != 0){

            CommonToken commonToken1 = commonGoalCards.get(0).getCommonTokens().get(0);
            String commonToken1GuiReference = commonToken1.getGuiReference();

            ImageView commonToken1ImageView = new ImageView(commonToken1GuiReference);

            this.commonToken1.setImage(commonToken1ImageView.getImage());
        }

        if (commonGoalCards.get(1).getCommonTokens().get(0) != null ||
                commonGoalCards.get(1).getCommonTokens().get(0).getPoints() != 0){

            CommonToken commonToken2 = commonGoalCards.get(1).getCommonTokens().get(0);
            String commonToken2GuiReference = commonToken2.getGuiReference();

            ImageView commonToken2ImageView = new ImageView(commonToken2GuiReference);

            this.commonToken2.setImage(commonToken2ImageView.getImage());
        }


    }

}
