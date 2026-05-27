package it.polimi.ingsw.model;

import it.polimi.ingsw.exception.BadSortedTilesListException;
import it.polimi.ingsw.exception.NonPickableTilesException;
import it.polimi.ingsw.exception.NotEnoughFreeBoxesInColumnException;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.util.Observable;

import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

public class Game extends Observable<Game.Event> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    transient private final String name;
    transient private final List<Tile> tiles;
    private final Bag bag;

    private final List<PlayerInGame> players;
    private final Board board;
    private final PlayerInGame firstPlayer;
    private PlayerInGame turnPlayer;
    private PlayerInGame winner;

    private final List<CommonGoalCard> commonGoalCards;
    private final List<PersonalGoalCard> personalGoalCards;

    private final  EndGameToken endgameToken;
    private boolean lastTurns = false;

     private List<Position> positionsChosenByTurnPlayerInBoard;
     private List<Position> positionsChosenByTurnPlayerInBookshelf;


    /**
     * Constructor that creates and initialises the game associated with each Patrick.
     *
     * @param name of the game.
     * @param players list of PlayerInGame in the game.
     * @param personalGoalCards list of PersonalGoalCards (one per player) drawn randomly from Patrick's deck.
     * @param endgameToken of the game.
     * @param tiles that are partly placed on the board and the rest in the bag
     * @param commonGoalCards list of two CommonGoalCards drawn randomly from Patrick's deck.
     */
    public Game (String name, List<PlayerInGame> players,List<PersonalGoalCard> personalGoalCards,EndGameToken endgameToken,
                 List<Tile> tiles, List<CommonGoalCard> commonGoalCards) {

        this.name = name;

        this.endgameToken = endgameToken;

        this.commonGoalCards = commonGoalCards;

        this.personalGoalCards = personalGoalCards;

        this.tiles = tiles;

        this.players = initializePlayers(players,this.personalGoalCards);

        this.bag = initializeBag(tiles);

        this.board = initializeBoard(this.bag,this.players.size());

        this.firstPlayer = chooseFirstPlayer(this.players);

        this.turnPlayer = firstPlayer;

    }

    /**
     * Used only by the constructor.
     * Initialise the bag by placing all the tiles in it.
     *
     * @param tiles list of tiles created by Patrick.
     * @return an initialised bag.
     */
    private Bag initializeBag(List<Tile> tiles){
         Bag bag = new Bag(tiles);
         return bag;
    }

    /**
     * Used only by the constructor.
     * Initialise the board by creating it and making the first refill of the game, according to the number of players
     * connected.
     *
     * @param bag where all the tiles are contained
     * @param numPlayers number of PlayerInGames connected to the game.
     * @return an initialised Board.
     */
    private Board initializeBoard(Bag bag, int numPlayers){
        Board board = new Board(numPlayers);
        board.refill(bag);
        return board;
    }


    /**
     * Initialise the list of players by associating each PlayerInGame with its own bookshelf and PersonalGoalCard.
     *
     * @param players list of PlayerInGame connected to the game.
     * @param personalGoalCards list of PersonalGoalCards of the game.
     * @return an initialised list of PlayerInGame.
     */
    private List<PlayerInGame> initializePlayers (List<PlayerInGame> players, List<PersonalGoalCard> personalGoalCards){

        for (int i = 0; i < players.size(); i++) {

            players.get(i).setPlayers(personalGoalCards.get(i),new Bookshelf());

        }

        return players;
    }


    /**
     * After shuffling the list of all PlayerInGames, it chooses as firstPlayer the PlayerInGame at position zero of
     * the list and assigns it the chair.
     *
     * @param players list of PlayerInGame connected to the game.
     * @return the PlayerInGame at position zero of the shuffled list.
     */
    private PlayerInGame chooseFirstPlayer (List<PlayerInGame> players){

        Collections.shuffle(players);

        for (int i = 0; i < players.size(); i++) {
            players.get(i).setId(i+1);
        }

        assignChair(players.get(0));

        return players.get(0);
    }

    /**
     * Assigns the chair to the firstPlayer.
     *
     * @param player the PlayerInGame at position zero of the shuffled list of players.
     */
    private void assignChair (PlayerInGame player){
        player.setChair(new Chair());
    }


    /**
     * Method used by Patrick to remove the player's chosen tiles from the bookshelf in case he wanted to undo the
     * choice and make a new one from the pick.
     * Remove one tile at a time from the bookshelf using its position.
     */
    public void removeTilesChosenByTurnPlayerInBookshelf(){

        for (Position x: positionsChosenByTurnPlayerInBookshelf) {

            turnPlayer.getBookshelf().removeTileFromBookshelf(x);

        }

    }

    /**
     * First of the three methods used by the client to make its turn.
     * Method used by the turnPlayer to make its choice of tiles to pick from the board. The list of tiles that can be
     * taken depends on the free space in its bookshelf.The method checks if the list of positions  chosen by the
     * turnPlayer is compatible with those calculated by the board and if so converts the positions in tiles and sets
     * them as an attribute of turnPlayer.
     *
     * @param positionsChosenByTurnPlayer list of Positions chosen by the turnPlayer.
     * @return the list of tiles associated with the Positions chosen by the turnPlayer
     * @throws NonPickableTilesException in the event that the positions chosen are not compatible with those calculated
     * by the board.
     */
    public List<Tile> pickTileFromBoard(List<Position> positionsChosenByTurnPlayer) throws NonPickableTilesException {


        List<List<Position>> combinationsOfTakeableTiles = board.combinationOfTakeableTilesForTurnPlayer(turnPlayer.getBookshelf());
        //logica della scelta tutta su board
        //combination viene mandata al NetworkLayer che la indirizza al client

        if(!board.checkIfPositionsAreCorrect(combinationsOfTakeableTiles,positionsChosenByTurnPlayer)){
            throw new NonPickableTilesException();
        }

        this.positionsChosenByTurnPlayerInBoard = positionsChosenByTurnPlayer;


        List<Tile> tilesFromPlayer = board.convertPositionsToTiles(positionsChosenByTurnPlayerInBoard);

        turnPlayer.setTiles(tilesFromPlayer);

        return tilesFromPlayer;

        //il Client può scegliere solo una delle combinazioni possibili calcolate da Board

    }

    /**
     * Second of the three methods used by the client to make its turn.
     * Method used by the turnPlayer to make the choice of how to order the chosen tiles. The method checks if the list
     * of tiles chosen by the turnPlayer is compatible with the list of the previous pick (minus the order). If so,
     * it sets the list of tiles ordered by the player as an attribute of turnPlayer.
     *
     * @param orderedTiles list of tiles chosen by turnPlayer with a specific order.
     * @throws BadSortedTilesListException if the list of orderedTiles is not compatible with the list of the previous
     * pick (minus the order).
     */
    public void sortTileToInsert(List<Tile> orderedTiles) throws BadSortedTilesListException {

        if(!sameListDifferentOrder(orderedTiles,turnPlayer.getTiles())){
            throw new BadSortedTilesListException();
        }
        turnPlayer.setTiles(orderedTiles);

    }

    /**
     * Method used by sortTileToInsert.
     * The method checks if the list of tiles chosen by the turnPlayer during the sort is compatible with the list of
     * the previous pick (minus the order).
     *
     * @param orderedTiles list of tiles chosen by turnPlayer with a specific order.
     * @param notOrderedTiles list of tiles chosen by turnPlayer during the pickMove.
     * @return a boolean, true if the two lists are compatible.
     */
    private boolean sameListDifferentOrder(List<Tile> orderedTiles,List<Tile> notOrderedTiles){

        List<Tile> orderedTilesCopy = new ArrayList<>(orderedTiles);
        List<Tile> notOrderedTilesCopy = new ArrayList<>(notOrderedTiles);

        for (Tile x : orderedTilesCopy) {

            notOrderedTilesCopy.removeIf(x::equals);
        }

        return notOrderedTilesCopy.isEmpty();

    }

    /**
     * Third of the three methods used by the client to make its turn.
     * Method used by the turnPlayer to insert the chosen tiles (in order) into a given column of the bookshelf.
     * The method checks whether the choice made is possible by checking the free space available in the chosen column.
     * If so, it first saves the positions chosen by the turnPlayer which will be occupied by the tiles in the bookshelf
     * (in case it should later remove them), and then inserts the tiles in the bookshelf of the turnPlayer.
     *
     * @param turnPlayerChosenColumn index of the bookshelf column chosen by the turnplauer where the tiles will be
     * inserted.
     * @throws NotEnoughFreeBoxesInColumnException if there is not enough space in the column chosen by the turnPlayer
     * to insert tiles.
     */
    public void chooseColumn(int turnPlayerChosenColumn) throws NotEnoughFreeBoxesInColumnException {

        Bookshelf turnPlayerBookshelf = turnPlayer.getBookshelf();
        List<Tile> turnPlayerTiles = turnPlayer.getTiles();
        int numOfTilesChosen = turnPlayerTiles.size();

        if(!turnPlayerBookshelf.checkEnoughSpaceInColumn(turnPlayerChosenColumn, numOfTilesChosen)){
            throw new NotEnoughFreeBoxesInColumnException(turnPlayerChosenColumn);
        }

        positionsChosenByTurnPlayerInBookshelf = turnPlayerBookshelf.calculatePositionsInBookshelf(turnPlayerChosenColumn,turnPlayerTiles.size());

        turnPlayer.insertIntoBookshelf(turnPlayerChosenColumn,turnPlayerTiles);

    }

    /**
     * The method is only invoked server-side once the turnPlayer has completed his moves for good. The method checks whether
     * the player has completed one or two commonGoalCards and if so assigns turnPlayer the tokens with the respective
     * scores (depending on which token is left in the list of each card)
     */
    public void checkCommonGoalCard(){

        CommonToken commonToken1 = commonGoalCards.get(0).compare(turnPlayer.getBookshelf());

        if (commonToken1.getPoints() != 0){

            turnPlayer.addCommonTokenAndUpdatePoints(commonToken1);

        }

        CommonToken commonToken2 =  commonGoalCards.get(1).compare(turnPlayer.getBookshelf());

        if (commonToken2.getPoints() != 0){

            turnPlayer.addCommonTokenAndUpdatePoints(commonToken2);
        }

    }

    /**
     * The method is only invoked server-side once the turnPlayer has completed his moves for good. The method checks
     * whether the turnPlayer has completed his bookshelf and whether anyone else has done so before and if so assigns
     * him the endgameToken and sets lastTurns to true.
     */
    public void checkEndgame(){

        if (turnPlayer.getBookshelf().isFull() && !lastTurns) {

            this.lastTurns = true;

            turnPlayer.addEndgameTokenAndUpdatePoints(endgameToken);

        }

    }

    /**
     * The method is only invoked server-side once the turnPlayer has completed his moves for good. This method first
     * removes from the board the tiles chosen by the turnPlayer during his turn and then in case only isolated tiles
     * are left on the board (which can only be taken one at a time) calls for a refill.
     * */
    public void removeTilesChosenAndRefillBoard(){

        board.removeTilesChosenByTurnPlayerFromBoard(positionsChosenByTurnPlayerInBoard);

    if (board.maxNumOfTakeableTilesWithASingleMove() < 2){

        board.refill(bag);

    }

    }


    /**
     * This method handles the end of turnPlayer's turn. It checks whether the game should end by returning false if it
     * is the last turn and the last player (in id order) has also made his move (i.e. is turnPlayer), otherwise it
     * assigns the turn to the next player and returns true.
     */
    public boolean nextTurn(){

        if (lastTurns && (turnPlayer.getId() == players.size())){

             return false;

        }else{

            turnPlayer = nextPlayer();

            return true;
        }
    }

    /**
     * Metodo utilizzato da nextTurn per assegnare a turnPlayer il giocatore successivo (in id order)
     *
     * @return the player who will be turnPlayer on the next turn.
     * */
    private PlayerInGame nextPlayer (){

        if (turnPlayer.getId() != players.size()){

            return players.get(turnPlayer.getId());           // il +1 non ci vuole perchè l'array parte da 0 e non da 1

        }else

            return firstPlayer;

    }

    /**
     * This method is invoked when nextTurn becomes false. It calls the end of the game by assigning the winner.
     */
    public void endGame(){

        winner = calculateWinner();

    }

    /**
     * This method is used by endGame to calculate the winner of the game. It calculates the scores of the players in
     * the game by adding the points accumulated so far (of the commonGoalCards and/or endGameTokens) with the points
     * of the personalGoalCard goals and those of the adjacent tile groups in each player's bookshelf.
     * In the event of a tie (according to the rules) the player furthest, by id, from firstPlayer wins.
     *
     * @return the winning PlayerInGame.
     */
    private PlayerInGame calculateWinner () {

        PlayerInGame winner = players.get(0);

        int points = 0;

        for (PlayerInGame x : players) {

            points = x.checkStaticPoints();
            x.addPoints(points);

            points = x.checkPersonalGoalCard();
            x.addPoints(points);

        }

        int max = players.get(0).getTotalPoints();

        for (int i = 1; i < players.size(); i++) {

            if (players.get(i).getTotalPoints() >= max) {

                max = players.get(i).getTotalPoints();

                winner = players.get(i);
            }


        }

        return winner;
    }

    public PlayerInGame getWinner() {return this.winner;}

    public Board getBoard(){ return this.board;}

    public List<CommonGoalCard> getCommonGoalCards() {
        return commonGoalCards;
    }

    public Player getTurnPlayer() { return turnPlayer; }

    public String getTurnPlayerName(){
        return turnPlayer.getName();
    }

    public Bookshelf getTurnPlayerBookshelf(){ return this.turnPlayer.getBookshelf();}

    public PersonalGoalCard getTurnPlayerPersonalGoalCard(){ return this.turnPlayer.getPersonalGoalCard(); }

    public List<String> getPlayersNames(){
        return this.players.stream().map(PlayerInGame::getName).collect(Collectors.toCollection(ArrayList::new));
    }

    public List<PlayerInGame> getPlayers() {
        return players;
    }

    public void setChangedAndNotifyObservers(Event arg) {
        setChanged();
        notifyObservers(arg);
    }

    public enum Event {

        STARTGAME,
        ENDGAME,
        STARTTURN,
        ENDTURN,
        UPDATE;

    }


    public boolean isLastTurn(){

        return lastTurns && (turnPlayer.getId() == players.size());
    }
}


