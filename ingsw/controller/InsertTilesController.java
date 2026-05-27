package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class InsertTilesController implements Initializable {


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
    private GridPane orderedTilesGridPaneInsert;

    @FXML
    private ImageView column0GraphicHint;
    @FXML
    private ImageView column1GraphicHint;
    @FXML
    private ImageView column2GraphicHint;
    @FXML
    private ImageView column3GraphicHint;
    @FXML
    private ImageView column4GraphicHint;

    private int resultColumn;

    private boolean insertDone = false;

    private Bookshelf bookshelf;

    private GameView gameView;

    private List<Tile> tilesToInsert;

    private List<ImageView> imageViewsOrderedTiles = new ArrayList<>();

    private List<Position> positionsChosenByTurnPlayerInBookshelf = new ArrayList<>();

    /**
     * The Initialize method is called after all @FXML annotated members have been injected.
     * This method is meant to handle any necessary post-construction setup,
     * and is not meant to contain any logic to do with setting up the user interface,
     * initializing any data needed for the fields, or any other setup requirements.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }

    /**
     * Initializes the game view with the players' data and sets the tiles of each player in the grid.
     *
     * @param gameView The GameView object that represents the state of the game
     * @param nickname The nickname of the player
     * @param orderedTiles The list of tiles for each player
     */
    public void setGameView(GameView gameView, String nickname,List<Tile> orderedTiles) {

        this.gameView = gameView;

        for (PlayerInGame p: gameView.getPlayers()) {

            if (p.getName().equals(nickname)){

                tilesToInsert = orderedTiles;
                setBookshelf(p.getBookshelf());
                setPersonalGoalCard(p.getPersonalGoalCard());
                setOrderedTilesGridPaneInsert(tilesToInsert);
                setColumnsGraphicHintImages(p.getBookshelf(),tilesToInsert.size());
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
     * Sets the bookshelf and tiles in the grid.
     *
     * @param bookshelf The Bookshelf object containing the tiles of a player
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
                    imageView.setFitWidth(70); // Set width to 44 pixels
                    imageView.setFitHeight(70); // Set height to 44 pixels
                    gridPaneBookshelf.add(imageView, col, row); // Add to cell (col, row)
                }
            }
        }
    }

    /**
     * Sets the personal goal card image for a player.
     *
     * @param personalGoalCard The personal goal card of the player
     */
    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {

        String personalGoalCardGuiReference = personalGoalCard.getGuiReference();

        ImageView personalCardImageView = new ImageView(personalGoalCardGuiReference);

        this.personalGoalCard.setImage(personalCardImageView.getImage());
    }


    /**
     * Sets the first common goal card image.
     *
     * @param commonGoalCard1 The first common goal card
     */
    public void setCommonGoalCard1(CommonGoalCard commonGoalCard1){

        String commonGoalCardGuiReference = commonGoalCard1.guiReference;

        ImageView commonCard1ImageView = new ImageView(commonGoalCardGuiReference);

        this.commonGoalCard1.setImage(commonCard1ImageView.getImage());
    }


    /**
     * Sets the second common goal card image.
     *
     * @param commonGoalCard2 The second common goal card
     */
    public void setCommonGoalCard2(CommonGoalCard commonGoalCard2) {

        String commonGoalCardGuiReference = commonGoalCard2.guiReference;

        ImageView commonCard2ImageView = new ImageView(commonGoalCardGuiReference);

        this.commonGoalCard2.setImage(commonCard2ImageView.getImage());
    }

    /**
     * Sets the description for the first common goal card.
     *
     * @param commonGoalCard1Description The description of the first common goal card
     */
    public void setCommonGoalCard1Description(String commonGoalCard1Description) {
        this.commonGoalCard1Description.setText(commonGoalCard1Description);

    }

    /**
     * Sets the description for the second common goal card.
     *
     * @param commonGoalCard2Description The description of the second common goal card
     */
    public void setCommonGoalCard2Description(String commonGoalCard2Description) {
        this.commonGoalCard2Description.setText(commonGoalCard2Description);
    }

    /**
     * Adds tiles to the grid.
     *
     * @param orderedTiles List of ordered tiles
     */
    private void setOrderedTilesGridPaneInsert(List<Tile> orderedTiles){

        int maxRow = 2;
        for (Tile t: orderedTiles) {

            String tileGuiReference = t.getGuiReference();
            ImageView imageView = new ImageView(new Image(tileGuiReference));
            imageView.setFitWidth(70); // Set width to 44 pixels
            imageView.setFitHeight(70); // Set height to 44 pixels
            orderedTilesGridPaneInsert.add(imageView,0,maxRow);
            imageViewsOrderedTiles.add(imageView);
            maxRow--;
        }


    }


    /**
     * Sets the graphic hint images for all columns in the bookshelf.
     *
     * @param bookshelf The Bookshelf object that contains the columns
     * @param sizeOrderedTiles The size of the ordered tiles
     */
    private void setColumnsGraphicHintImages(Bookshelf bookshelf, int sizeOrderedTiles){

        setColumnGraphicHintImage(0,sizeOrderedTiles,bookshelf, column0GraphicHint);
        setColumnGraphicHintImage(1,sizeOrderedTiles,bookshelf, column1GraphicHint);
        setColumnGraphicHintImage(2,sizeOrderedTiles,bookshelf, column2GraphicHint);
        setColumnGraphicHintImage(3,sizeOrderedTiles,bookshelf, column3GraphicHint);
        setColumnGraphicHintImage(4,sizeOrderedTiles,bookshelf,column4GraphicHint);

    }



    /**
     * Checks if there's enough space in a column of the bookshelf for a set of tiles.
     *
     * @param column Column index
     * @param numOfTiles Number of tiles
     * @param bookshelf The bookshelf object
     * @return true if there's enough space, false otherwise
     */
    public boolean checkEnoughSpaceInColumn (int column, int numOfTiles,Bookshelf bookshelf){

        boolean enoughSpace = false;

        int freeSpaces = 0;

        for (int i = 0; i < bookshelf.getRows(); i++) {

            if (bookshelf.getMatrix()[i][column] == null){

                freeSpaces++;
            }

        }
        if (freeSpaces >= numOfTiles){

            enoughSpace = true;

        }
        return enoughSpace;
    }



    /**
     * Sets the graphic hint image for a specific column in the bookshelf.
     * If there's enough space in the column for the sizeOrderedTiles, a green triangle image is set,
     * otherwise, a red triangle image is set.
     *
     * @param column The column number
     * @param sizeOrderedTiles The size of the ordered tiles
     * @param bookshelf The Bookshelf object that contains the columns
     * @param imageView The ImageView object where the hint image is set
     */
     private void setColumnGraphicHintImage(int column, int sizeOrderedTiles,Bookshelf bookshelf,ImageView imageView){

        String greenHint = "/Graphics/misc/120px-Green_triangle.svg.png";
        ImageView greenHintImageView = new ImageView(greenHint);

        String redHint = "/Graphics/misc/Red_triangle.svg.png";
        ImageView redHintImageView = new ImageView(redHint);

         if (checkEnoughSpaceInColumn(column,sizeOrderedTiles,bookshelf)){

            imageView.setImage(greenHintImageView.getImage());
             imageView.setEffect(new javafx.scene.effect.DropShadow());
             imageView.setEffect(new Bloom());
         }else{

             imageView.setImage(redHintImageView.getImage());

         }

     }


    /**
     * Handles the insertion of tiles into the first column of the bookshelf when a mouse event is detected.
     * If the graphic hint for the column is green, meaning there's enough space for the tiles,
     * the tiles are inserted, the graphic hint is lit up, and other threads waiting on this object are notified.
     * If the graphic hint is not green, meaning there isn't enough space, an error alert is shown.
     *
     * @param mouseEvent The MouseEvent that triggers the method call
     */
    public synchronized void insertInColumn0(MouseEvent mouseEvent) {

        String greenHint = "120px-Green_triangle.svg.png";

        String column0GraphicHintString = column0GraphicHint.getImage().getUrl();

        if (column0GraphicHintString.contains(greenHint)){
            column0GraphicHint.setEffect(new Lighting());
            resultColumn = 0;
            insertDone = true;
            positionsChosenByTurnPlayerInBookshelf = gameView.getTurnPlayerBookshelf().calculatePositionsInBookshelf(resultColumn,tilesToInsert.size());

            insertIntoBookshelf(0,tilesToInsert);
            makeOrderedTilesDisappear();
            notifyAll();
        }else {

            showAlert(Alert.AlertType.ERROR,"Wrong Insert Move","there is not enough space in the column for tiles ");

        }
    }


    /**
     * Handles the insertion of tiles into the second column of the bookshelf when a mouse event is detected.
     * If a tile has not yet been inserted and the graphic hint for the column is green, indicating there's enough space for the tiles,
     * the tiles are inserted, the graphic hint is lit up, and other threads waiting on this object are notified.
     * If a tile has already been inserted or there isn't enough space in the column, an error alert is shown.
     *
     * @param mouseEvent The MouseEvent that triggers the method call
     */
    public synchronized void insertInColumn1(MouseEvent mouseEvent) {

        String greenHint = "120px-Green_triangle.svg.png";

        String column1GraphicHintString = column1GraphicHint.getImage().getUrl();
    if (!insertDone) {
        if (column1GraphicHintString.contains(greenHint)) {
            column1GraphicHint.setEffect(new Lighting());
            resultColumn = 1;
            insertDone = true;
            positionsChosenByTurnPlayerInBookshelf = gameView.getTurnPlayerBookshelf().calculatePositionsInBookshelf(resultColumn,tilesToInsert.size());
            insertIntoBookshelf(1, tilesToInsert);
            makeOrderedTilesDisappear();
            notifyAll();

        } else {
            showAlert(Alert.AlertType.ERROR, "Wrong Insert Move", "there is not enough space in the column for tiles ");
        }
    } else{
        showAlert(Alert.AlertType.ERROR, "Wrong Insert Move", "Tile already inserted");
    }
    }

    /**
     * Handles the insertion of tiles into the third column of the bookshelf when a mouse event is detected.
     * If the graphic hint for the column is green, indicating there's enough space for the tiles, the tiles are inserted,
     * the graphic hint is lit up, and other threads waiting on this object are notified.
     * If there isn't enough space in the column, an error alert is shown.
     *
     * @param mouseEvent The MouseEvent that triggers the method call
     */
    public synchronized void insertInColumn2(MouseEvent mouseEvent) {

        String greenHint = "120px-Green_triangle.svg.png";

        String column2GraphicHintString = column2GraphicHint.getImage().getUrl();


        if (column2GraphicHintString.contains(greenHint)){
            column2GraphicHint.setEffect(new Lighting());
            resultColumn = 2;
            insertDone = true;
            positionsChosenByTurnPlayerInBookshelf = gameView.getTurnPlayerBookshelf().calculatePositionsInBookshelf(resultColumn,tilesToInsert.size());

            insertIntoBookshelf(2,tilesToInsert);
            makeOrderedTilesDisappear();
            notifyAll();

        }else {

            showAlert(Alert.AlertType.ERROR,"Wrong Insert Move","there is not enough space in the column for tiles ");

        }
    }

    /**
     * Handles the insertion of tiles into the fourth column of the bookshelf when a mouse event is detected.
     * If a tile has not yet been inserted and the graphic hint for the column is green, indicating there's enough space for the tiles,
     * the tiles are inserted, the graphic hint is lit up, and other threads waiting on this object are notified.
     * If a tile has already been inserted or there isn't enough space in the column, an error alert is shown.
     *
     * @param mouseEvent The MouseEvent that triggers the method call
     */
    public synchronized void insertInColumn3(MouseEvent mouseEvent) {

        String greenHint = "120px-Green_triangle.svg.png";

        String column3GraphicHintString = column3GraphicHint.getImage().getUrl();


        if (column3GraphicHintString.contains(greenHint)){
            column3GraphicHint.setEffect(new Lighting());
            resultColumn = 3;

            insertDone = true;
            positionsChosenByTurnPlayerInBookshelf = gameView.getTurnPlayerBookshelf().calculatePositionsInBookshelf(resultColumn,tilesToInsert.size());

            insertIntoBookshelf(3,tilesToInsert);
            makeOrderedTilesDisappear();
            notifyAll();

        }else {

            showAlert(Alert.AlertType.ERROR,"Wrong Insert Move","there is not enough space in the column for tiles ");

        }
    }


    /**
     * Handles the insertion of tiles into the fifth column of the bookshelf when a mouse event is detected.
     * If the graphic hint for the column is green, indicating there's enough space for the tiles, the tiles are inserted,
     * the graphic hint is lit up, and other threads waiting on this object are notified.
     * If there isn't enough space in the column, an error alert is shown.
     *
     * @param mouseEvent The MouseEvent that triggers the method call
     */
    public synchronized void insertInColumn4(MouseEvent mouseEvent) {

        String greenHint = "120px-Green_triangle.svg.png";

        String column4GraphicHintString = column4GraphicHint.getImage().getUrl();


        if (column4GraphicHintString.contains(greenHint)){
            column4GraphicHint.setEffect(new Lighting());
            resultColumn = 4;
            insertDone = true;
            positionsChosenByTurnPlayerInBookshelf = gameView.getTurnPlayerBookshelf().calculatePositionsInBookshelf(resultColumn,tilesToInsert.size());
            insertIntoBookshelf(4,tilesToInsert);
            makeOrderedTilesDisappear();
           this.notifyAll();

        }else {

            showAlert(Alert.AlertType.ERROR,"Wrong Insert Move","there is not enough space in the column for tiles ");

        }
    }


    /**
     * Chooses the column where tiles have been inserted. This method will block
     * until the insertion has been completed (i.e., 'insertDone' is true),
     * then it waits for a specified amount of time and returns the column where
     * the insertion occurred. Other threads are expected to call notify() or notifyAll()
     * to wake up this thread after setting 'insertDone' to true.
     *
     * @return The column where the tiles have been inserted.
     * @throws InterruptedException If the thread was interrupted while waiting for an insertion to be done.
     */
    public synchronized int chooseColumn() throws InterruptedException {

        while (!insertDone){
           this.wait();
        }
        TimeUnit.MILLISECONDS.sleep(2500);
        removeTilesChosenByTurnPlayerInBookshelf();
        return resultColumn;


    }


    /**
     * Displays an alert dialog box with a customizable message, title, and alert type.
     *
     * @param alertType The type of the alert, which can be INFORMATION, WARNING, ERROR, CONFIRMATION, or NONE.
     * @param title The title of the alert dialog box.
     * @param message The main content message to be displayed in the alert dialog box.
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
     * Inserts a list of Tile objects into a specific column of the bookshelf.
     *
     * @param column The column number where the tiles should be inserted.
     * @param tiles The list of Tile objects to be inserted.
     */
    public void insertIntoBookshelf (int column, List<Tile> tiles){

        for (Tile x: tiles) {

            addTile(column,x);

        }

    }



    /**
     * Adds a tile to a specific column in the bookshelf. The tile is added at the first
     * available space from bottom to top. An image view is created for the tile and added
     * to a grid pane representing the bookshelf.
     *
     * @param column The column number where the tile should be added.
     * @param tile The Tile object to be added.
     */
    public void addTile(int column, Tile tile){

        int rows = bookshelf.getRows();
        Tile[][] matrix = bookshelf.getMatrix();
        boolean spaceFound = false;

        for (int i = rows-1; i < rows && !spaceFound; i--) {

            if (matrix[i][column] == null){

                matrix[i][column] = tile;
                String tileGuiReference = matrix[i][column].getGuiReference();
                ImageView imageView = new ImageView(new Image(tileGuiReference));
                imageView.setFitWidth(70);
                imageView.setFitHeight(70);
                gridPaneBookshelf.add(imageView, column, i);
                spaceFound= true;
                fadeTransition(2000,imageView);

            }

        }

    }

    /**
     * Makes the image views of the ordered tiles disappear by applying a fade transition
     * and then setting the image to null. The method works on a copy of the image views list
     * to avoid concurrent modification issues.
     */
    private void makeOrderedTilesDisappear(){

        List<ImageView> imageViewList = new ArrayList<>(imageViewsOrderedTiles);

        for (ImageView i: imageViewList) {
            fadeTransition(2000,i);
            i.setImage(null);

        }
    }

    /**
     * Sets the names of the players in the game. The names are fetched from a list of PlayerInGame objects.
     *
     * @param players The list of PlayerInGame objects from which the names are fetched. Each player name is set
     *                on the corresponding playerName text field. The first two players are always set.
     *                If there are more than two players, the third and fourth player names are set conditionally.
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
     * Sets the images of common tokens on the user interface based on the given list of common goal cards.
     *
     * @param commonGoalCards A list of common goal cards that have common tokens.
     *                        Each common goal card can have multiple common tokens.
     *                        The method sets the images of the first common token of the first two common goal cards
     *                        if they are not null and if their points are not zero.
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
     * Creates a fade transition for an ImageView.
     * The fade transition changes the opacity of the image from 0.0 to 1.0 over a specified duration.
     *
     * @param durationInMilliseconds The duration of the fade transition in milliseconds.
     * @param imageView The ImageView for which the fade transition is created.
     */
    private void fadeTransition(int durationInMilliseconds,ImageView imageView){

        FadeTransition ft = new FadeTransition(Duration.millis(durationInMilliseconds), imageView);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();
    }



    public void removeTilesChosenByTurnPlayerInBookshelf(){

        for (Position x: positionsChosenByTurnPlayerInBookshelf) {

            gameView.getTurnPlayerBookshelf().removeTileFromBookshelf(x);

        }

    }


}

