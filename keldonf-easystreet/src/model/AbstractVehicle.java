/*
 * TCSS 305 - Fall 2016
 * Assignment 3 - EasyStreet
 *
 */
package model;


/**
 * AbstactVehicle
 * This is an abstract class called AbstractVehicle
 * which implements the interface call Vehicle. 
 * 
 * @author Keldon Fischer
 * @version 10/26/2016
 */
public abstract class AbstractVehicle implements Vehicle {

    /**
     * This saves the initial x.
     */
    private int myInitialX;
    
    /**
     * This saves the initial y.
     */
    private int myInitialY;
    
    /**
     * This saves the initial direction.
     */
    private Direction myInitialDir;
    
    /**
     * This saves the x.
     */
    private int myX;
    
    /**
     * This saves the y.
     */
    private int myY;
    
    /**
     * This saves the direction.
     */
    private Direction myDir;
    
    /**
     * This saves the death time.
     */
    private int myDeathTime;
    
    /**
     * This saves the death time left.
     */
    private int myDeathTimeLeft;
    
    /**
     * Abstract Vehicle
     * 
     * This is the default constructor for AbstractVehicle.
     * 
     * @param theX parameter for x.
     * @param theY parameter for y.
     * @param theDir parameter for direction.
     * @param theDeathTime parameter for death time.
     */
    AbstractVehicle(final int theX, 
                    final int theY, 
                    final Direction theDir, 
                    final int theDeathTime) {
        
        myInitialX = theX;
        myInitialY = theY;
        myInitialDir = theDir;
        myX = theX;
        myY = theY;
        myDir = theDir;
        myDeathTime = theDeathTime;
    }

    /**
     * Called when this Vehicle collides with the specified other Vehicle.
     * 
     * @param theOther The other object.
     */
    @Override
    public void collide(final Vehicle theOther) {
        if (this.isAlive() && theOther.isAlive()) {
            if (this.getDeathTime() > theOther.getDeathTime()) {
                this.myDeathTimeLeft = this.getDeathTime();
                this.setDirection(Direction.random());
            }
        }
    }

    /**
     * Returns the number of updates between this vehicle's death and when it
     * should be revived.
     * 
     * @return the number of updates.
     */
    @Override
    public int getDeathTime() {
        return myDeathTime;
    }

    /**
     * Returns the file name of the image for this Vehicle object, such as
     * "car.gif".
     * 
     * @return the file name.
     */
    @Override
    public String getImageFileName() {
        String result = "";
        final String name = this.getClass().getSimpleName().toLowerCase();
        if (isAlive()) {
            result = name + ".gif";
        } else {
            result = name + "_dead.gif";
        }
        return result;
    }
    
    /**
     * Returns this Vehicle object's direction.
     * 
     * @return the direction.
     */
    @Override
    public Direction getDirection() {
        return myDir;
    }

    /**
     * Returns this Vehicle object's x-coordinate.
     * 
     * @return the x-coordinate.
     */
    @Override
    public int getX() {
        return myX;
    }

    /**
     * Returns this Vehicle object's y-coordinate.
     * 
     * @return the y-coordinate.
     */
    @Override
    public int getY() {
        return myY;
    }

    /**
     * Returns whether this Vehicle object is alive and should move on the map.
     * 
     * @return true if the object is alive, false otherwise.
     */
    @Override
    public boolean isAlive() {
        boolean result = false;
        if (myDeathTimeLeft == 0) {
            result = true;
        }
        
        return result;
    }

    /**
     * Called by the UI to notify a dead vehicle that 1 movement round has
     * passed, so that it will become 1 move closer to revival.
     */
    @Override
    public void poke() {
        if (myDeathTimeLeft != 0) {
            myDeathTimeLeft--;
        }
    }

    /**
     * Moves this vehicle back to its original position.
     */
    @Override
    public void reset() {
        myX = myInitialX;
        myY = myInitialY;
        myDir = myInitialDir;
        myDeathTimeLeft = 0;
        

    }

    /**
     * Sets this object's facing direction to the given value.
     * 
     * @param theDir The new direction.
     */
    @Override
    public void setDirection(final Direction theDir) {
        myDir = theDir;

    }

    /**
     * Sets this object's x-coordinate to the given value.
     * 
     * @param theX The new x-coordinate.
     */
    @Override
    public void setX(final int theX) {
        myX = theX;
        

    }

    /**
     * Sets this object's y-coordinate to the given value.
     * 
     * @param theY The new y-coordinate.
     */
    @Override
    public void setY(final int theY) {
        myY = theY;

    }
    
    /**
     * This toString is used in the debugger to tell the
     * death time left for each vehicle.
     * 
     * @return a debug string
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        
        sb.append("DTL = ");
        sb.append(this.myDeathTimeLeft);
        
        return sb.toString();
        
    }

}
