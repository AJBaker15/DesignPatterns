package main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimulationEngine {

    private static final int TOTAL_SIMULATION_TIME = 180; // Simulate 180 minutes (3 hours)
    private static final int TIME_STEP = 30; // Update every 30 minutes

    private Airport airport;
    private AirplaneManager airplaneManager;
    private FlightManager flightManager;
    private RunwayManager runwayManager;
    private TicketManager ticketManager;
    private CheckIn checkIn;
    private AirTrafficControl ATC;
    private List<Passenger> passengers = new ArrayList<>();
    private List<Security> securityStations = new ArrayList<>();
    private List<Gate> gates = new ArrayList<>();
    private List<Destination> destinations = new ArrayList<>();

    public SimulationEngine() {
        initializeManagers();
        initializeAirport();
        initializeDestinations();
        initializeGates();
        initializeRunways();
        initializeAirplanes();
        initializeFlights();
        initializePassengers();
        initializeSecurity();
        initializeCheckIn();
    }

    // === Initialize Core Managers ===
    private void initializeManagers() {
        airplaneManager = new AirplaneManager();
        flightManager = new FlightManager(null);  // Temporarily null, will set ATC later
        runwayManager = new RunwayManager(null);  // Temporarily null, will set ATC later
        ticketManager = new TicketManager(null, flightManager); // Temporarily null

        // Now, create ATC AFTER managers are initialized
        ATC = new AirTrafficControl(airport, airplaneManager, flightManager, runwayManager, ticketManager);

        // Update managers to use the actual ATC instance
        flightManager.setATC(ATC);
        runwayManager.setATC(ATC);
        ticketManager.setATC(ATC);
    }


    private void initializeAirport() {
        airport = new Airport(ATC);
    }

    // === Initialize Destinations ===
    private void initializeDestinations() {
        destinations.add(new Destination("New York", 5000));
        destinations.add(new Destination("Los Angeles", 7000));
        destinations.add(new Destination("Chicago", 4500));
        destinations.add(new Destination("Houston", 5500));
        destinations.add(new Destination("Miami", 4800));
        destinations.add(new Destination("Philadelphia", 5000));
        destinations.add(new Destination("Charlotte", 4500));
        destinations.add(new Destination("Raleigh", 7000));
        destinations.add(new Destination("Berlin", 8000));
        destinations.add(new Destination("Rome", 7500));
        destinations.add(new Destination("Flagstaff", 3000));
        destinations.add(new Destination("Tempe", 2000));
        destinations.add(new Destination("Detroit", 4900));
        destinations.add(new Destination("Washington", 5000));
        destinations.add(new Destination("Boston", 2200));

        System.out.println("\n===== Destinations Initialized =====");
        for (Destination d : destinations) {
            System.out.println("[Destination] " + d.getDestination() + " | Fuel Needed: " + d.getFuelNeeded());
        }
    }

    // === Initialize Gates ===
    private void initializeGates() {
        for (int i = 1; i <= 10; i++) {
            gates.add(new Gate(i, true, destinations.get(i % destinations.size())));
        }
    }

    // === Initialize Runways ===
    private void initializeRunways() {
        for (int i = 1; i <= 10; i++) {
            final Runway runway = new Runway(i);
            runway.setClear(true); //  Mark runways as available
            runwayManager.addRunway(runway);
        }
    }



    // === Initialize Airplanes (Prototype Pattern) ===
    private void initializeAirplanes() {
        final Airplane basePlane = new Airplane(1, gates.get(0).getGateID(), 10000, destinations.get(0), 100);

        for (int i = 1; i <= 15; i++) {
            final Airplane clonedPlane = basePlane.clone();
            clonedPlane.changeGate(gates.get(i % gates.size()).getGateID());
            clonedPlane.setDestination(destinations.get(i % destinations.size()));

            // **Fix: Assign a unique planeID**
            clonedPlane.setPlaneID(i + 1);

            airplaneManager.addAirplane(clonedPlane);
        }
    }



    // === Initialize Flights ===
    private void initializeFlights() {
        if (airplaneManager.getAirplanes().size() < 15) {
            System.out.println("[ERROR] Not enough airplanes available to initialize flights.");
            return;
        }

        for (int i = 1; i <= 15; i++) {
            final Airplane airplane = airplaneManager.getAirplanes().get(i - 1);
            final Destination destination = destinations.get(i % destinations.size());

            final Flight flight = new Flight(i, airplane, null, destination);
            flight.setFlightStatus(Flight.FlightStatus.ON_TIME); // Ensure flights are ON_TIME
            flightManager.addFlight(flight);
        }
    }



    // === Initialize Passengers ===
    private void initializePassengers() {
        for (int i = 1; i <= 10; i++) {
            final Destination destination = destinations.get(i % destinations.size());
            passengers.add(new Passenger("Passenger" + i, (i % 5), null, destination));
        }
    }

    // === Initialize Security ===
    private void initializeSecurity() {
        for (int i = 1; i <= 3; i++) {
            securityStations.add(new Security(i, true, ATC));
        }
    }

    // === Initialize Check-In System ===
    private void initializeCheckIn() {
        checkIn = new CheckIn(airport, ticketManager, flightManager, ATC);
    }

    private void delayRandomFlights() {
        final Random rand = new Random();
        for (Flight flight : flightManager.getFlights()) {
            if (rand.nextInt(100) < 30) { // 30% chance to delay a flight
                ATC.delayFlight(flight.getFlightID());
            }
        }
    }

    private void handleTakeoffs() {
        for (Flight flight : flightManager.getFlights()) {
            if (flight.getFlightStatus() == Flight.FlightStatus.ON_TIME) {
                final Airplane airplane = flight.getAirplane();

                // Check fuel before takeoff
                if (airplane.getGallonsOfFuel() < airplane.getDestination().getFuelNeeded()) {
                    ATC.update("[ATC] Flight " + flight.getFlightID() + " needs refueling.");
                    ATC.refuelPlane(airplane.getPlaneID());
                }

                // Check maintenance before takeoff
                if (airplane.needsMaintenance()) {
                    ATC.update("[ATC] Flight " + flight.getFlightID() + " sent for maintenance.");
                    airplane.setNeedsMaintenance(false);
                }

                // Assign a runway if available
                final Runway assignedRunway = runwayManager.getOpenRunways().stream().findFirst().orElse(null);
                if (assignedRunway != null) {
                    flight.assignRunway(assignedRunway.getRunwayID());
                    ATC.apTakeoff(flight);
                    runwayManager.freeRunway(assignedRunway.getRunwayID()); // ✅ Free the runway after takeoff
                } else {
                    ATC.update("[ATC] No open runways for Flight " + flight.getFlightID() + ", delaying.");
                    ATC.delayFlight(flight.getFlightID());
                }
            }
        }
    }

    private void handleCheckIns() {
        for (int i = 1; i <= 10; i++) { // Simulating 10 passengers per cycle
            // Ensure passengers are assigned a valid destination
            final Destination assignedDestination = destinations.get(i % destinations.size());
            final Passenger passenger = new Passenger("Passenger" + i, new Random().nextInt(5), null, assignedDestination);

            final Ticket newTicket = checkIn.assignNewTicket(passenger);
            if (newTicket == null) {
                ATC.update("[Check In] No available flights for " + passenger.getName());
                continue; // If no ticket assigned, skip this passenger
            }

            // Process security check
            final Security assignedSecurity = airport.getSecurityByID(newTicket.getSec_station_id());
            if (!assignedSecurity.processPassenger(passenger, newTicket)) {
                ATC.update("[Check In] Passenger " + passenger.getName() + " failed security and cannot board.");
                continue; // If security fails, they don't board
            }

            // Attempt to board flight
            ticketManager.boardPassenger(newTicket.getTicketNum());
        }
    }






    // === Run Simulation Loop ===
    public void runSimulation() {
        System.out.println("\n===== Simulation Starting =====");

        for (int time = 0; time < TOTAL_SIMULATION_TIME; time += TIME_STEP) {
            System.out.println("\n===== Time: " + time + " minutes =====");

            // Display Airport State every 30 minutes
            airport.displayAirportState();

            if (time % 60 == 0) {
                delayRandomFlights();
            }

            handleCheckIns();
            handleTakeoffs();

            // Process Check-In, Security, and Boarding
            for (Passenger passenger : passengers) {
                final Ticket ticket = checkIn.assignNewTicket(passenger);
                if (ticket == null) continue; // Skip if no ticket was assigned

                final Security security = securityStations.get(ticket.getSec_station_id() % securityStations.size());
                final boolean passedSecurity = security.processPassenger(passenger, ticket);
                if (!passedSecurity) continue; // Skip if security failed

                ticketManager.boardPassenger(ticket.getTicketNum());
            }

            // Flights Take Off at the End of Each Cycle
            System.out.println("\n===== Flights Taking Off =====");
            for (Flight flight : flightManager.getFlights()) {
                ATC.apTakeoff(flight);
            }
        }

        System.out.println("\n===== Simulation Completed =====");
    }

    public static void main(String[] args) {
        final SimulationEngine sim = new SimulationEngine();
        sim.runSimulation();
    }
}
