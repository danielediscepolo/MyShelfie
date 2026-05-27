package it.polimi.ingsw.model;

import it.polimi.ingsw.util.Observable;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Board implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final int rows = 9;
    private final int columns = 9;

    private final Tile[][] matrix = new Tile[rows][columns];

    private final int numPlayers;

    transient private List<Tile> boardTiles = new ArrayList<>();  //only refill() should access this attribute


    /**
     * Constructor that creates that board with its number of players.
     *
      * @param numPlayers number of players that has joined the Game
     */
    public Board(int numPlayers) {
        this.numPlayers = numPlayers;
    }


    /**
     * Refill the board according to the number of players and the remaining tiles of the bag.
     *
     * @param bag created by Game().
     */
    public void refill(Bag bag) {

        boardTiles = bag.getTiles();

        removeTilesFromPreviousBoardAndAddRemainingTilesToArray();


        for (int i = 2; i < 7 && boardTiles.size() > 0; i++) {

            for (int j = 3; j < 6 && boardTiles.size() > 0; j++) {

                matrix[i][j] = boardTiles.get(0);

                boardTiles.remove(0);
            }

        }


        List<Sequence> sequenceList = new ArrayList<>();

        sequenceList.add(new Sequence(1, 3, 1));
        sequenceList.add(new Sequence(1, 4, 1));
        sequenceList.add(new Sequence(3, 2, 1));
        sequenceList.add(new Sequence(3, 6, 1));
        sequenceList.add(new Sequence(3, 7, 1));
        sequenceList.add(new Sequence(4, 1, 1));
        sequenceList.add(new Sequence(4, 2, 1));
        sequenceList.add(new Sequence(4, 6, 1));
        sequenceList.add(new Sequence(4, 7, 1));
        sequenceList.add(new Sequence(5, 1, 1));
        sequenceList.add(new Sequence(5, 2, 1));
        sequenceList.add(new Sequence(5, 6, 1));
        sequenceList.add(new Sequence(7, 4, 1));
        sequenceList.add(new Sequence(7, 5, 1));


        for (int i = 0; i < sequenceList.size() && !boardTiles.isEmpty(); i++) {

            int row = sequenceList.get(i).getRow();
            int col = sequenceList.get(i).getColumn();

            matrix[row][col] = boardTiles.get(0);

            boardTiles.remove(0);
        }


        if (numPlayers > 2) {

            List<Sequence> sequenceList3 = new ArrayList<>();

            sequenceList3.add(new Sequence(0, 3, 1));
            sequenceList3.add(new Sequence(2, 6, 1));
            sequenceList3.add(new Sequence(3, 8, 1));
            sequenceList3.add(new Sequence(5, 0, 1));
            sequenceList3.add(new Sequence(6, 2, 1));
            sequenceList3.add(new Sequence(6, 6, 1));
            sequenceList3.add(new Sequence(8, 5, 1));
            sequenceList3.add(new Sequence(2, 2, 1));

            for (int i = 0; i < sequenceList3.size() && !boardTiles.isEmpty(); i++) {

                int row = sequenceList3.get(i).getRow();
                int col = sequenceList3.get(i).getColumn();

                matrix[row][col] = boardTiles.get(0);

                boardTiles.remove(0);
            }

        }

        if (numPlayers > 3) {

            List<Sequence> sequenceList4 = new ArrayList<>();

            sequenceList4.add(new Sequence(0, 4, 1));
            sequenceList4.add(new Sequence(1, 5, 1));
            sequenceList4.add(new Sequence(3, 1, 1));
            sequenceList4.add(new Sequence(4, 0, 1));
            sequenceList4.add(new Sequence(4, 8, 1));
            sequenceList4.add(new Sequence(5, 7, 1));
            sequenceList4.add(new Sequence(7, 3, 1));
            sequenceList4.add(new Sequence(8, 4, 1));

            for (int i = 0; i < sequenceList4.size() && !boardTiles.isEmpty(); i++) {

                int row = sequenceList4.get(i).getRow();
                int col = sequenceList4.get(i).getColumn();

                matrix[row][col] = boardTiles.get(0);

                boardTiles.remove(0);
            }

        }

        bag.setTiles(boardTiles);

    }

    /**
     * Used in refill.
     *
     * Remove the tile from the previous board, which has to be refilled, and add the tiles removed to boardTiles.
     */
    private void removeTilesFromPreviousBoardAndAddRemainingTilesToArray() {

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {

                if (matrix[i][j] != null) {

                    Tile tile = matrix[i][j];

                    removeTile(i, j);

                    boardTiles.add(tile);

                }
            }
        }

    }

    /**
     * Calculate all the possible combinations of positions of tiles that the turnPlayer can select for their pickMove
     * according to the maximum column free space of the turnPlayer's bookshelf.
     *
     * @param bookshelf turnPlayer's bookshelf
     * @return a list of lists of positions.
     */
    public List<List<Position>> combinationOfTakeableTilesForTurnPlayer(Bookshelf bookshelf){

        List<List<Position>> combinationsOfTiles = combinationOfTakeableTiles();

        int maxColumnFreeSpaceInBookshelf= bookshelf.getMaxColumnFreeSpaceInBookshelf();

        for (int i = 0; i < combinationsOfTiles.size(); i++) {

            if (combinationsOfTiles.get(i).size() > maxColumnFreeSpaceInBookshelf){


                combinationsOfTiles.remove(i);

                i--;
            }

        }

        return combinationsOfTiles;
    }


    /**
     * Calculate all the possible combinations of positions of tiles that the turnPlayer can choose for their pickMove.
     *
     * @return a list of lists of positions.
     */
    public List<List<Position>> combinationOfTakeableTiles() {

        List<List<Position>> combinationsOfTiles = new ArrayList<>();


        //inizializzo le combinazioni singole
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {

                if (isTakeable(i,j) && matrix[i][j] != null){

                    List<Position> positionOfOneTile = new ArrayList<>();

                    positionOfOneTile.add(new Position(i,j));

                    combinationsOfTiles.add(positionOfOneTile);

                }

            }
        }

        // inizializzo le combinazioni doppie
        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {

                if (j < columns - 1){


                    if (isTakeable(i,j) && matrix[i][j] != null && isTakeable(i,j+1) && matrix[i][j+1] != null){

                        List<Position> positionOfTwoTiles = new ArrayList<>();

                        positionOfTwoTiles.add(new Position(i,j));
                        positionOfTwoTiles.add(new Position(i,j+1));

                        combinationsOfTiles.add(positionOfTwoTiles);

                    }

                }


                if (i < rows -1){

                    if (isTakeable(i,j) && matrix[i][j] != null && isTakeable(i+1,j) && matrix[i+1][j] != null){

                        List<Position> positionOfTwoTiles = new ArrayList<>();

                        positionOfTwoTiles.add(new Position(i,j));
                        positionOfTwoTiles.add(new Position(i+1,j));

                        combinationsOfTiles.add(positionOfTwoTiles);

                    }


                }


            }

        }


        //inizializziamo le combinazioni triple

        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {

                if (j < columns - 2){


                    if (isTakeable(i,j) && matrix[i][j] != null && isTakeable(i,j+1) && matrix[i][j+1] != null
                                                                && isTakeable(i,j+2) && matrix[i][j+2] != null){

                        List<Position> positionOfTwoTiles = new ArrayList<>();

                        positionOfTwoTiles.add(new Position(i,j));
                        positionOfTwoTiles.add(new Position(i,j+1));
                        positionOfTwoTiles.add(new Position(i,j+2));

                        combinationsOfTiles.add(positionOfTwoTiles);

                    }

                }


                if (i < rows - 2){

                    if (isTakeable(i,j) && matrix[i][j] != null && isTakeable(i+1,j) && matrix[i+1][j] != null
                                                                && isTakeable(i+2,j) && matrix[i+2][j] != null){

                        List<Position> positionOfTwoTiles = new ArrayList<>();

                        positionOfTwoTiles.add(new Position(i,j));
                        positionOfTwoTiles.add(new Position(i+1,j));
                        positionOfTwoTiles.add(new Position(i+2,j));

                        combinationsOfTiles.add(positionOfTwoTiles);

                    }


                }


            }

        }

        return combinationsOfTiles;
    }


    /**
     * Check if a tile, that is on the board, is takeable or not.
     *
     * @param i row coordinate
     * @param j column coordinate
     *
     * @return boolean: true if the tile is takeable, false otherwise.
     */
    private boolean isTakeable(int i, int j){

        if ( i == 0 || i == 8 || j == 0 || j == 8) {

            return true;

        }else return matrix[i + 1][j] == null || matrix[i - 1][j] == null || matrix[i][j - 1] == null || matrix[i][j + 1] == null;


    }

    /**
     * Calculate the maximum number of takeable tiles that turnPlayer can pick with a single move.
     *
     * @return int
     */
    public int maxNumOfTakeableTilesWithASingleMove() {

        boolean moreThanZero = false;
        boolean moreThanOne = false;
        boolean moreThanTwo = false;

        List<List<Position>> combinationsOfTiles = combinationOfTakeableTiles();

        for (List<Position> x: combinationsOfTiles) {

            if (x.size() > 2){

                moreThanTwo = true;

            } else if (x.size() > 1) {

                moreThanOne = true;

            } else if (x.size() > 0) {

                moreThanZero = true;

            }
        }


        if (moreThanTwo){

            return 3;

        } else if (moreThanOne) {

            return 2;

        } else if (moreThanZero) {

            return 1;

        } else

        return 0;
    }

    /**
     * Used in removeTilesChosenByTurnPlayerFromBoard.
     * Remove a tile from the board.
     *
     * @param row coordinate
     * @param col coordinate
     */
    private void removeTile(int row, int col) {

        matrix[row][col] = null;

    }

    /**
     * Transform the array of positions of tiles in an array of tiles. It uses the positions to see the type of tiles
     * (color and figure) on the board and creates a new array of tiles that represents the turnPlayer's choice.It does
     * NOT remove the tiles, of those positions, from the board.
     *
     * @param positions positions chosen by turnPlayer through theirs pick.
     * @return a new array of tiles that represents the tiles, which are on the board, in those positions.
     */
    public List<Tile> convertPositionsToTiles(List<Position> positions){

        List<Tile> tilesChosenByTurnPlayer = new ArrayList<>();

        for (Position x: positions) {

            Tile tile = matrix[x.getRow()][x.getColumn()];

            tilesChosenByTurnPlayer.add(new Tile(tile.getColor(),tile.getFigure()));

        }

        return tilesChosenByTurnPlayer;

    }


    /**
     * Remove, for each position in the array, the corresponding tile from the board.
     *
     * @param positions position of tile selected by turnPlayer after the end of his moves.
     */
    public void removeTilesChosenByTurnPlayerFromBoard (List<Position> positions){

        for (Position x: positions) {

            removeTile(x.getRow(),x.getColumn());

        }

    }


    /**
     * Check if the positions of tiles chosen by turnPlayer are allowed or not.
     * 
     * @param combinationsOfTakeableTiles all the possible combinations of positions of tiles that the turnPlayer
     * can select for their pickMove
     * @param positionsChosenByTurnPlayer positions of tiles chosen by turnPlayer through his pickMove.
     * @return boolean: true if the positions are correct, false otherwise.
     */
    public boolean checkIfPositionsAreCorrect (List<List<Position>> combinationsOfTakeableTiles, List<Position> positionsChosenByTurnPlayer){

        boolean correctChoice = false;
        int count;

        for (List<Position> x: combinationsOfTakeableTiles) {

            count = 0;

            for (Position position : x) {

                for (Position value : positionsChosenByTurnPlayer) {

                    if (position.equals(value)) {

                        count++;
                    }


                }


                if (count == x.size() && count == positionsChosenByTurnPlayer.size()) {

                    correctChoice = true;
                }


            }

        }

        return correctChoice;
    }

    /**
     * Calculate the number of tiles on the board that are takeable (individually)
     *
     * @return int
     */
    public int numOfTakeableTiles() {

        int count = 0;


        for (int i = 0; i < rows; i++) {

            for (int j = 0; j < columns; j++) {

                if (matrix[i][j] != null && isTakeable(i,j)){

                    count ++;
                }

            }

        }

        return count;

    }

    /**
     * Getter method.
     *
     * @return structure of the board (a matrix of Tile)
     */
    public Tile[][] getBoxes() {
        return matrix;
    }

    /**
     * Print method. Useful for tests.
     * Print the board on StandardOut, according to the toString of Tile.
     */
    public void printBoard() {
// Find the maximum length of the object converted to a string
        int maxStringLength = 0;
        for (Tile[] row : matrix) {
            for (Tile tile : row) {
                int length = 0;
                if (tile != null) {
                    length = tile.toString().length();
                }
                if (length > maxStringLength) {
                    maxStringLength = length;
                }
            }
        }

// Print the table with each box having the same dimension
// and an index of columns and an index of rows
        for (int i = 0; i < 9; i++) {
            // Print the index of columns
            if (i == 0) {
                System.out.print("   ");
                for (int j = 0; j < 9; j++) {
                    System.out.printf("%-" + (maxStringLength + 3) + "s", " " + j);
                }
                System.out.println();
            }

            // Print the horizontal dash
            System.out.print("   ");
            for (int j = 0; j < 9; j++) {
                for (int k = 0; k < maxStringLength; k++) {
                    System.out.print("-");
                }
                System.out.print("--");
            }
            System.out.println();

            // Print the index of rows and the table contents
            System.out.printf("%-2d|", i);
            for (Tile tile : matrix[i]) {
                String str = "";
                if (tile != null) {
                    str = tile.toString();
                }
                int padding = maxStringLength - str.length();
                System.out.print(" ");
                System.out.print(str);

                // Add padding to make each box the same size
                for (int k = 0; k < padding; k++) {
                    System.out.print(" ");
                }
                System.out.print(" |");
            }
            System.out.println();
        }

// Print the horizontal dash
        System.out.print("   ");
        for (int j = 0; j < 9; j++) {
            for (int k = 0; k < maxStringLength; k++) {
                System.out.print("-");
            }
            System.out.print("--");
        }
        System.out.println();
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }
}





