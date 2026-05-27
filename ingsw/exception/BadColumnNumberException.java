package it.polimi.ingsw.exception;

/**Exception to throw if the selected Column number is not in range.<br />
 * Help to maintain robustness in communication protocol.<br />
 */
public class BadColumnNumberException extends Exception{
    public BadColumnNumberException(int column) {super("The choosen column number is"+column+".\nThe provided number for the Column must be between 1 and 5");}
}
