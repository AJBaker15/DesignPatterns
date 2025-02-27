package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class RunwayManagerTest {

    private RunwayManager runwayManager;
    private AirTrafficControl ATC;
    private Runway runway1, runway2;
    private Flight flight1, flight2;
    private Airplane airplane;
    private Destination destination;

    @BeforeEach
    void setUp() {
        // Initialize dependencies
        ATC = new AirTrafficControl(null, null, null, null, null);
        runwayManager = new RunwayManager(ATC);

        // Create runways
        runway1 = new Runway(1);
        runway2 = new Runway(2);
        runwayManager.addRunway(runway1);
        runwayManager.addRunway(runway2);

        // Create flight and airplane
        destination = new Destination("New York", 5000);
        airplane = new Airplane(1, 1, 6000, destination, 100);
        flight1 = new Flight(1, airplane, null, destination);
        flight2 = new Flight(2, airplane, null, destination);
    }

    @Test
    void testAddRunway() {
        assertEquals(2, runwayManager.getOpenRunways().size(), "Should have 2 open runways.");
    }

    @Test
    void testAssignRunway_Success() {
        Runway assignedRunway = runwayManager.assignRunway(flight1);
        assertNotNull(assignedRunway, "Runway should be assigned.");
        assertEquals(1, assignedRunway.getRunwayID(), "Flight should be assigned to Runway 1.");
        assertFalse(assignedRunway.isClear(), "Runway should no longer be clear after assignment.");
    }

    @Test
    void testAssignRunway_NoOpenRunway() {
        // Occupy both runways
        runwayManager.assignRunway(flight1);
        runwayManager.assignRunway(flight2);

        // Assign a third flight (should return null)
        Flight flight3 = new Flight(3, airplane, null, destination);
        Runway assignedRunway = runwayManager.assignRunway(flight3);
        assertNull(assignedRunway, "No runway should be available.");
    }

    @Test
    void testGetOpenRunways() {
        runwayManager.assignRunway(flight1);
        List<Runway> openRunways = runwayManager.getOpenRunways();
        assertEquals(1, openRunways.size(), "Only 1 runway should be available after one assignment.");
    }

    @Test
    void testFreeRunway() {
        runwayManager.assignRunway(flight1);
        runwayManager.freeRunway(runway1.getRunwayID());
        assertTrue(runway1.isClear(), "Runway should be clear after being freed.");
    }

    @Test
    void testLoadRunway_FlightInQueue() {
        runwayManager.assignRunway(flight1);
        runway1.addToRunway(flight2);
        runwayManager.freeRunway(runway1.getRunwayID()); // Free the runway

        runwayManager.loadRunway(runway1.getRunwayID()); // Load from queue
        assertFalse(runway1.isClear(), "Runway should now be occupied by next flight.");
        assertEquals(flight2.getFlightID(), runway1.getFlightOnRunway(), "Only one flight should be in the queue.");
    }

    @Test
    void testLoadRunway_NoFlightsWaiting() {
        runwayManager.assignRunway(flight1);
        runwayManager.freeRunway(runway1.getRunwayID());
        runwayManager.loadRunway(runway1.getRunwayID());

        assertTrue(runway1.isClear(), "Runway should remain clear if no flights are waiting.");
    }

}
