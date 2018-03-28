package com.company.flight;

import java.util.Comparator;

public class Flight  implements Comparable<Flight>, Comparator<Flight> {
    String mAirplaneType;           //    airplane type
    int mTravelTime;                //    travel time
    String mFlightNumber;           //    Flight number
    String mDepartureAirport;       //    Departure airport and time (GMT or Local time is OK)
    String mDepartureTime;
    String mArrivalAirport;         //    Arrival airport and time (GMT or local time is OK)
    String mArrivalTime;
    double mFirstClassPrice;        //    Number of first class seats booked for flight and price of first class seat
    int mFirstClassReserved;
    double mCoachPrice;             //    Number of economy seats booked for flight and price of economy seat
    int mCoachReserved;

    public Flight () {
        mAirplaneType = "";
        mTravelTime = Integer.MAX_VALUE;
        mFlightNumber = "";
        mDepartureAirport = "";
        mDepartureTime = "";
        mArrivalAirport = "";
        mArrivalTime = "";
        mFirstClassPrice = Double.MAX_VALUE;
        mFirstClassReserved = Integer.MAX_VALUE;
        mCoachPrice = Double.MAX_VALUE;
        mCoachReserved = Integer.MAX_VALUE;
    }

    public Flight(String mAirplaneType,
            int mTravelTime,
            String mFlightNumber,
            String mDepartureAirport,
            String mDepartureTime,
            String mArrivalAirport,
            String mArrivalTime,
            double mFirstClassPrice,
            int mFirstClassReserved,
            double mCoachPrice,
            int mCoachReserved) {

        if (!isValidAirplaneType (mAirplaneType))
            throw new IllegalArgumentException (mAirplaneType);
        if (!isValidTravelTime (mTravelTime))
            throw new IllegalArgumentException (Integer.toString(mTravelTime));
        if (!isValidFlightNumber (mFlightNumber))
            throw new IllegalArgumentException (mFlightNumber);
        if (!isValidDepartureAirport (mDepartureAirport))
            throw new IllegalArgumentException (mDepartureAirport);
        if (!isValidDepartureTime (mDepartureTime))
            throw new IllegalArgumentException (mDepartureTime);
        if (!isValidArrivalAirport (mArrivalAirport))
            throw new IllegalArgumentException (mArrivalAirport);
        if (!isValidArrivalTime (mArrivalTime))
            throw new IllegalArgumentException (mArrivalTime);
        if (!isValidFirstClassPrice (mFirstClassPrice))
            throw new IllegalArgumentException (Double.toString(mFirstClassPrice));
        if (!isValidCoachPrice (mCoachPrice))
            throw new IllegalArgumentException (Double.toString(mCoachPrice));
        if (!isValidFirstClassReserved (mFirstClassReserved))
            throw new IllegalArgumentException (Integer.toString(mFirstClassReserved));
        if (!isValidCoachReserved (mCoachReserved))
            throw new IllegalArgumentException (Integer.toString(mCoachReserved));


        this.mAirplaneType = mAirplaneType;
        this.mTravelTime = mTravelTime;
        this.mFlightNumber = mFlightNumber;
        this.mDepartureAirport = mDepartureAirport;
        this.mDepartureTime = mDepartureTime;
        this.mArrivalAirport = mArrivalAirport;
        this.mArrivalTime = mArrivalTime;
        this.mFirstClassPrice = mFirstClassPrice;
        this.mFirstClassReserved = mFirstClassReserved;
        this.mCoachPrice = mCoachPrice;
        this.mCoachReserved = mCoachReserved;
    }

    public String toString() {
        StringBuffer as = new StringBuffer();

        as.append("Airplane: ").append(mAirplaneType).append(", flight time: ").append(mTravelTime).append(", flight number: ").append(mFlightNumber);
        as.append("\n");
        as.append("Departing ").append(mDepartureAirport).append(" at ").append(mDepartureTime);
        as.append("\n");
        as.append("Arriving at ").append(mArrivalAirport).append(" at ").append(mArrivalTime);
        as.append("\n");
        as.append("First class price: ").append(mFirstClassPrice).append(", ").append(mFirstClassReserved).append(" booked");
        as.append("\n");
        as.append("Coach price: ").append(mCoachPrice).append(", ").append(mCoachReserved).append(" booked");

        return as.toString();
    }

    public int compareTo(Flight other) {
        return this.mDepartureAirport.compareToIgnoreCase(other.mDepartureAirport);
    }

    public int compare(Flight flight1, Flight flight2) {
        return flight1.compareTo(flight2);
    }


    public boolean isValidAirplaneType (String airplaneType) {
        // If the name is null or empty it can't be valid
        if ((airplaneType == null) || (airplaneType == ""))
            return false;
        return true;
    }

    public boolean isValidTravelTime (int travelTime) {
        //Out of range can'' be valid
        if ((travelTime <= 0) || (travelTime > Integer.MAX_VALUE))
            return false;
        return true;
    }

    public boolean isValidFlightNumber (String flightNumber) {
        // If the name is null or empty it can't be valid
        if ((flightNumber == null) || (flightNumber == ""))
            return false;
        return true;
    }

    public boolean isValidDepartureAirport (String departureAirport) {
        // If the name is null or empty it can't be valid
        if ((departureAirport == null) || (departureAirport == ""))
            return false;
        return true;
    }

    public boolean isValidDepartureTime (String departureTime) {
        // If the name is null or empty it can't be valid
        if ((departureTime == null) || (departureTime == ""))
            return false;
        return true;
    }

    public boolean isValidArrivalAirport (String arrivalAirport) {
        // If the name is null or empty it can't be valid
        if ((arrivalAirport == null) || (arrivalAirport == ""))
            return false;
        return true;
    }

    public boolean isValidArrivalTime (String arrivalTime) {
        // If the name is null or empty it can't be valid
        if ((arrivalTime == null) || (arrivalTime == ""))
            return false;
        return true;
    }

    public boolean isValidFirstClassPrice (double firstClassPrice) {
        //Out of range can'' be valid
        if ((firstClassPrice <= 0) || (firstClassPrice > Double.MAX_VALUE))
            return false;
        return true;
    }

    public boolean isValidCoachPrice (double coachPrice) {
        //Out of range can'' be valid
        if ((coachPrice <= 0) || (coachPrice > Double.MAX_VALUE))
            return false;
        return true;
    }

    public boolean isValidFirstClassReserved (int firstClassReserved) {
        //Out of range can'' be valid
        if ((firstClassReserved < 0) || (firstClassReserved > Integer.MAX_VALUE))
            return false;
        return true;
    }

    public boolean isValidCoachReserved (int coachReserved) {
        //Out of range can'' be valid
        if ((coachReserved < 0) || (coachReserved > Integer.MAX_VALUE))
            return false;
        return true;
    }
}
