/*
 * TCSS 305 - Fall 2016
 * Assignment 3 - EasyStreet
 *
 */
package model;

import java.util.Map;

/**
 * Truck
 * This is the class for Truck which extends AbstractVehicle.
 * 
 * @author Keldon Fischer
 * @version 10/26/2016
 */
public class Truck extends AbstractVehicle {
    
    /**
     * Death time for truck is 0.
     */
    private static final int DEATH_TIME = 0;

    /**
     * Truck
     * This is the default constructor for Truck.
     * 
     * @param theX parameter for x.
     * @param theY parameter for y.
     * @param theDir parameter for direction.
     */
    public Truck(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);

        
    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        //The truck is driving through red cross walks.
        boolean result = false;
        if (theTerrain == Terrain.STREET 
        //This makes it so the Truck stops at cross walk lights when they are red.
                        || (theTerrain == Terrain.CROSSWALK && theLight != Light.RED)
        //This makes it so the Truck not stops at traffic lights.
                        || theTerrain == Terrain.LIGHT) {
            result = true;
        }
        return result;
    }

    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction result = this.getDirection();
        result = Direction.random();
        
        final Direction straight = this.getDirection();
        final Direction left = this.getDirection().left();
        final Direction right = this.getDirection().right();
        final Direction reverse = this.getDirection().reverse();
        
        //My while loop turns into an infinite loop if there are no possible out comes.      
        while (result == this.getDirection().reverse() 
                        || theNeighbors.get(result) == Terrain.WALL
                        || theNeighbors.get(result) == Terrain.TRAIL
                        || theNeighbors.get(result) == Terrain.GRASS) {
            
            //This if statement prevents an infinite loop when you get stuck in a hole~.
            if (isOk(theNeighbors.get(straight)) 
                            && isOk(theNeighbors.get(left)) 
                            && isOk(theNeighbors.get(right))) {
                result = reverse; 
                break; //Stops the while loop.
            }
            
            result = Direction.random();
            
        }
        
        return result;
    }
    
    /**
     * isOk
     * 
     * This is a helper method for chooseDirection() that does a shortcut
     * for seeing if wall, trail, or grass is equal to the neighbors.
     * 
     * @param theTerrain parameter for the terrain.
     * @return returns true if either the wall, trail, or grass is 
     * equal to the neighbors.
     */
    private boolean isOk(final Terrain theTerrain) {
        return Terrain.WALL == theTerrain || Terrain.TRAIL == theTerrain
               || Terrain.GRASS == theTerrain;
    }
    
}

