package it.polimi.ingsw.exception;

/**Exception to throw if the user has chosen to pass.<br />
 * Help to maintain robustness in communication protocol.<br />
 */
public class PassedMoveException extends Exception{
    public PassedMoveException() {super("The user has chosen to pass.");}
}