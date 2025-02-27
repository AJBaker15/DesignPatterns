package main;

import java.util.ArrayList;

public class Airport {

    ArrayList<Gate> gates;
    ArrayList<Destination> destinations;
    ArrayList<Security> sec_stations;
    ArrayList<Passenger> passengers;
    AirTrafficControl ATC;

    //functions
    public Airport(AirTrafficControl ATC) {
        this.ATC = ATC;
        gates = new ArrayList<>();
        destinations = new ArrayList<>();
        sec_stations = new ArrayList<>();
        passengers = new ArrayList<>();

    //Initializing some gates and sec stations
        Gate baseGate = new Gate(1, true, null);
        for(int i = 1; i <= 3; i++){
            Gate clonedGate = baseGate.clone();
            clonedGate.setGateID(i);
            gates.add(clonedGate);
        }

        for(int i = 1; i <= 3; i++){
            sec_stations.add(new Security(i, true, this.ATC));
        }
    }
    public void displayAirportState() {
        System.out.println("====Airport State====");
        System.out.println("Gates: " + gates.size());
        System.out.println("Open Gates:  " + getOpenGateCount());
        System.out.println("Runways Available: " + ATC.getAvailableRunways().size());
        System.out.println("Passengers in Airport: " + passengers.size());
        System.out.println("Security Stations Open: " + getOpenSecurityCount());
        System.out.println("Flights - Delayed: " + ATC.getDelayedFlightCount());
        System.out.println("Destinations: " + destinations.size()); //
    }


    private long getOpenGateCount() {
        return gates.stream().filter(Gate::isOpen).count();
    }
    private long getOpenSecurityCount() {
        return sec_stations.stream().filter(Security::getIsOpen).count();
    }

    public Security getSecurityByID(int securityID) {
        for (Security security : sec_stations) {
            if (security.getSecurityID() == securityID) {
                return security;
            }
        }
        return null;
    }


    public void notifyTakeOff(Airplane airplane) {
        ATC.update("[Airport] Airplane " + airplane.getPlaneID() + "has taken off!\n");
    }

    public void notifyLanding(Airplane airplane) {
        ATC.update("[Airport] Airplane " + airplane.getPlaneID() + "has landed!\n");
        assignGate(airplane);
    }

    public void manageGates() {
        if(passengers.size() > gates.size() * 10){
            Gate newGate = gates.get(0).clone();
            newGate.setGateID(gates.size() + 1);
            gates.add(newGate);
            ATC.update("[Airport] New Gate Added: Gate: " + newGate.gateID);
        } else if (gates.size() > 3 && passengers.size() > gates.size() * 5){
            Gate closingGate = gates.remove(gates.size() - 1);
            ATC.update("[Airport] Gate Removed: Gate: " + closingGate.gateID);
        }
    }

    public Gate assignGate(Airplane airplane) {
        for(Gate gate : gates){
            if(gate.isOpen()){
                gate.setDestination(airplane.getDestination());
                ATC.update("[Airport] Airplane " + airplane.getPlaneID() + "assigned to Gate " + gate.gateID);
                return gate;
            }
        }
        if(gates.isEmpty()){
            Gate newGate = gates.get(0).clone();
            newGate.setGateID(gates.size() + 1);
            gates.add(newGate);
            newGate.setDestination(airplane.getDestination());
            ATC.update("[Airport] New Gate Added: Gate: " + newGate.gateID);
            return newGate;
        }
        return null;
    }

    public Security assignSecurity() {
        for(Security security : sec_stations){
            if(security.getIsOpen()) {
                return security;
            }
        }
        return null;
    }

    public void manageSecurity() {
        if(passengers.size() > sec_stations.size() * 10){
            Security newSecurity = new Security(sec_stations.size() + 1, true, this.ATC);
            sec_stations.add(newSecurity);
            ATC.update("[Airport] Security Station: " + newSecurity.getSecurityID() +
                    "added.\n");
        } else if ( sec_stations.size() > 2 && passengers.size() < sec_stations.size() * 5){
            Security closingSecurity = sec_stations.remove(sec_stations.size() - 1);
            ATC.update("[Airport] Security Station " + closingSecurity.getSecurityID() + "has been closed.\n");
        }
    }

    public void manageDestinations(Destination dest, boolean add) {
        if(add){
            destinations.add(dest);
            ATC.update("[Airport] New destination added: " + dest.getDestination());
        } else {
            destinations.remove(dest);
            ATC.update("[Airport] Destination Removed: " + dest.getDestination());
        }
    }



}
