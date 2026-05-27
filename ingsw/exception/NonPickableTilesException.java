package it.polimi.ingsw.exception;

/**Exception to throw if the chosen tiles are not pickable.<br />
 * Help to maintain robustness in communication protocol.<br />
 */
public class NonPickableTilesException extends Exception{
    public NonPickableTilesException() {super("The choosen tiles are in a non-pickable status");}
}
