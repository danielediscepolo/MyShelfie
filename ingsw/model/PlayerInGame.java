package it.polimi.ingsw.model;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


    public class PlayerInGame extends Player {

        private final List<CommonToken> commonTokenList = new ArrayList<>();
        private PersonalGoalCard personalGoalCard;
        private EndGameToken endGameToken = null;
        private int id;
        private Bookshelf bookshelf = new Bookshelf();
        private int totalPoints=0;
        private List <Tile> tiles = new ArrayList<>();

        private Chair chair;


        /**
         * Constructor which creates the PlayerInGame from the name of the normal Player. The Player is the
         * representation of the client while the PlayerInGame is the Player in the particular instance of the game
         * created by Patrick (controller).
         *
         * @param name chosen by the Player when logging in to the server and which is inherited by PlayerInGame.
         */
        public PlayerInGame (String name){
            super(name);
        }


        /**
         * Getter method.
         *
         * @return PlayerInGame's personalGoalCard.
         */
        public PersonalGoalCard getPersonalGoalCard() {
            return personalGoalCard;
        }

        /**
         * Setter method.
         *
         * @param personalGoalCard set PlayerInGame's personalGoalCard.
         * @param bookshelf set PlayerInGame's bookshelf.
         */
        public void setPlayers (PersonalGoalCard personalGoalCard,Bookshelf bookshelf){
            this.personalGoalCard = personalGoalCard;
            this.bookshelf = bookshelf;
        }

        /**
         * Setter method.
         *
         * @param id set PlayerInGame's id.
         */
        public void setId(int id){
            this.id=id;
        }

        /**
         * Setter method.
         *
         * @param chair set PlayerInGame's chair.
         */
        public void setChair(Chair chair) {
            this.chair = chair;
        }

        /**
         * Getter method.
         *
         * @return PlayerInGame's bookshelf.
         */
        public Bookshelf getBookshelf() {
            return this.bookshelf;
        }

        /**
         * Getter method.
         *
         * @return PlayerInGame's totalPoints.
         */
        public int getTotalPoints() {
            return totalPoints;
        }

        /**
         * Setter method.
         *
         * @param tiles set PlayerInGame's tiles
         */
        public void setTiles(List<Tile> tiles) {
            this.tiles = tiles;
        }

        /**
         * Getter method.
         *
         * @return PlayerInGame's id.
         */
        public int getId() { return id; }

        /**
         * Getter method.
         *
         * @return PlayerInGame's tiles.
         */
        public List<Tile> getTiles() {
            return tiles;
        }

        /**
         * Insert tiles, one by one, into the bookshelf.
         *
         * @param column chosen by turnPlayer in chooseColumn().
         * @param tiles chosen in pickTileFromBoard() and sorted in sortTileToInsert() by turnPlayer
         */
        public void insertIntoBookshelf (int column, List<Tile> tiles){

            for (Tile x: tiles) {

                bookshelf.addTile(column,x);

            }

        }

        /**
         * Add commonToken to the PlayerInGame's arrayList and update PlayerInGame's totalPoints by adding commonToken
         * points.
         *
         * @param commonToken associated with the commonGoalCard examined in checkCommonGoal() (class Game).
         * The commonToken which is in the first position in the Token arrayList of the commonGoalCard is taken.
         */
        public void addCommonTokenAndUpdatePoints(CommonToken commonToken){
            this.totalPoints  += commonToken.getPoints();
            commonTokenList.add(commonToken);
        }

        /**
         * Add endgameToken to the PlayerInGame's arrayList and update PlayerInGame's totalPoints by adding endgameToken
         * points.
         *
         * @param endGameToken obtainable only once per game
         */
        public void addEndgameTokenAndUpdatePoints(EndGameToken endGameToken){
            this.endGameToken = endGameToken;
            this.totalPoints += endGameToken.getPoints();
        }


        /**
         * Check how many groups of adjacent tiles ( i.e., that have at least one side in common ) there are in the
         * PlayerInGame library.
         *
         * @return the number of points, calculated through the number of groups of adjacent tiles in the
         * PlayerInGame's library, that the turnPlayer scored at the end of the game.
         */
        public int checkStaticPoints(){

            int staticPoint = 0;

            List<Sequence> foundSequences = searchSequences();

            for (Sequence x: foundSequences) {

                    if (x.getLength() >= 6){

                        staticPoint += 8;

                    } else if (x.getLength() == 5) {

                        staticPoint += 5;

                    } else if (x.getLength() == 4) {

                        staticPoint += 3;

                    } else if (x.getLength()== 3) {

                        staticPoint += 2;

                    }
            }

            return staticPoint;

        }

        /**
         * Used by checkStaticPoints(). Find all possible occurrences of groups (of cardinality at least 2) of adjacent
         * tiles in the turnPlayer library.
         *
         * @return a list of all possible <Sequence> groups of adjacent tiles.
         */
        public List<Sequence> searchSequences (){

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
                        int sequenceLength = visit(i, j, playerMatrix, visited);
                        //If the counter returned by the visit function is greater than one, it means that a sequence of
                        // at least two identical adjacent elements has been found
                        if (sequenceLength > 1) {
                            //Save the position in a list of sequences found
                            sequenceList.add(new Sequence(i, j, sequenceLength));
                        }
                    }
                }
            }
            return sequenceList;
        }

        /**
         * Used by searchSequences(). Calculate, based on a given position passed as a parameter, the length of the
         * sequence of adjacent tile groups using the turnPlayer matrix and a boolean matrix specifying the positions
         * already visited.
         *
         * @param i row coordinate
         * @param j column coordinate
         * @param matrix turnPlayer bookshelf
         * @param visited matrix of booleans specifying whether a given position has already been visited.
         * @return the sequence length of the group of adjacent tiles.
         */
        public int visit(int i, int j, Tile[][] matrix, boolean[][] visited) {

            int rows = bookshelf.getRows();
            int cols =  bookshelf.getColumns();

            //Checking if the current position has already been visited or if it is possibly outside the
            // dimensions of the matrix
            if (i < 0 || i >= rows || j < 0 || j >= cols || visited[i][j]) {
                return 0;
            }
            //Initializing variables
            int counter = 1;
            visited[i][j] = true;
            //Checking adjacent elements
            if (i+1 < rows && matrix[i+1][j] != null && matrix[i+1][j].equals(matrix[i][j])) {
                counter += visit(i+1, j, matrix, visited);
            }
            if (i-1 >= 0 && matrix[i-1][j] != null && matrix[i-1][j].equals(matrix[i][j])) {
                counter += visit(i-1, j, matrix, visited);
            }
            if (j+1 < cols && matrix[i][j+1] != null && matrix[i][j+1].equals(matrix[i][j])) {
                counter += visit(i, j+1, matrix, visited);
            }
            if (j-1 >= 0 && matrix[i][j-1] != null && matrix[i][j-1].equals(matrix[i][j])) {
                counter += visit(i, j-1, matrix, visited);
            }
            return counter;
        }


        /**
         * Adds points to PlayerInGame.
         *
         * @param points number of points to add.
         */
        public void addPoints(int points){
            totalPoints += points;
        }


        /**
         * Calculates how many points the PlayerInGame got at the end of the game from the personalGoalCard.
         *
         * @return the number of points obtained by PlayerInGame, based on the number of equal positions between the
         * PlayerInGame library and the personalGoalCard library.
         */
        public int checkPersonalGoalCard (){

        Bookshelf bookshelfCard = personalGoalCard.getPattern();

        Tile[][] playerMatrix = bookshelf.getMatrix();

        Tile[][] cardMatrix = bookshelfCard.getMatrix();
        
        int count=0;

            for (int i = 0; i < bookshelf.getRows(); i++) {

                for (int j = 0; j < bookshelf.getColumns(); j++) {

                    if (playerMatrix[i][j] != null && playerMatrix[i][j].equals(cardMatrix[i][j]) ){
                        count++;
                    }
                }
                
            }

            if (count == 6) {

                return 12;

            } else if (count == 5) {

                return 9;

            } else if (count == 4) {

                return 6;

            } else if (count == 3) {

                return 4;

            } else if (count == 2) {

                return 2;

            } else if (count == 1) {

                return 1;

            }

            return 0;
        }

        /**
         * toString method.
         *
         * @return a string.
         */
        @Override
        public String toString() {
            return "PlayerInGame{" +
                    "name='" + name + '\'' +
                    '}';
        }


        public EndGameToken getEndGameToken() {
            return endGameToken;
        }
    }


