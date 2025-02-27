package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class RunwayTest {

    private Runway runway;
    private Flight flight1;
    private Flight flight2;

    @BeforeEach
    void setUp() {
        runway = new Runway(1);
        Destination destination = new Destination("Los Angeles", 7000);
        Airplane airplane = new Airplane(1, 1, 8000, destination, 150);

        flight1 = new Flight(101, airplane, null, destination);
        flight2 = new Flight(102, airplane, null, destination);
    }

    @Test
    void testRunwayInitialization() {
        assertEquals(1, runway.getRunwayID(), "Runway ID should be initialized correctly");
        assertTrue(runway.getFlightsWaiting().isEmpty(), "Runway should start with no flights waiting");
        assertTrue(runway.isClear(), "Runway should start as clear");
        assertEquals(0, runway.getFlightOnRunway(), "No flight should be on the runway initially");
    }

    @Test
    void testClearRunway() {
        runway.clearRunway();
        assertTrue(runway.isClear(), "Runway should be cleared");
        assertEquals(0, runway.getFlightOnRunway(), "No flight should remain on the runway after clearing");
    }

    @Test
    void testAddToRunway() {
        assertTrue(runway.isClear(), "Runway should initially be clear");

        // Add first flight (should occupy runway)
        runway.addToRunway(flight1);
        assertEquals(flight1.getFlightID(), runway.getFlightOnRunway(), "First flight should occupy the runway");
        assertFalse(runway.isClear(), "Runway should not be clear");

        // Add second flight (should go into waiting queue)
        runway.addToRunway(flight2);
        assertEquals(1, runway.getFlightsWaiting().size(), "Second flight should be in the waiting queue");
    }



    @Test
    void testRemoveFromRunway_FlightOnRunway() {
        // First, assign a flight to the runway
        runway.addToRunway(flight1);

        // Ensure flight is assigned
        assertEquals(flight1.getFlightID(), runway.getFlightOnRunway(), "Flight should be assigned to the runway");

        // Remove it
        runway.removeFromRunway(flight1.getFlightID());

        // Validate that runway is now clear
        assertTrue(runway.isClear(), "Runway should be cleared after removing the flight on it");
        assertEquals(0, runway.getFlightOnRunway(), "No flight should be on the runway after removal");
    }


    @Test
    void testRemoveFromRunway_WaitingFlights() {
        runway.addToRunway(flight1);
        runway.addToRunway(flight2);

        runway.removeFromRunway(flight1.getFlightID());
        assertEquals(1, runway.getFlightsWaiting().size(), "One flight should remain in the queue after removal");
        assertEquals(flight2, runway.getFlightsWaiting().get(0), "Remaining flight should be flight2");
    }

    @Test
    void testSetClear() {
        runway.setClear(true);
        assertTrue(runway.isClear(), "Runway should be marked as clear");

        runway.setClear(false);
        assertFalse(runway.isClear(), "Runway should be marked as not clear");
    }



}
