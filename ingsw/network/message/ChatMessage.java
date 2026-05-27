package it.polimi.ingsw.network.message;
import it.polimi.ingsw.model.Player;

/**Communication Protocol:<br />
 *      <li>ChatMessage: Client -> Server
 *               General Chat message.</br>
 *               Client side: Created from Sender name, Recipient name, Body, metadata<br/>
 *               Server side: The server has to process it and send an UpdateChatMessage to the recipient(s).</li>
 * @see Message
 * @since 1.0
 * @version 1.0
 * @deprecated
 * */
public class ChatMessage extends Message{
    Player sender;
    Player recipient;
    String body;
    String metadata;

    public ChatMessage(Player sender, Player recipient, String body, String metadata) {
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
        this.metadata = metadata;
    }

    public ChatMessage(Player sender, Player recipient, String body) {
        this.sender = sender;
        this.recipient = recipient;
        this.body = body;
    }

    public Player getRecipient() {
        return recipient;
    }

    public String getBody() {
        return body;
    }

    public String getMetadata() {
        return metadata;
    }

    public Player getSender() {
        return sender;
    }
}
