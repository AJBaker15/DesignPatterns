package main;

public class Security {

    private int securityID;
    private boolean secIsOpen;
    private AirTrafficControl ATC;

    public Security(int securityID, boolean secIsOpen, AirTrafficControl ATC) {
        this.securityID = securityID;
        this.secIsOpen = secIsOpen;
        this.ATC = ATC;
    }

    public Security(int securityID, boolean secIsOpen) {
        this.securityID = securityID;
        this.secIsOpen = secIsOpen;
    }

    public boolean getIsOpen() {
        return secIsOpen;
    }
    public int getSecurityID() {
        return securityID;
    }

    public void openSecurity() {
        secIsOpen = true;
        ATC.update("[Security] Security station " + securityID + " opened.");
    }
    public void closeSecurity() {
        secIsOpen = false;
        ATC.update("[Security] Security station " + securityID + " closed.");
    }

    public boolean processPassenger(Passenger passenger, Ticket ticket){
        if (!secIsOpen){
            ATC.update("[Security] Security station " + securityID + " not opened.");
            return false;
        }

        if (passenger.getThreatLevel() > 3) {
            ATC.update("[Security] Passenger " + passenger.getName() + "failed Security Checkpoint");
            return false;
        }

        ticket.setPassSecurity(true);
        ATC.update("[Security] Passenger " + passenger.getName() + " successfully processed.");
        return true;
    }
}
