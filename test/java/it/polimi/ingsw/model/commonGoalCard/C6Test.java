package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.*;
import junit.framework.Test;
import junit.framework.TestResult;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class C6Test implements Test {

    @org.junit.jupiter.api.Test
    void compare() {


        PlayerInGame player = new PlayerInGame("Vito");

        Bookshelf bookshelf = player.getBookshelf();

        Tile greenTile = new Tile(Color.Green, Figure.Cats);
        Tile whiteTile = new Tile(Color.White,Figure.Books);
        Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
        Tile blueTile = new Tile(Color.Blue,Figure.Frames);
        Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
        Tile pinkTile = new Tile(Color.Pink,Figure.Plants);



        List<Tile> tiles0 = new ArrayList<>();

        tiles0.add(pinkTile);
        tiles0.add(greenTile);
        tiles0.add(blueTile);
        tiles0.add(greenTile);
        tiles0.add(greenTile);
        tiles0.add(greenTile);

        List<Tile> tiles1 = new ArrayList<>();

        tiles1.add(greenTile);
        tiles1.add(lightBlueTile);
        tiles1.add(yellowTile);
        tiles1.add(yellowTile);
        tiles1.add(blueTile);
        tiles1.add(pinkTile);

        List<Tile> tiles2 = new ArrayList<>();

        tiles2.add(pinkTile);
        tiles2.add(lightBlueTile);
        tiles2.add(pinkTile);
        tiles2.add(whiteTile);
        tiles2.add(whiteTile);
        tiles2.add(blueTile);

        List<Tile> tiles3 = new ArrayList<>();

        tiles3.add(pinkTile);
        tiles3.add(blueTile);
        tiles3.add(greenTile);
        tiles3.add(whiteTile);
        tiles3.add(whiteTile);
        tiles3.add(blueTile);

        List<Tile> tiles4 = new ArrayList<>();

        tiles4.add(whiteTile);
        tiles4.add(greenTile);
        tiles4.add(greenTile);
        tiles4.add(lightBlueTile);
        tiles4.add(blueTile);
        tiles4.add(yellowTile);


        player.insertIntoBookshelf(0,tiles0);
        player.insertIntoBookshelf(1,tiles1);
        player.insertIntoBookshelf(2,tiles2);
        player.insertIntoBookshelf(3,tiles3);
        player.insertIntoBookshelf(4,tiles4);


        CommonGoalCard card = new C6();

        List<CommonToken> commonTokens = new ArrayList<>();

        commonTokens.add(new CommonToken(8));
        commonTokens.add(new CommonToken(6));
        commonTokens.add(new CommonToken(4));
        commonTokens.add(new CommonToken(2));

        card.setCommonTokens(commonTokens);


        CommonToken commonToken = card.compare(bookshelf);

        assertEquals(8,commonToken.getPoints(),"They should be equal");


        CommonToken commonToken1 = card.compare(bookshelf);

        assertEquals(6,commonToken1.getPoints(),"They should be equal");








        bookshelf.printBookshelf();




    }

    @Override
    public int countTestCases() {
        return 0;
    }

    @Override
    public void run(TestResult testResult) {

    }
}