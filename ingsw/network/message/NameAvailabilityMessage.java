package it.polimi.ingsw.network.message;

/**Communication Protocol:<br />
 *      <li>NameAvailabilityMessage: Server -> Client:
 *               Message to inform the client whether the chosen name is available or not.</br>
 *               Client side: In case of availability, wait for the game to start, otherwise asks the player for a new name.<br/>
 *               Server side: see ServerApp </li>
 * @see Message
 * @since 1.0
 * @version 1.0
 **/
public class NameAvailabilityMessage extends Message{
    boolean available;
    public NameAvailabilityMessage(boolean available) {
        this.available=available;
    }

    public boolean isAvailable() {
        return available;
    }
}
