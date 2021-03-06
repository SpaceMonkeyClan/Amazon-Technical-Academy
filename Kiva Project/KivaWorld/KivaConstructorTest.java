/**
 * Test 1-argument and 2-argument constructors of the Kiva class
 * and ensure output/functionality is as expected.
 *
 * @author Rene B. Dena
 * @version 8/10/2021
 */

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import edu.duke.Point;

public class KivaConstructorTest
{
    String defaultLayout = ""
                            + "-------------\n"
                            + "        P   *\n"
                            + "   **       *\n"
                            + "   **       *\n"
                            + "  K       D *\n"
                            + " * * * * * **\n"
                            + "-------------\n";

    FloorMap defaultMap = new FloorMap(defaultLayout);
    @Test
    public void testSingleArgumentConstructor()
    {
        // GIVEN
        // The default map we defined earlier

        // WHEN
        // We create a Kiva with the single-argument constructor
        Kiva kiva = new Kiva(defaultMap);

        // THEN
        // The initial Kiva location is (2, 4)
        Point initialLocation = kiva.getCurrentLocation();
        Point expectedLocation = new Point(2, 4);
        if (sameLocation(initialLocation, expectedLocation))
        {
            System.out.println("testSingleArgumentConstructor SUCCESS");
        } else
        {
            System.out.println(String.format("testSingleArgumentConstructor FAIL: %s != (2,4)!", initialLocation));
        }
    }

    private boolean sameLocation(Point a, Point b)
    {
        return a.getX() == b.getX() && a.getY() == b.getY();
    }

    // For you: create a test for the constructor taking two arguments.
    @Test
    public void testTwoArgumentConstructor()
    {
        // GIVEN
        // The default map and point we defined earlier

        // WHEN
        // We create a Kiva with the two-argument constructor
        Kiva kiva = new Kiva(defaultMap, new Point (5,6));
        // THEN
        // The initial Kiva location is (2, 4)
        Point initialLocation = kiva.getCurrentLocation();
        Point expectedLocation = new Point(5, 6);
        if (sameLocation(initialLocation, expectedLocation))
        {
            System.out.println("testDoubleArgumentConstructor SUCCESS");
        } else
        {
            System.out.println(String.format( "testDoubleArgumentConstructor FAIL: %s != (5,6)!", initialLocation));
        }
    }
}
