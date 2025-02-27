package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class FlightManagerTest {

    private FlightManager flightManager;
    private AirTrafficControl ATC;
    private Destination testDestination;
    private Airplane testAirplane;
    private Flight testFlight;

    @BeforeEach
    void setUp() {
        // Mock Dependencies
        Airport airport = new Airport(null);
        AirplaneManager airplaneManager = new AirplaneManager();
        RunwayManager runwayManager = new RunwayManager(null);
        TicketManager ticketManager = new TicketManager(null, null);

        // Initialize ATC
        ATC = new AirTrafficControl(airport, airplaneManager, null, runwayManager, ticketManager);

        // Initialize FlightManager with ATC
        flightManager = new FlightManager(ATC);
        flightManager.setATC(ATC);

        // Create test data
        testDestination = new Destination("Chicago", 4500);
        testAirplane = new Airplane(1, 1, 5000, testDestination, 100);
        testFlight = new Flight(1, testAirplane, null, testDestination);
    }

    @Test
    void testAddFlight() {
        flightManager.addFlight(testFlight);
        assertEquals(1, flightManager.getFlights().size(), "Flight should be added.");
    }

    @Test
    void testGetFlightById_Found() {
        flightManager.addFlight(testFlight);
        Flight foundFlight = flightManager.getFlightById(1);
        assertNotNull(foundFlight, "Flight should be found.");
        assertEquals(1, foundFlight.getFlightID(), "Flight ID should match.");
    }

    @Test
    void testGetFlightById_NotFound() {
        Flight foundFlight = flightManager.getFlightById(999);
        assertNull(foundFlight, "Non-existent flight should return null.");
    }

    @Test
    void testGetDelayedFlights() {
        testFlight.setFlightStatus(Flight.FlightStatus.DELAYED);
        flightManager.addFlight(testFlight);
        List<Flight> delayedFlights = flightManager.getDelayedFlights();
        assertEquals(1, delayedFlights.size(), "One flight should be delayed.");
    }

    @Test
    void testGetOnTimeFlights() {
        flightManager.addFlight(testFlight);
        List<Flight> onTimeFlights = flightManager.getOnTime();
        assertEquals(1, onTimeFlights.size(), "One flight should be ON_TIME.");
    }

    @Test
    void testModifyRunway_FlightExists() {
        flightManager.addFlight(testFlight);
        flightManager.modifyRunway(1, 5);
        assertEquals(5, testFlight.getRunwayID(), "Runway ID should be updated.");
    }

    @Test
    void testModifyRunway_FlightNotFound() {
        flightManager.modifyRunway(999, 5);
        // No assertion needed since method logs an error
    }

    @Test
    void testAssignFlight_OnTimeFlightAvailable() {
        flightManager.addFlight(testFlight);
        Flight assignedFlight = flightManager.assignFlight(testDestination);
        assertNotNull(assignedFlight, "An available ON_TIME flight should be assigned.");
        assertEquals(testFlight, assignedFlight, "Assigned flight should match.");
    }

    @Test
    void testAssignFlight_DelayedFlightAvailable() {
        testFlight.setFlightStatus(Flight.FlightStatus.DELAYED);
        flightManager.addFlight(testFlight);
        Flight assignedFlight = flightManager.assignFlight(testDestination);
        assertNotNull(assignedFlight, "A delayed flight should be assigned.");
        assertEquals(Flight.FlightStatus.ON_TIME, assignedFlight.getFlightStatus(), "Delayed flight should be reset to ON_TIME.");
    }

    @Test
    void testAssignFlight_NoAvailableFlight() {
        Flight assignedFlight = flightManager.assignFlight(testDestination);
        assertNull(assignedFlight, "No available flights should return null.");
    }

    @Test
    void testAssignFlight_NullDestination() {
        Flight assignedFlight = flightManager.assignFlight(null);
        assertNull(assignedFlight, "Assigning a null destination should return null.");
    }
}
