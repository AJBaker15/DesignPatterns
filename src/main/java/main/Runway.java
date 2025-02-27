package main;

import java.util.ArrayList;


public class Runway {

    private int runwayID;
    private int flightOnRunway;
    private boolean isClear;
    private ArrayList<Flight> flightsWaiting;


    //constructor
    public Runway(int runwayID) {
        this.runwayID = runwayID;
        this.flightsWaiting = new ArrayList<Flight>();
        isClear = false;
        flightOnRunway = 0;
    }

    public int getRunwayID() {
        return runwayID;
    }

    public boolean isClear() {
        return isClear;
    }

    public ArrayList<Flight> getFlightsWaiting() {
        return flightsWaiting;
    }

    public void clearRunway() {
        System.out.println("Clearing runway " + runwayID);
        flightOnRunway = 0;
        isClear = true;
    }

    public int getFlightOnRunway() {
        return flightOnRunway;
    }

    public void setClear(boolean clear) {
        isClear = clear;
    }

    public void removeFromRunway(int flightID) {
        if(flightOnRunway == flightID) {
            flightOnRunway = 0;
            isClear = true;
        } else {
            flightsWaiting.removeIf(flight -> flight.getFlightID() == flightID);
        }
    }

    public void addToRunway(Flight flight) {
        flightsWaiting.add(flight);
    }

    @Override
    public String toString() {
        return "Runway " + runwayID + " | " + (isClear ? "Clear" : "Waiting") + " | Flights Waiting: "
                + flightsWaiting.size();
    }

}
