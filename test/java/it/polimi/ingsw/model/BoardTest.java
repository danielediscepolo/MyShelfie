package it.polimi.ingsw.model;

import junit.framework.Test;
import junit.framework.TestResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class BoardTest implements Test {

    @org.junit.jupiter.api.Test
    void refill() {

        Board board = new Board(4);

        Tile greenTile = new Tile(Color.Green,Figure.Cats);
        Tile whiteTile = new Tile(Color.White,Figure.Books);
        Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
        Tile blueTile = new Tile(Color.Blue,Figure.Frames);
        Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
        Tile pinkTile = new Tile(Color.Pink,Figure.Plants);

        List<Tile> tiles = new ArrayList<>(132);

        List<Tile> typeTiles = new ArrayList<>(6);

        typeTiles.add(greenTile);
        typeTiles.add(whiteTile);
        typeTiles.add(yellowTile);
        typeTiles.add(blueTile);
        typeTiles.add(lightBlueTile);
        typeTiles.add(pinkTile);

        for (int j = 0; j < typeTiles.size(); j++) {

            for (int i = 0; i < 5; i++) {
                tiles.add(typeTiles.get(j));
            }

        }

        Collections.shuffle(tiles);

        Bag bag = new Bag(tiles);


        System.out.println();

        System.out.println(bag.getTiles().size());

        board.refill(bag);

        System.out.println(bag.getTiles().size());

        board.printBoard();


        System.out.println();

        System.out.println();


        List<Tile> tiles1 = new ArrayList<>(132);

        for (int j = 0; j < typeTiles.size(); j++) {

            for (int i = 0; i < 22; i++) {
                tiles1.add(typeTiles.get(j));
            }

        }

        Bag bag1 = new Bag(tiles1);

        Collections.shuffle(tiles1);

        board.refill(bag1);

        System.out.println(bag1.getTiles().size());

        board.printBoard();

    }

    @org.junit.jupiter.api.Test
    void combinationOfPickableTiles() {

        Board board = new Board(4);

        Tile greenTile = new Tile(Color.Green,Figure.Cats);
        Tile whiteTile = new Tile(Color.White,Figure.Books);
        Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
        Tile blueTile = new Tile(Color.Blue,Figure.Frames);
        Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
        Tile pinkTile = new Tile(Color.Pink,Figure.Plants);

        List<Tile> tiles = new ArrayList<>(132);

        List<Tile> typeTiles = new ArrayList<>(6);

        typeTiles.add(greenTile);
        typeTiles.add(whiteTile);
        typeTiles.add(yellowTile);
        typeTiles.add(blueTile);
        typeTiles.add(lightBlueTile);
        typeTiles.add(pinkTile);

        for (int j = 0; j < typeTiles.size(); j++) {

            for (int i = 0; i < 6; i++) {
                tiles.add(typeTiles.get(j));
            }

        }

        Collections.shuffle(tiles);

        Bag bag = new Bag(tiles);


        System.out.println();

        System.out.println(bag.getTiles().size());

        board.refill(bag);

        System.out.println(bag.getTiles().size());

        board.printBoard();

        List<List<Position>>  combinationsOfTiles = board.combinationOfTakeableTiles();


        for (int i = 0; i < combinationsOfTiles.size(); i++) {

            System.out.println("Lista: " + i + " Size: " + combinationsOfTiles.get(i).size() );

            for (int j = 0; j < combinationsOfTiles.get(i).size(); j++) {

                System.out.println("Combinazione: " + combinationsOfTiles.get(i).get(j));

            }

        }





    }

    @org.junit.jupiter.api.Test
     void combinationOfPickableTilesForTurnPlayer(){

        PlayerInGame player = new PlayerInGame("Vito");

        Bookshelf bookshelf = player.getBookshelf();


        Board board = new Board(4);

        Tile greenTile = new Tile(Color.Green,Figure.Cats);
        Tile whiteTile = new Tile(Color.White,Figure.Books);
        Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
        Tile blueTile = new Tile(Color.Blue,Figure.Frames);
        Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
        Tile pinkTile = new Tile(Color.Pink,Figure.Plants);

        List<Tile> tiles = new ArrayList<>(132);

        List<Tile> typeTiles = new ArrayList<>(6);

        typeTiles.add(greenTile);
        typeTiles.add(whiteTile);
        typeTiles.add(yellowTile);
        typeTiles.add(blueTile);
        typeTiles.add(lightBlueTile);
        typeTiles.add(pinkTile);

        for (int j = 0; j < typeTiles.size(); j++) {

            for (int i = 0; i < 6; i++) {
                tiles.add(typeTiles.get(j));
            }

        }

        Collections.shuffle(tiles);

        Bag bag = new Bag(tiles);


        System.out.println();

        System.out.println(bag.getTiles().size());

        board.refill(bag);

        System.out.println(bag.getTiles().size());

        board.printBoard();

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println();


        List<Tile> tiles0 = new ArrayList<>();

        tiles0.add(blueTile);



        List<Tile> tiles1 = new ArrayList<>();

        tiles1.add(greenTile);
        tiles1.add(greenTile);

        tiles1.add(yellowTile);
        tiles1.add(blueTile);
        tiles1.add(greenTile);

        List<Tile> tiles2 = new ArrayList<>();

        tiles2.add(yellowTile);
        tiles2.add(yellowTile);
        tiles2.add(yellowTile);
        tiles2.add(blueTile);
        tiles2.add(whiteTile);
        tiles2.add(greenTile);

        List<Tile> tiles3 = new ArrayList<>();

        tiles3.add(yellowTile);
        tiles3.add(whiteTile);
        tiles3.add(whiteTile);
        tiles3.add(whiteTile);
        tiles3.add(whiteTile);


        List<Tile> tiles4 = new ArrayList<>();

        tiles4.add(yellowTile);
        tiles4.add(yellowTile);
        tiles4.add(yellowTile);
        tiles4.add(yellowTile);
        tiles4.add(yellowTile);



        player.insertIntoBookshelf(0,tiles0);
        player.insertIntoBookshelf(1,tiles1);
        player.insertIntoBookshelf(2,tiles2);
        player.insertIntoBookshelf(3,tiles3);
        player.insertIntoBookshelf(4,tiles4);

        bookshelf.printBookshelf();

        System.out.println();

        List<List<Position>>  combinationsOfTiles = board.combinationOfTakeableTilesForTurnPlayer(bookshelf);


        for (int i = 0; i < combinationsOfTiles.size(); i++) {

            System.out.println("Lista numero: " + i);

            for (int j = 0; j < combinationsOfTiles.get(i).size(); j++) {

                System.out.println("Combinazione: " + combinationsOfTiles.get(i).get(j));

            }

        }
    }

    @org.junit.jupiter.api.Test
    void maxNumOfPickableTilesWithASingleMove() {

        Board board = new Board(4);

        Tile greenTile = new Tile(Color.Green,Figure.Cats);
        Tile whiteTile = new Tile(Color.White,Figure.Books);
        Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
        Tile blueTile = new Tile(Color.Blue,Figure.Frames);
        Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
        Tile pinkTile = new Tile(Color.Pink,Figure.Plants);

        List<Tile> tiles = new ArrayList<>(132);

        List<Tile> typeTiles = new ArrayList<>(6);

        typeTiles.add(greenTile);
        typeTiles.add(whiteTile);
        typeTiles.add(yellowTile);
        typeTiles.add(blueTile);
        typeTiles.add(lightBlueTile);
        typeTiles.add(pinkTile);

        for (int j = 0; j < typeTiles.size(); j++) {

            for (int i = 0; i < 4; i++) {
                tiles.add(typeTiles.get(j));
            }

        }

        Collections.shuffle(tiles);

        Bag bag = new Bag(tiles);


        System.out.println();

        System.out.println(bag.getTiles().size());

        board.refill(bag);

        System.out.println(bag.getTiles().size());

        board.printBoard();

        List<List<Position>>  combinationsOfTiles = board.combinationOfTakeableTiles();

        for (int i = 0; i < combinationsOfTiles.size(); i++) {

            System.out.println("Lista: " + i + " Size: " + combinationsOfTiles.get(i).size() );

            for (int j = 0; j < combinationsOfTiles.get(i).size(); j++) {

                System.out.println("Combinazione: " + combinationsOfTiles.get(i).get(j));

            }

        }

        System.out.println("Massimo numero di tile prendibili con una sola mossa: " + board.maxNumOfTakeableTilesWithASingleMove());


    }

    @org.junit.jupiter.api.Test
    void printBoard() {


        Board board = new Board(4);

        Tile greenTile = new Tile(Color.Green,Figure.Cats);
        Tile whiteTile = new Tile(Color.White,Figure.Books);
        Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
        Tile blueTile = new Tile(Color.Blue,Figure.Frames);
        Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
        Tile pinkTile = new Tile(Color.Pink,Figure.Plants);

        List<Tile> tiles = new ArrayList<>(132);

        List<Tile> typeTiles = new ArrayList<>(6);

        typeTiles.add(greenTile);
        typeTiles.add(whiteTile);
        typeTiles.add(yellowTile);
        typeTiles.add(blueTile);
        typeTiles.add(lightBlueTile);
        typeTiles.add(pinkTile);

        for (int j = 0; j < typeTiles.size(); j++) {

            for (int i = 0; i < 5; i++) {
                tiles.add(typeTiles.get(j));
            }

        }

        Collections.shuffle(tiles);

        Bag bag = new Bag(tiles);


        System.out.println();

        System.out.println(bag.getTiles().size());

        board.refill(bag);

        System.out.println(bag.getTiles().size());

        board.printBoard();


   }


   @org.junit.jupiter.api.Test
   void checkIfPositionsAreCorrect() {

       PlayerInGame player = new PlayerInGame("Vito");

       Bookshelf bookshelf = player.getBookshelf();


       Board board = new Board(4);

       Tile greenTile = new Tile(Color.Green, Figure.Cats);
       Tile whiteTile = new Tile(Color.White, Figure.Books);
       Tile yellowTile = new Tile(Color.Yellow, Figure.Games);
       Tile blueTile = new Tile(Color.Blue, Figure.Frames);
       Tile lightBlueTile = new Tile(Color.LightBlue, Figure.Trophies);
       Tile pinkTile = new Tile(Color.Pink, Figure.Plants);

       List<Tile> tiles = new ArrayList<>(132);

       List<Tile> typeTiles = new ArrayList<>(6);

       typeTiles.add(greenTile);
       typeTiles.add(whiteTile);
       typeTiles.add(yellowTile);
       typeTiles.add(blueTile);
       typeTiles.add(lightBlueTile);
       typeTiles.add(pinkTile);

       for (int j = 0; j < typeTiles.size(); j++) {

           for (int i = 0; i < 6; i++) {
               tiles.add(typeTiles.get(j));
           }

       }

       Collections.shuffle(tiles);

       Bag bag = new Bag(tiles);


       System.out.println();

       System.out.println(bag.getTiles().size());

       board.refill(bag);

       System.out.println(bag.getTiles().size());

       board.printBoard();

       System.out.println();
       System.out.println();
       System.out.println();
       System.out.println();


       List<Tile> tiles0 = new ArrayList<>();

       tiles0.add(blueTile);
       tiles0.add(blueTile);
       tiles0.add(blueTile);
       tiles0.add(blueTile);

       tiles0.add(yellowTile);
       tiles0.add(blueTile);

       List<Tile> tiles1 = new ArrayList<>();

       tiles1.add(greenTile);
       tiles1.add(greenTile);

       tiles1.add(yellowTile);
       tiles1.add(blueTile);
       tiles1.add(greenTile);

       List<Tile> tiles2 = new ArrayList<>();

       tiles2.add(yellowTile);
       tiles2.add(yellowTile);
       tiles2.add(yellowTile);
       tiles2.add(blueTile);
       tiles2.add(whiteTile);
       tiles2.add(greenTile);

       List<Tile> tiles3 = new ArrayList<>();

       tiles3.add(yellowTile);
       tiles3.add(whiteTile);
       tiles3.add(whiteTile);
       tiles3.add(whiteTile);
       tiles3.add(whiteTile);


       List<Tile> tiles4 = new ArrayList<>();

       tiles4.add(yellowTile);
       tiles4.add(yellowTile);
       tiles4.add(yellowTile);
       tiles4.add(yellowTile);




       player.insertIntoBookshelf(0, tiles0);
       player.insertIntoBookshelf(1, tiles1);
       player.insertIntoBookshelf(2, tiles2);
       player.insertIntoBookshelf(3, tiles3);
       player.insertIntoBookshelf(4, tiles4);

       bookshelf.printBookshelf();

       System.out.println();

       List<List<Position>> combinationsOfTiles = board.combinationOfTakeableTilesForTurnPlayer(bookshelf);


       for (int i = 0; i < combinationsOfTiles.size(); i++) {

           System.out.println("Lista numero: " + i);

           for (int j = 0; j < combinationsOfTiles.get(i).size(); j++) {

               System.out.println("Combinazione: " + combinationsOfTiles.get(i).get(j));

           }

       }


       List<Position> positionsChosenByTurnPlayer = new ArrayList<>();

       positionsChosenByTurnPlayer.add(new Position(7,5));
       positionsChosenByTurnPlayer.add(new Position(8,5));



       System.out.println("Scelta possibile?  "+ board.checkIfPositionsAreCorrect(combinationsOfTiles,positionsChosenByTurnPlayer));


   }


   @org.junit.jupiter.api.Test
   void removeTilesChosenByTurnPlayerFromBoard(){

       Board board = new Board(4);

       Tile greenTile = new Tile(Color.Green,Figure.Cats);
       Tile whiteTile = new Tile(Color.White,Figure.Books);
       Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
       Tile blueTile = new Tile(Color.Blue,Figure.Frames);
       Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
       Tile pinkTile = new Tile(Color.Pink,Figure.Plants);

       List<Tile> tiles = new ArrayList<>(132);

       List<Tile> typeTiles = new ArrayList<>(6);

       typeTiles.add(greenTile);
       typeTiles.add(whiteTile);
       typeTiles.add(yellowTile);
       typeTiles.add(blueTile);
       typeTiles.add(lightBlueTile);
       typeTiles.add(pinkTile);

       for (int j = 0; j < typeTiles.size(); j++) {

           for (int i = 0; i < 4; i++) {
               tiles.add(typeTiles.get(j));
           }

       }

       Collections.shuffle(tiles);

       Bag bag = new Bag(tiles);


       System.out.println();

       System.out.println(bag.getTiles().size());

       board.refill(bag);

       System.out.println(bag.getTiles().size());

       board.printBoard();




       List<Position> positions = new ArrayList<>();

       positions.add(new Position(1,3));
       positions.add(new Position(1,4));


       List<Tile> tilesTurnPlayer = board.convertPositionsToTiles(positions);

       System.out.println();

       for (Tile x: tilesTurnPlayer) {

           System.out.println(x);

       }

       board.removeTilesChosenByTurnPlayerFromBoard(positions);

       System.out.println();
       System.out.println();

       board.printBoard();


   }


   @org.junit.jupiter.api.Test
   void convertPositionsToTiles(){


       Board board = new Board(4);

       Tile greenTile = new Tile(Color.Green,Figure.Cats);
       Tile whiteTile = new Tile(Color.White,Figure.Books);
       Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
       Tile blueTile = new Tile(Color.Blue,Figure.Frames);
       Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
       Tile pinkTile = new Tile(Color.Pink,Figure.Plants);

       List<Tile> tiles = new ArrayList<>(132);

       List<Tile> typeTiles = new ArrayList<>(6);

       typeTiles.add(greenTile);
       typeTiles.add(whiteTile);
       typeTiles.add(yellowTile);
       typeTiles.add(blueTile);
       typeTiles.add(lightBlueTile);
       typeTiles.add(pinkTile);

       for (int j = 0; j < typeTiles.size(); j++) {

           for (int i = 0; i < 4; i++) {
               tiles.add(typeTiles.get(j));
           }

       }

       Collections.shuffle(tiles);

       Bag bag = new Bag(tiles);


       System.out.println();

       System.out.println(bag.getTiles().size());

       board.refill(bag);

       System.out.println(bag.getTiles().size());

       board.printBoard();




       List<Position> positions = new ArrayList<>();

       positions.add(new Position(1,3));
       positions.add(new Position(1,4));


       List<Tile> tilesTurnPlayer = board.convertPositionsToTiles(positions);

       System.out.println();

       for (Tile x: tilesTurnPlayer) {

           System.out.println(x);

       }

   }

@org.junit.jupiter.api.Test
   void numOfPickableTiles (){
    Board board = new Board(4);

    Tile greenTile = new Tile(Color.Green,Figure.Cats);
    Tile whiteTile = new Tile(Color.White,Figure.Books);
    Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
    Tile blueTile = new Tile(Color.Blue,Figure.Frames);
    Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
    Tile pinkTile = new Tile(Color.Pink,Figure.Plants);

    List<Tile> tiles = new ArrayList<>(132);

    List<Tile> typeTiles = new ArrayList<>(6);

    typeTiles.add(greenTile);
    typeTiles.add(whiteTile);
    typeTiles.add(yellowTile);
    typeTiles.add(blueTile);
    typeTiles.add(lightBlueTile);
    typeTiles.add(pinkTile);

    for (int j = 0; j < typeTiles.size(); j++) {

        for (int i = 0; i < 4; i++) {
            tiles.add(typeTiles.get(j));
        }

    }

    Collections.shuffle(tiles);

    Bag bag = new Bag(tiles);


    System.out.println();

    System.out.println(bag.getTiles().size());

    board.refill(bag);

    System.out.println(bag.getTiles().size());

    board.printBoard();

    List<List<Position>>  combinationsOfTiles = board.combinationOfTakeableTiles();

    for (int i = 0; i < combinationsOfTiles.size(); i++) {

        System.out.println("Lista numero: " + i);

        for (int j = 0; j < combinationsOfTiles.get(i).size(); j++) {

            System.out.println("Combinazione: " + combinationsOfTiles.get(i).get(j));

        }

    }


    System.out.println("Numero totale di Tile prendibili: " + board.numOfTakeableTiles());


}
    @Override
    public int countTestCases() {
        return 0;
    }

    @Override
    public void run(TestResult testResult) {

    }
}