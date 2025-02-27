package main;

import java.util.List;


public class AirTrafficControl {

    private Airport airport;
    private AirplaneManager apManager;
    private FlightManager flManager;
    private RunwayManager runManager;
    private TicketManager tickManager;

    public AirTrafficControl(Airport airport, AirplaneManager apManager, FlightManager flManager,
                             RunwayManager runManager, TicketManager tickManager) {
        this.airport = airport;
        this.apManager = apManager;
        this.flManager = flManager;
        this.runManager = runManager;
        this.tickManager = tickManager;
    }

    public void update(String message) {
        System.out.println("[Air Traffic Control Update]" + message);
    }


    public void apTakeoff(Flight flight) {
        final Airplane airplane = flight.getAirplane();

        if (airplane.getGallonsOfFuel() < flight.getDestination().getFuelNeeded()) {
            update("[ATC] Flight " + flight.getFlightID() + " has insufficient fuel, delaying.");
            flight.setFlightStatus(Flight.FlightStatus.DELAYED);
            return;
        }

        if (airplane.needsMaintenance()) {
            update("[ATC] Flight " + flight.getFlightID() + " needs maintenance, delaying.");
            flight.setFlightStatus(Flight.FlightStatus.DELAYED);
            return;
        }

        // Assign a runway and allow takeoff
        final Runway assignedRunway = runManager.assignRunway(flight);
        if (assignedRunway == null) {
            update("[ATC] No open runways for Flight " + flight.getFlightID() + ", delaying.");
            flight.setFlightStatus(Flight.FlightStatus.DELAYED);
            return;
        }

        update("[ATC] Flight " + flight.getFlightID() + " is taking off from Runway " + assignedRunway.getRunwayID());
        runManager.freeRunway(assignedRunway.getRunwayID());
    }


    public void apLand(int flightID) {
        final Flight flight = flManager.getFlightById(flightID);
        if (flight == null) {
            System.out.println("[ATC] Flight not found");
            return;
        }
        final Airplane airplane = flight.getAirplane();
        if (airplane == null) {
            System.out.println("[ATC] Airplane not found");
        }

        final Runway assignRunway = runManager.assignRunway(flight);
        if (assignRunway == null){
            update("Flight" + flightID + "is awaiting a free runway to land.");
            return;
        }
        flight.assignRunway(assignRunway.getRunwayID());
        update("Flight" + flightID + "has landed on runway " + assignRunway.getRunwayID() + "\n");
        airport.notifyLanding(airplane);
    }

    public int getDelayedFlightCount() {
        final List<Flight> delayedFlights = flManager.getDelayedFlights();
        return delayedFlights.size();
    }

    public void freeRunway(int runwayID) {
        runManager.freeRunway(runwayID);
        update("Runway " + runwayID + "is now available.\n");
    }

    public void modifyFlightRunway(int flightID, int newRunwayID) {
        final Flight flight = flManager.getFlightById(flightID);
        if (flight == null) {
            update("Flight" + flightID + "has no available runway\n");
            return;
        }
        flight.assignRunway(newRunwayID);
        update("Flight" + flightID + "has added to the runway\n");
    }

    public void delayFlight(int flightID) {
        final Flight flight = flManager.getFlightById(flightID);
        if (flight == null) {
            update("Flight" + flightID + "not found.\n");
            return;
        }
        if (flight.getFlightStatus() == Flight.FlightStatus.CANCELED){
            update("Flight" + flightID + "is canceled and cannot be delayed.\n");
        }
        flight.setFlightStatus(Flight.FlightStatus.DELAYED);
        update("Flight" + flightID + "has delayed flight.\n");
    }

    public void refuelPlane(int planeID) {
        final Airplane airplane = apManager.getAirplane(planeID);
        if (airplane == null) {
            update("Airplane " + planeID + "not found.\n");
            return;
        }
        final int fuelNeeded = airplane.getDestination().getFuelNeeded();
        airplane.refuel(fuelNeeded);
        update("Airplane " + planeID + "refueled.\n");
    }

    public List<Runway> getAvailableRunways() {
        return runManager.getOpenRunways();
    }

    public void assignRunway(int flightID) {
        final Flight flight = flManager.getFlightById(flightID);
        if (flight == null) {
            update("Flight" + flightID + "not found.\n");
            return;
        }
        final Runway runway = runManager.assignRunway(flight);
        if (runway != null) {
            flight.assignRunway(runway.getRunwayID());
            update("Flight" + flightID + "has assigned runway\n");
        }

    }
    public void notifyPassengers(int flightID) {
        final List<Ticket> tickets = tickManager.getTicketsByFlight(flightID);
        if (tickets.isEmpty()) {
            update("No passengers to notify.\n");
            return;
        }
        for (Ticket ticket : tickets) {
            update("Notifying passenger " + ticket.getPassenger().getName() + "about Flight "
                    + flightID);
        }
    }






}