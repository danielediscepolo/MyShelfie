package it.polimi.ingsw.model;

import java.io.Serial;
import java.io.Serializable;

public abstract class Token implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final int points;

    public Token(int points) {
        this.points = points;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public String toString() {
        return "Token{" +
                "points=" + points +
                '}';
    }
}
