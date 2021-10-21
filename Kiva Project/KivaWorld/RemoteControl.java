/**
 * This is the class that controls Kiva's actions. Implement the <code>run()</code>
 * method to deliver the pod and avoid the obstacles.
 *
 * @author Rene B. Dena
 * @version 8/10/2021
 */

import edu.duke.FileResource;
import java.util.Arrays;

/**
 * This is the class that controls Kiva's actions.
 */
public class RemoteControl
{
    KeyboardResource keyboardResource;

    /**
     * This method is the letter representation of the moves Kiva robot can take.
     */
    public RemoteControl()
    {
        keyboardResource = new KeyboardResource();
    }

    /**
     * The controller that directs Kiva's activity.
     * Prompts the user for the floor map
     * to load, displays the map, and asks the user for
     * the commands for Kiva to execute.
     * Calls the <code>convertToKivaCommands(String userCommands)</code>
     * method to convert the user input into an array of KivaCommands and
     * executes them.
     */
    public void run()
    {
        System.out.println("Please select a map file.");
        FileResource fileResource = new FileResource();
        String inputMap = fileResource.asString();
        FloorMap floorMap = new FloorMap(inputMap);
        System.out.println(floorMap);
        Kiva kiva= new Kiva(floorMap);
        System.out.println("Please enter the directions for the Kiva Robot to take.");
        String directions = keyboardResource.getLine();
        System.out.println("Directions that you typed in: " + directions);
        KivaCommand[] commands = convertToKivaCommands(directions);
        
        for(int i =0; i<directions.length();i++)
        {
            System.out.println(commands[i]);
            kiva.move(commands[i]);
        }
        
        if(kiva.successfullyDropped && commands[directions.length()-1]==KivaCommand.DROP)
        {
            System.out.println("Successfully picked up the pod and dropped it off.Thank you!");
        }
        else
        {
            System.out.println("I'm sorry. The Kiva Robot did not pick up the pod then drop it off in the right place.");
        }
    }

    /**
     * Converts the string of characters (representing the directionKeys)
     * into an array of KivaCommands.
     * @param userCommands The string the user input.
     * This string represents the list of directionKeys that
     * will be used to find the correlated KivaCommands and added to the
     * KivaCommands array.
     * @return An array of KivaCommands that will be iterated over in the
     * <code>run()</code> method.
       */
    public KivaCommand[] convertToKivaCommands(String commands)
    {
        KivaCommand[] arr = new KivaCommand[commands.length()];
        for(int i = 0; i< commands.length();i++)
        {
            switch(commands.charAt(i))
            {
            case 'F':
                arr[i]= KivaCommand.FORWARD;
                break;
            case 'R':
                arr[i]= KivaCommand.TURN_RIGHT;
                break;
            case 'L':
                arr[i]= KivaCommand.TURN_LEFT;
                break;
            case 'D':
                arr[i]= KivaCommand.DROP;
                break;
            case 'T':
                arr[i]= KivaCommand.TAKE;
                break;
            default: throw new IllegalArgumentException("This character does not correspond to a command please use: F,L,R,D,T");
            }
        }
        return arr;
    }
}
