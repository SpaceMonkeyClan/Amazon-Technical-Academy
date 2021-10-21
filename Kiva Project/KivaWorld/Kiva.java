/**
 * Kiva objects can perform actions such as moving around
 * and picking and dropping items.
 * A Kiva object's attributes include it's current location on a map,
 * current direction it's facing, the map layout that it's moving on, status of
 * whether it's carrying a pod, if it has successfully dropped a pod and a
 * motor life time that indicates how much of it's motor life has been spent.
 *
 * @author Rene B. Dena
 * @version 8/10/2021
 */

import edu.duke.Point;

public class Kiva
{
    Point currentLocation;
    FacingDirection directionFacing;
    FloorMap map;
    boolean carryingPod;
    boolean successfullyDropped;
    //72,000,000,000 milliseconds in 20,000 hours = robot motor life time
    double motorLifetime;

    /**
     * Creates a Kiva robot instance using only a FloorMap.
     * @param map FloorMap of the area. Includes the starting location of
     * the Kiva robot, location of the boundaries, obstacles, pod and drop-zone.
     */
    public Kiva(FloorMap map)
    {
        this.currentLocation = map.getInitialKivaLocation();
        directionFacing = FacingDirection.UP;
        carryingPod = false;
        successfullyDropped = false;
        this.map= map;
        motorLifetime=0;
    }

    /**
     * Constructor for Kiva object.
     * @param   newPoint  The initial starting Location of the Kiva robot.
     */
    public Kiva (Point newPoint)
    {
        this.currentLocation = newPoint;
        directionFacing = FacingDirection.UP;
        motorLifetime=0;
    }

    /**
     * Creates a Kiva robot instance using a FloorMap and a Point to set
     * the starting location of the Kiva robot.
     * @param map FloorMap of the area. Includes the locations of the Kiva robot,
     * boudaries, obstacles, pod and drop-zone.
     * @param currentLocation Starting location of the Kiva robot.
     */
    public Kiva(FloorMap map,Point newPoint)
    {
        this.currentLocation = newPoint;
        directionFacing = FacingDirection.UP;
        this.map= map;
        motorLifetime=0;
    }

    /**
    * Returns the current location of the Kiva object.
    * @return the current location as a
    * Point (x-y coordinate).
    */
    public Point getCurrentLocation()
    {
        return currentLocation;
    }

    /**
    * Returns true if Kiva is carrying the pod and false if not.
    */
    public boolean isCarryingPod()
    {
        return carryingPod;
    }

    /**
    * Returns true if Kiva successfully dropped the pod and false if not.
    */
    public boolean isSuccessfullyDropped()
    {
        return successfullyDropped;
    }

    /**
    * Returns the motorLifetime utilized by the Kiva, measured in milliseconds.
    */
    public double getMotorLifetime()
    {
        return motorLifetime;
    }

    /**
    * Adds 1000 milliseconds to the motorLifetime
    * (indicating that another 1000 milliseconds have been used).
    */
    public void incrementMotorLifetime()
    {
        motorLifetime += 1000;
    }


    /**
     * Depending on the KivaCommand it receives, move() will update the
     * current location, the direction the robot is pointing,
     * whether it is carrying a pod or if the pod has been successfully dropped.
     * @param command KivaCommand that indicates the specific movement the
     * Kiva robot should perform.
     * @see #moveForward()
     * @see #moveTurn_Left()
     * @see #moveTurn_Right()
     * @see #Take()
     * @see #Drop()
     */
    public void move(KivaCommand command)
    {
        //System.out.println(this.map.getObjectAtLocation(this.currentLocation));
        //System.out.println(this.currentLocation);
        switch(command)
        {
        
            case FORWARD:
               this.moveForward();
                break;
            case TURN_LEFT:
               this.moveTurn_Left();
               break;
            case TURN_RIGHT:
               this.moveTurn_Right();
               break;
            case TAKE:
               this.Take();
               break;
            case DROP:
               this.Drop();
               break;
            default:
               this.moveForward();
        }
    }

