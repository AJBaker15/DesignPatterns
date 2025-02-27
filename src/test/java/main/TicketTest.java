package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TicketTest {
    private Ticket ticket;
    private Passenger passenger;
    private Destination destination;

    @BeforeEach
    void setUp() {
        destination = new Destination("New York", 5000);
        passenger = new Passenger("John Doe", 2, null, destination);
        ticket = new Ticket(passenger, 101, 1, 5);
    }

    @Test
    void testTicketInitialization() {
        assertNotNull(ticket, "Ticket should be initialized.");
        assertEquals(passenger, ticket.getPassenger(), "Passenger should be correctly set.");
        assertEquals(101, ticket.getFlightID(), "Flight ID should be correctly set.");
        assertEquals(1, ticket.getGateNum(), "Gate number should be correctly set.");
        assertEquals(5, ticket.getSec_station_id(), "Security station ID should be correctly set.");
        assertFalse(ticket.getPassSecurity(), "Ticket should start with passSecurity = false.");
        assertFalse(ticket.getBoarded(), "Ticket should start with boarded = false.");
    }

    @Test
    void testCheckIn() {
        ticket.checkIn();
        assertTrue(ticket.getCheckedIn(), "Ticket should be marked as checked in.");
    }

    @Test
    void testSetPassSecurity() {
        ticket.setPassSecurity(true);
        assertTrue(ticket.getPassSecurity(), "Ticket should mark passenger as having passed security.");
    }

    @Test
    void testSetBoarded() {
        ticket.setBoarded(true);
        assertTrue(ticket.getBoarded(), "Ticket should be marked as boarded.");
    }

    @Test
    void testSetGateNum() {
        ticket.setGateNum(2);
        assertEquals(2, ticket.getGateNum(), "Gate number should be updated.");
    }

    @Test
    void testSetSecStationId() {
        ticket.setSec_station_id(10);
        assertEquals(10, ticket.getSec_station_id(), "Security station ID should be updated.");
    }

    @Test
    void testToString() {
        String expected = "[Ticket] ID: " + ticket.getTicketNum() +
                " | Passenger: " + passenger.getName() +
                " | Flight: " + ticket.getFlightID() +
                " | Gate: " + ticket.getGateNum() +
                " | Sec Station: " + ticket.getSec_station_id() +
                " | Passed Security: " + ticket.getPassSecurity() +
                " | Boarded: " + ticket.getBoarded();
        assertEquals(expected, ticket.toString(), "toString should return the correct ticket details.");
    }
}
