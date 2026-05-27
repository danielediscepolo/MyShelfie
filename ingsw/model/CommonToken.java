package it.polimi.ingsw.model;

import java.io.Serial;
import java.io.Serializable;

public class CommonToken extends Token implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;


    String guiReference ;

    public CommonToken() {

        super(0);
    }

    public CommonToken(int points) {

        super(points);

        if (points == 8){

            guiReference = "/Graphics/scoring_tokens/scoring_8.jpg";
        } else if (points == 6) {
            guiReference = "/Graphics/scoring_tokens/scoring_6.jpg";
        }else if (points == 4 ){
            guiReference = "/Graphics/scoring_tokens/scoring_4.jpg";
        }else {
            guiReference = "/Graphics/scoring_tokens/scoring_2.jpg";

        }
    }

    @Override
    public int getPoints() {

        return super.getPoints();
    }


    public String getGuiReference() {
        return guiReference;
    }
}
