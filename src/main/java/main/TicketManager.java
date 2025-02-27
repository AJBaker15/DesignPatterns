package main;

import java.util.ArrayList;

public class TicketManager {
    private ArrayList<Ticket> tickets = new ArrayList<>();
    private AirTrafficControl ATC;
    private FlightManager flightManager;

    public TicketManager(AirTrafficControl ATC, FlightManager flightManager) {
        this.ATC = ATC;
        this.flightManager = flightManager;
    }

    public void setATC(AirTrafficControl ATC) {
        this.ATC = ATC;
    }

    // === Add Ticket ===
    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }

    public void removeTicket(Ticket ticket) {
        tickets.remove(ticket);
    }

    // === Get Ticket By ID ===
    public Ticket getTicketById(int ticketNum) {
        for (Ticket ticket : tickets) {
            if (ticket.getTicketNum() == ticketNum) {
                return ticket;
            }
        }
        return null;
    }

    // === Get All Tickets for a Flight ===
    public ArrayList<Ticket> getTicketsByFlight(int flightID) {
        ArrayList<Ticket> flightTickets = new ArrayList<>();
        for (Ticket ticket : tickets) {
            if (ticket.getFlightID() == flightID) {
                flightTickets.add(ticket);
            }
        }
        return flightTickets;
    }

    // === Board Passenger (Decreases Airplane Capacity) ===
    public void boardPassenger(int ticketNum) {
        Ticket ticket = getTicketById(ticketNum);
        if (ticket == null) {
            ATC.update("[TicketManager] Ticket " + ticketNum + " not found.");
            return;
        }

        if (!ticket.getPassSecurity()) {
            ATC.update("[TicketManager] Passenger " + ticket.getPassenger().getName() + " cannot board. Security not passed.");
            return;
        }

        Flight flight = flightManager.getFlightById(ticket.getFlightID());
        if (flight == null) {
            ATC.update("[TicketManager] Flight " + ticket.getFlightID() + " not found.");
            return;
        }

        Airplane airplane = flight.getAirplane();
        if (airplane == null || airplane.getCapacity() <= 0) {
            ATC.update("[TicketManager] Flight " + flight.getFlightID() + " is full. Cannot board passenger " + ticket.getPassenger().getName() + ".");
            return;
        }

        // Board Passenger and Update Airplane Capacity
        ticket.setBoarded(true);
        airplane.updateCapacity(airplane.getCapacity() - 1);
        ATC.update("[TicketManager] Passenger " + ticket.getPassenger().getName() + " boarded Flight " + flight.getFlightID() + ".");
    }


}
