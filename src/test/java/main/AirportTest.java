package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class AirportTest {

    private Airport airport;
    private AirTrafficControl atcStub;

    @BeforeEach
    void setUp() {
        // Create a simple ATC stub
        atcStub = new AirTrafficControl(null, new AirplaneManager(), new FlightManager(null), new RunwayManager(null), new TicketManager(null, new FlightManager(null)));
        airport = new Airport(atcStub);
    }

    @Test
    void testAirportInitialization() {
        assertNotNull(airport);
        assertEquals(3, airport.gates.size(), "Airport should start with 3 gates");
        assertEquals(3, airport.sec_stations.size(), "Airport should start with 3 security stations");
        assertTrue(airport.destinations.isEmpty(), "Airport should start with no destinations");
    }

    @Test
    void testAssignGate() {
        Airplane airplane = new Airplane(1, 1, 5000, new Destination("New York", 5000), 100);
        Gate assignedGate = airport.assignGate(airplane);

        assertNotNull(assignedGate, "Gate should be assigned to the airplane");
        assertEquals("New York", assignedGate.getDestination().getDestination(), "Gate should be assigned to New York");
    }

    @Test
    void testAssignSecurity() {
        Security security = airport.assignSecurity();
        assertNotNull(security, "There should be at least one open security station");
        assertTrue(security.getIsOpen(), "Assigned security station should be open");
    }

    @Test
    void testManageGates_AddNewGate() {
        for (int i = 0; i < 31; i++) {
            airport.passengers.add(new Passenger("Passenger" + i, 1, null, null));
        }

        airport.manageGates();
        assertEquals(4, airport.gates.size(), "A new gate should be added when passenger count exceeds threshold");
    }

    @Test
    void testManageGates_RemoveGate() {
        for (int i = 0; i < 10; i++) {
            airport.passengers.add(new Passenger("Passenger" + i, 1, null, null));
        }
        airport.manageGates();

        assertEquals(3, airport.gates.size(), "Gate count should remain 3 when passenger count is low");
    }

    @Test
    void testManageSecurity_AddSecurityStation() {
        for (int i = 0; i < 31; i++) {
            airport.passengers.add(new Passenger("Passenger" + i, 1, null, null));
        }
        airport.manageSecurity();

        assertEquals(4, airport.sec_stations.size(), "A new security station should be added");
    }

    @Test
    void testManageSecurity_RemoveSecurityStation() {
        airport.sec_stations.add(new Security(4, true, atcStub)); // Add extra security station
        for (int i = 0; i < 5; i++) {
            airport.passengers.add(new Passenger("Passenger" + i, 1, null, null));
        }
        airport.manageSecurity();

        assertEquals(3, airport.sec_stations.size(), "Extra security station should be removed");
    }

    @Test
    void testManageDestinations_Add() {
        Destination destination = new Destination("Tokyo", 9000);
        airport.manageDestinations(destination, true);

        assertEquals(1, airport.destinations.size(), "Destination should be added");
        assertEquals("Tokyo", airport.destinations.get(0).getDestination(), "Destination should match");
    }

    @Test
    void testManageDestinations_Remove() {
        Destination destination = new Destination("Tokyo", 9000);
        airport.destinations.add(destination);

        airport.manageDestinations(destination, false);
        assertTrue(airport.destinations.isEmpty(), "Destination should be removed");
    }

    @Test
    void testNotifyTakeOff() {
        Airplane airplane = new Airplane(1, 1, 5000, new Destination("London", 7000), 100);
        airport.notifyTakeOff(airplane);
        // No assertion here because it's a print statement; we just ensure no errors occur.
    }

    @Test
    void testNotifyLanding() {
        Airplane airplane = new Airplane(1, 1, 5000, new Destination("London", 7000), 100);
        airport.notifyLanding(airplane);
        // No assertion here because it's a print statement; we just ensure no errors occur.
    }
}
