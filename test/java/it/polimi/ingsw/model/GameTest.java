package it.polimi.ingsw.model;

import junit.framework.Test;
import junit.framework.TestResult;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class GameTest implements Test {


    @org.junit.jupiter.api.Test
    void inizializeBag(){

        Tile greenTile = new Tile(Color.Green, Figure.Cats);
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

            for (int i = 0; i < 22; i++) {
                tiles.add(typeTiles.get(j));
            }

        }

        Collections.shuffle(tiles);

        Bag bag = new Bag(tiles);

        for (int i = 0; i < tiles.size(); i++) {

            System.out.println("Tile numero : " + i);

            System.out.println(bag.getTiles().get(0));

        }


    }

    @org.junit.jupiter.api.Test
    void inizializeBoard (){


        Tile greenTile = new Tile(Color.Green, Figure.Cats);
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

            for (int i = 0; i < 22; i++) {
                tiles.add(typeTiles.get(j));
            }

        }

        Collections.shuffle(tiles);

        Bag bag = new Bag(tiles);


        int numPlayers = 3;

        Board board = new Board(numPlayers);
        board.refill(bag);

        board.printBoard();

    }

    @org.junit.jupiter.api.Test
    void inizializePlayers(){

        int [] posix1 = {1,4,2,3,3,1,0,2,5,2,0,0};
        int [] posix2 = {2,0,3,4,2,2,5,4,4,3,1,1};
        int [] posix3 = {3,1,5,0,1,3,1,0,3,4,2,2};
        int [] posix4 = {4,2,4,1,0,4,2,2,0,2,3,3};
        int [] posix5 = {5,3,3,2,5,0,3,1,1,1,4,4};
        int [] posix6 = {0,4,2,3,4,1,4,3,0,2,5,0};
        int [] posix7 = {0,0,5,2,4,4,1,3,3,0,2,1};
        int [] posix8 = {1,1,4,3,5,3,0,4,2,2,3,0};
        int [] posix9 = {2,2,3,4,0,2,5,0,4,1,4,4};
        int [] posix10 = {3,3,2,0,1,1,4,1,0,4,5,3};
        int [] posix11 = {4,4,1,1,2,0,3,2,5,3,0,2};
        int [] posix12 = {5,0,0,2,4,4,2,2,3,3,1,1};


        List<PersonalGoalCard> deckPersonalCard =  new ArrayList<>(12);



        int numPlayers = 3;

        List<PersonalGoalCard> deckPersonalGoalCardInGame = new ArrayList<>(4);

        Collections.shuffle(deckPersonalCard);

        for (int i = 0; i < numPlayers; i++) {
            deckPersonalGoalCardInGame.add(i,deckPersonalCard.get(i));
        }

        List<Player> players = new ArrayList<>();

        players.add(new Player("Marco"));
        players.add(new Player("Vito"));
        players.add(new Player("Luca"));



        List<PlayerInGame> playersInGame = new ArrayList<>(4);

        for (Player x: players) {
            playersInGame.add(new PlayerInGame(x.getName()));
        }



        for (int i = 0; i < playersInGame.size(); i++) {

            playersInGame.get(i).setPlayers(deckPersonalGoalCardInGame.get(i),new Bookshelf());

        }


        for (PlayerInGame x: playersInGame) {

            System.out.println(x);

            System.out.println();
            x.getPersonalGoalCard().getPattern().printBookshelf();

            System.out.println();
            x.getBookshelf().printBookshelf();

        }


    }



    @org.junit.jupiter.api.Test
    void chooseFirstPlayer (){


        List<PlayerInGame> players = new ArrayList<>();

        players.add(new PlayerInGame("Marco"));
        players.add(new PlayerInGame("Vito"));
        players.add(new PlayerInGame("Luca"));



        Collections.shuffle(players);

        for (int i = 0; i < players.size(); i++) {
            players.get(i).setId(i+1);
        }


        for (PlayerInGame x : players) {

            System.out.println(x + " Id: " + x.getId());

            System.out.println();
        }


    }


    @org.junit.jupiter.api.Test
    void game (){

/*
        Patrick patrick = new Patrick();

        List<Player> players = new ArrayList<>();

        players.add(new Player("Paolo"));
        players.add(new Player("Daniele"));
        players.add(new Player("Vito"));


        patrick.run(players,"Partita");

 */
/*
        Game game = patrick.getGame();

        System.out.println("Nome partita: " + game.getName());

        System.out.println();

        System.out.println("EndgameToken: " + game.getEndgameToken().getPoints());

        System.out.println();

        for (CommonGoalCard x: game.getCommonGoalCards()) {



            System.out.println(x);

            for (CommonToken y: x.getCommonTokens()) {

                System.out.println(y);

            }

            System.out.println();

        }

        System.out.println();

        for (PersonalGoalCard x: game.getPersonalGoalCards()) {

            System.out.println("PersonalGoalCard");

            x.getPattern().printBookshelf();

            System.out.println();

        }

        System.out.println();

        System.out.println(game.getTiles().size());

        System.out.println();

        for (PlayerInGame x: game.getPlayers()) {

            System.out.println(x);

            x.getPersonalGoalCard().getPattern().printBookshelf();

            x.getBookshelf().printBookshelf();

        }


        System.out.println();

        System.out.println("FirstPlayer: " + game.getFirstPlayer());

        System.out.println();

        System.out.println("TurnPlayer: " + game.getTurnPlayer());

        System.out.println();

        game.getBoard().printBoard();

        System.out.println(game.getFirstPlayer().getChair());


        System.out.println(game.nextTurn());

        System.out.println("TurnPlayer: " + game.getTurnPlayer());

        System.out.println(game.nextTurn());

        System.out.println("TurnPlayer: " + game.getTurnPlayer());

        System.out.println(game.nextTurn());

        System.out.println("TurnPlayer: " + game.getTurnPlayer());

        System.out.println("FirstPlayer: " + game.getFirstPlayer());

        game.getPlayers().get(1).addPoints(1);

        game.endGame();

        System.out.println("Winner: " + game.getWinner());


 */

    }







    @org.junit.jupiter.api.Test
    void turn() {



    }





    @org.junit.jupiter.api.Test
    void endGame() {
    }

    @Override
    public int countTestCases() {
        return 0;
    }

    @Override
    public void run(TestResult testResult) {

    }
}