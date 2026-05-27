package it.polimi.ingsw.exception;

/**Exception to throw in the case the Unsorted List and the sorted one have different tiles.<br />
 * Help to maintain robustness in communication protocol.<br />
 */
public class BadSortedTilesListException extends Exception{
    public BadSortedTilesListException() {super("The ordered list of tiles is not congruent with the unordered list");}
}
