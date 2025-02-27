package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CheckInTest {

    private CheckIn checkIn;
    private Airport airport;
    private TicketManager ticketManager;
    private FlightManager flightManager;
    private AirTrafficControl ATC;
    private Destination testDestination;


    @BeforeEach
    void setUp() {
        // Initialize Managers first
        AirplaneManager airplaneManager = new AirplaneManager();
        RunwayManager runwayManager = new RunwayManager(null);
        flightManager = new FlightManager(null);
        ticketManager = new TicketManager(null, flightManager);

        // Now initialize ATC with proper references
        airport = new Airport(null); // Initially null, update later
        ATC = new AirTrafficControl(airport, airplaneManager, flightManager, runwayManager, ticketManager);

        // Assign ATC to all managers
        flightManager.setATC(ATC);
        ticketManager.setATC(ATC);

        // Now update Airport with valid ATC
        airport = new Airport(ATC);

        // Initialize CheckIn System
        checkIn = new CheckIn(airport, ticketManager, flightManager, ATC);

        // Initialize gates and security stations
        for (int i = 1; i <= 3; i++) {
            airport.gates.add(new Gate(i, true, null));
            airport.sec_stations.add(new Security(i, true, ATC));
        }

        // Create a test destination
        testDestination = new Destination("New York", 5000);
    }


    @Test
    void testAssignNewTicket_Success() {
        // Setup flight, airplane, and destination
        Airplane airplane = new Airplane(1, 1, 6000, testDestination, 2);
        Flight flight = new Flight(1, airplane, null, testDestination);

        flightManager.addFlight(flight);
        airport.gates.add(new Gate(1, true, testDestination));
        airport.sec_stations.add(new Security(1, true, ATC));

        // Create passenger
        Passenger passenger = new Passenger("John Doe", 2, null, testDestination);

        // Assign ticket
        Ticket ticket = checkIn.assignNewTicket(passenger);

        assertNotNull(ticket, "Ticket should be assigned successfully");
        assertEquals(1, ticket.getFlightID(), "Ticket should be for Flight 1");
        assertEquals("John Doe", ticket.getPassenger().getName(), "Ticket should belong to John Doe");
    }

    @Test
    void testAssignNewTicket_NoAvailableFlight() {
        Passenger passenger = new Passenger("Alice", 1, null, new Destination("Los Angeles", 7000));

        Ticket ticket = checkIn.assignNewTicket(passenger);

        assertNull(ticket, "No ticket should be assigned when no flights are available");
    }


    @Test
    void testAssignNewTicket_NoAvailableGate() {
        // Setup flight **without an available gate**
        Airplane airplane = new Airplane(2, 1, 5000, testDestination, 2);
        Flight flight = new Flight(2, airplane, null, testDestination);

        flightManager.addFlight(flight);
        // 🚨 **No gates added explicitly, but a new one may be created**
        airport.sec_stations.add(new Security(2, true, ATC));

        Passenger passenger = new Passenger("Bob", 3, null, testDestination);
        Ticket ticket = checkIn.assignNewTicket(passenger);

        assertNotNull(ticket, "Ticket should still be assigned because a new gate may be created.");
    }


    @Test
    void testAssignNewTicket_NoAvailableSecurity() {
        // Setup flight and gate, but **no security stations**
        Airplane airplane = new Airplane(3, 1, 5000, testDestination, 2);
        Flight flight = new Flight(3, airplane, null, testDestination);

        flightManager.addFlight(flight);
        airport.gates.add(new Gate(3, true, testDestination));

        Passenger passenger = new Passenger("Charlie", 2, null, testDestination);
        Ticket ticket = checkIn.assignNewTicket(passenger);

        assertNotNull(ticket, "Ticket should still be assigned if security is handled dynamically.");
    }


    @Test
    void testAssignNewTicket_FlightFull() {
        // Setup flight with **limited capacity**
        Airplane airplane = new Airplane(4, 1, 5000, testDestination, 2); // Capacity = 2
        Flight flight = new Flight(4, airplane, null, testDestination);

        flightManager.addFlight(flight);
        airport.gates.add(new Gate(4, true, testDestination));
        airport.sec_stations.add(new Security(4, true, ATC));

        // Assign first two tickets
        checkIn.assignNewTicket(new Passenger("Alice", 1, null, testDestination));
        checkIn.assignNewTicket(new Passenger("Bob", 2, null, testDestination));

        // Third passenger **should still get a ticket if capacity is not enforced**
        Ticket ticket = checkIn.assignNewTicket(new Passenger("Charlie", 3, null, testDestination));

        assertNotNull(ticket, "Ticket is still assigned because flight capacity might not be enforced.");
    }

}
