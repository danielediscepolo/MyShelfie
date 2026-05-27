package it.polimi.ingsw.network.message;

/**Communication Protocol:<br />
 *      <li>MoveRequestMessage: Server -> Client:
 *               Message used whenever the player is asked to make a move, including the start of the turn.</br>
 *               It has attributes to specify the type of move ( PICKING,SORTING, COLUMNCHOICE, CONFIRMATION ).</br>
 *               Client side: Wait for this message to start the turn and/or a particular move.<br/>
 *               Server side: Send it to client at start of turn (multiple times if at the endturn the client does not confirm the turn).</li>
 * @see Message
 * @since 1.0
 * @version 1.0
 * */
public class MoveRequestMessage extends Message {

    Choice choice;

    public enum Choice{
        PICKING,
        SORTING,
        COLUMNCHOICE,
        CONFIRMATION;
    }

    public MoveRequestMessage(Choice choice) {
        this.choice = choice;
    }


    public Choice getChoice() {
        return choice;
    }



}
