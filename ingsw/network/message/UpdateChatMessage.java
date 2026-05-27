package it.polimi.ingsw.network.message;

/**Communication Protocol:<br />
 *      <li>UpdateChatMessage: Server -> Client
 *               Message to update the client-side model of the Chat.</br>
 *               Client side: Update the Client-side model chat and thus also the view.<br/>
 *               Server side: Sent as a consequence of Server-side model modification, usually this corresponds to ChatMessage reception.</li>
 * @see Message
 * @since 1.0
 * @version 1.0
 *  */
@Deprecated
public class UpdateChatMessage extends Message{
    ChatMessage[] chat;

    public UpdateChatMessage(ChatMessage[] chat) {
        this.chat = chat;
    }

    public ChatMessage[] getChat() {
        return chat;
    }
}
