package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonToken;
import it.polimi.ingsw.model.Tile;

public class C3 extends CommonGoalCard{

    public C3() {

    super.description = """
            Four tiles of the same type in the four
            corners of the bookshelf.
            """ ;
        this.guiReference = "/Graphics/common_goal_cards/8.jpg";

    }

    @Override
    public CommonToken compare(Bookshelf bookshelf) {

        Tile[][] playerMatrix = bookshelf.getMatrix();
        boolean goalAchieved = false;

        if (playerMatrix[0][0] != null && playerMatrix[0][4] != null && playerMatrix[5][0] != null) {

            goalAchieved = playerMatrix[0][0].equals(playerMatrix[0][4]) && playerMatrix[0][4].equals(playerMatrix[5][0])
                    && playerMatrix[5][0].equals(playerMatrix[5][4]);
        }

        if (goalAchieved){

            return assignCommonTokenAndRemoveFromList();

        }else

            return new CommonToken();
    }

}