    //*** HELPER METHODS FOR move(KivaCommand command) method
    private void moveForward()
    {
        //WHEN FACING UP
        Point temp = this.currentLocation;
        int x = temp.getX();
        int y= temp.getY();
        //Obstacle
        int oldX= x;
        int oldY=y;
        
        if(directionFacing==FacingDirection.UP)
        {
            y--;
        }
        else if( directionFacing==FacingDirection.LEFT)
        {
            x--;
        }
        else if( directionFacing==FacingDirection.DOWN)
        {
            y++;
        }
        else if( directionFacing==FacingDirection.RIGHT)
        {
            x++;
        }
        
        this.currentLocation= new Point(x,y);
        
        if(x < 0 || x > this.map.getMaxColNum())
        {
            this.currentLocation=new Point (oldX, oldY);
            throw new IllegalMoveException("Out of bounds");
        }
        if(y < 0 || y > this.map.getMaxRowNum())
        {
            this.currentLocation=new Point (oldX, oldY);
            throw new IllegalMoveException("Out of bounds");
        }
        if(this.map.getObjectAtLocation(this.currentLocation)==FloorMapObject.OBSTACLE)
        {
            Point tempPoint = new Point(x,y);
            this.currentLocation=new Point (oldX, oldY);
            throw new IllegalMoveException(String.format("IllegalMoveException: Can't move onto an obstacle at " + tempPoint+"!"));
        }
        this.incrementMotorLifetime();
    }

    //WHEN FACING DOWN
    public FacingDirection getDirectionFacing()
    {
     return directionFacing;
    }

    //WHEN FACING LEFT
    private void moveTurn_Left()
    {
        this.incrementMotorLifetime();
        if(directionFacing==FacingDirection.UP)
        {
            directionFacing = FacingDirection.LEFT;
        }
        else if( directionFacing==FacingDirection.LEFT)
        {
            directionFacing=FacingDirection.DOWN;
        }
        else if( directionFacing==FacingDirection.DOWN)
        {
            directionFacing=FacingDirection.RIGHT;
        }
        else if( directionFacing==FacingDirection.RIGHT)
        {
            directionFacing=FacingDirection.UP;
        }
    }

    //WHEN FACING RIGHT
    private void moveTurn_Right()
    {
        this.incrementMotorLifetime();
        if(directionFacing==FacingDirection.UP)
        {
            directionFacing = FacingDirection.RIGHT;
        }
        else if( directionFacing==FacingDirection.LEFT)
        {
            directionFacing=FacingDirection.UP;
        }
        else if( directionFacing==FacingDirection.DOWN)
        {
            directionFacing=FacingDirection.LEFT;
        }
        else if( directionFacing==FacingDirection.RIGHT)
        {
            directionFacing=FacingDirection.DOWN;
        }
    }

    /**
    * Kiva object will take/pick up pod.
    * This will throw a NoPodException if it is already holding a pod
    * or, if there is no pod at the current location.
    */
    private void Take()
    {
        if(this.map.getObjectAtLocation(this.currentLocation)==FloorMapObject.POD)
        {
            if (!this.isCarryingPod())
            {
                carryingPod= true;
            } else
            {
               throw new NoPodException("currently carrying POD");
            }
        } else
        {
            throw new NoPodException(String.format("NoPodException: Can't take nonexistent pod from location " + this.currentLocation+"!"));
        }
    }

    /**
    * Kiva object will drop pod.
    * This will throw a IllegalMoveException if it is not holding a pod at the
    * time of the drop or, an IllegalDropZoneException if it is not
    * at a drop-zone location.
    */
    private void Drop()
    {
        if(this.map.getObjectAtLocation(this.currentLocation)==FloorMapObject.DROP_ZONE)
        {
            if (this.isCarryingPod())
            {
                carryingPod= false;
                this.successfullyDropped=true;
            } else
            {
                throw new IllegalDropZoneException("No POD carried");
                //System.out.println("I'm sorry.The Kiva Robot did not pick up the pod and then drop it off in the right place");
            }
        } else
        {
            throw new IllegalDropZoneException(String.format("IllegalDropZoneException: Can't just drop pods willy-nilly at " + this.currentLocation +"!"));
        }
    }
}
