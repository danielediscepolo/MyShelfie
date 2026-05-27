package it.polimi.ingsw;

import it.polimi.ingsw.controller.PickTilesController;
import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoalCard.*;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.NameAvailabilityMessage;
import it.polimi.ingsw.network.message.PickMoveMessage;
import it.polimi.ingsw.network.message.UpdateGameMessage;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.gui.GUI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

public class GuiTest {


    public static void main(String[] args) {

        Logger logger = Logger.getLogger("Logger");


        new Thread(()->GUI.main(args)).start();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        UserInterface UI = GUI.gui;

        UI.endgame("Buba");
        logger.info("Starting GUI interface");
        UI.startInterface();

/*
      Object[] loginMessage =  UI.welcome();

        System.out.println("Nome Giocatore: " + loginMessage[0]);
       System.out.println("Preferenza Giocatore: " + loginMessage[1]);
  players.add(new Player((String) loginMessage[0]));
*/
       List<Player> players = new ArrayList<>();

       players.add(new Player("Don"));
       players.add(new Player("Matteo"));
        players.add(new Player("Marco"));




        List<CommonGoalCard> deckCommonCard = createDeckCommonCard();         //Creating commonGoalCards

        List<PersonalGoalCard> deckPersonalCard = createDeckPersonalCard();   //Creating personalGoalCards

        List<Tile> tiles = createArrayTile();                                 //Creating the tiles

        EndGameToken endGameToken = new EndGameToken();                       //Creating the endgameToken

        List<CommonGoalCard> deckCommonGoalCardInGame = drawCommonGoalCard(deckCommonCard, players.size());

        List<PersonalGoalCard> deckPersonalGoalCardInGame = drawPersonalGoalCard(deckPersonalCard, players.size());

        List<PlayerInGame> playersInGame = createPlayers(players);

        Game game = new Game("Boh", playersInGame, deckPersonalGoalCardInGame, endGameToken, tiles, deckCommonGoalCardInGame);
         GameView gameView = new GameView(game);

       // UI.waitingRoom("aaaaaao",300);

        UpdateGameMessage updateGameMessage = new UpdateGameMessage(gameView);

       // UI.updateModel(updateGameMessage, (String) loginMessage[0]);
       //  UI.updateModel(updateGameMessage.getModel(), "Don");


        PickMoveMessage pickMoveMessage = new PickMoveMessage();

   List<Position> posix =  UI.pickTilesFromBoard();

   System.out.println(posix);
  List<Tile> tiles2 = UI.sortTilesToInsert(new ArrayList<>());

    //System.out.println(tiles2);
    int choose = UI.chooseColumn();
       System.out.println(choose);



       // System.out.println(UI.confirmChoice("Connection Failure. Do you want to retry? Clicking NO, will exit from interface."));

    }


    /**
     * It creates the complete deck, one of each type, of all twelve commonGoalCards through all its respective subclasses.
     *
     * @return a list of twelve commonGoalCards.
     */
    private static List<CommonGoalCard> createDeckCommonCard() {

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

        return deckCommonCard;
    }

