package main;

import java.util.ArrayList;

public class RunwayManager {
    private ArrayList<Runway> runways = new ArrayList<>();
    private AirTrafficControl ATC;

    public RunwayManager(AirTrafficControl ATC) {
        this.ATC = ATC;
    }

    public void setATC(AirTrafficControl ATC) {
        this.ATC = ATC;
    }

    public void addRunway(Runway runway) {
        runways.add(runway);
        ATC.update("[Runway Manager] Runway added");
    }

    public ArrayList<Runway> getOpenRunways() {
        final ArrayList<Runway> openRunways = new ArrayList<>();
        for (Runway runway : runways) {
            if (runway.isClear()){
                openRunways.add(runway);
            }
        }
        return openRunways;
    }


    public ArrayList<Flight> getRunwayFlightsWaiting(int runwayID) {
        for (Runway runway : runways) {
            if (runway.getRunwayID() == runwayID){
                return runway.getFlightsWaiting();
            }
        }
        return new ArrayList<>();
    }

    public Runway assignRunway(Flight flight) {
        for (Runway runway : runways){
            if (runway.isClear()){
                runway.addToRunway(flight);
                ATC.update("[Runway Manager] Flight " + flight.getFlightID() + " assigned to runway " + runway.getRunwayID());
                return runway;
            }
        }
        return null;
    }

    public void freeRunway(int runwayID) {
        for (Runway runway : runways){
            if (runway.getRunwayID() == runwayID){
                runway.clearRunway();
                runway.removeFromRunway(runway.getFlightOnRunway());
                ATC.update("[Runway Manager] Runway " + runwayID + " is clear.\n");
            }
        }
    }
    public void loadRunway(int runwayID) {
        for (Runway runway: runways){
            if (runway.getRunwayID() == runwayID){
                if (runway.isClear()) {
                    if (!runway.getFlightsWaiting().isEmpty()) {
                        final Flight nextFlight = runway.getFlightsWaiting().remove(0); // Remove from queue
                        runway.removeFromRunway(0); // Ensure no lingering flights
                        runway.addToRunway(nextFlight); // Assign flight directly to runway
                        runway.setClear(false); // 🚀 Mark runway as occupied
                        ATC.update("[Runway Manager] Flight " + nextFlight.getFlightID() + " assigned to runway " + runway.getRunwayID());
                    } else {
                        ATC.update("[Runway Manager] No flights waiting for runway " + runway.getRunwayID());
                    }
                    return;
                }
            }
            ATC.update("[Runway Manager] Runway " + runway.getRunwayID() + " is not clear.\n");
        }
    }

}
