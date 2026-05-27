package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonToken;
import it.polimi.ingsw.model.Tile;

public class C7 extends CommonGoalCard{
    public C7() {

        super.description = """
                Five tiles of the same type forming a
                diagonal.
                """ ;
        this.guiReference = "/Graphics/common_goal_cards/11.jpg";

    }

    @Override
    public CommonToken compare(Bookshelf bookshelf) {


        Tile[][] playerMatrix = bookshelf.getMatrix();
        boolean goalAchieved = false;

        if (playerMatrix[0][0] != null && playerMatrix[1][1] != null && playerMatrix[2][2] != null &&
            playerMatrix[3][3] != null && playerMatrix[4][0] != null && playerMatrix[3][1] != null &&
            playerMatrix[1][3] != null && playerMatrix[1][0] != null && playerMatrix[2][1] != null &&
            playerMatrix[3][2] != null && playerMatrix[4][3] != null && playerMatrix[5][0] != null &&
            playerMatrix[4][1] != null && playerMatrix[2][3] != null) {

            if (playerMatrix[0][0].equals(playerMatrix[1][1]) && playerMatrix[1][1].equals(playerMatrix[2][2])
                    && playerMatrix[2][2].equals(playerMatrix[3][3]) && playerMatrix[3][3].equals(playerMatrix[4][4])) {

                goalAchieved = true;

            } else if (playerMatrix[4][0].equals(playerMatrix[3][1]) && playerMatrix[3][1].equals(playerMatrix[2][2])
                    && playerMatrix[2][2].equals(playerMatrix[1][3]) && playerMatrix[1][3].equals(playerMatrix[0][4])) {

                goalAchieved = true;

            } else if (playerMatrix[1][0].equals(playerMatrix[2][1]) && playerMatrix[2][1].equals(playerMatrix[3][2])
                    && playerMatrix[3][2].equals(playerMatrix[4][3]) && playerMatrix[4][3].equals(playerMatrix[5][4])) {

                goalAchieved = true;

            } else if (playerMatrix[5][0].equals(playerMatrix[4][1]) && playerMatrix[4][1].equals(playerMatrix[3][2])
                    && playerMatrix[3][2].equals(playerMatrix[2][3]) && playerMatrix[2][3].equals(playerMatrix[1][4])) {

                goalAchieved = true;

            }
        }

        if (goalAchieved){

            return assignCommonTokenAndRemoveFromList();

        }else

            return new CommonToken();                     //il costruttore di default inserisce come punteggio di default 0
    }

    }
