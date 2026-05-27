package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonToken;
import it.polimi.ingsw.model.Tile;

import java.util.HashSet;
import java.util.Set;

public class C9 extends CommonGoalCard{
    public C9() {

        super.description = """
                Two columns each formed by 6
                different types of tiles.
                """ ;
        this.guiReference = "/Graphics/common_goal_cards/2.jpg";

    }

    @Override
    public CommonToken compare(Bookshelf bookshelf) {


        Tile[][] playerMatrix = bookshelf.getMatrix();
        boolean goalAchieved = false;

        int count = 0;


        for(int j = 0; j < bookshelf.getColumns(); j++){

                Set<Tile> columnSet = new HashSet<>();

                for(int i = 0; i < bookshelf.getRows(); i++){

                    columnSet.add(playerMatrix[i][j]);

                }
                if(columnSet.size() == 6){

                    count++;

                }

        }


        if (count >= 2){

            goalAchieved = true;

        }




        if (goalAchieved){

            return assignCommonTokenAndRemoveFromList();

        }else

            return new CommonToken();                     //il costruttore di default inserisce come punteggio di default 0
    }

}
