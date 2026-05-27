package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonToken;
import it.polimi.ingsw.model.Tile;

import java.util.HashSet;
import java.util.Set;

public class C10 extends CommonGoalCard{
    public C10() {

        super.description = """
                Two lines each formed by 5 different
                types of tiles. One line can show the
                same or a different combination of the
                other line.
                """ ;
        this.guiReference = "/Graphics/common_goal_cards/6.jpg";

    }

    @Override
    public CommonToken compare(Bookshelf bookshelf) {

        Tile[][] playerMatrix = bookshelf.getMatrix();
        boolean goalAchieved = false;

        int count = 0;

        for(int i = 0; i < bookshelf.getRows(); i++) {

            Set<Tile> rowTiles = new HashSet<>();

            for (int j = 0; j < bookshelf.getColumns(); j++) {

                Tile tile = playerMatrix[i][j];

                if (rowTiles.contains(tile)) {

                    break;

                }

                rowTiles.add(tile);

                if (rowTiles.size() == 5) {

                    count++;

                }
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
