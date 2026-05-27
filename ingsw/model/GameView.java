package it.polimi.ingsw.model;

import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.util.Observable;
import it.polimi.ingsw.util.Observer;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.List;

public class GameView  extends Observable<Game.Event> implements Observer<Game, Game.Event>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Game model;

    public GameView(Game game){
        if (game == null) {
            throw new IllegalArgumentException();
        }
        this.model = game;
    }

    @Override
    public void update(Game o, Game.Event arg) {
        this.model = o;
        setChanged();
        notifyObservers(arg);
    }

    public Tile[][] getBoardBoxes(){
        return this.model.getBoard().getBoxes();
    }

    public Board getBoard(){ return this.model.getBoard();}

    public Bookshelf getTurnPlayerBookshelf(){ return this.model.getTurnPlayerBookshelf();}

    public String getTurnPlayerName(){ return this.model.getTurnPlayerName();}

    public List<PlayerInGame> getPlayers(){
        return this.model.getPlayers();
    }
    public Player getTurnPlayer(){ return this.model.getTurnPlayer();}

    public Game getModel(){ return model;}

    public PlayerInGame getWinner(){ return model.getWinner();}

    public List<CommonGoalCard> getCommonGoalCards() {
        return this.model.getCommonGoalCards();
    }

    public PersonalGoalCard getTurnPlayerPersonalGoalCard() {
        return this.model.getTurnPlayerPersonalGoalCard();
    }
}
