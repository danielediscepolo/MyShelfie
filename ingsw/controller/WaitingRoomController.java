package it.polimi.ingsw.controller;

import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class WaitingRoomController extends Transition {

    @FXML
    private ImageView myShelfieLogo;


    /**
     * Starts the rotation animation of the logo image.
     * It creates a RotateTransition object and configures it with a rotation angle, cycle count, and auto-reverse behavior.
     * The animation is played when the method is called.
     */
    @FXML
    public void rotation(){

        RotateTransition rt = new RotateTransition(Duration.INDEFINITE, myShelfieLogo);
        rt.setByAngle(180);
        rt.setCycleCount(40);
        rt.setAutoReverse(true);
        rt.play();
    }

    /**
     * It is an overridden method from the Transition class.
     * It is called during the animation and provides the current position in the animation.
     * In this implementation, it creates and plays a RotateTransition animation for the logo image
     *
     * @param v
     */
    @Override
    protected void interpolate(double v) {
        RotateTransition rt = new RotateTransition(Duration.INDEFINITE, myShelfieLogo);
        rt.setByAngle(180);
        rt.setCycleCount(40);
        rt.setAutoReverse(true);
        rt.play();
    }
}
