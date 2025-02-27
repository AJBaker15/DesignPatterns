package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AirplaneTest {

    private Airplane airplane;
    private Destination destination;

    @BeforeEach
    void setUp() {
        destination = new Destination("New York", 5000);
        airplane = new Airplane(1, 2, 10000, destination, 150);
    }

    @Test
    void testAirplaneInitialization() {
        assertEquals(1, airplane.getPlaneID(), "Plane ID should be initialized correctly");
        assertEquals(2, airplane.getGateNum(), "Gate number should be set correctly");
        assertEquals(10000, airplane.getGallonsOfFuel(), "Fuel amount should be initialized correctly");
        assertEquals(destination, airplane.getDestination(), "Destination should be set correctly");
        assertEquals(150, airplane.getCapacity(), "Capacity should be initialized correctly");
        assertFalse(airplane.needsMaintenance(), "Airplane should not need maintenance initially");
        assertFalse(airplane.checkCleared(), "Airplane should not be cleared initially");
    }

    @Test
    void testSetDestination() {
        Destination newDestination = new Destination("Los Angeles", 7000);
        airplane.setDestination(newDestination);
        assertEquals(newDestination, airplane.getDestination(), "Destination should be updated correctly");
    }

    @Test
    void testRefuel() {
        airplane.refuel(5000);
        assertEquals(15000, airplane.getGallonsOfFuel(), "Refuel should correctly increase fuel");

        airplane.refuel(-1000);
        assertEquals(15000, airplane.getGallonsOfFuel(), "Negative refuel should not decrease fuel");
    }

    @Test
    void testConsumeFuel_SufficientFuel() {
        airplane.consumeFuel();
        assertEquals(5000, airplane.getGallonsOfFuel(), "Fuel should be correctly consumed when sufficient fuel is available");
    }

    @Test
    void testConsumeFuel_InsufficientFuel() {
        Airplane lowFuelPlane = new Airplane(2, 3, 2000, destination, 100);
        lowFuelPlane.consumeFuel();

        assertTrue(lowFuelPlane.needsMaintenance(), "Airplane should need maintenance when fuel is insufficient");
        assertEquals(2000, lowFuelPlane.getGallonsOfFuel(), "Fuel should remain unchanged if not enough for the trip");
    }

    @Test
    void testSetNeedsMaintenance() {
        airplane.setNeedsMaintenance(true);
        assertTrue(airplane.needsMaintenance(), "Airplane should be in maintenance when set");

        airplane.setNeedsMaintenance(false);
        assertFalse(airplane.needsMaintenance(), "Airplane should not be in maintenance when set to false");
    }

    @Test
    void testChangeClearedStatus() {
        airplane.changeCleared(true);
        assertTrue(airplane.checkCleared(), "Airplane should be cleared when set");

        airplane.changeCleared(false);
        assertFalse(airplane.checkCleared(), "Airplane should not be cleared when reset");
    }

    @Test
    void testChangeGate() {
        airplane.changeGate(5);
        assertEquals(5, airplane.getGateNum(), "Gate number should be updated correctly");
    }

    @Test
    void testUpdateCapacity() {
        airplane.updateCapacity(200);
        assertEquals(200, airplane.getCapacity(), "Capacity should be updated correctly");

        airplane.updateCapacity(-50);
        assertEquals(200, airplane.getCapacity(), "Negative capacity should not update the value");
    }

    @Test
    void testClone() {
        Airplane clonedPlane = airplane.clone();

        assertNotNull(clonedPlane, "Cloned airplane should not be null");
        assertNotSame(airplane, clonedPlane, "Cloned airplane should be a different object");
        assertEquals(10000, clonedPlane.getGallonsOfFuel(), "Cloned airplane should have reset fuel level");
        assertFalse(clonedPlane.needsMaintenance(), "Cloned airplane should not need maintenance");
        assertFalse(clonedPlane.checkCleared(), "Cloned airplane should not be cleared");
    }
}
