package it.polimi.ingsw.model;

import java.io.Serial;
import java.io.Serializable;

public class EndGameToken extends Token {

    public EndGameToken() {
        super(1);
    }

    @Override
    public int getPoints() {
        return super.getPoints();
    }

    public String getGuiReference() {
        return "Graphics/scoring_tokens/end game.jpg";
    }
}
