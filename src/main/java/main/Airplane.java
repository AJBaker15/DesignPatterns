package main;
//Amanda Baker
//SER316
//Date: 02/26/25

//public class for airplanes
public class Airplane implements Cloneable {

    private int planeID;
    private int gateNum;
    private int gallonsOfFuel;
    private Destination destination;
    private int capacity;
    private boolean maintenanceNeeded;
    private boolean cleared;

    //constructor
    public Airplane(int planeID, int gateNum, int gallonsOfFuel, Destination destination, int capacity) {
        this.planeID = planeID;
        this.gateNum = gateNum;
        this.gallonsOfFuel = gallonsOfFuel;
        this.destination = destination;
        this.capacity = capacity;
        maintenanceNeeded = false;
        cleared = false;
    }

    public int getPlaneID() {
        return planeID;
    }

    public void setPlaneID(int planeID) {
        this.planeID = planeID;
    }

    public int getGateNum(){
        return gateNum;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getGallonsOfFuel() {
        return gallonsOfFuel;
    }
    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public int checkFuel() {
        return this.gallonsOfFuel;
    }

    public boolean needsMaintenance() {
        return maintenanceNeeded;
    }

    public boolean checkCleared() {
        return cleared;
    }

    public void setNeedsMaintenance(boolean needed) {
        this.maintenanceNeeded = needed;
    }

    public void changeCleared(boolean cleared) {
        this.cleared = cleared;
    }
    public void refuel(int amount) {
        if (amount > 0) {
            this.gallonsOfFuel += amount;
        }
    }

    public void consumeFuel() {
        final int fuelNeeded = destination.getFuelNeeded();
        if (this.gallonsOfFuel >= fuelNeeded) {  // ✅ Correct condition
            this.gallonsOfFuel -= fuelNeeded;
        } else {
            System.out.println("Not enough fuel to reach destination! Needs refueling.");
            this.maintenanceNeeded = true;
        }
    }


    public void changeGate(int newGateNum) {
        this.gateNum = newGateNum;
    }

    public void updateCapacity(int newCapacity) {
        if (newCapacity >= 0) {
            this.capacity = newCapacity;
        }
    }
    //override the toString method for debugging and displaying Airport state.
    @Override
    public String toString() {
        return "Airplane: " + planeID + "\n" +
                "Gate Num: " + gateNum + "\n" +
                "Gallons of Fuel" + gallonsOfFuel + "\n" +
                "Destination: " + destination + "\n" +
                "Capacity: " + capacity + "\n" +
                "Maintenance Needed: " + maintenanceNeeded + "\n" +
                "Cleared: " + cleared + "\n";


    }


    @Override
    public Airplane clone() {
        try {
            final Airplane clone = (Airplane) super.clone();
            this.gallonsOfFuel = 10000;
            this.maintenanceNeeded = false;
            this.cleared = false;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
