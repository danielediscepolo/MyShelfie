package it.polimi.ingsw.exception;

/**Exception to throw if the selected Column cannot contain the selected Tiles.<br />
 * Help to maintain robustness in communication protocol.<br />
 */
public class NotEnoughFreeBoxesInColumnException extends Exception{
    public NotEnoughFreeBoxesInColumnException(int column) {super("The choosen column number is"+column+".\nThe provided number for the Column must be between 1 and 5");}
}
