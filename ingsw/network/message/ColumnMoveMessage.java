package it.polimi.ingsw.network.message;

import it.polimi.ingsw.exception.BadColumnNumberException;

/**Communication Protocol:<br />
 *      <li>ColumnMoveMessage: Client -> Server:
 *               Message to inform the server for the Column chosen. It can contain the passedTurn flag. </br>
 *               Client side: Write into it the column number or the passedTurn flag.<br/>
 *               Server side: Check if the turn is passed, in this case skip the turn, otherwise take the column from the message.</li>
 * @see Message
 * @since 1.0
 * @version 1.0
 * */
public class ColumnMoveMessage extends Message{
    int column;
    boolean passedTurn;

    public ColumnMoveMessage(){
        this.passedTurn = true;
    }

    public ColumnMoveMessage(int column) /*throws BadColumnNumberException*/ {
/*        if(column > 5 || column < 1){
            throw new BadColumnNumberException(column);
        }*/
        this.column = column;
        this.passedTurn=false;
    }

    public int getColumn() {
        return column;
    }

    public boolean isPassedTurn(){ return passedTurn;}
}
