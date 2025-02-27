package main;

public class Passenger {

    private String name;
    private int threatLevel;
    private Ticket ticket;
    private Destination destination;

    //constructor
    public Passenger(String name, int threatLevel, Ticket ticket, Destination destination) {
        this.name = name;
        this.threatLevel = threatLevel;
        this.ticket = ticket;
        this.destination = destination;
    }

    public int getThreatLevel() {
        return threatLevel;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Destination getDestination() {
        return destination;
    }

    public void setThreatLevel(int threatLevel) {
        this.threatLevel = threatLevel;
    }

    public String getName() {
        return name;
    }

    public Ticket getTicket() {
        return ticket;
    }
}
