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
        Flight assignedFlight = flightManager.assignFlight(passenger.getDestination());
        if(assignedFlight == null){
            ATC.update("[Check In] No Available flights for " + passenger.getName());
            return null;
        }

        Gate assignedGate = airport.assignGate(assignedFlight.getAirplane());
        if(assignedGate == null){
            ATC.update("[Check In] No available gate for flight "+ assignedFlight.getAirplane());
            return null;
        }

        Security assignedSecurity = airport.assignSecurity();
        if(assignedSecurity == null){
            ATC.update("[Check In] No available security for " + passenger.getName());
            return null;
        }

        Ticket newTicket = new Ticket(passenger, assignedFlight.getFlightID(), assignedGate.getGateID(), assignedSecurity.getSecurityID());
        ticketManager.addTicket(newTicket);

        ATC.update("[Check In] Ticket assigned to " + passenger.getName() + " for Flight " + assignedFlight.getFlightID());

        int numTicketsByFlight = ticketManager.getTicketsByFlight(assignedFlight.getFlightID()).size();
        if(numTicketsByFlight >= assignedFlight.getAirplane().getCapacity()) {
            ATC.update("[Check In] Flight: " + assignedFlight.getFlightID() + " is now full!\n");
        }
        return newTicket;
    }

}
