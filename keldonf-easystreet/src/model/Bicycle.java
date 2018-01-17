/*
 * TCSS 305 - Fall 2016
 * Assignment 3 - EasyStreet
 *
 */
package model;

import java.util.ArrayList;
import java.util.Map;

/**
 * Bicycle
 * This is the class for Bicycle which extends AbstractVehicle.
 * @author Keldon Fischer
 * @version 10/26/2016
 */
public class Bicycle extends AbstractVehicle {

    /**
     * Death Time for a bicycle.
     */
    private static final int DEATH_TIME = 30;
    
    /**
     * Bicycle
     * This is the default constructor for Bicycle.
     * 
     * @param theX parameter for x.
     * @param theY parameter for y.
     * @param theDir parameter for direction.
     */
    public Bicycle(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);           
    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;
        if (theTerrain == Terrain.TRAIL
                        || theTerrain == Terrain.STREET
                        || (theTerrain == Terrain.LIGHT && theLight != Light.GREEN)
                        || (theTerrain == Terrain.CROSSWALK && theLight != Light.GREEN)) {
            result = true;
        }
        return result;
    }
    
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction result = this.getDirection();
        final Direction straight = this.getDirection();
        final Direction left = this.getDirection().left();
        final Direction right = this.getDirection().right();
        final Direction reverse = this.getDirection().reverse();

        final ArrayList<Direction> possibleDirections = new ArrayList<Direction>();
        possibleDirections.add(straight);
        possibleDirections.add(left);
        possibleDirections.add(right);

        if (Terrain.TRAIL == theNeighbors.get(straight)) {
            result = straight;
        } else if (Terrain.TRAIL == theNeighbors.get(left)) {
            result = left;
        } else if (Terrain.TRAIL == theNeighbors.get(right)) {
            result = right;
        } else if (isOk(theNeighbors.get(straight))) {
            result = straight;
        } else if (isOk(theNeighbors.get(left))) {
            result = left;
        } else if (isOk(theNeighbors.get(right))) {
            result = right;
        } else {
            result = reverse;
        }

        return result;
    }

    /**
     * isOk
     * 
     * This is a helper method for chooseDirection() that does a shortcut
     * for seeing if light, street, or cross walk is equal to the neighbors.
     * 
     * @param theTerrain parameter for the terrain.
     * @return returns true if either the light, street, or cross walk is 
     * equal to the neighbors.
     */
    private boolean isOk(final Terrain theTerrain) {
        return Terrain.LIGHT == theTerrain || Terrain.STREET == theTerrain
               || Terrain.CROSSWALK == theTerrain;
    }


}
