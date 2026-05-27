package it.polimi.ingsw.network.message;

import it.polimi.ingsw.exception.BadNumberOfPlayersException;

/**Communication Protocol:<br />
 *      <li>LoginMessage: Client -> Server:
 *               Message to make a login request.<br />
 *               Client side: Insert name and numOfPlayers.<br />
 *               Server side: Unpack the Message, create a New Player obj and add a connection object in it.</li>
 * @see Message
 * @since 1.0
 * @version 1.0
 * */
public class LoginMessage extends Message{

    String name;
    int waitingRoomPreference;

    public LoginMessage(String name, int waitingRoomPreference) throws BadNumberOfPlayersException {
        if(waitingRoomPreference < 2 || waitingRoomPreference > 4){
            throw new BadNumberOfPlayersException(waitingRoomPreference);
        }
        this.name = name;
        this.waitingRoomPreference = waitingRoomPreference;
    }

    public String getName() {
        return name;
    }

    public int getWaitingRoomPreference() {
        return waitingRoomPreference;
    }

}
