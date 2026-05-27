package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonToken;
import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;

public class C5 extends CommonGoalCard{

    public C5() {

        super.description = """
                Three columns each formed by 6 tiles
                of maximum three different types. One
                column can show the same or a different
                combination of another column.
                 """ ;
        this.guiReference = "/Graphics/common_goal_cards/5.jpg";

    }

    @Override
    public CommonToken compare(Bookshelf bookshelf) {


        Tile[][] playerMatrix = bookshelf.getMatrix();
        boolean goalAchieved = false;


        int countCols = 0;
        int countDifferentTypes;
        int countTilesPerCol;



        for (int j = 0; j < bookshelf.getColumns(); j++) {

            countTilesPerCol= 0;

            List<Tile> differentTiles = new ArrayList<>();

            for (int i = 0; i < bookshelf.getRows(); i++) {

                if (playerMatrix[i][j] != null) {

                    countTilesPerCol++;

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

            if (differentTiles.size() < 4 && countTilesPerCol == 6) {

                countCols++;

            }


        }


        if (countCols > 3){

            goalAchieved = true;

        }


        if (goalAchieved){

            return assignCommonTokenAndRemoveFromList();

        }else

            return new CommonToken();                     //il costruttore di default inserisce come punteggio di default 0
    }

}


