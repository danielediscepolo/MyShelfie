package it.polimi.ingsw.network.message;

import java.io.Serial;
import java.io.Serializable;

/** Abstract Serializable class representing the Message of the Communication Protocol.<br />
 * Each message is a wrapper for specific information, and is conveyed by the network sublayer (RMI or Socket)<br />
 * The messages available in the communication protocol are as follows:<ul>
 *      <li>LoginMessage: Client -> Server:
 *               Message to make a login request.<br />
 *               Client side: Insert name and numOfPlayers.<br />
 *               Server side: Unpack the Message, create a New Player obj and add a connection object in it.</li>
 *      <li>NameAvailabilityMessage: Server -> Client:
 *               Message to inform the client whether the chosen name is available or not.</br>
 *               Client side: In case of availability, wait for the game to start, otherwise asks the player for a new name.<br/>
 *               Server side: see ServerApp </li>
 *      <li>UpdateGameMessage: Server -> Client:
 *               Message to update the client-side model of the game.</br>
 *               Client side: The Clients can interpret it as the start of game, but not as the start of a turn (for this it must wait for MoveRequestMessage).<br/>
 *               Server side: Sent to everybody at STARTGAME, STARTTURN, ENDTURN</li>
 *      <li>MoveRequestMessage: Server -> Client:
 *               Message used whenever the player is asked to make a move, including the start of the turn.</br>
 *               It has attributes to specify the type of move ( PICKING,SORTING, COLUMNCHOICE, CONFIRMATION ).</br>
 *               Client side: Wait for this message to start the turn and/or a particular move.<br/>
 *               Server side: Send it to client at start of turn (multiple times if at the endturn the client does not confirm the turn).</li>
 *      <li>PickMoveMessage: Client -> Server:
 *               Message to inform the server for the picked Tiles. It can contain the passedTurn flag.</br>
 *               Client side: Write into it the List of positions in the Board or the passedTurn flag. <br/>
 *               Server side: Check if the turn is passed, in this case skip the turn, otherwise take the List of positions in the Board from the message.</li>
 *      <li>SortMoveMessage: Client -> Server:
 *               Message to inform the server for the sorted tiles. It can contain the passedTurn flag.</br>
 *               Client side: Write into it the sorted List of tiles or the passedTurn flag.<br/>
 *               Server side: Check if the turn is passed, in this case skip the turn, otherwise take the sorted List of tiles from the message.</li>
 *      <li>ColumnMoveMessage: Client -> Server:
 *               Message to inform the server for the Column chosen. It can contain the passedTurn flag. </br>
 *               Client side: Write into it the column number or the passedTurn flag.<br/>
 *               Server side: Check if the turn is passed, in this case skip the turn, otherwise take the column from the message.</li>
 *      <li>TurnConfirmationMessage: Client -> Server:
 *               Message used by the client to confirm a Turn.</br>
 *               Client side: Sent after confirmation prompt (upon reception of MoveRequestMessage of Reception type)<br/>
 *               Server side: Upon reception, server can restart the turn or move to the next player. </li>
 *      <li>ResponseMessage: Server -> Client:
 *               General response message that the server sends after a move made by the player.<br/>
 *               It may contain a confirmation of the move or a relative error.</br>
 *               Client side: If there is an error repeat the move, otherwise wait for next MoveMessage.<br/>
 *               Server side: Sent after a move</li>
 *      <li>EndGameMessage: Server -> Client
 *               Message to inform the Client that the game is concluded and it is time to announce the winner.</br>
 *               Client side: Take Winner's name from the message and update it.<br/>
 *               Server side: Sent at the end og the game. This is the last message sent by a controller thread.</li>
 *      <li>UpdateChatMessage: Server -> Client
 *               Message to update the client-side model of the Chat.</br>
 *               Client side: Update the Client-side model chat and thus also the view.<br/>
 *               Server side: Sent as a consequence of Server-side model modification, usually this corresponds to ChatMessage reception.</li>
 *      <li>ChatMessage: Client -> Server
 *               General Chat message.</br>
 *               Client side: Created from Sender name, Recipient name, Body, metadata<br/>
 *               Server side: The server has to process it and send an UpdateChatMessage to the recipient(s).</li>
 *      <li>PingMessage: Server <-> Client
 *               Simple Ping message to test connection.</br>
 *               Both  sides: Sent for test network connection. Discarded on reception.<br/>
 *
 * </ul>
 * @since 1.0
 * @version 1.0
 */
public abstract class Message implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;



}
