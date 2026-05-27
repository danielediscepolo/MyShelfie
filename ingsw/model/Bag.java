package it.polimi.ingsw.model;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Bag implements Serializable{


    @Serial
    private static final long serialVersionUID = 1L;

    private List<Tile> tiles = new ArrayList<>();

    public Bag(List<Tile> tiles) {

        this.tiles = tiles;
    }

    public List<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(List<Tile> tiles) {
        this.tiles = tiles;
    }

    @Override
    public String toString() {
        return "Bag{" +
                "tiles=" + tiles +
                '}';
    }
}
