package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Game;
import it.polimi.ingsw.model.GameView;

/**Communication Protocol:<br />
 *      <li>UpdateGameMessage: Server -> Client:
 *               Message to update the client-side model of the game.</br>
 *               Client side: The Clients can interpret it as the start of game, but not as the start of a turn (for this it must wait for MoveRequestMessage).<br/>
 *               Server side: Sent to everybody at STARTGAME, STARTTURN, ENDTURN</li>
 * @see Message
 * @since 1.0
 * @version 1.0
 *  */
public class UpdateGameMessage extends Message {

    GameView model;

    public UpdateGameMessage(GameView model) {
        this.model = model;
    }

    public GameView getModel() {
        return model;
    }
}
