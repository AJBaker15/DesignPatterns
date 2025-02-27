package main;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AirplaneManagerTest {

    private AirplaneManager airplaneManager;

    @BeforeEach
    void setUp() {
        airplaneManager = new AirplaneManager();
    }

    @Test
    void testAddAirplane_Success() {
        Airplane airplane = new Airplane(1, 1, 10000, new Destination("New York", 5000), 150);
        airplaneManager.addAirplane(airplane);

        assertEquals(1, airplaneManager.getAirplanes().size(), "Airplane should be added successfully");
    }

    @Test
    void testAddAirplane_DuplicateID() {
        Airplane airplane1 = new Airplane(1, 1, 10000, new Destination("New York", 5000), 150);
        Airplane airplane2 = new Airplane(1, 2, 8000, new Destination("Los Angeles", 7000), 180);

        airplaneManager.addAirplane(airplane1);
        airplaneManager.addAirplane(airplane2);

        assertEquals(1, airplaneManager.getAirplanes().size(), "Duplicate airplane should not be added");
    }

    @Test
    void testRemoveAirplane_Success() {
        Airplane airplane = new Airplane(2, 1, 10000, new Destination("Chicago", 4500), 200);
        airplaneManager.addAirplane(airplane);

        assertTrue(airplaneManager.removeAirplane(2), "Airplane should be removed successfully");
        assertEquals(0, airplaneManager.getAirplanes().size(), "Airplane list should be empty after removal");
    }

    @Test
    void testRemoveAirplane_NotFound() {
        assertFalse(airplaneManager.removeAirplane(99), "Removing a non-existent airplane should return false");
    }

    @Test
    void testGetAirplane_Exists() {
        Airplane airplane = new Airplane(3, 1, 5000, new Destination("Miami", 4800), 120);
        airplaneManager.addAirplane(airplane);

        Airplane retrieved = airplaneManager.getAirplane(3);
        assertNotNull(retrieved, "Airplane should be found");
        assertEquals(3, retrieved.getPlaneID(), "Retrieved airplane ID should match");
    }

    @Test
    void testGetAirplane_NotFound() {
        assertNull(airplaneManager.getAirplane(404), "Retrieving non-existent airplane should return null");
    }

    @Test
    void testGetAirplanesNeedingFuel() {
        Airplane airplane1 = new Airplane(4, 1, 4000, new Destination("Berlin", 8000), 250);
        Airplane airplane2 = new Airplane(5, 1, 9000, new Destination("Boston", 2200), 180);

        airplaneManager.addAirplane(airplane1);
        airplaneManager.addAirplane(airplane2);

        List<Airplane> lowFuelPlanes = airplaneManager.getAirplanesNeedingFuel();
        assertEquals(1, lowFuelPlanes.size(), "Only one airplane should need fuel");
        assertEquals(4, lowFuelPlanes.get(0).getPlaneID(), "Airplane 4 should need fuel");
    }

    @Test
    void testGetAirplanesNeedingMaintenance() {
        Airplane airplane1 = new Airplane(6, 1, 10000, new Destination("Rome", 7500), 150);
        Airplane airplane2 = new Airplane(7, 1, 10000, new Destination("Detroit", 4900), 120);
        airplane2.setNeedsMaintenance(true);

        airplaneManager.addAirplane(airplane1);
        airplaneManager.addAirplane(airplane2);

        List<Airplane> maintenancePlanes = airplaneManager.getAirplanesNeedingMaintenance();
        assertEquals(1, maintenancePlanes.size(), "Only one airplane should need maintenance");
        assertEquals(7, maintenancePlanes.get(0).getPlaneID(), "Airplane 7 should need maintenance");
    }

    @Test
    void testDisplayAirplanes_NoCrashes() {
        Airplane airplane1 = new Airplane(8, 1, 7000, new Destination("Raleigh", 7000), 180);
        airplaneManager.addAirplane(airplane1);

        assertDoesNotThrow(() -> airplaneManager.displayAirplanes(), "Display function should not crash");
    }
}
