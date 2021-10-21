/**
* The KivaCommand enum contains the list of commands that a Kiva object
* can perform.
* Each command is associated with a char-valued direction key.
*
* @author Rene B. Dena
* @version 8/10/2021
*/

public enum KivaCommand
{
    FORWARD('F'),
    TURN_LEFT('L'),
    TURN_RIGHT('R'),
    DROP('D'),
    TAKE('T');

    private char directionKey;

    private KivaCommand (char directionKey)
    {
        this.directionKey= directionKey;
    }
    
    /**
    * Returns the unique char associated with its KivaCommand.
    * @return The direction key (char) of the given KivaCommand.
    * Direction Keys: 'F','L','R','T','D'.
    */
    public char getDirectionKey()
    {
        return directionKey;
    }
}
