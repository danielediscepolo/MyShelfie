package it.polimi.ingsw.network.message;

import it.polimi.ingsw.model.Player;

/**Communication Protocol:<br />
 *      <li>EndGameMessage: Server -> Client
 *               Message to inform the Client that the game is concluded and it is time to announce the winner.</br>
 *               Client side: Take Winner's name from the message and update it.<br/>
 *               Server side: Sent at the end og the game. This is the last message sent by a controller thread.</li>
 * @see Message
 * @since 1.0
 * @version 1.0
 * */
public class EndGameMessage extends Message{
    String winner;
    String otherInfo;

    public EndGameMessage(String winner, String otherInfo) {
        this.winner = winner;
        this.otherInfo = otherInfo;
    }

    public String getWinner() {
        return winner;
    }

    public String getOtherInfo() {
        return otherInfo;
    }
}
