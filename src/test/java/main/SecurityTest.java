package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SecurityTest {
    private Security security;
    private Security securityWithoutATC;
    private AirTrafficControl ATC;
    private Passenger passenger;
    private Ticket ticket;

    @BeforeEach
    void setUp() {
        // Mock AirTrafficControl with null for now
        ATC = new AirTrafficControl(null, null, null, null, null);

        // Create a Security instance with ATC
        security = new Security(1, true, ATC);

        // Create a Security instance without ATC
        securityWithoutATC = new Security(2, false);

        // Create Passenger and Ticket for processing
        Destination destination = new Destination("New York", 5000);
        passenger = new Passenger("John Doe", 2, null, destination);
        ticket = new Ticket(passenger, 101, 1, 1);
    }

    @Test
    void testOpenSecurity() {
        security.closeSecurity();
        security.openSecurity();
        assertTrue(security.getIsOpen(), "Security should be open after calling openSecurity.");
    }

    @Test
    void testCloseSecurity() {
        security.closeSecurity();
        assertFalse(security.getIsOpen(), "Security should be closed after calling closeSecurity.");
    }

    @Test
    void testProcessPassenger_Success() {
        boolean result = security.processPassenger(passenger, ticket);
        assertTrue(result, "Passenger with threat level 2 should pass security.");
        assertTrue(ticket.getPassSecurity(), "Ticket should be marked as passed security.");
    }

    @Test
    void testProcessPassenger_SecurityClosed() {
        security.closeSecurity();
        boolean result = security.processPassenger(passenger, ticket);
        assertFalse(result, "Passenger should fail security if it's closed.");
        assertFalse(ticket.getPassSecurity(), "Ticket should not be marked as passed security.");
    }

    @Test
    void testProcessPassenger_FailedDueToThreatLevel() {
        Passenger highThreatPassenger = new Passenger("Alice", 4, null, new Destination("Los Angeles", 7000));
        boolean result = security.processPassenger(highThreatPassenger, ticket);
        assertFalse(result, "Passenger with threat level >3 should fail security.");
        assertFalse(ticket.getPassSecurity(), "Ticket should not be marked as passed security.");
    }
}
