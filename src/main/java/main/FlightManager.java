package main;

import java.util.ArrayList;
import java.util.List;

public class FlightManager {

    private ArrayList<Flight> flights = new ArrayList<>();
    private AirTrafficControl ATC;

    public FlightManager(AirTrafficControl ATC) {
        this.ATC = ATC;
    }
    public void setATC(AirTrafficControl ATC) {
        this.ATC = ATC;
    }

    public ArrayList<Flight> getFlights() {
        return flights;
    }

    public void addFlight(Flight flight) {
        flights.add(flight);
    }

    public ArrayList<Flight> getDelayedFlights() {
        final ArrayList<Flight> delayedFlights = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getFlightStatus() == Flight.FlightStatus.DELAYED) {
                delayedFlights.add(flight);
            }
        }
        return delayedFlights;
    }

    public Flight getFlightById(int flightID) {
        for (Flight flight : flights) {
            if (flight.flightID == flightID) {
                return flight;
            }
        }
        return null;
    }


    public ArrayList<Flight> getOnTime() {
        final ArrayList<Flight> onTimeFlights = new ArrayList<>();
        for (Flight flight : flights) {
            if (flight.getFlightStatus() == Flight.FlightStatus.ON_TIME){
                onTimeFlights.add(flight);
            }
        }
        return onTimeFlights;
    }

    public void modifyRunway(int flightID, int newRunwayID) {
        final Flight flight = getFlightById(flightID);
        if (flight != null) {
            flight.assignRunway(newRunwayID);
            ATC.update("[FlightManager] Flight: " + flight.getFlightID() + " assigned to Runway " + newRunwayID);
        } else {
            ATC.update("[FlightManager] Flight with ID " + flightID + " not found.\n"); // ✅ FIXED: No `null.getFlightID()`
        }
    }


    public Flight assignFlight(Destination destination) {
        if (destination == null) {
            ATC.update("[FlightManager] ERROR: Null destination passed to assignFlight.");
            return null;
        }

        for (Flight flight : flights) {
            if (flight.getFlightStatus() == Flight.FlightStatus.ON_TIME &&
                    flight.getAirplane().getCapacity() > 0 &&
                    flight.getDestination().equals(destination)) {
                return flight;
            }
        }

        // Try finding a delayed flight if no ON_TIME flights exist
        for (Flight flight : flights) {
            if (flight.getFlightStatus() == Flight.FlightStatus.DELAYED &&
                    flight.getAirplane().getCapacity() > 0 &&
                    flight.getDestination().equals(destination)) {
                flight.setFlightStatus(Flight.FlightStatus.ON_TIME); //Reset to ON_TIME
                return flight;
            }
        }

        ATC.update("[FlightManager] No available flights for destination: " + destination.getDestination());
        return null;
    }



}
