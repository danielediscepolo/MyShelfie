package it.polimi.ingsw.view.cli;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;


import it.polimi.ingsw.model.*;
import it.polimi.ingsw.model.commonGoalCard.CommonGoalCard;
import it.polimi.ingsw.network.message.Message;
import it.polimi.ingsw.network.message.UpdateGameMessage;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.network.message.ChatMessage;

import static it.polimi.ingsw.view.cli.Toolbox.*;

/**CLI is Intended to handle the Input/output of the project in the context of a Command Line Interface
 * @see UserInterface
 * @since 1.0
 *
 * */

public class CLI implements UserInterface{
    private Boolean offline = false;
    private Boolean activeDebug = true;
    private BufferedReader bufferedReader;
    private PrintStream printStream;
    private static final Locale locale = Locale.ROOT;
    private WaitingRoomThread waitingRoom;
    private boolean waitingRoomInterrupt;

    /**Constructor
     * */
    public CLI() {
        this.bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        this.printStream = new PrintStream(System.out);
    }

    /**Constructor
     * */
    public CLI(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        this.printStream = new PrintStream(System.out);
    }

    /**Constructor
     * */
    public CLI(InputStream inputStream, Boolean offline) {
        this.offline = offline;
        this.printStream = new PrintStream(System.out);
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    public Boolean getOffline() {

        return offline;
    }

    public Boolean getActiveDebug() {

        return activeDebug;
    }

    public BufferedReader getInputStream() {

        return bufferedReader;
    }

    public PrintStream getPrintStream() {

        return this.printStream;
    }

    public void setOffline(Boolean offline) {

        this.offline = offline;
    }

    public void setOutputStream(PrintStream printStream) {

        this.printStream = new PrintStream(printStream);
    }

    public void setActiveDebug(Boolean activeDebug) {

        this.activeDebug = activeDebug;
    }

    public void setInputStream(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    /**Input method managing streams.
     * @return String
     * @param output Output String in the CLI describing the requested input*/
    public String inputString(String output){

        String input = "";
        out("\n"+output);
        try{
            input=this.bufferedReader.readLine();
            if(input == null){debugMessage("The End of the stream has been reached.");}
        }catch (IOException e){/*NOP*/}
        return input;
    }

    /**Input method casting Strings to Integers.
     * @return Integer
     * @param output Output String in the CLI describing the requested integer input*/
    public int inputInt(String output){
        int value;
        while (true) {
            try{
                String input = inputString(output);
                value = Integer.parseInt(input);
                return value;
            }catch(NumberFormatException exc){this.errorMessage("The input is not an integer.\nTry Again:");}
        }
    }

    /**Input method casting Strings to Integers.
     * @return Integer
     * @param output Output String in the CLI describing the requested integer input
     * @param lowerBound integer delimiting the lower bound of the input.
     * @param upperBound integer delimiting the upper bound of the input.*/
    public int inputInt(String output, int lowerBound, int upperBound){
        int value;
        while (true) {
            try{
                String input=this.inputString(output);
                this.debugMessage("Your input is "+input);
                value = Integer.parseInt(input);
                if(value >= lowerBound && value <= upperBound){
                    return value;
                }else{
                    this.errorMessage("The input is not in range. It must be between "+lowerBound+" and "+upperBound);
                }
            }catch(NumberFormatException exc){
                    this.errorMessage("The input is not an integer.");
            }
        }
    }

    /**Output method with carriage return.
     * @param output String to be output in the terminal*/
    public void outln(String output){
        out(output+"\n");
    }

    /**Output method without carriage return.
     * @param output String to be output in the terminal*/
    public void out(String output){
        this.printStream.print(output);
    }

    /**Setter to set internal waiting room interrupt attribute.</br>
     * Used to signal the waiting room thread to interrupt.
     * @param value Boolean for the interrupt setting.
     * */
    public void setWaitingRoomInterrupt(boolean value){this.waitingRoomInterrupt = value;}

    @Override
    public void endgame(String winner) {

        infoMessage("The winner is "+ winner +".");
        infoMessage("Thank you for playing with us.");
    }

    /**Getter for internal waiting room interrupt attribute.</br>
     * Used to signal the waiting room thread to interrupt.
     * @return  Boolean for the interrupt setting.
     * */
    public boolean getWaitingRoomInterrupt(){return this.waitingRoomInterrupt;}


    /**
     * Start the CLI, opening a new Terminal and printing the welcome message and the main menu.
     *
     * @return integer returned by interactiveMainMenu. An int representing the choice of the user.
     */
    @Override
    public int startInterface(){
        outln(banner);
        return interactiveMainMenu();
    }


    public void showBanner(){
        outln(banner);
    }


    /**Cyan Message informing the user, for example that she/he passed the turn.
     * @param output String to be printed in color.
     * */
    @Override
    public void infoMessage(String output){
        out("\n" + ANSI_CYAN + output + ANSI_RESET);
    }


    /**Red Message alerting the user of a specific error, for example that a chosen column is full.
     *@param output String to be printed in color.
     * */
    @Override
    public void errorMessage(String output){
        out("\n" + ANSI_RED + output + ANSI_RESET);
    }


    /**Yellow Message for debugging purposes.
     *@param output String to be printed in color.
     * */
    @Override
    public void debugMessage(String output){if(this.activeDebug){out(ANSI_YELLOW + "\nDEBUG: "+ output + ANSI_RESET);}}


    /**
     * General manager for displaying Easter eggs.<br />
     *
     * @param type integer representing the type of Easter egg to be displayed.
     */
    @Override
    public void easterEgg(int type) {
        if (type == 42) {
            outln(file42);
            infoMessage("Ehi! There is no question to this answer!");
            infoMessage("I need to calculate it!");
            //noinspection InfiniteLoopStatement
            while (true) {
                long sevenPointFiveMillionOfYears = Long.parseUnsignedLong("236520000000000");
                waitingRoom("Calculating the Ultimate Question of Life, the Universe, and Everything.", sevenPointFiveMillionOfYears);
            }
        }
    }


    /**Output method without carriage return and output in typewriter style.<br/>
     * Synchronized with PrintStream resource.
     * @param output String to be output in the terminal
     * @param milliseconds milliseconds from a char to another*/
    public synchronized void typewriterOut(String output, int milliseconds){
        for(int i=0; i<output.length(); i++){
            try {
                TimeUnit.MILLISECONDS.sleep(milliseconds);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.out(output.substring(i,i+1));
        }
    }


    /**Interface opening method.<br />
     * Print opening message, interactive menu and asks for name and number of players.
     *
     * @return Object[]:<br />
     * Object[0]: A String of player's name<br />
     * Object[1]: An Integer for number of players.
     */
    @Override
    public Object[] welcome() {

        Object[] ret;
        ret = introduceYourself();
        return ret;
    }


    /** Static method to ask the player for his/her name and for the number of players.
     *This method should never return a different type of object.
     * @return Object[]:<br />
     * Object[0]: A String of player's name<br />
     * Object[1]: An Integer for number of players.
     */
    Object[] introduceYourself(){

        Object[] ret = new Object[2];

        while(true) {
            String name=inputString(ANSI_BOLD+"Please choose a nickname:"+ANSI_RESET);
            if(name.length() > 20){
                outln("\n"+doge);
                outln(ANSI_BOLD+"Wow! Such length! Much Aristocracy!"+ANSI_RESET);
                outln("Let's call you...Black. So there'll be chances you'll be "+ANSI_ITALIC+"Serious"+ANSI_RESET+" about this.\n");
                name = "SiriusBlack";
            }
            int num=inputInt(ANSI_BOLD+"Please choose the number of players for your next game:"+ANSI_RESET, 2, 4);

            if(confirmChoice("Do you want to confirm your choices?")){
                ret[0] = name;
                ret[1] = num;
                return ret;
            }
        }
    }


    /** Static method to ask the players for their names in offline mode.
     *
     * @return List of names</>:<br />
     */
    public List<String> offlineIntroduceYourself() {

        List<String> playersNames = new ArrayList<>();

        int numOfPlayers = 0;
        boolean keepSpinning0 = true;
        while (keepSpinning0) {

            boolean keepSpinning1 = true;

            while (keepSpinning1) {
                String name = inputString(ANSI_BOLD + "Hi Player" + (numOfPlayers +1) + ". Please choose a nickname:" + ANSI_RESET);
                if (name.length() > 20) {
                    outln("\n" + doge);
                    outln(ANSI_BOLD + "Wow! Such length! Much Aristocracy!" + ANSI_RESET);
                    outln("Let's call you...Black. So there'll be chances you'll be " + ANSI_ITALIC + "Serious" + ANSI_RESET + " about this.\n");
                    name = "SiriusBlack";
                }

                if(confirmChoice("Do you want to confirm your choices?")){
                    playersNames.add(name);
                    numOfPlayers++;
                    keepSpinning1 = false;
                }
            }


            if(numOfPlayers>1 && numOfPlayers<4){
                if(!confirmChoice("Do you want to add another player?")){
                    keepSpinning0 = false;
                }
            }

            if(numOfPlayers>3){keepSpinning0 = false;}

        }
        return playersNames;
    }


    /*TODO -- Add menu choices
     *      -- Set a common communication protocol with the View
     *      -- Cannot Be Void*/
    /**Start Interactive menu.<br />
     * Return the choice or keep asking.
     * @return int representing the choice */
    public int interactiveMainMenu(){
        String[] menuOptions = {
                "Exit the game",
                "Show the Game Rules",
                "Continue to play",
                "Restart the Game in GUI Mode"
        };

        out("Welcome to the main menu of MyShelfie.");
        while(true) {
            outln("\nPlease chose from the following actions:");
            for (int i = 1; i < menuOptions.length; i++) {
                outln(i + ". " + menuOptions[i]);
            }
            outln(0 + ". " + menuOptions[0]);
            int choice = inputInt("\nChoose a number:");
            switch (choice) {
                case 0 -> exitInterface(0);
                case 1 -> out(rules);
                case 2 -> {return 2;}
                case 3 -> {return 3;}
                case 42 -> easterEgg(42);
                default -> outln("Functionality non implemented");
            }
        }
    }


    /**
     * Display the appropriate user interface for creating a game.<br />
     * The interface must ask the user for Game's name and num of players.
     *
     * @return String Name of the game.
     */
    @Override
    public String createGame(){
        outln("Creating a new game");
        String name = inputString("Please, input a name for the new game:");
        if(name.length() > 20){
            outln(doge);
            outln("Wow!, Such longness! Much Aristocracy!");
            outln("Let's call it...BluesNotes. It sounds smooth.");
            name = "BluesNotes";
        }
        outln("Thank you!\nWe are informing Patrick to create a new Game called "+ANSI_BLUE+name+ANSI_RESET);
        return name;
    }


    /**
     * Display a waiting room for few seconds<br />
     * @param message Message to Show
     * @param waitingTime Time in seconds to wait
     */
    @Override
    public void waitingRoom(String message,long waitingTime) {
        setWaitingRoomInterrupt(false);
        this.waitingRoom = new WaitingRoomThread(message, this, waitingTime);
        Thread thread = new Thread(this.waitingRoom);
        thread.start();
    }


    /**
     * Start the game interface.<br />
     *
     * @param game an array with all the information to start a game:<br />
     */
    @Override
    public void startGame(Object[] game) {
        infoMessage("You're game is starting!");
    }


    /*TODO -- Add checks for Tile consistency logic
     *     -- Add checks on repeated picks
     *     -- Specify in param output of inputString the format to describe a specific Tile
     *     -- Parse and validate the input format*/
    /**
     * Ask the user for Tiles.<br />
     * The Regex and the while ensure the input is in bound
     *
     * @return empty List if the user chooses to pass the turn, List of Position o length 1-3 otherwise.
     */
    @Override
    public List<Position> pickTilesFromBoard(){

        List<Position> posix = new ArrayList<>();
        String[] buff = new String[3];
        Pattern pattern = Pattern.compile("^R[012345678]C[0123456789]$", Pattern.CASE_INSENSITIVE);
        outln("Please choose a Tile or input 'pass' to pass.");
        outln("Chose a Tile with the following format: R<row number>C<column number>");
        //showBoard();
        int i=0;
        while(i<3){
            String choice = inputString("Choose Tile n°"+(i+1)+":");
            debugMessage("Your Choice is "+choice);
            if(choice.equals("pass")) {break;}
            if(pattern.matcher(choice).find()){
                buff[i] = choice;
                debugMessage("Regex check is PASSED");
                i++;
            }
        }
        String[] tiles = new String[i];
        System.arraycopy(buff, 0, tiles, 0, tiles.length);

        for(String str: tiles){
            posix.add(new Position(Character.getNumericValue(str.charAt(1)),Character.getNumericValue(str.charAt(3))));
        }
        debugMessage("The Position List has "+posix.size()+" elements");
        return posix;
    }


    /**
     * Display the appropriate user interface for sorting Tiles.<br />
     *
     * @param tiles List of Tiles to be ordered.
     * @return List of ordered Tiles.
     * */
    @Override
    public List<Tile> sortTilesToInsert(List<Tile> tiles){

        char ch = (char)(65 + tiles.size() - 1);
        debugMessage("tiles.size() == "+ tiles.size() );
        Pattern pattern = Pattern.compile("^(?:([A-"+ch+"])(?!.*\\1)){"+ ( tiles.size()) +"}$",Pattern.CASE_INSENSITIVE);
        out("\nPlease choose ordering of the above tiles using the following format: ABC, ACB, ecc.\nPlease consider that the first Tiles in this ordering will be the first to be inserted in the column.");

        String input;
        String ordering;
        while(true) {
             input = inputString("Type the ordering here:");
             debugMessage("Your input is '"+input+"'.");
             ordering = input.toUpperCase(locale);

            if (pattern.matcher(ordering).find()) { debugMessage("The input '"+ input +"' has been ACCEPTED.") ;break;
            }else{ errorMessage("The input does not match the ordering pattern."); }
        }

        List<Tile> orderedTiles = new ArrayList<>(3);
        for(int i=0; i<tiles.size(); i++){
            int index = ((int) ordering.charAt(i)) - 65;
            orderedTiles.add(tiles.get(index));
        }
        return orderedTiles;
    }


    /**Chose the column number to insert tiles into.
     * @return A number indicating the chosen column to place the tiles. */
    @Override
    public int chooseColumn() {
        return inputInt("Please specify a number (from 0 to 4) indicating the column in which you want to place the tiles.",0,4);
    }


    /**Simple loop asking for confirmation.
     * @return True or False. */
    @Override
    public Boolean confirmChoice(String output){

        while(true){
            String confirm=inputString(output+" [y/n]");

            switch (confirm.toLowerCase(locale)){
                case "y", "yes", "yep" -> { return true; }
                case "n", "no", "nope" -> { return false; }
                }
            }
    }


    /**
     * Display or update the Board.<br />
     *
     * @param matrix the 2-dimension Array of Tiles representing the Board.
     */
    private void showBoard(Tile[][] matrix) {

        StringBuilder out= new StringBuilder(boardStringHeader);
        String[][] colors = new String[9][9];
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(matrix[i][j] == null ){
                    colors[i][j] = "";
                }else{
                    switch(matrix[i][j].getColor()){
                        case Green -> colors[i][j] = BG_ANSI_GREEN;
                        case Yellow -> colors[i][j] = BG_ANSI_YELLOW;
                        case Blue -> colors[i][j] = BG_ANSI_BLUE;
                        case Pink -> colors[i][j] = BG_ANSI_PURPLE;
                        case White -> colors[i][j] = BG_ANSI_WHITE;
                        case LightBlue -> colors[i][j] = BG_ANSI_CYAN;
                    }
                }
            }
        }
        for(int i=0; i<9; i++){
            out.append(boardStringSeparator);
            out.append(boardspacer);
            for(int j=0; j<9; j++){
                out.append(boardStringLeftEdge).append(j == 0 ? "█" : "");
                out.append(colors[i][j].equals("") ? "░" : " ").append(colors[i][j]).append("     ").append(ANSI_RESET).append(colors[i][j].equals("") ? "░" : " ");
            }
            out.append("███  ").append(i).append("\n");
            out.append(boardspacer);
            for(int j=0; j<9; j++){
                out.append(boardStringLeftEdge).append(j == 0 ? "█" : "");
                out.append(colors[i][j].equals("") ? "░" : " ").append(colors[i][j]).append("     ").append(ANSI_RESET).append(colors[i][j].equals("") ? "░" : " ");
            }
            out.append("███     \n");

        }
        out.append(boardStringFooter);
        out(out.toString());
    }


    /**
     * Display or update the Common Goal Cards.<br />
     * The interface must ask the user for Game's name and num of players.
     *
     * @param commonGoalCards Array containing the Common Goal cards to be shown.
     * @return
     */
    private List<CommonGoalCard> showCommonGoalCards(List<CommonGoalCard> commonGoalCards) {
        outln("""
                This match has the following 2 Common Cards.
                --------------------------------------------
                Common Card n° 1:
                """);
        outln(commonGoalCards.get(0).getDescription());
        outln("Common Card n° 2:");
        outln(commonGoalCards.get(1).getDescription());
        return null;
    }


    /**
     * Display or update the Player Status.<br />
     * Show/Update all the elements of the player: bookshelf, personal goal card, Endgame token, ecc.
     *
     * @param player the Player to be shown.
     */
    private void showPlayerStatus(Player player) {
        infoMessage("Player name:"+player.getName());
        infoMessage("Connection Status:");
        infoMessage("Has Endgame Token: ");
    }


    /**
     * Display or update the bookshelf.<br />
     *
     * @param matrix A matrix representing the Bookshelf.
     */
    private void showBookshelf(Tile[][] matrix) {

        StringBuilder out= new StringBuilder(bookshelfStringHeader);
        String[][] colors = new String[6][5];
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                if(matrix[i][j] == null ){
                    colors[i][j] = "";
                }else{
                    switch(matrix[i][j].getColor()){
                        case Green -> colors[i][j] = BG_ANSI_GREEN;
                        case Yellow -> colors[i][j] = BG_ANSI_YELLOW;
                        case Blue -> colors[i][j] = BG_ANSI_BLUE;
                        case Pink -> colors[i][j] = BG_ANSI_PURPLE;
                        case White -> colors[i][j] = BG_ANSI_WHITE;
                        case LightBlue -> colors[i][j] = BG_ANSI_CYAN;
                    }
                }
            }
        }
        for(int i=0; i<6; i++){
            out.append(bookshelfStringSeparator);
            out.append(bookshelfSpacer + "░");
            for(int j=0; j<5; j++){
                out.append(bookshelfStringLeftEdge);
                out.append(" ").append(colors[i][j]).append("    ").append(ANSI_RESET).append(colors[i][j].equals("") ? "░" : " ");
            }
            out.append(bookshelfStringLeftEdge + "  ").append(i).append("\n");
            out.append(bookshelfSpacer + "░");
            for(int j=0; j<5; j++){
                out.append(bookshelfStringLeftEdge);
                out.append(" ").append(colors[i][j]).append("    ").append(ANSI_RESET).append(colors[i][j].equals("") ? "░" : " ");
            }
            out.append(bookshelfStringLeftEdge + "     \n");
        }
        out.append(bookshelfStringFooter);
        out(out.toString());
    }


    /**
     * Display or update the Personal Goal Card.<br />
     *
     * @param personalGoalCard the personal Goal Card to be shown.
     */
    private void showPersonalGoalCard(PersonalGoalCard personalGoalCard) {

        outln("This is your Personal Goal Card:\n");
        Tile[][] matrix = personalGoalCard.getPattern().getMatrix();
        StringBuilder out= new StringBuilder(personalCardStringHeader);
        String[][] colors = new String[6][5];
        for(int i=0; i<6; i++){
            for(int j=0; j<5; j++){
                if(matrix[i][j] == null ){
                    colors[i][j] = "";
                }else{
                    switch(matrix[i][j].getColor()){
                        case Green -> colors[i][j] = BG_ANSI_GREEN;
                        case Yellow -> colors[i][j] = BG_ANSI_YELLOW;
                        case Blue -> colors[i][j] = BG_ANSI_BLUE;
                        case Pink -> colors[i][j] = BG_ANSI_PURPLE;
                        case White -> colors[i][j] = BG_ANSI_WHITE;
                        case LightBlue -> colors[i][j] = BG_ANSI_CYAN;
                    }
                }
            }
        }
        for(int i=0; i<6; i++){

            out.append(personalCardStringSpacer);
            for(int j=0; j<5; j++){

                out.append(j != 0 ? "▓▓" : "██").append(colors[i][j]).append(colors[i][j].equals("") ? "░░░" : "   ").append(ANSI_RESET);
            }
            out.append(personalCardStringLeftEdge + " ").append(i).append("\n");
            out.append(personalCardStringSeparator);
        }
        out(out.toString());
    }


    /**
     * Display or update the EndGame Token.<br />
     *
     * @param endGameToken the EndGame Token to be shown.
     */
    private void showEndgameToken(EndGameToken endGameToken) {
        out("᧔𐓪᧓");
    }


    /**
     * Ask the user for a message to be sent.<br />
     *
     * @return array containing:<br />
     * 1) Author of the message.<br />
     * 2) Recipient of the message.<br />
     * 3) Body of the message.<br />
     * 4) Metadata.<br />
     */
    @Override
    public String[] writeMessage() {

        String[] ret = new String[2];
        ret[0] ="";
        ret[1] ="";

        while(true) {
            ret[0]=inputString(ANSI_BOLD+"Please type the name of the recipient (press enter for everybody):"+ANSI_RESET);
            if(ret[0].length() > 20){
                outln("\nMmmmh, i'm coding this late night, i'll just set this to everybody ù.ù");
                ret[0] = "everybody";
            }
            if(ret[0].equals("")){ret[0] = "everybody"; }

            ret[1]=inputString(ANSI_BOLD+"Please type the message (max 150 chars pls):"+ANSI_RESET);
            if(ret[1].length() > 150){
                outln("\nEhi! Am i a joke to you!? I'll just send something ಠ_ಠ");
                ret[1] = "(ﾉ◕ヮ◕)ﾉ*:・ﾟ✧";
            }
            if(ret[1].equals("")){
                outln("Come on don't be shy! Say something!");
                ret[1] = shyguy; }

            boolean keepSpinning2 = true;
            while(keepSpinning2){
                outln("\nThe message is:");
                outln(ANSI_CYAN+ANSI_BOLD+"To "+ret[0]+ANSI_RESET+": "+ret[1]);
                outln("Message Length:"+ret[1].length());
                String confirm=inputString("Do you want to send it? [y/n/exit]");

                switch (confirm){
                    case "y" -> {return ret;}
                    case "n" -> keepSpinning2=false;
                    case "exit" -> {return null;}
                }
            }
        }
    }


    /**Print Chat messages to the user.<br />
     *
     * @param messages arrays of ChatMessage objects
     */
    @Override
    public void readMessage(ChatMessage[] messages) {
        for (ChatMessage message : messages) {
            outln(ANSI_CYAN + ANSI_BOLD + message.getSender() + ANSI_RESET + ": " + message.getBody());
        }
    }


    /**
     * Display the appropriate user interface for the end game
     *
     * @param winner string containing name of the winner
     * @param numOfPoints string containing total points of the winner
     */
    @Override
    public void closeGame(String winner, int numOfPoints) {

        infoMessage("The winner is "+winner+"!!\n With "+numOfPoints+" total points!!!");
    }

    /**
     * GUI exclusive method.
     * @param model modelView of the game.
     */
    @Override
    public void updateModel(GameView model ,String nickname) {

        this.showBoard(model.getBoardBoxes());
        this.infoMessage("\n" + model.getTurnPlayerName() + "'s bookshelf \n");
        this.showBookshelf(model.getTurnPlayerBookshelf().getMatrix());
        System.out.println();
        this.showCommonGoalCards(model.getCommonGoalCards());
        if (nickname.equals(model.getTurnPlayerName())) {
            this.showPersonalGoalCard(model.getTurnPlayerPersonalGoalCard());
        }
    }


    /**
     * Gracefully exit from the user interface.<br />
     *
     * @param status exit status.
     */
    @Override
    public void exitInterface(int status) {
        //TODO disconnect from server
        outln("Thanks for playing our game. See you soon!");
        try {
            bufferedReader.close();
            printStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.exit(0);
    }

}
