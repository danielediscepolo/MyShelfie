package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.util.Duration;

import javax.swing.text.View;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SortTilesController implements Initializable {


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
    private Button MyPlayer1;
    @FXML
    private Button MyPlayer2;
    @FXML
    private Button MyPlayer3;
    @FXML
    private Button MyPlayer4;

    @FXML
    private ImageView commonGoalCard1;
    @FXML
    private ImageView commonGoalCard2 ;

    @FXML
    private ImageView personalGoalCard ;

    @FXML
    private Label commonGoalCard1Description;
    @FXML
    private Label  commonGoalCard2Description;

    @FXML
    private GridPane gridPaneBookshelf;

    @FXML
    private GridPane orderedTilesGridPane;

    @FXML
    private GridPane chosenTilesGridPane;

    private Bookshelf bookshelf;

    private GameView gameView;

    private List<Tile> chosenTiles = new ArrayList<>();

    private List<Tile> orderedTiles = new ArrayList<>();

    private List<Position> posixChosenTiles = new ArrayList<>();


    /**
     * Checks if the selected tiles are sorted correctly.
     * It verifies various conditions, such as whether all tiles have been ordered and if the ordered tiles match the chosen tiles.
     * If any of these conditions are not met, an error alert is displayed.
     * If all conditions are met, the method notifies all waiting threads.
     */
    @FXML
    public synchronized void checkSort(){

    if (orderedTiles == null){
        showAlert(Alert.AlertType.ERROR,"Wrong Sort Move ", "You did not order all the tiles you had chosen." +
                " Please select all tiles in order to move on.");
    } else if (orderedTiles.size() < chosenTiles.size()){
        showAlert(Alert.AlertType.ERROR,"Wrong Sort Move ", "You did not order all the tiles you had chosen." +
                " Please select all tiles in order to move on.");
    } else if (!sameListDifferentOrder(orderedTiles,chosenTiles)) {
        showAlert(Alert.AlertType.ERROR,"Wrong Sort Move ", "The tiles ordered do not correspond to those " +
                "you had chosen. Please try again!");
    }else{
        notifyAll();
    }

    }


    /**
     * Sorts the tiles to be inserted based on the user's selection.
     * Waits until all tiles are ordered before proceeding.
     * Resets the 'hasBeenClicked' flag for both ordered and chosen tiles.
     *
     * @return The list of ordered tiles.
     * @throws InterruptedException if the current thread is interrupted while waiting.
     */
    public synchronized List<Tile> sortTilesToInsert() throws InterruptedException {

        while ( orderedTiles != null && orderedTiles.size() < chosenTiles.size()){
            this.wait();
        }

        for (Tile t: orderedTiles) {
            t.setHasBeenClicked(false);
        }
        for (Tile t: chosenTiles) {
            t.setHasBeenClicked(false);
        }

        return orderedTiles;

    }

    /**
     * Initializes the controller after its root element has been completely processed.
     * This method is called automatically after the FXML file has been loaded and its
     * contents have been injected into the controller's fields.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resource bundle used for localization, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    /**
     * Sets the gameView field to the provided gameView object.
     * Initializes the UI components for the player with the given nickname, including setting up the bookshelf, personal goal card, and chosen tiles.
     * Sets up the UI components for the common goal cards, including setting the images and descriptions.
     * Sets up the UI components for player names and common tokens.
     *
     * @param gameView The game view object representing the current game state.
     * @param nickname The nickname of the player.
     * @param tiles The tiles chosen by the player.
     */
    public void setGameView(GameView gameView, String nickname,List<Tile> tiles) {

        this.gameView = gameView;

        for (PlayerInGame p: gameView.getPlayers()) {

            if (p.getName().equals(nickname)){

                setBookshelf(p.getBookshelf());
                setPersonalGoalCard(p.getPersonalGoalCard());
                this.chosenTiles = tiles;
                setChosenTilesGridPane(this.chosenTiles);
            }
        }

        setCommonGoalCard1(this.gameView.getCommonGoalCards().get(0));
        setCommonGoalCard2(this.gameView.getCommonGoalCards().get(1));
        setCommonGoalCard1Description(this.gameView.getCommonGoalCards().get(0).getDescription());
        setCommonGoalCard2Description(this.gameView.getCommonGoalCards().get(1).getDescription());;
        setPlayerNames(this.gameView.getPlayers());
        setCommonTokens(this.gameView.getCommonGoalCards());
    }


   /**
    * Sets the bookshelf field to the provided bookshelf object.
    * Retrieves the dimensions (number of rows and columns) of the bookshelf.
    * Iterates through each cell in the bookshelf matrix.
    * Checks if the cell contains a tile.
    * If a tile exists in the cell, retrieves the GUI reference for the tile.
    * Creates an ImageView object with the tile's GUI reference as the image source.
    * Sets the dimensions (width and height) of the image view.
    * Adds the image view to the corresponding cell (col, row) in the grid pane.
    *
    * @param bookshelf The bookshelf object representing the player's bookshelf.
    */
    private void setBookshelf(Bookshelf bookshelf) {

        this.bookshelf = bookshelf;

        int NUM_ROWS = bookshelf.getRows();
        int NUM_COLS = bookshelf.getColumns();

        Tile[][] playerMatrix = this.bookshelf.getMatrix();
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {

                if (playerMatrix[row][col] != null){
                    String tileGuiReference = playerMatrix[row][col].getGuiReference();
                    ImageView imageView = new ImageView(new Image(tileGuiReference));
                    imageView.setFitWidth(70); // Set width to 44 pixels
                    imageView.setFitHeight(70); // Set height to 44 pixels
                    gridPaneBookshelf.add(imageView, col, row); // Add to cell (col, row)
                }
            }
        }
    }

    /**
     * Retrieves the GUI reference for the personal goal card.
     * Creates an ImageView object with the personal goal card's GUI reference as the image source.
     * Sets the image of the personalGoalCard field to the image of the personalCardImageView.
     *
     * @param personalGoalCard The personal goal card representing the player's personal goal.
     */
    private void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {

        String personalGoalCardGuiReference = personalGoalCard.getGuiReference();

        ImageView personalCardImageView = new ImageView(personalGoalCardGuiReference);

        this.personalGoalCard.setImage(personalCardImageView.getImage());
    }


    /**
     * Sets the UI component for the first common goal card based on the provided common goal card.
     *
     * @param commonGoalCard1 The first common goal card.
     */
    private void setCommonGoalCard1(CommonGoalCard commonGoalCard1){

        String commonGoalCardGuiReference = commonGoalCard1.guiReference;

        ImageView commonCard1ImageView = new ImageView(commonGoalCardGuiReference);

        this.commonGoalCard1.setImage(commonCard1ImageView.getImage());
    }


    /**
     * Sets the UI component for the second common goal card based on the provided common goal card.
     *
     * @param commonGoalCard2 The second common goal card.
     */
    private void setCommonGoalCard2(CommonGoalCard commonGoalCard2) {

        String commonGoalCardGuiReference = commonGoalCard2.guiReference;

        ImageView commonCard2ImageView = new ImageView(commonGoalCardGuiReference);

        this.commonGoalCard2.setImage(commonCard2ImageView.getImage());
    }

    /**
     * Sets the description text for the first common goal card.
     *
     * @param commonGoalCard1Description The description text for the first common goal card.
     */
    private void setCommonGoalCard1Description(String commonGoalCard1Description) {
        this.commonGoalCard1Description.setText(commonGoalCard1Description);

    }

    /**
     * Sets the description text for the second common goal card.
     *
     * @param commonGoalCard2Description The description text for the second common goal card.
     */
    private void setCommonGoalCard2Description(String commonGoalCard2Description) {
        this.commonGoalCard2Description.setText(commonGoalCard2Description);
    }


    /**
     * Sets the grid pane with the chosen tiles.
     * Iterates over the tilesChosen list.
     * Creates an ImageView for each tile and sets its dimensions.
     * Associates an onMouseClicked event handler with each image view to handle tile movement.
     * Adds the image view to the chosenTilesGridPane at the corresponding position in the grid.
     *
     * @param tilesChosen The list of tiles chosen by the player.
     */
    private void setChosenTilesGridPane(List<Tile> tilesChosen){

        int maxRow = 2;
        for (Tile t: tilesChosen) {


            String tileGuiReference = t.getGuiReference();
            ImageView imageView = new ImageView(new Image(tileGuiReference));
            imageView.setFitWidth(70); // Set width to 44 pixels
            imageView.setFitHeight(70); // Set height to 44 pixels
            int finalMaxRow = maxRow;
            posixChosenTiles.add(new Position(maxRow,0));
            imageView.setOnMouseClicked(event -> moveImageView(imageView,t, finalMaxRow,0));
            chosenTilesGridPane.add(imageView,0,maxRow);
            maxRow--;
        }


    }

    /**
     * Sets the names of the players in the UI.
     * Retrieves the names of the players from the players list and sets them in the corresponding UI elements (playerName1, playerName2, etc.).
     * If there are more than two players, it sets the name for playerName3.
     * If there are more than three players, it sets the name for playerName4.
     *
     * @param players The list of players in the game.
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
     * Checks if two lists contain the same elements regardless of their order.
     * Creates copies of the input lists to avoid modifying the original lists.
     * Iterates over the elements in orderedTiles and removes each element from notOrderedTilesCopy if it is found.
     * Finally, it checks if notOrderedTilesCopy is empty, indicating that both lists contain the same elements regardless of their order.
     *
     * @param orderedTiles    The first list of tiles in a specific order.
     * @param notOrderedTiles The second list of tiles in a different order.
     * @return {@code true} if the two lists contain the same elements regardless of their order, {@code false} otherwise.
     */
    private boolean sameListDifferentOrder(List<Tile> orderedTiles,List<Tile> notOrderedTiles){

        List<Tile> orderedTilesCopy = new ArrayList<>(orderedTiles);
        List<Tile> notOrderedTilesCopy = new ArrayList<>(notOrderedTiles);

        for (Tile x : orderedTilesCopy) {

            notOrderedTilesCopy.removeIf(x::equals);
        }

        return notOrderedTilesCopy.isEmpty();

    }


    /**
     * Moves the specified ImageView to the ordered grid pane and performs necessary actions.
     * Checks if the specified position (row, col) exists in the posixChosenTiles list. If it does:
     * Adds the tile to the ordered grid pane.
     * Deletes the position (row, col) from the posixChosenTiles list.
     * Applies a fade transition animation to the imageView.
     * Sets the imageView image to null, effectively making it disappear from the view.
     *
     * @param imageView The ImageView to be moved.
     * @param tile      The corresponding Tile object.
     * @param row       The row index of the ImageView in the grid pane.
     * @param col       The column index of the ImageView in the grid pane.
     */
    private void moveImageView(ImageView imageView, Tile tile,int row,int col) {

        if (posixChosenTiles.stream().filter((Position p) -> p.getColumn() == col && p.getRow() == row).count() != 0) {
            addTileInOrderedGridPane(tile);
            deletePosition(new Position(row,col));
            fadeTransition(2000,imageView);
           imageView.setImage(null);

        }
    }


    /**
     * Deletes the specified position from the list of chosen positions.
     *
     * @param position The position to be deleted.
     */
    public void deletePosition (Position position){

        List<Position> positionList = new ArrayList<>(posixChosenTiles);
        for (Position p: positionList) {
            if (p != null && p.equals(position)){
                posixChosenTiles.remove(p);
            }
        }
    }



    /**
     * Shows an alert dialog with the specified alert type, title, and message.
     *
     * @param alertType The type of the alert dialog.
     * @param title     The title of the alert dialog.
     * @param message   The message to be displayed in the alert dialog.
     */
    private  void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        javafx.scene.control.DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.LIGHTCORAL, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.show();
    }

    /**
     * Displays an information dialog box with the specified information message, header text, and title.
     *
     * @param infoMessage The information message to be displayed.
     * @param headerText  The header text of the dialog box.
     * @param title       The title of the dialog box.
     */
    public  void infoBox(String infoMessage, String headerText, String title){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(infoMessage);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.show();
    }


    /**
     * Adds a tile to the ordered tiles grid pane and updates the ordered tiles list.
     *
     * @param t The tile to be added.
     */
    private void addTileInOrderedGridPane(Tile t){
        int maxRow = 2;
        String tileGuiReference = t.getGuiReference();
        ImageView imageView = new ImageView(new Image(tileGuiReference));
        imageView.setFitWidth(70); // Set width to 44 pixels
        imageView.setFitHeight(70); // Set height to 44 pixels
        orderedTilesGridPane.add(imageView,0,maxRow-orderedTiles.size());
        orderedTiles.add(t);
        fadeTransition(3000,imageView);
            }

    /**
     * Sets the common tokens on the GUI based on the specified list of common goal cards.
     *
     * @param commonGoalCards The list of common goal cards containing the common tokens.
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


    /**
     * Applies a fade transition animation to the specified image view.
     *
     * @param durationInMilliseconds The duration of the fade transition in milliseconds.
     * @param imageView              The image view to apply the fade transition to.
     */
    private void fadeTransition(int durationInMilliseconds,ImageView imageView){

        FadeTransition ft = new FadeTransition(Duration.millis(durationInMilliseconds), imageView);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }



}
