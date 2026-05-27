package it.polimi.ingsw.network.message;

/**Communication Protocol:<br />
 *       <li>ResponseMessage: Server -> Client:
 *               General response message that the server sends after a move made by the player.<br/>
 *               It may contain a confirmation of the move or a relative error.</br>
 *               Client side: If there is an error repeat the move, otherwise wait for next MoveMessage.<br/>
 *               Server side: Sent after a move</li>
 * @see Message
 * @since 1.0
 * @version 1.0
 * */
public class ResponseMessage extends Message {

    ResponseType responseType;

    public enum ResponseType {
        NONPICKABLETILES,
        BADSORTEDTILESLIST,
        NOTENOUGHFREEBOXESINCOLUMN,
        PICKCONFIRMATION,
        SORTCONFIRMATION,
        COLCONFIRMATION;
    }

    public ResponseMessage(ResponseType responseType) {
        this.responseType = responseType;
    }

    public ResponseType getResponseType() {
        return responseType;
    }

}
