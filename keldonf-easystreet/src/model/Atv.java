/*
 * TCSS 305 - Fall 2016
 * Assignment 3 - EasyStreet
 *
 */
package model;

import java.util.ArrayList;
import java.util.Map;

/**
 * ATV
 * This is the ATV class which extends AbstractVehicle.
 * 
 * @author Keldon Fischer
 * @version 10/26/2016
 */
public class Atv extends AbstractVehicle {

    /**
     * Death Time for ATV.
     */
    private static final int DEATH_TIME = 20;

    /**
     * ATV
     * This is the default constructor for ATV.
     * 
     * @param theX parameter for x.
     * @param theY parameter for y.
     * @param theDir parameter for direction.
     */
    public Atv(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);

    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;
        if (theTerrain != Terrain.WALL) {
            result = true;
        }
        return result;
    }
    
    @Override
    public Direction chooseDirection(final Map<Direction, Terrain> theNeighbors) {
        Direction result = getDirection();
        
        final Direction straight = this.getDirection();
        final Direction left = this.getDirection().left();
        final Direction right = this.getDirection().right();
        
        boolean isStraightOk = false;
        boolean isLeftOk = false;
        boolean isRightOk = false;
        
        final ArrayList<Direction> dirPossible = new ArrayList<Direction>();
        dirPossible.add(straight);
        dirPossible.add(left);
        dirPossible.add(right);
        
        
        if (theNeighbors.get(result) != Terrain.WALL) {
            isStraightOk = true;
        }
        if (theNeighbors.get(result.left()) != Terrain.WALL) {
            isLeftOk = true;
        }
        if (theNeighbors.get(result.right()) != Terrain.WALL) {
            isRightOk = true;
        }
        
        if (!isStraightOk && !isLeftOk && !isRightOk) {
            result = this.getDirection().reverse();
        } else {
            if (!isStraightOk) {
                dirPossible.remove(straight);
            }
            if (!isLeftOk) {
                dirPossible.remove(left);
            }
            if (!isRightOk) {
                dirPossible.remove(right);
            }
            result = dirPossible.get((int) (Math.random() * dirPossible.size()));
        }
            
        return result;
    }

}
