package it.polimi.ingsw.controller;

import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.awt.event.ActionEvent;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class WelcomeController implements Initializable {
    @FXML
    private Label myLabel;
    @FXML
    private ChoiceBox<String> myChoiceBox;
    @FXML
    private Label myLabel2;
    @FXML
    private TextField chooseName;
    @FXML
    private Button myButton;

    int gamePreference ;

    String playerNickname;

        /**
         * It is an overridden method from the Initializable interface.
         * It is called after the controller's root element has been fully processed and provides an opportunity to initialize the controller.
         * In this implementation, the method configures a choice box myChoiceBox by adding three items representing different game preferences: "2", "3", and "4". These items are added to the choice box's list of items.
         * Additionally, the method sets the event handler for the choice box's onAction event. The event handler is set to the method insertGamePreference, which will be invoked when a selection is made in the choice box.
         * Overall, the initialize method sets up the choice box with game preferences and assigns the event handler to handle selection changes.
         *
         * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
         * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
         */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        myChoiceBox.getItems().add("2");
        myChoiceBox.getItems().add("3");
        myChoiceBox.getItems().add("4");

        myChoiceBox.setOnAction(this::insertGamePreference);
    }


        /**
         * It is invoked when a game preference is selected in the choice box.
         * It retrieves the selected value from the choice box, which is assumed to be a string representation of an integer.
         * The method converts the selected value to an integer using Integer.parseInt() and assigns it to the gamePreference variable.
         * The actionEvent parameter represents the action event triggered by selecting a game preference.
         * It provides information about the event, such as the source of the event and any additional data associated with it.
         *
         * @param actionEvent The event representing the action triggered by selecting a game preference.
         */
        private void insertGamePreference(javafx.event.ActionEvent actionEvent) {
            gamePreference = Integer.parseInt(myChoiceBox.getValue());
        }

        /**
         * It is responsible for validating the login information provided by the user.
         * It checks the length of the nickname and the game preference value, and displays appropriate error messages if they are incorrect.
         * If the login information is valid, it sets the player nickname and notifies any waiting threads.
         * The method uses the showAlert method to display error alerts with specific messages based on the validation results.
         * It also uses the infoBox method to display a success message when the login operation is completed successfully.
         * The method is synchronized to ensure thread safety when accessing and modifying the player nickname and notifying waiting threads.
         */
        @FXML
    public synchronized void checkLogin (){
        playerNickname=null;
        String _playerNickname = chooseName.getText();
        if ((_playerNickname.length()==0 || _playerNickname.length() > 20 ) && gamePreference == 0)
        {
            showAlert(Alert.AlertType.ERROR,"Wrong login information", "Login failed: nickname and " +
                    "gamePreference are both incorrect. Retry!");
        }else if (_playerNickname.length()==0 ){
            showAlert(Alert.AlertType.ERROR,"Wrong nickname", "You have not selected a nickname. Please enter a name.");
        } else if (_playerNickname.length() > 20 ) {
            showAlert(Alert.AlertType.ERROR,"Wrong nickname length", "Your nickname is too long. Please enter less than 20 characters.");
        } else if (gamePreference == 0){
            showAlert(Alert.AlertType.ERROR,"Wrong gamePreference", "Your must select a number of player you want to play with.");

        }else {
            infoBox("Login operation successful completed", null,"Login completed" );
            playerNickname = _playerNickname;
            this.notifyAll();


        }


    }

        /**
         * Waits until the player has logged in by checking the playerNickname variable.
         * It waits using the wait method until the playerNickname is not null.
         * Once the player has logged in, it creates and returns an Object array containing the playerNickname and gamePreference.
         * The method is synchronized to ensure thread safety when accessing the playerNickname variable and waiting for the player to log in.
         * If the thread is interrupted while waiting, it throws an InterruptedException.
         *
         * @return An Object array containing the playerNickname and gamePreference.
         * @throws InterruptedException if the thread is interrupted while waiting for the player to log in.
         */
    public synchronized Object[] welcome () throws InterruptedException {

        while ( playerNickname == null ) {
            this.wait();
        }
        Object[] loginMessage = new Object[2];
        loginMessage[0] = playerNickname;
        loginMessage[1] = gamePreference;
        return loginMessage;

    }

        /**
         * Displays an alert dialog with the specified alertType, title, and message.
         * It creates an Alert object with the given alertType and sets the title, headerText, and contentText properties of the alert dialog.
         * The dialog's background color is set to light coral. Finally, the alert dialog is displayed using the show method.
         *
         * @param alertType the type of alert dialog to display (e.g., ERROR, INFORMATION, WARNING)
         * @param title the title of the alert dialog
         * @param message the message to display in the alert dialog
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
     * Displays an information dialog box with the specified infoMessage, headerText, and title.
     * It creates an Alert object with the AlertType. INFORMATION type and sets the contentText, title, and headerText properties of the dialog.
     * The dialog's background color is set to light blue.
     * The dialog is displayed using the show method. After a delay of 1 second, the dialog is automatically closed.
     *
     * @param infoMessage the message to display in the information dialog
     * @param headerText the header text to display in the information dialog
     * @param title the title of the information dialog
     */
        public  void infoBox(String infoMessage, String headerText, String title){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            javafx.scene.control.DialogPane dialogPane = alert.getDialogPane();
            dialogPane.setBackground(new Background(new BackgroundFill(Color.LIGHTBLUE, CornerRadii.EMPTY, javafx.geometry.Insets.EMPTY)));
            alert.setContentText(infoMessage);
            alert.setTitle(title);
            alert.setHeaderText(headerText);
            alert.show();
            PauseTransition delay = new PauseTransition(Duration.seconds(1));
            delay.setOnFinished(event -> alert.close());
            delay.play();
        }
    }

