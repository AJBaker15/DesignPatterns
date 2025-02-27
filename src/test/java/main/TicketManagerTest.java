package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

class TicketManagerTest {
    private TicketManager ticketManager;
    private FlightManager flightManager;
    private AirTrafficControl ATC;
    private Airport airport;
    private Airplane airplane;
    private Flight flight;
    private Passenger passenger;
    private Ticket ticket;
    private Destination destination;

    @BeforeEach
    void setUp() {
        // Setup required objects
        airport = new Airport(null);
        flightManager = new FlightManager(null);
        RunwayManager runwayManager = new RunwayManager(null);
        AirplaneManager airplaneManager = new AirplaneManager();

        // Initialize ATC AFTER all managers
        ATC = new AirTrafficControl(airport, airplaneManager, flightManager, runwayManager, ticketManager);
        flightManager.setATC(ATC);

        // Initialize TicketManager
        ticketManager = new TicketManager(ATC, flightManager);
        ticketManager.setATC(ATC);

        // Create a test destination, airplane, and flight
        destination = new Destination("New York", 5000);
        airplane = new Airplane(1, 1, 6000, destination, 10);  // Plane with capacity 10
        flight = new Flight(101, airplane, null, destination);
        flightManager.addFlight(flight);

        // Create a passenger and ticket
        passenger = new Passenger("John Doe", 2, null, destination);
        ticket = new Ticket(passenger, flight.getFlightID(), 1, 5);
        ticketManager.addTicket(ticket);
    }

    @Test
    void testAddTicket() {
        assertEquals(1, ticketManager.getTicketsByFlight(flight.getFlightID()).size(),
                "Ticket should be added to TicketManager.");
    }

    @Test
    void testRemoveTicket() {
        ticketManager.removeTicket(ticket);
        assertEquals(0, ticketManager.getTicketsByFlight(flight.getFlightID()).size(),
                "Ticket should be removed from TicketManager.");
    }

    @Test
    void testGetTicketById() {
        Ticket retrievedTicket = ticketManager.getTicketById(ticket.getTicketNum());
        assertNotNull(retrievedTicket, "Ticket should be retrievable by ticket number.");
        assertEquals(passenger.getName(), retrievedTicket.getPassenger().getName(),
                "Retrieved ticket should belong to the correct passenger.");
    }

    @Test
    void testGetTicketsByFlight() {
        ArrayList<Ticket> tickets = ticketManager.getTicketsByFlight(flight.getFlightID());
        assertEquals(1, tickets.size(), "There should be one ticket for the flight.");
    }

    @Test
    void testBoardPassenger_Success() {
        ticket.setPassSecurity(true); // Ensure passenger has passed security
        ticketManager.boardPassenger(ticket.getTicketNum());

        assertTrue(ticket.getBoarded(), "Passenger should be marked as boarded.");
        assertEquals(9, airplane.getCapacity(), "Airplane capacity should decrease after boarding.");
    }

    @Test
    void testBoardPassenger_Fails_NoSecurity() {
        ticket.setPassSecurity(false); // Passenger hasn't passed security
        ticketManager.boardPassenger(ticket.getTicketNum());

        assertFalse(ticket.getBoarded(), "Passenger should not board if security not passed.");
    }

    @Test
    void testBoardPassenger_Fails_FlightFull() {
        // Create a plane with 1 seat
        Airplane smallAirplane = new Airplane(2, 2, 5000, destination, 1);
        Flight smallFlight = new Flight(202, smallAirplane, null, destination);
        flightManager.addFlight(smallFlight);

        Passenger firstPassenger = new Passenger("Alice", 1, null, destination);
        Passenger secondPassenger = new Passenger("Bob", 1, null, destination);

        Ticket firstTicket = new Ticket(firstPassenger, smallFlight.getFlightID(), 2, 6);
        Ticket secondTicket = new Ticket(secondPassenger, smallFlight.getFlightID(), 2, 6);

        ticketManager.addTicket(firstTicket);
        ticketManager.addTicket(secondTicket);

        firstTicket.setPassSecurity(true);
        secondTicket.setPassSecurity(true);

        // First passenger boards successfully
        ticketManager.boardPassenger(firstTicket.getTicketNum());
        assertTrue(firstTicket.getBoarded(), "First passenger should board successfully.");

        System.out.println("DEBUG: Capacity before Bob boards: " + smallAirplane.getCapacity());

        // Second passenger should **not** board (flight is full)
        ticketManager.boardPassenger(secondTicket.getTicketNum());

        System.out.println("DEBUG: Capacity after Bob tries boarding: " + smallAirplane.getCapacity());

        assertFalse(secondTicket.getBoarded(), "Second passenger should not board, as flight is full.");
    }

}
