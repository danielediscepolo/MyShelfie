package it.polimi.ingsw.network.message;

import it.polimi.ingsw.exception.BadNumberOfTilesToSortException;
import it.polimi.ingsw.model.Position;

import java.util.List;

/**Communication Protocol:<br />
 *      <li>PickMoveMessage: Client -> Server:
 *               Message to inform the server for the picked Tiles. It can contain the passedTurn flag.</br>
 *               Client side: Write into it the List of positions in the Board or the passedTurn flag. <br/>
 *               Server side: Check if the turn is passed, in this case skip the turn, otherwise take the List of positions in the Board from the message.</li>
 * @see Message
 * @since 1.0
 * @version 1.0
 *  */
public class PickMoveMessage extends Message{
    List<Position> posix;
    boolean passedTurn;

    public PickMoveMessage(){
        this.passedTurn = true;
    }

    public PickMoveMessage(List<Position> posix) /*throws BadNumberOfTilesToSortException*/ {
/*        if(posix.size() > 3){
            throw new BadNumberOfTilesToSortException(posix.size());
        }*/
        this.posix = posix;
        this.passedTurn = false;
    }

    public List<Position> getPosix() {
        return posix;
    }

    public boolean isPassedTurn(){ return passedTurn;}
}
