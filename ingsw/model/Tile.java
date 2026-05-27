package it.polimi.ingsw.model;

import javafx.scene.image.ImageView;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Tile implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private final Color color;
    private final Figure figure;

    private boolean hasBeenClicked = false;

    private final List<String> blueTilesGuiReferences = new ArrayList<>();
    private final List<String> whiteTilesGuiReferences = new ArrayList<>();
    private final List<String> yellowTilesGuiReferences = new ArrayList<>();
    private final List<String> lightBlueTilesGuiReferences = new ArrayList<>();
    private final List<String> pinkTilesGuiReferences = new ArrayList<>();
    private final List<String> greenTilesGuiReferences = new ArrayList<>();


    private  String guiReference;

    public Tile(Color color, Figure figure) {
        this.color = color;
        this.figure = figure;

        createListOfGuiReferenceAccordingToColor();
    }

    public Color getColor() {
        return color;
    }

    public Figure getFigure() {
        return figure;
    }


    //Tested
    @Override
    public boolean equals(Object o) throws NullPointerException{
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return color == tile.color && figure == tile.figure;
    }

    public String getGuiReference() {
        if (color == Color.Blue){
            this.guiReference= drawRandomImageOfASpecificTileColor(blueTilesGuiReferences);
        }
        else if (color == Color.Pink){
            this.guiReference= drawRandomImageOfASpecificTileColor(pinkTilesGuiReferences);
        }
        else if (color == Color.Yellow) {
            this.guiReference= drawRandomImageOfASpecificTileColor(yellowTilesGuiReferences);
        }
        else if (color == Color.White) {
            this.guiReference= drawRandomImageOfASpecificTileColor(whiteTilesGuiReferences);
        }
        else if (color == Color.LightBlue) {
            this.guiReference= drawRandomImageOfASpecificTileColor(lightBlueTilesGuiReferences);
        }
        else if (color == Color.Green) {
            this.guiReference= drawRandomImageOfASpecificTileColor(greenTilesGuiReferences);
        }
        return guiReference;
    }

    @Override
    public String toString() {
        return "(" + color + ","+ figure +")";
    }


    private String drawRandomImageOfASpecificTileColor(List<String> listOfTileColorGuiReferences){

        Collections.shuffle(listOfTileColorGuiReferences);

        return listOfTileColorGuiReferences.get(0);
    }




    private void createListOfGuiReferenceAccordingToColor(){

        blueTilesGuiReferences.add("/Graphics/item_tiles/Cornici1.1.png");
        blueTilesGuiReferences.add("/Graphics/item_tiles/Cornici1.2.png");
        blueTilesGuiReferences.add("/Graphics/item_tiles/Cornici1.3.png");

        greenTilesGuiReferences.add("/Graphics/item_tiles/Gatti1.1.png");
        greenTilesGuiReferences.add("/Graphics/item_tiles/Gatti1.2.png");
        greenTilesGuiReferences.add("/Graphics/item_tiles/Gatti1.3.png");

        yellowTilesGuiReferences.add("/Graphics/item_tiles/Giochi1.1.png");
        yellowTilesGuiReferences.add("/Graphics/item_tiles/Giochi1.2.png");
        yellowTilesGuiReferences.add("/Graphics/item_tiles/Giochi1.3.png");

        whiteTilesGuiReferences.add("/Graphics/item_tiles/Libri1.2.png");
        whiteTilesGuiReferences.add("/Graphics/item_tiles/Libri1.1.png");
        whiteTilesGuiReferences.add("/Graphics/item_tiles/Libri1.3.png");

        pinkTilesGuiReferences.add("/Graphics/item_tiles/Piante1.1.png");
        pinkTilesGuiReferences.add("/Graphics/item_tiles/Piante1.2.png");
        pinkTilesGuiReferences.add("/Graphics/item_tiles/Piante1.3.png");

        lightBlueTilesGuiReferences.add("/Graphics/item_tiles/Trofei1.1.png");
        lightBlueTilesGuiReferences.add("/Graphics/item_tiles/Trofei1.2.png");
        lightBlueTilesGuiReferences.add("/Graphics/item_tiles/Trofei1.3.png");


    }


    public boolean hasBeenClicked() {
        return hasBeenClicked;
    }

    public void setHasBeenClicked(boolean hasBeenClicked) {
        this.hasBeenClicked = hasBeenClicked;
    }


}