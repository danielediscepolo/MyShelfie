package it.polimi.ingsw.model;

import it.polimi.ingsw.model.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.logging.Logger;


public class WaitingRoom {

    static Logger logger = Logger.getLogger("Logger");
    private final int maxNumberOfPlayers;
    private List<Player> players = new ArrayList<>();

    public WaitingRoom(int numOfPlayers) {
        this.maxNumberOfPlayers = numOfPlayers;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public int size(){
        return players.size();
    }

    public boolean isFull(){
        return players.size() == maxNumberOfPlayers;
    }

    public boolean isEmpty(){
        return players.size() == 0;
    }

    public void addPlayer(Player player) throws RuntimeException {
        if(this.players.size() < this.maxNumberOfPlayers) {
            this.players.add(player);
        } else{
            throw new RuntimeException();
        }
    }

    public void removePlayer(Player player){
        this.players.remove(player);
    }

    public void flush(){
        players = new ArrayList<>();
    }

    /**It checks if in the context of waiting room exists a CONNECTED player with the specified name.
     * @param name describing the player.
     * @return the existing player or NULL.
     *
     * */
    public Player existingPlayerWithSameName(String name) {

        ListIterator<Player> iter = players.listIterator();
        while(iter.hasNext()){
            Player player = iter.next();
            if(player.getName().equals(name)){
                if(!player.isConnected()){
                    iter.remove();
                    logger.info("Found player '"+player.getName()+"' in waiting Room "+this.maxNumberOfPlayers+". Not connected removing it.");

                }else{
                    return player;
                }
            }
        }
        return null;
    }

    /**Utility method, it checks if every player in a waiting room, is connected. Kick it if it is not.
     * As a redundant check, it checks if the room is full
     * @return boolean*/
    public boolean everybodyInWaitingRoomIsConnected() {
        boolean isEverybodyConnected = true;
        List<Player> playerListCopy = new ArrayList<>(players);

        for (Player p: playerListCopy) {
            if (!p.isConnected()){
               removePlayer(p);
                isEverybodyConnected = false;
            }
        }

        return isEverybodyConnected;
    }

}

