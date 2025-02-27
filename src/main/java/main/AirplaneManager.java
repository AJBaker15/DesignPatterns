package main;

import java.util.ArrayList;
import java.util.List;

public class AirplaneManager {
    private ArrayList<Airplane> airplanes;

    public AirplaneManager() {
        airplanes = new ArrayList<>();
    }

    public List<Airplane> getAirplanes() {
        return new ArrayList<>(airplanes);
    }

    public void addAirplane(Airplane airplane) {
        for (Airplane a : airplanes) {
            if (a.getPlaneID() == airplane.getPlaneID()) {
                System.out.println("Airplane with ID " + airplane.getPlaneID() + " already exists!");
                return;
            }
        }
        airplanes.add(airplane);
    }


    public boolean removeAirplane(int planeID) {
        return airplanes.removeIf(airplane -> airplane.getPlaneID() == planeID);
    }

    public Airplane getAirplane(int planeID) {
        for (Airplane airplane : airplanes) {
            if (airplane.getPlaneID() == planeID) {
                return airplane;
            }
        }
        return null;
    }

    public List<Airplane> getAirplanesNeedingFuel() {
        List<Airplane> lowFuelPlanes = new ArrayList<>();
        for (Airplane airplane : airplanes) {
            if (airplane.getGallonsOfFuel() < airplane.getDestination().getFuelNeeded()) {
                lowFuelPlanes.add(airplane);
            }
        }
        return lowFuelPlanes;
    }

    public List<Airplane> getAirplanesNeedingMaintenance() {
        List<Airplane> maintenancePlanes = new ArrayList<>();
        for (Airplane airplane : airplanes) {
            if (airplane.needsMaintenance()) {
                maintenancePlanes.add(airplane);
            }
        }
        return maintenancePlanes;
    }

    public void displayAirplanes() {
        for (Airplane airplane : airplanes) {
            System.out.println(airplane.toString());
        }
    }
}
