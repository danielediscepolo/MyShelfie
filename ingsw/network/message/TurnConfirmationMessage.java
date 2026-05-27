package it.polimi.ingsw.network.message;

/**Communication Protocol:<br />
 *      <li>TurnConfirmationMessage: Client -> Server:
 *               Message used by the client to confirm a Turn.</br>
 *               Client side: Sent after confirmation prompt (upon reception of MoveRequestMessage of Reception type)<br/>
 *               Server side: Upon reception, server can restart the turn or move to the next player. </li>
 * @see Message
 * @since 1.0
 * @version 1.0
 *  */
public class TurnConfirmationMessage extends Message{
    Boolean isConfirmed;

    public TurnConfirmationMessage(Boolean isConfirmed) {
        this.isConfirmed = isConfirmed;
    }

    public Boolean getConfirmation() {
        return isConfirmed;
    }
}
