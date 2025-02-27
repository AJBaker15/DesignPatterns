package main;


public class Gate implements Cloneable{

    int gateID;
    boolean gateIsOpen;
    Destination destination;

    //constructor
    public Gate(int gateID, boolean open, Destination destination) {
        this.gateID = gateID;
        this.destination = destination;
        this.gateIsOpen = open;
    }

    public int getGateID(){
        return gateID;
    }

    public void setGateID(int gateID) {
        this.gateID = gateID;
    }

    public  boolean isOpen(){
        return gateIsOpen;
    }

    public void setGateIsOpen(boolean gateIsOpen) {
        this.gateIsOpen = gateIsOpen;
    }

    Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    @Override
    public Gate clone() {
        try {
            Gate clone = (Gate) super.clone();
            clone.gateIsOpen = true;
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
