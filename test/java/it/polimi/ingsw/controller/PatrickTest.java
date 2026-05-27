package it.polimi.ingsw.controller;

import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoalCard.*;
import junit.framework.Test;
import junit.framework.TestResult;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PatrickTest implements Test {

    @BeforeEach
    void setUp() {





    }

    @AfterEach
    void tearDown() {






    }


    @org.junit.jupiter.api.Test
    void createDeckCommonCard(){

        List<CommonGoalCard> deckCommonCard = new ArrayList<>(12);

        deckCommonCard.add(new C1());
        deckCommonCard.add(new C2());
        deckCommonCard.add(new C3());
        deckCommonCard.add(new C4());
        deckCommonCard.add(new C5());
        deckCommonCard.add(new C6());
        deckCommonCard.add(new C7());
        deckCommonCard.add(new C8());
        deckCommonCard.add(new C9());
        deckCommonCard.add(new C10());
        deckCommonCard.add(new C11());
        deckCommonCard.add(new C12());


        Collections.shuffle(deckCommonCard);


        for (CommonGoalCard x: deckCommonCard) {

            System.out.println(x);

        }
    }


    @org.junit.jupiter.api.Test
    void createDeckPersonalCards(){


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



        for (PersonalGoalCard x: deckPersonalCard) {

            System.out.println(x);

            Bookshelf bookshelf = x.getPattern();

            bookshelf.printBookshelf();

            System.out.println();

        }

    }


    @org.junit.jupiter.api.Test
    void createPlayers(){

        List<Player> players = new ArrayList<>();

        players.add(new Player("Marco"));
        players.add(new Player("Vito"));
        players.add(new Player("Luca"));
        players.add(new Player("Ferdi"));


        List<PlayerInGame> playersInGame = new ArrayList<>(4);

        for (Player x: players) {
            playersInGame.add(new PlayerInGame(x.getName()));
        }

        for (Player x: playersInGame) {
            System.out.println(x);
        }



    }

    @org.junit.jupiter.api.Test
    void createArrayTile(){


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


        for (Tile x: tiles) {

            System.out.println(x);

            System.out.println();

        }




    }


    @org.junit.jupiter.api.Test
    void drawPersonalGoalCard(){

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


        for (PersonalGoalCard x:
             deckPersonalGoalCardInGame) {

            System.out.println(x);

            x.getPattern().printBookshelf();

            System.out.println();

        }


    }


    @org.junit.jupiter.api.Test
    void drawCommonGoalCard(){


        List<CommonGoalCard> deckCommonCard = new ArrayList<>(12);

        deckCommonCard.add(new C1());
        deckCommonCard.add(new C2());
        deckCommonCard.add(new C3());
        deckCommonCard.add(new C4());
        deckCommonCard.add(new C5());
        deckCommonCard.add(new C6());
        deckCommonCard.add(new C7());
        deckCommonCard.add(new C8());
        deckCommonCard.add(new C9());
        deckCommonCard.add(new C10());
        deckCommonCard.add(new C11());
        deckCommonCard.add(new C12());

        int numPlayers = 2;


        Collections.shuffle(deckCommonCard);


        List<CommonGoalCard> deckCommonGoalCardInGame = new ArrayList<>(2);

        Collections.shuffle(deckCommonCard);

        if (numPlayers == 2) {

            List<List<CommonToken>> commonTokenslist = new ArrayList<>();

            commonTokenslist.add(new ArrayList<>());

            commonTokenslist.get(0).add(new CommonToken(8));
            commonTokenslist.get(0).add(new CommonToken(4));

            commonTokenslist.add(new ArrayList<>());

            commonTokenslist.get(1).add(new CommonToken(8));
            commonTokenslist.get(1).add(new CommonToken(4));


            for (int i = 0; i < 2; i++) {

                deckCommonCard.get(i).setCommonTokens(commonTokenslist.get(i));

                deckCommonGoalCardInGame.add(i, deckCommonCard.get(i));

            }
        }
        if (numPlayers == 3) {

            List<List<CommonToken>> commonTokenslist = new ArrayList<>();

            commonTokenslist.add(new ArrayList<>());

            commonTokenslist.get(0).add(new CommonToken(8));
            commonTokenslist.get(0).add(new CommonToken(6));
            commonTokenslist.get(0).add(new CommonToken(4));

            commonTokenslist.add(new ArrayList<>());

            commonTokenslist.get(1).add(new CommonToken(8));
            commonTokenslist.get(1).add(new CommonToken(6));
            commonTokenslist.get(1).add(new CommonToken(4));


            for (int i = 0; i < 2; i++) {

                deckCommonCard.get(i).setCommonTokens(commonTokenslist.get(i));

                deckCommonGoalCardInGame.add(i, deckCommonCard.get(i));

            }
        }
        if (numPlayers == 4) {

            List<List<CommonToken>> commonTokenslist = new ArrayList<>();

            commonTokenslist.add(new ArrayList<>());

            commonTokenslist.get(0).add(new CommonToken(8));
            commonTokenslist.get(0).add(new CommonToken(6));
            commonTokenslist.get(0).add(new CommonToken(4));
            commonTokenslist.get(0).add(new CommonToken(2));

            commonTokenslist.add(new ArrayList<>());

            commonTokenslist.get(1).add(new CommonToken(8));
            commonTokenslist.get(1).add(new CommonToken(6));
            commonTokenslist.get(1).add(new CommonToken(4));
            commonTokenslist.get(1).add(new CommonToken(2));


            for (int i = 0; i < 2; i++) {

                deckCommonCard.get(i).setCommonTokens(commonTokenslist.get(i));

                deckCommonGoalCardInGame.add(i, deckCommonCard.get(i));

            }
        }


        for (CommonGoalCard x:
             deckCommonGoalCardInGame) {

            System.out.println(x);

            System.out.println();

            for (CommonToken y : x.getCommonTokens()){

                System.out.println(y);

            }

            System.out.println();

        }


    }

    @org.junit.jupiter.api.Test
    void testRun() {
    }

    @Override
    public int countTestCases() {
        return 0;
    }

    @Override
    public void run(TestResult testResult) {


    }




}