package it.polimi.ingsw.exception;

/**Exception to throw if the selected number of Tiles is not in range.<br />
 * Help to maintain robustness in communication protocol.<br />
 */
public class BadNumberOfTilesToSortException extends Exception {
    public BadNumberOfTilesToSortException(int number) { super("The choosen number of tiles is"+number+".\nThe provided number of Tiles is wrong");}
}
