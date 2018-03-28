package com.company.airplane;

import java.util.Comparator;

public class Airplane implements Comparable<Airplane>, Comparator<Airplane> {

    /**
     * Airplane attributes as defined by the CS509 server interface XML
     */
    private String mManufacturer;       // Name of the manufacturer
    private String mModel;              // The model code
    private int mFirstClassSeats;       // Number of first class seats
    private int mCoachSeats;         // Number of coach seats

    /**
     * Default constructor
     *
     * Constructor without params. Requires object fields to be explicitly
     * set using setter methods
     *
     * @pre None
     * @post member attributes are initialized to invalid default values
     */
    public Airplane () {
        mManufacturer = "";
        mModel = "";
        mFirstClassSeats = Integer.MAX_VALUE;
        mCoachSeats = Integer.MAX_VALUE;
    }

    /**
     * Initializing constructor.
     *
     * All attributes are initialized with input values
     *
     * @param manufacturer Name of the manufacturer
     * @param model The model code
     * @param firstClassSeats Number of first class seats
     * @param coachSeats Number of coach seats
     *
     * @pre
     * @post
     * @throws IllegalArgumentException is any parameter is invalid
     */
    public Airplane (String manufacturer, String model, int firstClassSeats, int coachSeats) {
        if (!isValidManufacturer(manufacturer))
            throw new IllegalArgumentException (manufacturer);
        if (!isValidModel(model))
            throw new IllegalArgumentException(model);
        if (!isValidSeats(firstClassSeats))
            throw new IllegalArgumentException(Double.toString(firstClassSeats));
        if (!isValidSeats(coachSeats))
            throw new IllegalArgumentException(Double.toString(coachSeats));

        mManufacturer = manufacturer;
        mModel = model;
        mFirstClassSeats = firstClassSeats;
        mCoachSeats = coachSeats;
    }

    /**
     * Convert object to printable string
     *
     * @return the object formatted as String to display
     */
    public String toString() {
        StringBuffer as = new StringBuffer();

        as.append(mManufacturer).append(", model ");
        as.append(mModel).append(", ");
        as.append(Integer.toString(mFirstClassSeats)).append(" first class seats, ");
        as.append(Integer.toString(mCoachSeats)).append(" coach seats.");

        return as.toString();
    }

    /**
     * Compare two airplanes based on the name of their manufacturers
     *
     * This implementation delegates to the case insensitive version of string compareTo
     * @return results of String.compareToIgnoreCase
     */
    public int compareTo(Airplane other) {
        return this.mManufacturer.compareToIgnoreCase(other.mManufacturer);
    }

    /**
     * Compare two airplanes for sorting, ordering
     *
     * @param airplane1 the first airplane for comparison
     * @param airplane2 the second / other airplane for comparison
     * @return -1 if airport ordered before airport2, +1 of airport1 after airport2, zero if no different in order
     */
    public int compare(Airplane airplane1, Airplane airplane2) {
        return airplane1.compareTo(airplane2);
    }


    //Getters and Setters
    public String getmManufacturer() {
        return mManufacturer;
    }

    public void setmManufacturer(String manufacturer) {
        if (isValidManufacturer(manufacturer))
            mManufacturer = manufacturer;
        else
            throw new IllegalArgumentException (manufacturer);
    }

    public String getmModel() {
        return mModel;
    }

    public void setmModel(String model) {
        if (isValidModel(model))
            mModel = model;
        else
            throw new IllegalArgumentException (model);
    }

    public int getmFirstClassSeats() {
        return mFirstClassSeats;
    }

    public void setmFirstClassSeats(int firstClassSeats) {
        if (isValidSeats(firstClassSeats))
            mFirstClassSeats = firstClassSeats;
        else
            throw new IllegalArgumentException (Integer.toString(firstClassSeats));
    }

    public double getmCoachSeats() {
        return mCoachSeats;
    }

    public void setmCoachSeats(int coachSeats) {
        if (isValidSeats(coachSeats))
            mCoachSeats = coachSeats;
        else
            throw new IllegalArgumentException (Integer.toString(coachSeats));
    }

    //End of getters and setters


    /**
     * Check for invalid airplane manufacturer.
     *
     * @param manufacturer is the manufacturer of the airplane
     * @return false if null or empty string, else assume valid and return true
     */
    public boolean isValidManufacturer (String manufacturer) {
        // If the name is null or empty it can't be valid
        if ((manufacturer == null) || (manufacturer == ""))
            return false;
        return true;
    }

    /**
     * Check for invalid airplane model.
     *
     * @param model is the model of the airplane
     * @return false if null or empty string, else assume valid and return true
     */
    public boolean isValidModel (String model) {
        // If the name is null or empty it can't be valid
        if ((model == null) || (model == ""))
            return false;
        return true;
    }

    /**
     * Check for invalid number of seats.
     *
     * @param seats is the number of seats (either first class or coach)
     * @return false if out of range, else assume valid and return true
     */
    public boolean isValidSeats (int seats) {
        // If the name is null or empty it can't be valid
        if ((seats < 0) || (seats > Integer.MAX_VALUE))
            return false;
        return true;
    }
}
