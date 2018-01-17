/*
 * TCSS 305 - Fall 2016
 * Assignment 3 - EasyStreet
 *
 */
package model;

import java.util.Map;

/**
 * Car
 * This is the class for Car which extends AbstractVehicle.
 * 
 * @author Keldon Fischer
 * @version 10/26/2016
 */
public class Car extends AbstractVehicle {

    /**
     * Death Time for car.
     */
    private static final int DEATH_TIME = 10;
    
    /**
     * Car
     * Default constructor for the car.
     * @param theX parameter for x.
     * @param theY parameter for y.
     * @param theDir parameter for direction.
     */
    public Car(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);

    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;
        if (theTerrain == Terrain.STREET 
        //This makes it so the car stops at cross walk lights when they are yellow or red.
                        || (theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN)
        //This makes it so the car stops at traffic lights when they are red.
                        || (theTerrain == Terrain.LIGHT && theLight != Light.RED)) {
            result = true;
        }
        return result;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction result = this.getDirection();
        
        if (theNeighbors.get(result) == Terrain.GRASS
                        || theNeighbors.get(result) == Terrain.TRAIL
                        || theNeighbors.get(result) == Terrain.WALL) {
            //If can't go straight, it tries to go left.
            if (theNeighbors.get(result.left()) == Terrain.STREET
                            || theNeighbors.get(result.left()) == Terrain.CROSSWALK
                            || theNeighbors.get(result.left()) == Terrain.LIGHT) {
                result = result.left();
            //If can't turn left, it tries to go right.
            } else if (theNeighbors.get(result.right()) == Terrain.STREET
                            || theNeighbors.get(result.right()) == Terrain.CROSSWALK
                            || theNeighbors.get(result.right()) == Terrain.LIGHT) {
                result = result.right();
            //If it can't go straight, left, or right; it goes reverse.
            } else {
                result = result.reverse();
            }
        }
        
        return result;
    }
    
    
    
    

    
    
    
}
