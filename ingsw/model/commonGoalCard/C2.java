package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.Bookshelf;
import it.polimi.ingsw.model.CommonToken;
import it.polimi.ingsw.model.Sequence;
import it.polimi.ingsw.model.Tile;

import java.util.ArrayList;
import java.util.List;

public class C2 extends CommonGoalCard{

    public C2() {

        super.description ="""
                     Four groups each containing at least
                     4 tiles of the same type (not necessarily
                     in the depicted shape).
                     The tiles of one group can be different
                     from those of another group.
                     """ ;
        this.guiReference = "/Graphics/common_goal_cards/3.jpg";

    }

    @Override
    public CommonToken compare(Bookshelf bookshelf) {


        Tile[][] playerMatrix = bookshelf.getMatrix();
        boolean goalAchieved = false;
        int countSequences = 0;

        List<Sequence> foundSequences = searchSequences(bookshelf);


        for (Sequence x: foundSequences) {

            if (x.getLength() > 3){

                countSequences++;
            }

        }

        if (countSequences > 3 ){

            goalAchieved = true;
        }


        if (goalAchieved){

            return assignCommonTokenAndRemoveFromList();

        }else

            return new CommonToken();                     //il costruttore di default inserisce come punteggio di default 0
    }


    public List<Sequence> searchSequences (Bookshelf bookshelf){

        Tile[][] playerMatrix = bookshelf.getMatrix();
        int rows = bookshelf.getRows();
        int cols =  bookshelf.getColumns();

        List<Sequence> sequenceList = new ArrayList<>();
        boolean[][] visited = new boolean[rows][cols];
        //Start scrolling the array and for each element not yet visited
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!visited[i][j]) {
                    //Call the visit function on the current position
                    int sequenceLength = visit(i, j, playerMatrix, visited,bookshelf);
                    //If the counter returned by the visit function is greater than one, it means that a sequence of at least two identical adjacent elements has been found
                    if (sequenceLength > 1) {
                        //Save the position in a list of sequences found
                        sequenceList.add(new Sequence(i, j, sequenceLength));
                    }
                }
            }
        }
        return sequenceList;
    }


    public int visit(int i, int j, Tile[][] matrix, boolean[][] visited,Bookshelf bookshelf) {

        int rows = bookshelf.getRows();
        int cols =  bookshelf.getColumns();

        //Checking if the current position has already been visited or if it is possibly outside the dimensions of the matrix
        if (i < 0 || i >= rows || j < 0 || j >= cols || visited[i][j]) {
            return 0;
        }
        //Initializing variables
        int counter = 1;
        visited[i][j] = true;
        //Checking adjacent elements
        if (i+1 < rows && matrix[i+1][j] != null && matrix[i+1][j].equals(matrix[i][j])) {
            counter += visit(i+1, j, matrix, visited,bookshelf);
        }
        if (i-1 >= 0 && matrix[i-1][j] != null && matrix[i-1][j].equals(matrix[i][j])) {
            counter += visit(i-1, j, matrix, visited,bookshelf);
        }
        if (j+1 < cols && matrix[i][j+1] != null && matrix[i][j+1].equals(matrix[i][j])) {
            counter += visit(i, j+1, matrix, visited,bookshelf);
        }
        if (j-1 >= 0 && matrix[i][j-1] != null && matrix[i][j-1].equals(matrix[i][j])) {
            counter += visit(i, j-1, matrix, visited,bookshelf);
        }
        return counter;
    }




}


