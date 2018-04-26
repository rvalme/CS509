package flight;

import java.util.Comparator;
import airport.Airport;
import driver.Driver;
import utils.TimeConversion;
import airplane.Airplane;
import handler.InputHandler;

public class Flight  implements Comparable<Flight>, Comparator<Flight> {
    String mAirplaneType;           //    airplane type
    int mTravelTime;                //    travel time
    String mFlightNumber;           //    Flight number
    String mDepartureAirport;       //    Departure airport and time (GMT or Local time is OK)
    String mDepartureTime;
    String mArrivalAirport;         //    Arrival airport and time (GMT or local time is OK)
    String mArrivalTime;
    String mDepartureTimeLocal = "";//    Departure time in local time zone
    String mArrivalTimeLocal = "";  //    Arrival time in local time zone
    double mFirstClassPrice;        //    Number of first class seats booked for flight and price of first class seat
    int mFirstClassReserved;
    double mCoachPrice;             //    Number of economy seats booked for flight and price of economy seat
    int mCoachReserved;

    /**
     * Default constructor.
     * Requires all fields to be set manually using setters
     * All attributes are initialized with default values
     *
     */
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

    /**
     * Initializing constructor.
     * All attributes are initialized with input values
     *
     * @param mAirplaneType The type of the airplane
     * @param mTravelTime The travel time of this flight in minutes
     * @param mFlightNumber The flight number
     * @param mDepartureAirport The code of the departure airport
     * @param mDepartureTime The time of the departure in GMT
     * @param mArrivalAirport The code of the arrival airport
     * @param mArrivalTime The time of the arrival in GMT
     * @param mFirstClassPrice Price of first class seating
     * @param mFirstClassReserved Number of first class seats already reserved
     * @param mCoachPrice Price of coach seating
     * @param mCoachReserved Number of coach seats already reserved
     *
     * @throws IllegalArgumentException is any parameter is invalid
     */
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

    /**
     * Convert object to printable string
     * Display departure time and arrival time as local time of corresponding airports
     *
     * @return the object formatted as String to display
     */
    public String toString() {
        Airport departure = Driver.getAirport(mDepartureAirport);
        Airport arrival = Driver.getAirport(mArrivalAirport);

        mDepartureTimeLocal = TimeConversion.INSTANCE.gmtToLocal(mDepartureTime, departure.getmTimeZone());
        mArrivalTimeLocal = TimeConversion.INSTANCE.gmtToLocal(mArrivalTime, arrival.getmTimeZone());
        StringBuffer as = new StringBuffer();

        as.append("Airplane: ").append(mAirplaneType).append(", flight time: ").append(mTravelTime).append(", flight number: ").append(mFlightNumber);
        as.append("\n");
        as.append("Departing ").append(mDepartureAirport).append(" at ").append(mDepartureTimeLocal);
        as.append("\n");
        as.append("Arriving at ").append(mArrivalAirport).append(" at ").append(mArrivalTimeLocal);
        as.append("\n");
        as.append("First class price: ").append(mFirstClassPrice).append(", ").append(mFirstClassReserved).append(" booked");
        as.append("\n");
        as.append("Coach price: ").append(mCoachPrice).append(", ").append(mCoachReserved).append(" booked");

        return as.toString();
    }

    /**
     * Compare this flight object to another flight object by comparing the departure airport
     *
     * @return <0 if this departure airport is before the other departure airport alphabetically
     *         0 if equals
     *         >0 if after
     */
    public int compareTo(Flight other) {
        return this.mDepartureAirport.compareToIgnoreCase(other.mDepartureAirport);
    }

    /**
     * Compare two flight objects
     *
     * @return <0 if departure airport of first flight is before the departure airport of second flight alphabetically
     *         0 if equals
     *         >0 if after
     */
    public int compare(Flight flight1, Flight flight2) {
        return flight1.compareTo(flight2);
    }

