package main;

public class CheckIn {

    private Airport airport;
    private TicketManager ticketManager;
    private FlightManager flightManager;
    private AirTrafficControl ATC;

    public CheckIn(Airport airport, TicketManager ticketManager, FlightManager flightManager, AirTrafficControl ATC) {
        this.airport = airport;
        this.ticketManager = ticketManager;
        this.flightManager = flightManager;
        this.ATC = ATC;
    }

    public Ticket assignNewTicket(Passenger passenger) {
        if (passenger == null || passenger.getDestination() == null) {
            ATC.update("[Check In] Error: Passenger or destination is null.");
            return null;
        }

        final Flight assignedFlight = flightManager.assignFlight(passenger.getDestination());
        if (assignedFlight == null) {
            ATC.update("[Check In] No Available flights for " + passenger.getName());
            return null;
        }

        final Gate assignedGate = airport.assignGate(assignedFlight.getAirplane());
        if (assignedGate == null) {
            ATC.update("[Check In] No available gate for flight " + assignedFlight.getFlightID());
            return null;
        }

        final Security assignedSecurity = airport.assignSecurity();
        if (assignedSecurity == null) {
            ATC.update("[Check In] No available security for " + passenger.getName());
            return null;
        }

        // Ensure ticketManager is not null before adding a ticket
        if (ticketManager == null) {
            ATC.update("[Check In] Error: Ticket Manager is not initialized.");
            return null;
        }

        final Ticket newTicket = new Ticket(passenger, assignedFlight.getFlightID(), assignedGate.getGateID(), assignedSecurity.getSecurityID());
        ticketManager.addTicket(newTicket);

        ATC.update("[Check In] Ticket assigned to " + passenger.getName() + " for Flight " + assignedFlight.getFlightID());

        // Ensure we retrieve a valid list before checking size
        final int numTicketsByFlight = ticketManager.getTicketsByFlight(assignedFlight.getFlightID()) != null
                ? ticketManager.getTicketsByFlight(assignedFlight.getFlightID()).size()
                : 0;

        if (numTicketsByFlight >= assignedFlight.getAirplane().getCapacity()) {
            ATC.update("[Check In] Flight: " + assignedFlight.getFlightID() + " is now full!\n");
        }

        return newTicket;
    }
}
