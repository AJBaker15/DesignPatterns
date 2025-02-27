package main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PassengerTest {

    @Test
    void testPassengerInitialization() {
        Destination destination = new Destination("New York", 5000);
        Ticket ticket = new Ticket(null, 1, 1, 1); // Ticket created without passenger reference for testing
        Passenger passenger = new Passenger("John Doe", 2, ticket, destination);

        assertEquals("John Doe", passenger.getName(), "Passenger name should be initialized correctly");
        assertEquals(2, passenger.getThreatLevel(), "Threat level should be initialized correctly");
        assertEquals(destination, passenger.getDestination(), "Destination should be set correctly");
        assertEquals(ticket, passenger.getTicket(), "Ticket should be set correctly");
    }

    @Test
    void testSetThreatLevel() {
        Passenger passenger = new Passenger("Alice", 1, null, null);
        passenger.setThreatLevel(3);
        assertEquals(3, passenger.getThreatLevel(), "Threat level should be updated correctly");
    }

    @Test
    void testSetDestination() {
        Passenger passenger = new Passenger("Bob", 2, null, null);
        Destination newDestination = new Destination("Los Angeles", 7000);
        passenger.setDestination(newDestination);
        assertEquals(newDestination, passenger.getDestination(), "Destination should be updated correctly");
    }

    @Test
    void testSetTicket() {
        Passenger passenger = new Passenger("Charlie", 3, null, null);
        Ticket newTicket = new Ticket(passenger, 2, 3, 2);
        passenger.setTicket(newTicket);
        assertEquals(newTicket, passenger.getTicket(), "Ticket should be updated correctly");
    }
}