    /**
     * get the arrival time in GMT
     *
     * @return the arrival time
     */
    public String getmArrivalTime(){
        return mArrivalTime;
    }

    /**
     * get the departure time in GMT
     *
     * @return the departure time
     */
    public String getmDepartureTime(){
        return mDepartureTime;
    }

    /**
     * get the departure airport code
     *
     * @return the departure airport
     */
    public String getmDepartureAirport() {
        return mDepartureAirport;
    }

    /**
     * get the arrival airport code
     *
     * @return the arrival airport
     */
    public String getmArrivalAirport() {
        return mArrivalAirport;
    }

    /**
     * get the flight number
     *
     * @return the flight number
     */
    public String getmFlightNumber() {
        return mFlightNumber;
    }

    /**
     * get the departure time in local time zone of the departure airport
     *
     * @return the arrival time
     */
    public String getmDepartureTimeLocal() {
        return mDepartureTimeLocal;
    }

    /**
     * get the arrival time in local time zone of the arrival airport
     *
     * @return the arrival time
     */
    public String getmArrivalTimeLocal() {
        return mArrivalTimeLocal;
    }

    /**
     * Check if the required seating type is available for reservation on this flight
     *
     * @return true if available, false if not
     */
    public boolean isSeatAvailable() {
        Airplane airplane = Driver.getAirplane(mAirplaneType);

        if (InputHandler.INSTANCE.getSeat_type().equals("coach")) {
            return (mCoachReserved < airplane.getmCoachSeats());
        }
        else if (InputHandler.INSTANCE.getSeat_type().equals("first class")) {
            return (mFirstClassReserved < airplane.getmFirstClassSeats());
        }

        return false;
    }

    private boolean isValidAirplaneType (String airplaneType) {
        // If the name is null or empty it can't be valid
        if ((airplaneType == null) || (airplaneType == ""))
            return false;
        return true;
    }

    private boolean isValidTravelTime (int travelTime) {
        //Out of range can'' be valid
        if ((travelTime <= 0) || (travelTime > Integer.MAX_VALUE))
            return false;
        return true;
    }

    private boolean isValidFlightNumber (String flightNumber) {
        // If the name is null or empty it can't be valid
        if ((flightNumber == null) || (flightNumber == ""))
            return false;
        return true;
    }

    private boolean isValidDepartureAirport (String departureAirport) {
        // If the name is null or empty it can't be valid
        if ((departureAirport == null) || (departureAirport == ""))
            return false;
        return true;
    }

    private boolean isValidDepartureTime (String departureTime) {
        // If the name is null or empty it can't be valid
        if ((departureTime == null) || (departureTime == ""))
            return false;
        return true;
    }

    private boolean isValidArrivalAirport (String arrivalAirport) {
        // If the name is null or empty it can't be valid
        if ((arrivalAirport == null) || (arrivalAirport == ""))
            return false;
        return true;
    }

    private boolean isValidArrivalTime (String arrivalTime) {
        // If the name is null or empty it can't be valid
        if ((arrivalTime == null) || (arrivalTime == ""))
            return false;
        return true;
    }

    private boolean isValidFirstClassPrice (double firstClassPrice) {
        //Out of range can'' be valid
        if ((firstClassPrice <= 0) || (firstClassPrice > Double.MAX_VALUE))
            return false;
        return true;
    }

    private boolean isValidCoachPrice (double coachPrice) {
        //Out of range can'' be valid
        if ((coachPrice <= 0) || (coachPrice > Double.MAX_VALUE))
            return false;
        return true;
    }

    private boolean isValidFirstClassReserved (int firstClassReserved) {
        //Out of range can'' be valid
        if ((firstClassReserved < 0) || (firstClassReserved > Integer.MAX_VALUE))
            return false;
        return true;
    }

    private boolean isValidCoachReserved (int coachReserved) {
        //Out of range can'' be valid
        if ((coachReserved < 0) || (coachReserved > Integer.MAX_VALUE))
            return false;
        return true;
    }
}
