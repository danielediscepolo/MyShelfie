package it.polimi.ingsw.controller;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class EndgameController implements Initializable {


    @FXML
    private javafx.scene.control.Label andTheWinnerIsLabel;

    @FXML
    private javafx.scene.control.Label winnerLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }


    public void setWinnerLabel(String winnerName) {

        winnerLabel.setText(winnerName);



        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), andTheWinnerIsLabel);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(1.0);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);

        fadeTransition.play();

        winnerLabel.setOpacity(0.0);


        FadeTransition fadeTransition2 = new FadeTransition(Duration.seconds(3), winnerLabel);
        fadeTransition2.setFromValue(0.0);
        fadeTransition2.setToValue(2.0);
        fadeTransition2.setCycleCount(1);
        fadeTransition2.setAutoReverse(false);

        fadeTransition2.play();


    }
}
