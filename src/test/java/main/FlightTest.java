package main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class FlightTest {

    @Test
    void testFlightInitialization() {
        Destination destination = new Destination("Chicago", 4500);
        Airplane airplane = new Airplane(1, 1, 6000, destination, 150);
        Ticket ticket = new Ticket(new Passenger("John Doe", 1, null, destination), 1, 1, 1);
        Flight flight = new Flight(1, airplane, ticket, destination);

        assertNotNull(flight, "Flight object should be initialized.");
        assertEquals(1, flight.getFlightID(), "Flight ID should be 1.");
        assertEquals(destination, flight.getDestination(), "Destination should match.");
        assertEquals(airplane, flight.getAirplane(), "Airplane should match.");
        assertEquals(ticket, flight.getTicket(), "Ticket should match.");
        assertEquals(Flight.FlightStatus.ON_TIME, flight.getFlightStatus(), "Default flight status should be ON_TIME.");
    }

    @Test
    void testSetFlightStatus() {
        Flight flight = new Flight(2, null, null, new Destination("Los Angeles", 7000));

        flight.setFlightStatus(Flight.FlightStatus.DELAYED);
        assertEquals(Flight.FlightStatus.DELAYED, flight.getFlightStatus(), "Flight status should be DELAYED.");

        flight.setFlightStatus(Flight.FlightStatus.CANCELED);
        assertEquals(Flight.FlightStatus.CANCELED, flight.getFlightStatus(), "Flight status should be CANCELED.");
    }

    @Test
    void testAssignRunway() {
        Flight flight = new Flight(3, null, null, new Destination("New York", 5000));

        flight.assignRunway(5);
        assertEquals(5, flight.getRunwayID(), "Runway ID should be assigned correctly.");

        flight.assignRunway(-1);
        assertNotEquals(-1, flight.getRunwayID(), "Runway ID should not be set to a negative value.");
    }

    @Test
    void testToStringMethod() {
        Destination destination = new Destination("Miami", 4800);
        Flight flight = new Flight(4, null, null, destination);
        String expectedString = "Flight 4| Status ON_TIME| Destination: " + destination;

        assertEquals(expectedString, flight.toString(), "toString() should return the correct format.");
    }
}
