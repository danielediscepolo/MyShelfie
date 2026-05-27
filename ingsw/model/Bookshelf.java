package it.polimi.ingsw.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bookshelf implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final int rows = 6;
    private final int columns = 5;

    private Tile[][] matrix = new Tile[rows][columns];

    /**
     * Default constructor that creates the bookshelf.
     */
    public Bookshelf() {}


    /**
     * Constructor which creates the bookshelf contained in the personalGoalCard. It uses an array of positions
     * according to the following scheme:
     *          posix[0] greenTile row                posix[1] greenTile column
     *          posix[2] whiteTile row                posix[3] whiteTile column
     *          posix[4] yellowTile row               posix[5] yellowTile column
     *          posix[6] BlueTile row                 posix[7] blueTile column
     *          posix[8] LightBlueTile row            posix[9] lightBlueTile column
     *          posix[10] PinkTile row                posix[11] pinkTile column
     *
     * @param posix an array of int that contains all the positions of the tiles in the personalGoalCard.
     */
    public Bookshelf (int[] posix){

        //See createDeckPersonalCard in Patrick for implementation.
        Tile greenTile = new Tile(Color.Green,Figure.Cats);
        Tile whiteTile = new Tile(Color.White,Figure.Books);
        Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
        Tile blueTile = new Tile(Color.Blue,Figure.Frames);
        Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
        Tile pinkTile = new Tile(Color.Pink,Figure.Plants);

        matrix[posix[0]][posix[1]] = greenTile;
        matrix[posix[2]][posix[3]] = whiteTile;
        matrix[posix[4]][posix[5]] = yellowTile;
        matrix[posix[6]][posix[7]] = blueTile;
        matrix[posix[8]][posix[9]] = lightBlueTile;
        matrix[posix[10]][posix[11]] = pinkTile;


    }

    /**
     * Standard constructor that creates the bookshelf with an already initialised matrix.
     *
     * @param matrix already initialised.
     */
    public Bookshelf(Tile[][] matrix) {
        this.matrix = matrix;
    }


    /**
     * Check if the bookshelf is full. Method used by Game to know the first player to complete the bookshelf.
     *
     * @return boolean: true if the bookshelf is full, false otherwise.
     */
    public boolean isFull(){

        int count=0;

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {

                if (matrix[i][j] == null){

                    count ++;
                }

            }

        }

        return count <= 0;

    }

    /**
     * Calculate the maximum vertical free space of the player's bookshelf.
     *
     * @return the number of empty tiles in the freest column of the player's bookshelf.
     */
    public int getMaxColumnFreeSpaceInBookshelf (){
        int max  = 0;

        for (int j = 0; j < columns; j++) {

            int count = 0;

            for (int i = 0; i < rows; i++) {

                if (matrix[i][j] == null){

                    count++;
                }

            }

            if (count > max){

                max = count;
            }
        }

        return max;

    }

    /**
     * Removes a tile from the player's bookshelf.
     *
     * @param position to be removed.
     */
    public void removeTileFromBookshelf(Position position){

        if ( position != null) {

            matrix[position.getRow()][position.getColumn()] = null;

        }

    }

    /**
     * Calculate the positions of the tiles that have been chosen by the player.
     *
     * @param column chosen by the player.
     * @param numOfTiles number of tiles chosen by the player.
     * @return the list of all chosen positions.
     */
    public List<Position> calculatePositionsInBookshelf(int column, int numOfTiles){

        List<Position> positionsChosenByTurnPlayerInBookshelf = new ArrayList<>();

        int foundFreeSpace = 0;

        for (int i = 0; i < rows; i++) {

            if (matrix[i][column] == null && i >= foundFreeSpace){

                foundFreeSpace = i;

            }

        }
            for (int i = 0; i < numOfTiles; i++) {


                positionsChosenByTurnPlayerInBookshelf.add(new Position(foundFreeSpace,column));

                foundFreeSpace--;

            }

        return positionsChosenByTurnPlayerInBookshelf;

    }


    /**
     * Check if there is enough space in the column chosen by the player for the selected number of tiles.
     *
     * @param column chosen by the player.
     * @param numOfTiles number of tiles chosen by the player.
     * @return a boolean: true if there is enough space, false otherwise.
     */
    public boolean checkEnoughSpaceInColumn (int column, int numOfTiles){

        boolean enoughSpace = false;

        int freeSpaces = 0;

        for (int i = 0; i < rows; i++) {

            if (matrix[i][column] == null){

                freeSpaces++;
            }

        }
        if (freeSpaces >= numOfTiles){

            enoughSpace = true;

        }
        return enoughSpace;
    }


    /**
     * Getter method.
     *
     * @return the structure, a matrix of tiles, of the bookshelf.
     */
    public Tile[][] getMatrix() {
        return matrix;
    }

    /**
     * Getter method.
     *
     * @return the number of rows of the bookshelf.
     */
    public int getRows() {
        return rows;
    }


    /**
     * Getter method.
     *
     * @return the number of columns of the bookshelf.
     */
    public int getColumns() {
        return columns;
    }


    /**
     * Add a tile to the bookshelf.
     *
     * @param column where the tile is inserted.
     * @param tile to be inserted.
     */
    public void addTile(int column, Tile tile){

        boolean spaceFound = false;

        for (int i = rows-1; i < rows && !spaceFound; i--) {

            if (matrix[i][column] == null){

                matrix[i][column] = tile;
                spaceFound= true;

            }

        }

    }

    /**
     * Print method.
     * Print the bookshel on StandardOut, according to the toString of Tile.
     */
    public void printBookshelf() {

        // Determine the maximum length of any object string in the table
        int maxCellLength = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Tile cellValue = matrix[i][j];
                if (cellValue != null) {
                    String cellString = cellValue.toString();
                    if (cellString.length() > maxCellLength) {
                        maxCellLength = cellString.length();
                    }
                }
            }
        }

        // Print the table with column headers
        System.out.print("  ");
        for (int j = 1; j <= columns; j++) {
            System.out.print("Col"+j);
            for (int k = 1; k <= maxCellLength - 1; k++) {
                System.out.print(" ");
            }
            System.out.print(" ");
        }
        System.out.println();
        for (int i = 0; i < rows; i++) {
            System.out.print((i+1) + " ");
            for (int j = 0; j < columns; j++) {
                Object cellValue = matrix[i][j];
                if (cellValue != null) {
                    System.out.print("  " + cellValue);
                    String cellString = cellValue.toString();
                    for (int k = 1; k <= maxCellLength - cellString.length(); k++) {
                        System.out.print(" ");
                    }
                } else {
                    for (int k = 1; k <= maxCellLength + 2; k++) {
                        System.out.print(" ");
                    }
                }
                System.out.print("| ");
            }
            System.out.println();
            for (int k = 0; k < columns; k++) {
                for (int l = 0; l < maxCellLength + 3; l++) {
                    System.out.print("-");
                }
            }
            System.out.println();
        }
    }
}



