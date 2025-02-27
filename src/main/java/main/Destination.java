package main;

public class Destination {
    private String destination;
    private int fuelNeeded;

    public Destination(String destination, int fuelNeeded) {
        this.destination = destination;
        this.fuelNeeded = fuelNeeded;
    }

    public String getDestination() {
        return destination;
    }
    public int getFuelNeeded() {
        return fuelNeeded;
    }

    @Override
    public String toString() {
        return "Destination [destination=" + destination + ", fuelNeeded=" + fuelNeeded + "]";
    }

}
