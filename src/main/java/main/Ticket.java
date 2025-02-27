package main;

public class Ticket {

    private static int ticketCounter = 1;
    private Passenger passenger;
    private int ticketNum;
    private int gateNum;
    private boolean passSecurity;
    private boolean boarded;
    private int sec_station_id;
    private int flightID;
    private boolean checkedIn;

    //constructor
    public Ticket(Passenger passenger, int flightID, int gateNum, int sec_station_id) {
        this.ticketNum = ticketCounter++;
        this.passenger = passenger;
        this.flightID = flightID;
        this.gateNum = gateNum;
        this.sec_station_id = sec_station_id;
        passSecurity = false;
        boarded = false;
        checkedIn = false;

    }

    public void checkIn(){
        checkedIn = true;
    }

    public boolean getCheckedIn(){
        return checkedIn;
    }

    public int getTicketNum() {
        return ticketNum;
    }

    public Passenger getPassenger() {
        return passenger;
    }
    public int getGateNum() {
        return gateNum;
    }

    public boolean getPassSecurity() {
        return passSecurity;
    }

    public void setPassSecurity(boolean passSecurity) {
        this.passSecurity = passSecurity;
    }
    public boolean getBoarded() {
        return boarded;
    }

    public int getFlightID() {
        return flightID;
    }

    public int getSec_station_id() {
        return sec_station_id;
    }

    public void setGateNum(int gateNum) {
        this.gateNum = gateNum;
    }
    public void setBoarded(boolean boarded) {
        this.boarded = boarded;
    }

    public void setSec_station_id(int sec_station_id) {
        this.sec_station_id = sec_station_id;
    }

    @Override
    public String toString() {
        return "[Ticket] ID: " + ticketNum +
                " | Passenger: " + passenger.getName() +
                " | Flight: " + flightID +
                " | Gate: " + gateNum +
                " | Sec Station: " + sec_station_id +
                " | Passed Security: " + passSecurity +
                " | Boarded: " + boarded;
    }

}
