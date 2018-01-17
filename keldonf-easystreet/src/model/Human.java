/*
 * TCSS 305 - Fall 2016
 * Assignment 3 - EasyStreet
 *
 */
package model;

import java.util.ArrayList;
import java.util.Map;

/**
 * Human
 * 
 * This is the class for Human which extends AbstractVehicle.
 * 
 * @author Keldon Fischer
 * @version 10/26/2016
 */
public class Human extends AbstractVehicle {

    /**
     * Death Time
     * death time for humans is 50.
     */
    private static final int DEATH_TIME = 50;

    /**
     * Human
     * This is the default constructor for Human.
     * @param theX parameter for x.
     * @param theY parameter for y.
     * @param theDir parameter for direction.
     */
    public Human(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir, DEATH_TIME);
    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;
        if (theTerrain == Terrain.GRASS
            // This makes it so the car stops at cross walk lights when they are
            // yellow or red.
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

        if (theNeighbors.get(result) == Terrain.CROSSWALK
            || theNeighbors.get(result.left()) == Terrain.CROSSWALK
            || theNeighbors.get(result.right()) == Terrain.CROSSWALK) {

            for (int i = 0; i < possibleDirections.size(); i++) {
                if (theNeighbors.get(possibleDirections.get(i)) == Terrain.CROSSWALK) {
                    result = possibleDirections.get(i);
                }
            }

        } else {
            if (theNeighbors.get(result) != Terrain.CROSSWALK) {

                result = possibleDirections.get((int) (Math.random() 
                                * possibleDirections.size()));

            }
            int count = 0;
            while (theNeighbors.get(result) != Terrain.CROSSWALK
                   && theNeighbors.get(result) != Terrain.GRASS) {

                result = possibleDirections.get((int) (Math.random() 
                                * possibleDirections.size()));
                count++;
                final int bigNumber = 10000;
                if (count == bigNumber) {
                    result = reverse;
                    count = 0;
                    break;
                }

            }
        }

        return result;
    }

}
