package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonToken;
import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;

public class C11 extends CommonGoalCard{
    public C11() {

        super.description = """
                Five tiles of the same type forming an X.
                """ ;
        this.guiReference = "/Graphics/common_goal_cards/10.jpg";

    }

    @Override
    public CommonToken compare(Bookshelf bookshelf) {


        Tile[][] playerMatrix = bookshelf.getMatrix();
        boolean goalAchieved = false;

        List<int []> occurences = findOccurrences(playerMatrix);

        if (occurences.size() > 0){

            goalAchieved = true;

        }

        if (goalAchieved){

            return assignCommonTokenAndRemoveFromList();

        }else

            return new CommonToken();                     //il costruttore di default inserisce come punteggio di default 0
    }



    public static List<int[]> findOccurrences(Object[][] matrix) {
        List<int[]> occurrences = new ArrayList<>();

        int numRows = matrix.length;
        int numCols = matrix[0].length;


        for (int i = 0; i < numRows - 2 ; i++) {

            for (int j = 0; j < numCols - 2; j++) {

                if (matrix[i][j] != null && matrix[i][j].equals(matrix[i+2][j]) &&
                        matrix[i][j].equals(matrix[i][j+2]) &&
                        matrix[i][j].equals(matrix[i+2][j+2]) &&
                        matrix[i][j].equals(matrix[i+1][j+1])){

                    occurrences.add( new int []{i,j});

                }

            }
            
        }


        return occurrences;
    }

}
