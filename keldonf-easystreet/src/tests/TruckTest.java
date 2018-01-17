/*
 * TCSS 305 - Fall 2016
 * Assignment 3 - EasyStreet
 *
 */
package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Direction;
import model.Light;
import model.Terrain;
import model.Truck;

import org.junit.Test;



/**
 * TruckTest
 * This JUnit is used for testing all the methods in the Truck class.
 * 
 * @author Keldon Fischer
 * @version 10/26/10
 *
 */
public class TruckTest {

    /**
     * The number of times to repeat a test to have a high probability that all
     * random possibilities have been explored.
     */
    private static final int TRIES_FOR_RANDOMNESS = 50;
    

    /**
     * Test method for {@link model.Truck#Truck(int, int, model.Direction)}.
     */
    @Test
    public void testTruck() {
        final Truck t = new Truck(10, 11, Direction.NORTH);
        
        assertEquals("Truck x coordinate not initialized correctly!", 10, t.getX());
        assertEquals("Truck y coordinate not initialized correctly!", 11, t.getY());
        assertEquals("Truck direction not initialized correctly!",
                     Direction.NORTH, t.getDirection());
        assertEquals("Truck death time not initialized correctly!", 0, t.getDeathTime());
        assertTrue("Truck isAlive() fails initially!", t.isAlive());
    }

