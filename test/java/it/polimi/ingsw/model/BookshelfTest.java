package it.polimi.ingsw.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookshelfTest {

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void isFull() {
        Bookshelf emptyBookshelf = new Bookshelf();
        assertEquals(emptyBookshelf.isFull(), false, "The bookshelf should not be full.");
        emptyBookshelf.printBookshelf();
    }

    @Test
    void getMatrix() {
    }

    @Test
    void getRows() {
    }

    @Test
    void getColumns() {
    }

    @Test
    void getMaxColumnFreeSpaceInBookshelf(){

        PlayerInGame player = new PlayerInGame("Vito");

        Bookshelf bookshelf = player.getBookshelf();

        Tile greenTile = new Tile(Color.Green, Figure.Cats);
        Tile whiteTile = new Tile(Color.White,Figure.Books);
        Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
        Tile blueTile = new Tile(Color.Blue,Figure.Frames);
        Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
        Tile pinkTile = new Tile(Color.Pink,Figure.Plants);



        List<Tile> tiles0 = new ArrayList<>();

        tiles0.add(blueTile);
        tiles0.add(blueTile);

        tiles0.add(yellowTile);
        tiles0.add(blueTile);

        List<Tile> tiles1 = new ArrayList<>();

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


        List<Tile> tiles4 = new ArrayList<>();

        tiles4.add(yellowTile);



        player.insertIntoBookshelf(0,tiles0);
        player.insertIntoBookshelf(1,tiles1);
        player.insertIntoBookshelf(2,tiles2);
        player.insertIntoBookshelf(3,tiles3);
        player.insertIntoBookshelf(4,tiles4);

        bookshelf.printBookshelf();

        System.out.println();

       assertEquals(5,bookshelf.getMaxColumnFreeSpaceInBookshelf(),"They should be equal");


    }

    @Test
    void checkEnoughSpaceInColumn (){

        PlayerInGame player = new PlayerInGame("Vito");

        Bookshelf bookshelf = player.getBookshelf();

        Tile greenTile = new Tile(Color.Green, Figure.Cats);
        Tile whiteTile = new Tile(Color.White,Figure.Books);
        Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
        Tile blueTile = new Tile(Color.Blue,Figure.Frames);
        Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
        Tile pinkTile = new Tile(Color.Pink,Figure.Plants);



        List<Tile> tiles0 = new ArrayList<>();

        tiles0.add(blueTile);
        tiles0.add(blueTile);

        tiles0.add(yellowTile);
        tiles0.add(blueTile);

        List<Tile> tiles1 = new ArrayList<>();

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


        List<Tile> tiles4 = new ArrayList<>();





        player.insertIntoBookshelf(0,tiles0);
        player.insertIntoBookshelf(1,tiles1);
        player.insertIntoBookshelf(2,tiles2);
        player.insertIntoBookshelf(3,tiles3);
        player.insertIntoBookshelf(4,tiles4);

        bookshelf.printBookshelf();





    }
    @Test
     void calculatePositionsInBookshelf (){



        PlayerInGame player = new PlayerInGame("Vito");

        Bookshelf bookshelf = player.getBookshelf();

        Tile greenTile = new Tile(Color.Green, Figure.Cats);
        Tile whiteTile = new Tile(Color.White,Figure.Books);
        Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
        Tile blueTile = new Tile(Color.Blue,Figure.Frames);
        Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
        Tile pinkTile = new Tile(Color.Pink,Figure.Plants);



        List<Tile> tiles0 = new ArrayList<>();

        tiles0.add(blueTile);
        tiles0.add(blueTile);

        tiles0.add(yellowTile);
        tiles0.add(blueTile);

        List<Tile> tiles1 = new ArrayList<>();

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


        List<Tile> tiles4 = new ArrayList<>();

        tiles4.add(whiteTile);
        tiles4.add(whiteTile);
        tiles4.add(whiteTile);




        player.insertIntoBookshelf(0,tiles0);
        player.insertIntoBookshelf(1,tiles1);
        player.insertIntoBookshelf(2,tiles2);
        player.insertIntoBookshelf(3,tiles3);

        player.insertIntoBookshelf(4,tiles4);

        List<Position> positionsChosenByTurnPlayerInBookshelf = bookshelf.calculatePositionsInBookshelf(3,3);


        for (Position x: positionsChosenByTurnPlayerInBookshelf) {

            System.out.println("Posizione tessera: " + x);


        }




        bookshelf.printBookshelf();


    }

    @Test
    void removeTileFromBookshelf(){

        PlayerInGame player = new PlayerInGame("Vito");

        Bookshelf bookshelf = player.getBookshelf();

        Tile greenTile = new Tile(Color.Green, Figure.Cats);
        Tile whiteTile = new Tile(Color.White,Figure.Books);
        Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
        Tile blueTile = new Tile(Color.Blue,Figure.Frames);
        Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
        Tile pinkTile = new Tile(Color.Pink,Figure.Plants);



        List<Tile> tiles0 = new ArrayList<>();

        tiles0.add(blueTile);
        tiles0.add(blueTile);

        tiles0.add(yellowTile);
        tiles0.add(blueTile);

        List<Tile> tiles1 = new ArrayList<>();

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


        List<Tile> tiles4 = new ArrayList<>();

        tiles4.add(whiteTile);
        tiles4.add(whiteTile);
        tiles4.add(whiteTile);




        player.insertIntoBookshelf(0,tiles0);
        player.insertIntoBookshelf(1,tiles1);
        player.insertIntoBookshelf(2,tiles2);
        player.insertIntoBookshelf(3,tiles3);
        player.insertIntoBookshelf(4,tiles4);

        List<Position> positionsChosenByTurnPlayerInBookshelf = bookshelf.calculatePositionsInBookshelf(3,3);


        List<Tile> tiles5 = new ArrayList<>();

        tiles5.add(whiteTile);
        tiles5.add(whiteTile);
        tiles5.add(whiteTile);
        player.insertIntoBookshelf(3,tiles5);

        bookshelf.printBookshelf();


        for (Position x: positionsChosenByTurnPlayerInBookshelf) {

            System.out.println("Posizione tessera: " + x);

            bookshelf.removeTileFromBookshelf(x);


        }

        bookshelf.printBookshelf();



    }

    @Test
    void addTile() {

        Tile greenTile = new Tile(Color.Green,Figure.Cats);
        Tile whiteTile = new Tile(Color.White,Figure.Books);
        Tile pinkTile =  new Tile(Color.Pink, Figure.Plants);


        Bookshelf bookshelf = new Bookshelf();

        int column = 2;

        bookshelf.addTile(column,greenTile);


        Tile [][] matrix = bookshelf.getMatrix();

        assertEquals(matrix[5][2],greenTile,"They should be equal");

        bookshelf.addTile(column,whiteTile);

        assertEquals(matrix[4][2],whiteTile,"They should be equal");

        assertNotEquals(greenTile,matrix[3][2],"The boxe should be empty");

        bookshelf.addTile(column,greenTile);

        assertEquals(matrix[3][2],greenTile,"They should be equal");

        bookshelf.addTile(4,pinkTile);

        assertEquals(matrix[5][4],pinkTile,"They should be equal");

        bookshelf.addTile(4,pinkTile);
        bookshelf.addTile(4,pinkTile);
        bookshelf.addTile(4,whiteTile);
        bookshelf.addTile(4,pinkTile);
        bookshelf.addTile(4,pinkTile);


        assertEquals(matrix[4][4],pinkTile,"They should be equal");
        assertEquals(matrix[3][4],pinkTile,"They should be equal");
        assertEquals(matrix[2][4],whiteTile,"They should be equal");
        assertEquals(matrix[1][4],pinkTile,"They should be equal");
        assertEquals(matrix[0][4],pinkTile,"They should be equal");


        bookshelf.printBookshelf();
        System.out.println();


    }
}