    /**
     * It creates the complete deck of all 12 personalGoalCards using 12 different arrays. Each of the 12 arrays initialises
     * a different personalGoalCard via an array of positions.
     * The bookshelf constructor uses the positions according to the following scheme:
     *         posix[0] greenTile row                posix[1] greenTile column
     *         posix[2] whiteTile row                posix[3] whiteTile column
     *         posix[4] yellowTile row               posix[5] yellowTile column
     *         posix[6] BlueTile row                 posix[7] blueTile column
     *         posix[8] LightBlueTile row            posix[9] lightBlueTile column
     *         posix[10] PinkTile row                posix[11] pinkTile column
     *
     * @return a list of twelve personalGoalCards.
     */
    private static List<PersonalGoalCard> createDeckPersonalCard() {

        int[] posix1 = {1, 4, 2, 3, 3, 1, 0, 2, 5, 2, 0, 0};
        int[] posix2 = {2, 0, 3, 4, 2, 2, 5, 4, 4, 3, 1, 1};
        int[] posix3 = {3, 1, 5, 0, 1, 3, 1, 0, 3, 4, 2, 2};
        int[] posix4 = {4, 2, 4, 1, 0, 4, 2, 2, 0, 2, 3, 3};
        int[] posix5 = {5, 3, 3, 2, 5, 0, 3, 1, 1, 1, 4, 4};
        int[] posix6 = {0, 4, 2, 3, 4, 1, 4, 3, 0, 2, 5, 0};
        int[] posix7 = {0, 0, 5, 2, 4, 4, 1, 3, 3, 0, 2, 1};
        int[] posix8 = {1, 1, 4, 3, 5, 3, 0, 4, 2, 2, 3, 0};
        int[] posix9 = {2, 2, 3, 4, 0, 2, 5, 0, 4, 1, 4, 4};
        int[] posix10 = {3, 3, 2, 0, 1, 1, 4, 1, 0, 4, 5, 3};
        int[] posix11 = {4, 4, 1, 1, 2, 0, 3, 2, 5, 3, 0, 2};
        int[] posix12 = {5, 0, 0, 2, 4, 4, 2, 2, 3, 3, 1, 1};


        List<PersonalGoalCard> deckPersonalCard = new ArrayList<>(12);

        deckPersonalCard.add(new PersonalGoalCard(new Bookshelf(posix1),"/Graphics/personal_goal_cards/Personal_Goals.png"));
        deckPersonalCard.add(new PersonalGoalCard(new Bookshelf(posix2),"/Graphics/personal_goal_cards/Personal_Goals2.png"));
        deckPersonalCard.add(new PersonalGoalCard(new Bookshelf(posix3),"/Graphics/personal_goal_cards/Personal_Goals3.png"));
        deckPersonalCard.add(new PersonalGoalCard(new Bookshelf(posix4),"/Graphics/personal_goal_cards/Personal_Goals4.png"));
        deckPersonalCard.add(new PersonalGoalCard(new Bookshelf(posix5),"/Graphics/personal_goal_cards/Personal_Goals5.png"));
        deckPersonalCard.add(new PersonalGoalCard(new Bookshelf(posix6),"/Graphics/personal_goal_cards/Personal_Goals6.png"));
        deckPersonalCard.add(new PersonalGoalCard(new Bookshelf(posix7),"/Graphics/personal_goal_cards/Personal_Goals7.png"));
        deckPersonalCard.add(new PersonalGoalCard(new Bookshelf(posix8),"/Graphics/personal_goal_cards/Personal_Goals8.png"));
        deckPersonalCard.add(new PersonalGoalCard(new Bookshelf(posix9),"/Graphics/personal_goal_cards/Personal_Goals9.png"));
        deckPersonalCard.add(new PersonalGoalCard(new Bookshelf(posix10),"/Graphics/personal_goal_cards/Personal_Goals10.png"));
        deckPersonalCard.add(new PersonalGoalCard(new Bookshelf(posix11),"/Graphics/personal_goal_cards/Personal_Goals11.png"));
        deckPersonalCard.add(new PersonalGoalCard(new Bookshelf(posix12),"/Graphics/personal_goal_cards/Personal_Goals12.png"));

        return deckPersonalCard;
    }

    /**
     * It creates, sorted randomly, a list of all 132 tiles, (22 for each of the 6 types), in the game.
     *
     * @return a list of 132 tiles (22 for each of the 6 types).
     */
    private static List<Tile> createArrayTile() {

        //We initialise one for each of the six types

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

        for (Tile typeTile : typeTiles) {

            for (int i = 0; i < 22; i++) {
                tiles.add(typeTile);
            }

        }

        Collections.shuffle(tiles);

        return tiles;

    }

    /**
     * It creates a list of PlayerInGame by their respective names. Player is the representation of the client that has
     * connected, while PlayerInGame is the Player in the particular instance of the game.
     *
     * @param players list of clients that has connected to the game.
     * @return a list of PlayerInGame that represents players in the particular instance of the game.
     */
    private static List<PlayerInGame> createPlayers(List<Player> players) {

        List<PlayerInGame> playersInGame = new ArrayList<>(4);

        for (Player x : players) {
            playersInGame.add(new PlayerInGame(x.getName()));
        }

        return playersInGame;
    }

    /**
     * It draws as many personalGoalCards as there are players (maximum 4), and places them in a list.
     *
     * @param deckPersonalCard  a list of all twelve personalGoalCards
     * @param numPlayers number of players that has connected to the game.
     * @return a list of as many personalGoalCards as there are players (maximum 4).
     */
    private static List<PersonalGoalCard> drawPersonalGoalCard(List<PersonalGoalCard> deckPersonalCard, int numPlayers) {

        List<PersonalGoalCard> deckPersonalGoalCardInGame = new ArrayList<>(4);

        Collections.shuffle(deckPersonalCard);

        for (int i = 0; i < numPlayers; i++) {
            deckPersonalGoalCardInGame.add(i, deckPersonalCard.get(i));
        }

        return deckPersonalGoalCardInGame;
    }

    /**
     * It draws two commonGoalCards from the deck of all twelve commonGoalCards. Depending on the number of players, it also
     * initialises the arrayList of tokens and its values.
     *
     * @param deckCommonCard a list of all twelve commonGoalCards.
     * @param numPlayers number of players that has connected to the game.
     * @return a list of commonGoalCards that have as many tokens in their arrayList as there are connected players.
     */
    private static List<CommonGoalCard> drawCommonGoalCard(List<CommonGoalCard> deckCommonCard, int numPlayers) {

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


        return deckCommonGoalCardInGame;


    }


}



