package main;


public class Flight {

    public enum FlightStatus {ON_TIME, DELAYED, CANCELED}
    public int flightID;
    private FlightStatus flightStatus;
    private Airplane airplane;
    private Ticket ticket;
    private Destination destination;
    private int runwayID;

    //constructor
    public Flight(int flightID, Airplane airplane, Ticket ticket, Destination destination) {
        this.flightID = flightID;
        this.airplane = airplane;
        this.ticket = ticket;
        this.flightStatus = FlightStatus.ON_TIME;
        this.destination = destination;
    }

    public int getFlightID() {
        return flightID;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public FlightStatus getFlightStatus() {
        return flightStatus;
    }

    public void setFlightStatus(FlightStatus flightStatus) {
        this.flightStatus = flightStatus;

    }

    public Airplane getAirplane() {
        return airplane;
    }
    public Ticket getTicket() {
        return ticket;
    }
    public int getRunwayID() {
        return runwayID;
    }

    public void assignRunway(int runwayID) {
        if (runwayID >= 0) {
            this.runwayID = runwayID;
        }
    }

    @Override
    public String toString() {
        return "Flight " + flightID + "| Status " + flightStatus + "| Destination: " + destination;
    }
}
