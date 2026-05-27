package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonToken;
import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;

public class C12 extends CommonGoalCard {
    public C12() {
        super.description = """
                Five columns of increasing or decreasing
                height. Starting from the first column on
                the left or on the right, each next column
                must be made of exactly one more tile.
                Tiles can be of any type.
                """ ;
        this.guiReference = "/Graphics/common_goal_cards/12.jpg";

    }

    @Override
    public CommonToken compare(Bookshelf bookshelf) {


        Tile[][] playerMatrix = bookshelf.getMatrix();
        boolean goalAchieved = false;
        Integer countTilesPerCol ;

        List<Integer> tilesPerColLeft = new ArrayList<>();
        List<Integer> tilesPerColRight = new ArrayList<>();



        //leftSide
        for (int j = 0; j < bookshelf.getColumns(); j++) {

            countTilesPerCol = 0;

            for (int i = 0; i < bookshelf.getRows(); i++) {

                if (playerMatrix[i][j] != null) {

                    countTilesPerCol ++;

                }

            }

            tilesPerColLeft.add(countTilesPerCol);

        }

        //left side
        int countLeft = 0;

        for (int i = 0; i < tilesPerColLeft.size(); i++) {

           if (i < tilesPerColLeft.size()-1){


                Integer heightColumn = tilesPerColLeft.get(i);

                Integer heightColumnPost= tilesPerColLeft.get(i+1);

                Integer one = 1;

                if (heightColumn - one == heightColumnPost){

                    countLeft++;

                }

            }
        }


        if (tilesPerColLeft.get(tilesPerColLeft.size()-1) == 2){

            countLeft++;

        }


        //rightSide
        for (int j = bookshelf.getColumns()-1; j >= 0; j--) {

            countTilesPerCol = 0;

            for (int i = 0; i < bookshelf.getRows(); i++) {

                if (playerMatrix[i][j] != null) {

                    countTilesPerCol ++;

                }

            }

            tilesPerColRight.add(countTilesPerCol);

        }



        int countRight= 0;

        for (int i = 0; i < tilesPerColRight.size(); i++) {

          if (i < tilesPerColRight.size()-1){

                Integer heightColumn = tilesPerColRight.get(i);

                Integer heightColumnPost= tilesPerColRight.get(i+1);

                Integer one = 1;

                if (heightColumn - one == heightColumnPost ){

                    countRight++;

                }

            }
        }



        if (tilesPerColRight.get(tilesPerColRight.size()-1) == 2){

            countRight++;

        }


        if (countLeft == bookshelf.getColumns() || countRight == bookshelf.getColumns()){

            goalAchieved = true;
        }



        if (goalAchieved) {

            return assignCommonTokenAndRemoveFromList();

        } else

            return new CommonToken();                     //il costruttore di default inserisce come punteggio di default 0
    }

}
