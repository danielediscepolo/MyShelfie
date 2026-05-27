package it.polimi.ingsw.network.message;

/**Communication Protocol:<br />
 *      <li>PingMessage: Server <-> Client
 *               Simple Ping message to test connection.</br>
 *               Both  sides: Sent for test network connection. Discarded on reception.<br/>
 * @see Message
 * @since 1.0
 * @version 1.0
 * */
public class PingMessage extends Message{
    public PingMessage(){}
}
