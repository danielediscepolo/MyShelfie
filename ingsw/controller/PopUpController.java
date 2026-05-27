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


public class PopUpController implements Initializable {


    @FXML
    private Label myLabel;

    private boolean choiceDone = false;
    private Boolean result ;

    /**
     * Initializes the controller.
     *
     * @param url            The location used to resolve relative paths
     * @param resourceBundle The resources used to localize the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    /**
     * Handles the action when the confirm button is clicked.
     *
     * @param actionEvent The action event triggered by the confirm button
     */
    public synchronized void confirmAction(ActionEvent actionEvent) {
        result = true;
        choiceDone = true;
        notifyAll();
    }

    /**
     * Handles the action when the retry button is clicked.
     *
     * @param actionEvent The action event triggered by the retry button
     */
    public synchronized void retryAction(ActionEvent actionEvent) {
        result = false;
        choiceDone = true;
        notifyAll();
    }


    /**
     * Waits for the user to confirm their choice.
     *
     * @return The result of the user's choice
     * @throws InterruptedException if any thread interrupted the current thread
     */
    public synchronized boolean confirmChoice() throws InterruptedException {

        while (!choiceDone) {
            this.wait();
        }

        return result;

    }


    /**
     * Sets the text of the label in the pop-up window.
     *
     * @param s The text to be set
     */
    public void setMyLabel(String s){

        myLabel.setText(s);
    }

}
