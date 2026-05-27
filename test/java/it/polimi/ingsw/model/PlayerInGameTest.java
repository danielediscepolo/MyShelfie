package it.polimi.ingsw.model;

import junit.framework.Test;
import junit.framework.TestResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlayerInGameTest implements Test {

    @org.junit.jupiter.api.Test
    void insertIntoBookshelf() {

        PlayerInGame player = new PlayerInGame("Vito");
        List<Tile> tiles1 = new ArrayList<>();
        tiles1.add(new Tile(Color.Green,Figure.Cats));
        tiles1.add(new Tile(Color.White,Figure.Books));
        tiles1.add(new Tile(Color.Blue,Figure.Trophies));


        List<Tile> tiles2 = new ArrayList<>();
        tiles2.add(new Tile(Color.Yellow,Figure.Games));
        tiles2.add(new Tile(Color.Yellow,Figure.Games));
        tiles2.add(new Tile(Color.Yellow,Figure.Games));
        tiles2.add(new Tile(Color.Yellow,Figure.Games));
        tiles2.add(new Tile(Color.Yellow,Figure.Games));

        player.insertIntoBookshelf(1,tiles1);
        player.insertIntoBookshelf(2,tiles2);

        Bookshelf bookshelf = player.getBookshelf();

        bookshelf.printBookshelf();

    }

    @org.junit.jupiter.api.Test
    void addCommonTokenAndUpdatePoints() {
    }

    @org.junit.jupiter.api.Test
    void addEndgameTokenAndUpdatePoints() {
    }

    @org.junit.jupiter.api.Test
    void checkStaticPoints() {


        PlayerInGame player = new PlayerInGame("Vito");
        List<Tile> tiles1 = new ArrayList<>();
        tiles1.add(new Tile(Color.Green,Figure.Cats));
        tiles1.add(new Tile(Color.White,Figure.Books));
        tiles1.add(new Tile(Color.Blue,Figure.Trophies));


        List<Tile> tiles2 = new ArrayList<>();
        tiles2.add(new Tile(Color.Pink,Figure.Plants));
        tiles2.add(new Tile(Color.Pink,Figure.Plants));
        tiles2.add(new Tile(Color.Pink,Figure.Plants));
        tiles2.add(new Tile(Color.Pink,Figure.Plants));
        tiles2.add(new Tile(Color.Pink,Figure.Plants));

         List<Tile> tiles3 = new ArrayList<>();
        tiles3.add(new Tile(Color.Pink,Figure.Plants));
        tiles3.add(new Tile(Color.Pink,Figure.Plants));
        tiles3.add(new Tile(Color.Pink,Figure.Plants));
        tiles3.add(new Tile(Color.Pink,Figure.Plants));


        player.insertIntoBookshelf(1,tiles1);
        player.insertIntoBookshelf(2,tiles2);
        player.insertIntoBookshelf(0,tiles3);

        Bookshelf bookshelf = player.getBookshelf();

        bookshelf.printBookshelf();

        Tile[][] playerMatrix = player.getBookshelf().getMatrix();

       assertEquals(8,player.checkStaticPoints());

        System.out.println(player.checkStaticPoints());

    }

    @org.junit.jupiter.api.Test
    void searchSequences() {
    }

    @org.junit.jupiter.api.Test
    void visit() {
    }

    @org.junit.jupiter.api.Test
    void addPoints() {
    }

    @org.junit.jupiter.api.Test
    void checkPersonalGoalCard() {
    }

    @Override
    public int countTestCases() {
        return 0;
    }

    @Override
    public void run(TestResult testResult) {

    }
}