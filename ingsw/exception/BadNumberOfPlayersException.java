package it.polimi.ingsw.exception;

/**Exception to throw if the selected number of Players for a game is not in range.<br />
 * Help to maintain robustness in communication protocol.<br />
 */
public class BadNumberOfPlayersException extends Exception{
        public BadNumberOfPlayersException(int number) { super("The choosen column number is"+number+".\nThe provided number of Players must be between 2 and 4");}
}
