package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoalCard.C6;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.view.gui.CallbackRunner;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;


import javax.naming.Context;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateModelController implements Initializable {


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

    private Bookshelf bookshelf;

    private GameView gameView;







    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets up event handlers for mouse movement and mouse exit events on buttons (MyPlayer1, MyPlayer2, MyPlayer3, MyPlayer4).
     * When the mouse is moved over a button, its background color is set to green, and when the mouse exits the button, its background color is set to white.
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
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
     * Sets the game view with the specified GameView object and nickname.
     * Iterates over the players in the game view and finds the player with the matching nickname.
     * Calls the setBookshelf method to set the bookshelf for the player.
     * Calls the setPersonalGoalCard method to set the personal goal card for the player.
     * Sets the common goal cards and their descriptions.
     * Calls the setBoard method to set the board.
     * Calls the setEndGameToken method to set the end game token for the players.
     * Calls the setPlayerNames method to set the player names.
     * Calls the setCommonTokens method to set the common tokens.
     *
     * @param gameView  The GameView object containing the game information.
     * @param nickname  The nickname of the player.
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
        setEndGameToken(gameView.getPlayers());
        setPlayerNames(this.gameView.getPlayers());
        setCommonTokens(this.gameView.getCommonGoalCards());

    }


    /**
     * Sets the board with the specified Board object.
     * It iterates over the rows and columns of the board and checks if a tile is present at each position.
     * If a tile exists, it creates an ImageView with the corresponding GUI reference and adds it to the grid pane at the specified cell.
     * The dimensions of the image view are set to 49 pixels for both width and height.
     *
     * @param board The Board object containing the board information.
     */
    private void setBoard(Board board) {
        this.board = board;


        int NUM_ROWS = board.getRows();
        int NUM_COLS = board.getColumns();

        Tile[][] boardBoxes = board.getBoxes();
        for (int row = 0; row < NUM_ROWS; row++) {
            for (int col = 0; col < NUM_COLS; col++) {

                if (boardBoxes[row][col] != null){
                    String tileGuiReference = boardBoxes[row][col].getGuiReference();
                    ImageView imageView = new ImageView(new Image(tileGuiReference));
                    imageView.setFitWidth(49); // Set width to 44 pixels
                    imageView.setFitHeight(49); // Set height to 44 pixels
                    gridPaneBoard.add(imageView, col, row); // Add to cell (col, row)
                }
            }
        }
    }

    /**
     * Sets the bookshelf with the specified Bookshelf object.
     * It iterates over the rows and columns of the bookshelf and checks if a tile is present at each position.
     * If a tile exists, it creates an ImageView with the corresponding GUI reference and adds it to the grid pane at the specified cell.
     * The dimensions of the image view are set to 44 pixels for both width and height.
     *
     * @param bookshelf The Bookshelf object containing the bookshelf information.
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
     * Sets the personal goal card with the specified PersonalGoalCard object.
     * It retrieves the GUI reference from the personal goal card and creates an ImageView using that reference.
     * Finally, it sets the image of the ImageView to the personalGoalCard image view.
     *
     * @param personalGoalCard The PersonalGoalCard object containing the personal goal card information.
     */
    public void setPersonalGoalCard(PersonalGoalCard personalGoalCard) {

        String personalGoalCardGuiReference = personalGoalCard.getGuiReference();

        ImageView personalCardImageView = new ImageView(personalGoalCardGuiReference);

        this.personalGoalCard.setImage(personalCardImageView.getImage());
    }


    /**
     * Sets the first common goal card with the specified CommonGoalCard object.
     * It retrieves the GUI reference from the common goal card and creates an ImageView using that reference.
     * Finally, it sets the image of the ImageView to the commonGoalCard1 image view.
     *
     * @param commonGoalCard1 The CommonGoalCard object containing the common goal card information.
     */
    public void setCommonGoalCard1(CommonGoalCard commonGoalCard1){

        String commonGoalCardGuiReference = commonGoalCard1.guiReference;

        ImageView commonCard1ImageView = new ImageView(commonGoalCardGuiReference);

        this.commonGoalCard1.setImage(commonCard1ImageView.getImage());
    }


    /**
     * Sets the second common goal card with the specified CommonGoalCard object.
     * It retrieves the GUI reference from the common goal card and creates an ImageView using that reference.
     * Finally, it sets the image of the ImageView to the commonGoalCard2 image view.
     *
     * @param commonGoalCard2 The CommonGoalCard object containing the common goal card information.
     */
    public void setCommonGoalCard2(CommonGoalCard commonGoalCard2) {

        String commonGoalCardGuiReference = commonGoalCard2.guiReference;

        ImageView commonCard2ImageView = new ImageView(commonGoalCardGuiReference);

        this.commonGoalCard2.setImage(commonCard2ImageView.getImage());
    }

    /**
     * Sets the description of the first common goal card.
     *
     * @param commonGoalCard1Description The description of the first common goal card.
     */
    public void setCommonGoalCard1Description(String commonGoalCard1Description) {
        this.commonGoalCard1Description.setText(commonGoalCard1Description);

    }

    /**
     * Sets the description of the second common goal card.
     *
     * @param commonGoalCard2Description The description of the second common goal card.
     */
    public void setCommonGoalCard2Description(String commonGoalCard2Description) {
        this.commonGoalCard2Description.setText(commonGoalCard2Description);
    }

    /**
     * Sets the end game token based on the players' information.
     *  It checks each player in the game and if a player has the end game token, it sets the image of the endgameToken to null, indicating that the end game token is present.
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
     * Sets the common tokens based on the common goal cards.
     * It checks the first common goal card and if the first common token exists and has non-zero points, it sets the image of commonToken1 to the corresponding image.
     * Similarly, it checks the second common goal card and sets the image of commonToken2 if the token exists and has non-zero points.
     *
     * @param commonGoalCards The list of common goal cards.
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
     * Sets the names of the players in the GUI.
     * It expects a list of players and sets the text of playerName1 to the name of the first player, playerName2 to the name of the second player.
     * If the list contains more than two players, it sets playerName3 to the name of the third player.
     * If the list contains more than three players, it sets playerName4 to the name of the fourth player.
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
}


