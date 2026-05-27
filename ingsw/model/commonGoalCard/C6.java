package it.polimi.ingsw.model.commonGoalCard;

import it.polimi.ingsw.model.*;

public class C6 extends CommonGoalCard {

    public C6() {

        super.description = """
                Eight tiles of the same type. There’s no
                restriction about the position of these
                tiles.
                """ ;
        this.guiReference = "/Graphics/common_goal_cards/9.jpg";

    }

    @Override
    public CommonToken compare(Bookshelf bookshelf) {


        Tile[][] playerMatrix = bookshelf.getMatrix();
        boolean goalAchieved = false;

        int greenCount = 0;
        int whiteCount = 0;
        int pinkCount = 0;
        int yellowCount = 0;
        int blueCount = 0;
        int lightblueCount = 0;


        Tile greenTile = new Tile(Color.Green, Figure.Cats);
        Tile whiteTile = new Tile(Color.White,Figure.Books);
        Tile yellowTile = new Tile(Color.Yellow,Figure.Games);
        Tile blueTile = new Tile(Color.Blue,Figure.Frames);
        Tile lightBlueTile = new Tile(Color.LightBlue,Figure.Trophies);
        Tile pinkTile = new Tile(Color.Pink,Figure.Plants);




        for (int i = 0; i < bookshelf.getRows(); i++) {

            for (int j = 0; j < bookshelf.getColumns(); j++) {

                if (playerMatrix[i][j] != null && playerMatrix[i][j].equals(greenTile)){

                    greenCount ++;

                } else if (playerMatrix[i][j] != null && playerMatrix[i][j].equals(yellowTile)) {

                    yellowCount ++;

                }else if (playerMatrix[i][j] != null && playerMatrix[i][j].equals(whiteTile)) {

                    whiteCount ++;

                }else if (playerMatrix[i][j] != null && playerMatrix[i][j].equals(blueTile)) {

                    blueCount ++;

                }else if (playerMatrix[i][j] != null && playerMatrix[i][j].equals(lightBlueTile)) {

                    lightblueCount ++;

                } else if (playerMatrix[i][j] != null && playerMatrix[i][j].equals(pinkTile)) {

                    pinkCount++;

                }

            }
        }


        if (greenCount >= 8 || yellowCount >= 8 || whiteCount >= 8 || blueCount >= 8 || lightblueCount >= 8 || pinkCount >= 8){

            goalAchieved = true;
        }





        if (goalAchieved){

            return assignCommonTokenAndRemoveFromList();

        }else

            return new CommonToken();                     //il costruttore di default inserisce come punteggio di default 0
    }

}

