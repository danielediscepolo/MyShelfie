package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonToken;
import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;

public class C8 extends CommonGoalCard{
    public C8() {
        super.description = """
                Four lines each formed by 5 tiles of
                maximum three different types. One
                line can show the same or a different
                combination of another line.
                """ ;
        this.guiReference = "/Graphics/common_goal_cards/7.jpg";

    }

    @Override
    public CommonToken compare(Bookshelf bookshelf) {


        Tile[][] playerMatrix = bookshelf.getMatrix();
        boolean goalAchieved = false;
        int countRows = 0;
        int countDifferentTypes;
        int countTilesPerRow;



        for (int i = 0; i < bookshelf.getRows(); i++) {

            countTilesPerRow = 0;

            List<Tile> differentTiles = new ArrayList<>();

            for (int j = 0; j < bookshelf.getColumns(); j++) {

                if (playerMatrix[i][j] != null) {

                    countTilesPerRow++;

                    countDifferentTypes = 0;

                    for (Tile x : differentTiles) {

                        if (!playerMatrix[i][j].equals(x)) {

                            countDifferentTypes++;

                        }

                    }

                    if (countDifferentTypes == differentTiles.size()){

                        differentTiles.add(playerMatrix[i][j]);
                    }

                }

            }

            if (differentTiles.size() < 4 && countTilesPerRow == 5) {

                countRows++;

            }


        }


        if (countRows > 3){

            goalAchieved = true;

        }

        if (goalAchieved){

            return assignCommonTokenAndRemoveFromList();

        }else

            return new CommonToken();                     //il costruttore di default inserisce come punteggio di default 0
    }

}
