package main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DestinationTest {

    @Test
    void testDestinationInitialization() {
        Destination destination = new Destination("New York", 5000);

        assertNotNull(destination, "Destination object should be initialized.");
        assertEquals("New York", destination.getDestination(), "Destination name should match.");
        assertEquals(5000, destination.getFuelNeeded(), "Fuel needed should match.");
    }

    @Test
    void testToStringMethod() {
        Destination destination = new Destination("Los Angeles", 7000);
        String expectedString = "Destination [destination=Los Angeles, fuelNeeded=7000]";

        assertEquals(expectedString, destination.toString(), "toString() should return the correct format.");
    }
}
