# DesignPatterns
Assignment 5 SER 316 Spring A

=======Planning===========
Objects: Airport, Airplane, Passenger, Gate, Desk, Ticket, AirControl


Events: Landing/Taking Off; Boarding/Unboarding; Check-In/Check-Out(Baggage?); Open Gate/Closed Gate


Design Patterns: 

Behavioral: 
   Command Pattern: Could use the Ticket object as a command object that holds all the 
information about the Passenger, Airplane, Gate, Airport

   Observer Pattern: Could use the AirControl object as an observer for when the planes have landed, 
taken off, boarded (full), or taxied.

Creational: 
    Prototype Pattern: Could use the prototype pattern to create an interface for making Airplanes,
Gates, or Airports.

**** See PDF for astah class diagrams****

Airport Simulation Pseudocode
//Objects
Airport:
List<Gate> gates;
List<Destinations> destinations;
List<Security> sec_stations;
List<Passenger> passengers;
AirTrafficControl ATC;

//funcs
displayAirportState()
-display:
- # of open/closed gates w/ airplane ids
- # of runways w/ # of planes waiting
-how many passengers in airport
- how many security stations are open
-flight info -> # of delayed, on-time
-destinations travelled to/from
-total number of flights
-flights in progress
-total security checks passed.

     manageGates()
        - open or close gates depending on traffic
     manageSecurity()
        -open or close sec stations
     manageDestinations()
        -add/remove a destination
     assignGate(destination)
        -pick an open gate
     assignSec()
        -return first open sec_station

AirTrafficControl: (OBSERVER)
Airport airport;
AirplaneManager apManager;
FlightManager flManager;
RunwayManager runManager;
TicketManager tickManager;

//funcs
update()
-prints the update from which manager
manageAirplanes()
use apManager
//as planes take off and land ATC can use the managers to adjust open runways, decrease tickets in airport, free flights. Notify Airport(Gates/Passengers?)
apTakeOff()
-a plane takes off
apLand()
- a plane lands
manageRunways()
use runManager
manageFlights()
use flManager
manageTickets()
use tickManager





AirplaneManager:
List<Airplane> airplanes;

getAirplanes()
-return list of Airplanes
notifyObservers()
manageFuel(airplane)
-change/monitor fuel levels
manageRunway(airplane)
-change the runway of an airplane
checkMaintenance(airplane)
- send or release from maintenance
changeGate(airplane)
-manage gate
changeCapacity(airplane)
-add or remove capacity
checkCleared(airplane)
-indicate cleared for takeoff.

TicketManager: (COMMAND)
List<Ticket> tickets

getTicketCount()
-return # of open tickets
manageTickets()
open/close a ticket

changeTicket(ticket id)
-make a change to a ticket
generateNewTicket()
assign a new ticket number
cancelTicket()
-cancel a ticket.

FlightManager:
List<Flight> flights


//funcs

getFlightList()
returns current list
changeFlight(flightID)
change a flight
getDelayed()
return delayed flights
getTraffic()
return # planes landing/taking off
getOnTime()
return # of planes landing on time
modifyFlightRunway()
-change runway of a flight
assignFlight()
-assign a free flight

RunwayManager:

List<Runway> runways;


//funcs
getOpenRunways()
get Avaliable runways
manageRunways()
open/close a runway - notify ATC
getRunwayQueueCount(runway id)
returns # of flights in Queue on runway
getRunwayQueue(runway id)
returns a queue of flights
assignRunway()
assign a runway to a plane/flight
freeRunway()
free a runway for next flight.

Gate: (PROTOTYPE) (Cloneable)
int gateID;
Boolean isOpen;
Destination destination;

Gate clone()
-make a new Gate with this prototype
getGateID()
-return gateID
checkIsOpen()
return isOpen
getDestination(gateID)
return destination;

Airplane: (PROTOTYPE) (Cloneable)
int planeID;
int GateNum;
int gallonsOfFuel;
Destination destination;
int capacity;
Boolean maintenanceNeeded;
Boolean cleared;

Airplane Clone()
-create another airplane
checkFuel()
-return gallonsOfFuel


Passenger:
String name;
int threatLevel;
Ticket ticket;
String destination;
getThreatLevel
return threatLevel
setThreatLevel
set threat level
getName
return name;
getTicketNum
return ticket number

//constructor
Passenger(String name, int ticketNum, destination);


Ticket:
Passenger passenger;
int ticketNum;
int gateNum;
Boolean passSecurity;
Boolean boarded;
int sec_station_id;
int flightID;

//normal getters here for all params.
//setters for gateNum, passSecurity, boarded, flightID ???
//constructor
//do I need passenger in the constructor???
Ticket(passenger, ticketNum, destination, gateNum, sec_station_id, flightID);
- set passSec and boarded to false.

CheckIn:
Airport airport (has to be the same one as ATC)
TicketManager tickManager;
FlightManager flManager;


//constructor
Checkin(tickManager, flManager, airport)
get gateNum, flightID, sec_station_id

CreateTicket() -- returns true if ticket is created.
new Ticket(all params like above)
add to TicketList.


Runway:
int runwayID;
//flightID
int flightOnRunway;
Boolean isClear;
Queue planesWaiting;

clearRunway();
-a plane takes off the runway is clear
->Notify ATC
loadRunway();
-add the next plane for takeoff
-notify ATC
getFlightOnRunway()
-what plane is ready for takeoff, on what runway?
-Notify ATC
removeFromRunwayQueue(plane/flight)
-take out of queue
addToRunwayQueue(plane/flight)
-add a plane to the queue.

Flight:
int flightNum;
String flightStatus = {waiting for gate, boarding, waiting for runway, tookOff, landed};
Airplane airplane;
Ticket ticket;
int runwayID;

//normal getters for all params
//setters for status, plane, destination, and runway
//constructor
new Flight(int flightNum, ticket, airplane)
-find runway w/ ticket info
set status to waiting for gate.

SimulationEngine:
advanceTime
move time forward by 30 min
randomDelays
create random delays
checkFlightStatus
updates flight status.

============Creation===============
Observer: AirTrafficControl Class: This design pattern is useful here because we want AirTrafficControl 
to watch the other Managers and get notified when something changes. This allows this class to make
sure to manage the different classes that actually run the airport.
ProtoType: Airplane, Gate: Gate and Airplane are used as Prototypes because we can clone them and create
multiple Planes and Gates, this design pattern makes it easy to make new objects on the fly.
Command Pattern: Ticket: I chose the Command pattern for the Ticket, so that as the Passenger makes theiR
way around the airport, the Ticket object is issuing the commands and holds the info for the other classes
