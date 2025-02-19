# DesignPatterns
Assignment 5 SER 316 Spring A
Objects: Airport, Airplane, Passenger, Gate, Desk, Ticket, AirControl
Events: Landing/Taking Off; Boarding/Unboarding; Check-In/Check-Out(Baggage?); Open Gate/Closed Gate
Design Patterns: 
Behavioral: Command Pattern: Could use the Ticket object as a command object that holds all the 
information about the Passenger, Airplane, Gate, Airport
Observer Pattern: Could use the AirControl object as an observer for when the planes have landed, 
taken off, boarded (full), or taxied. 
Structural: Composite Pattern: Could use a heirarchy to hold Airport as the head which holds all 
objects. 
Creational: Prototype Pattern: Could use the prototype pattern to create an interface for making Airplanes,
Gates, or Airports.
