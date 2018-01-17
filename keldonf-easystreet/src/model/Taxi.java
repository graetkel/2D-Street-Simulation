/*
 * TCSS 305 - Fall 2016
 * Assignment 3 - EasyStreet
 *
 */
package model;

/**
 * Taxi
 * This is the taxi class which extends car.
 * 
 * @author Keldon Fischer
 * @version 10/25/2016
 */
public class Taxi extends Car {
    /**
     * myCounter
     * this myCounter counts the rounds.
     */
    private int myCounter;

    /**
     * Taxi
     * 
     * This is the default constructor for taxi.
     * @param theX parameter for x.
     * @param theY parameter for y.
     * @param theDir parameter for the direction.
     */
    public Taxi(final int theX, final int theY, final Direction theDir) {
        super(theX, theY, theDir);
        myCounter = 0;
    }

    @Override
    public boolean canPass(final Terrain theTerrain, final Light theLight) {
        boolean result = false;
        final int timeLeft = 3;
        if (theTerrain == Terrain.STREET 
        //This makes it so the car stops at cross walk lights when they are yellow or red.
                        || (theTerrain == Terrain.CROSSWALK && theLight == Light.GREEN)
                        //This makes it so the car stops at traffic lights when they are red.
                        || (theTerrain == Terrain.LIGHT && theLight != Light.RED)) {
            result = true;
        }
        //If the taxi starts to wait at light then the myCounter starts.
        //If the myCounter reaches 3 then the taxi drives through.
        if (theTerrain == Terrain.CROSSWALK && theLight == Light.RED) {
            myCounter++;
            if (myCounter == timeLeft) {
                result = true;
                myCounter = 0;
            }
        }
        return result;
    }
}
