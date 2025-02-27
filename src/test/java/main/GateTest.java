package main;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GateTest {

    @Test
    void testGateInitialization() {
        Destination destination = new Destination("New York", 5000);
        Gate gate = new Gate(1, true, destination);

        assertEquals(1, gate.getGateID(), "Gate ID should be initialized correctly");
        assertTrue(gate.isOpen(), "Gate should be open initially");
        assertEquals(destination, gate.getDestination(), "Gate should have correct destination");
    }

    @Test
    void testSetGateID() {
        Gate gate = new Gate(1, true, null);
        gate.setGateID(5);
        assertEquals(5, gate.getGateID(), "Gate ID should be updated correctly");
    }

    @Test
    void testSetGateIsOpen() {
        Gate gate = new Gate(1, false, null);
        gate.setGateIsOpen(true);
        assertTrue(gate.isOpen(), "Gate should be open after setting to true");

        gate.setGateIsOpen(false);
        assertFalse(gate.isOpen(), "Gate should be closed after setting to false");
    }

    @Test
    void testSetDestination() {
        Gate gate = new Gate(1, true, null);
        Destination newDestination = new Destination("Los Angeles", 7000);
        gate.setDestination(newDestination);
        assertEquals(newDestination, gate.getDestination(), "Gate should have updated destination");
    }

    @Test
    void testClone() {
        Destination destination = new Destination("Chicago", 4500);
        Gate originalGate = new Gate(2, false, destination);

        Gate clonedGate = originalGate.clone();

        assertNotSame(originalGate, clonedGate, "Cloned gate should be a different object");
        assertEquals(originalGate.getGateID(), clonedGate.getGateID(), "Cloned gate should have the same ID");
        assertNotNull(clonedGate, "Cloned gate should not be null");
        assertTrue(clonedGate.isOpen(), "Cloned gate should always be open");
    }
}