    /**
     * Test whether I can set all of the values of a new Truck object to whatever I want.
     */
    @Test
    public void testTruckSetters() {
        final Truck t = new Truck(10, 11, Direction.NORTH);
        
        t.setX(12);
        assertEquals("Truck setX failed!", 12, t.getX());
        t.setY(13);
        assertEquals("Truck setY failed!", 13, t.getY());
        t.setDirection(Direction.SOUTH);
        assertEquals("Truck setDirection failed!", Direction.SOUTH, t.getDirection());
    }
    
    
    /**
     * Test method for {@link model.Truck#canPass(model.Terrain, model.Light)}.
     */
    @Test
    public void testCanPass() {
        
        // Truck can move to Street, Light or CROSSWALKS
        // so we need to test both of those conditions
        
        // Truck should NOT choose to move to other terrain types
        // so we need to test that Humans never move to other terrain types
        
        // Truck should only reverse direction if no other option is available
        // so we need to be sure to test that requirement also
        
        final List<Terrain> validTerrain = new ArrayList<>();
        validTerrain.add(Terrain.LIGHT);
        validTerrain.add(Terrain.STREET);
        validTerrain.add(Terrain.CROSSWALK);
                
        final Truck truck = new Truck(0, 0, Direction.NORTH);
        
        
        // test each terrain type as a destination
        for (final Terrain destinationTerrain : Terrain.values()) {
            // try the test under each light condition
            for (final Light currentLightCondition : Light.values()) {
                if (destinationTerrain == Terrain.STREET) {
                
                    // Truck can pass Street under any light condition
                    assertTrue("Truck should be able to pass Street"
                               + ", with light " + currentLightCondition,
                               truck.canPass(destinationTerrain, currentLightCondition));
                    
                
                } else if (destinationTerrain == Terrain.LIGHT) {
                    // Truck can pass Street under any light condition
                    assertTrue("Truck should be able to pass Street"
                               + ", with light " + currentLightCondition,
                               truck.canPass(destinationTerrain, currentLightCondition));
                    
                    
                    
                } else if (destinationTerrain == Terrain.CROSSWALK) {
                           // Truck can pass CROSSWALK
                           // if the light is YELLOW or GREEN but not RED

                    if (currentLightCondition == Light.GREEN) {
                        assertTrue("Truck should NOT be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            truck.canPass(destinationTerrain,
                                          currentLightCondition));
                    } else if (currentLightCondition == Light.YELLOW) {
                        assertTrue("Truck should NOT be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            truck.canPass(destinationTerrain,
                                          currentLightCondition));
                    } else { // light is yellow or red
                        assertFalse("Truck should be able to pass " + destinationTerrain
                            + ", with light " + currentLightCondition,
                            truck.canPass(destinationTerrain,
                                          currentLightCondition));
                    }
                } else if (!validTerrain.contains(destinationTerrain)) {
 
                    assertFalse("Truck should NOT be able to pass " + destinationTerrain
                        + ", with light " + currentLightCondition,
                        truck.canPass(destinationTerrain, currentLightCondition));
                    
                    
                }
            } 
        }
    }

    /**
     * Test if you choose direction is never reverse unless thats the only option left.
     */
    @Test
    public void testChooseDirectionSurroundedByStreets() {
        final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
        neighbors.put(Direction.WEST, Terrain.STREET);
        neighbors.put(Direction.NORTH, Terrain.STREET);
        neighbors.put(Direction.EAST, Terrain.STREET);
        neighbors.put(Direction.SOUTH, Terrain.STREET);
        
        boolean seenWest = false;
        boolean seenNorth = false;
        boolean seenEast = false;
        boolean seenSouth = false;
        
        final Truck truck = new Truck(0, 0, Direction.NORTH);
        
        for (int count = 0; count < TRIES_FOR_RANDOMNESS; count++) {
            final Direction d = truck.chooseDirection(neighbors);
            
            if (d == Direction.WEST) {
                seenWest = true;
            } else if (d == Direction.NORTH) {
                seenNorth = true;
            } else if (d == Direction.EAST) {
                seenEast = true;
            } else if (d == Direction.SOUTH) { // this should NOT be chosen
                seenSouth = true;
            }
        }
 
        assertTrue("Truck chooseDirection() fails to select randomly "
                   + "among all possible valid choices!",
                   seenWest && seenNorth && seenEast);
        
        assertFalse("Truck chooseDirection() fails to select randomly "
                        + "among all possible valid choices!",
                        !seenWest || !seenNorth || !seenEast);
        
        assertFalse("Truck chooseDirection() reversed direction when not necessary!",
                    seenSouth);
        
    }
    

    /**
     * Test if you choose reverse if thats the only option left.
     */
    @Test
    public void testChooseDirectionOnStreetCrosswalkLightMustReverse() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, Terrain.STREET);
                
                final Truck truck = new Truck(0, 0, Direction.NORTH);
                
                // the Truck must reverse and go SOUTH (Street)
                assertEquals("Truck chooseDirection() failed "
                                + "when reverse was the only valid choice!",
                             Direction.SOUTH, truck.chooseDirection(neighbors));
                
                neighbors.clear();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, Terrain.LIGHT);
                
                // the Truck must reverse and go SOUTH (Light)
                assertEquals("Truck chooseDirection() failed "
                                + "when reverse was the only valid choice!",
                             Direction.SOUTH, truck.chooseDirection(neighbors));
                
                neighbors.clear();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, Terrain.CROSSWALK);
                
                // the Truck must reverse and go SOUTH (Cross Walk)
                assertEquals("Truck chooseDirection() failed "
                                + "when reverse was the only valid choice!",
                             Direction.SOUTH, truck.chooseDirection(neighbors));
                
            }
                
        }
    }
    
    /**
     * Test if you choose left if thats the only option left.
     */
    @Test
    public void testChooseDirectionOnStreetCrosswalkLightMustTurnLeft() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, Terrain.STREET);
                neighbors.put(Direction.SOUTH, t);
                
                final Truck truck = new Truck(0, 0, Direction.NORTH);
                
                // the Truck must turn left and go EAST (Street)
                assertEquals("Truck chooseDirection() failed "
                                + "when left was the only valid choice!",
                             Direction.EAST, truck.chooseDirection(neighbors));
                
                neighbors.clear();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, Terrain.LIGHT);
                neighbors.put(Direction.SOUTH, t);
                
                // the Truck must turn left and go EAST (Light)
                assertEquals("Truck chooseDirection() failed "
                                + "when left was the only valid choice!",
                             Direction.EAST, truck.chooseDirection(neighbors));
                
                neighbors.clear();
                neighbors.put(Direction.WEST, t);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, Terrain.CROSSWALK);
                neighbors.put(Direction.SOUTH, t);
                
                // the Truck must turn left and go EAST (Cross Walk)
                assertEquals("Truck chooseDirection() failed "
                                + "when left was the only valid choice!",
                             Direction.EAST, truck.chooseDirection(neighbors));
                
            }
                
        }
    }
    

    /**
     * Test if you choose right if thats the only option left.
     */
    @Test
    public void testChooseDirectionOnStreetCrosswalkLightMustTurnRight() {
        
        for (final Terrain t : Terrain.values()) {
            if (t != Terrain.STREET && t != Terrain.CROSSWALK && t != Terrain.LIGHT) {
                
                final Map<Direction, Terrain> neighbors = new HashMap<Direction, Terrain>();
                neighbors.put(Direction.WEST, Terrain.STREET);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, t);
                
                final Truck truck = new Truck(0, 0, Direction.NORTH);
                
                // the Truck must turn right and go WEST (Street)
                assertEquals("Truck chooseDirection() failed "
                                + "when right was the only valid choice!",
                                Direction.WEST, truck.chooseDirection(neighbors));
                
                neighbors.clear();
                neighbors.put(Direction.WEST, Terrain.LIGHT);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, t);
                
                // the Truck must turn right and go WEST (Light)
                assertEquals("Truck chooseDirection() failed "
                                + "when right was the only valid choice!",
                                Direction.WEST, truck.chooseDirection(neighbors));
                
                neighbors.clear();
                neighbors.put(Direction.WEST, Terrain.CROSSWALK);
                neighbors.put(Direction.NORTH, t);
                neighbors.put(Direction.EAST, t);
                neighbors.put(Direction.SOUTH, t);
                
                // the Truck must turn right and go WEST (Cross Walk)
                assertEquals("Truck chooseDirection() failed "
                                + "when right was the only valid choice!",
                                Direction.WEST, truck.chooseDirection(neighbors));
                
            }
                
        }
    }
    
    
    
    
    
    
} //End of Class
