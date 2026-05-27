package it.polimi.ingsw.network.message;

import it.polimi.ingsw.exception.BadNumberOfTilesToSortException;
import it.polimi.ingsw.model.Tile;

import java.util.List;

/**Communication Protocol:<br />
 *      <li>SortMoveMessage: Client -> Server:
 *               Message to inform the server for the sorted tiles. It can contain the passedTurn flag.</br>
 *               Client side: Write into it the sorted List of tiles or the passedTurn flag.<br/>
 *               Server side: Check if the turn is passed, in this case skip the turn, otherwise take the sorted List of tiles from the message.</li>
 * @see Message
 * @since 1.0
 * @version 1.0
 *  */
public class SortMoveMessage extends Message{
    List<Tile> tiles;
    boolean passedTurn;

    public SortMoveMessage(){
        this.passedTurn = true;
    }

    public SortMoveMessage(List<Tile> tiles) /*throws BadNumberOfTilesToSortException*/ {
/*        if(tiles.size() > 3){
            throw new BadNumberOfTilesToSortException(tiles.size());
        }*/
        this.tiles = tiles;
        this.passedTurn = false;
    }

    public List<Tile> getSortedTiles() {
        return tiles;
    }

    public boolean isPassedTurn(){ return passedTurn;}
}
