package flight;

import handler.InputHandler;

import java.util.ArrayList;

public class Trip extends ArrayList<Flight> {
    private static final long serialVersionUID = 4L;

    //a valid trip object has layover time between legs no less than 30 mins and no more than 3 hours
    private boolean isValid = true;
    //total travel time from first take off to last landing
    private int totalTravelTime = 0;
    //total price of the user specified seating type
    private double totalPrice = 0;

    /**
     * Whether this is a valid trip or not
     *
     * @return true if this trip is a valid trip, false otherwise
     */
    public boolean isValid() {
        return isValid;
    }

    /**
     * Get the total travel time for this trip
     *
     * @return the total travel time in minutes
     */
    public int getTotalTravelTime() {
        return totalTravelTime;
    }

    /**
     * Get the total price for the seating type the user wants
     *
     * @return the total price
     */
    public double getTotalPrice() {
        return totalPrice;
    }

    /**
     * Get xmlFlight string required to reserve all flights inside this trip
     *
     * @return the xml formatted string needed
     */
    public String reserveString() {
        String returnString = "<Flights>";

        String seatType;
        if (InputHandler.INSTANCE.getSeat_type().equals("coach")) seatType = "Coach";
        else seatType = "FirstClass";

        for (Flight flight: this) {
            returnString += "<Flight number=\"" + flight.getmFlightNumber() + "\" seating=\"" + seatType + "\"/>";
        }

        returnString += "</Flights>";

        System.out.println(returnString);
        return returnString;
    }

    /**
     * Convert object to printable string
     * Display the total travel time, the total price as long as all legs in this trip
     *
     * @return the object formatted as String to display
     */
    public String toString() {
        StringBuffer as = new StringBuffer();

        as.append("Total travel time: ").append(totalTravelTime).append(" minutes\n");
        as.append("Total price: $").append(totalPrice).append("\n");
        for (int i = 0; i < this.size(); i++) {
            as.append("---------------------\n");
            as.append("Leg number ").append(i+1).append(": \n");
            as.append(get(i).toString());
            as.append("\n");
        }

        return as.toString();
    }

    /**
     * Add the next leg to this trip, updating the total travel and total price
     * Calculate the layover time and determine if the trip is valid or not accordingly
     *
     * @return true if added successfully, false otherwise
     */
    @Override
    public boolean add(Flight flight) {
        if (size() > 0) {
            Flight lastLeg = get(size()-1);
            int layover = getLayoverTime(lastLeg, flight);
            if ((layover < 30) || (layover > 180)) isValid = false;
        }

        boolean result = super.add(flight);

        totalTravelTime = getDurationBetween(get(0).mDepartureTime, get(size()-1).mArrivalTime);

        if (InputHandler.INSTANCE.getSeat_type().equals("coach")) {
            totalPrice += flight.mCoachPrice;
        }
        else {
            totalPrice += flight.mFirstClassPrice;
        }

        return result;
    }

    private int getLayoverTime(Flight first, Flight second) {
        String land = first.getmArrivalTime();
        String takeoff = second.getmDepartureTime();

        int difference = getDurationBetween(land, takeoff);

//        System.out.println(land);
//        System.out.println(takeoff);
//        System.out.println(difference);

        return difference;
    }

    private int getDurationBetween(String timeFirst, String timeSecond) {
        int first = getTimeSinceStartOfMay(timeFirst);
        int second = getTimeSinceStartOfMay(timeSecond); //time since start of May

        int difference = second - first;

//        System.out.println(land);
//        System.out.println(takeoff);
//        System.out.println(difference);

        return difference;
    }

    private int getTimeSinceStartOfMay (String GMTTimeString) {
        String[] timeSplit = GMTTimeString.split(" ");
        int day = Integer.parseInt(timeSplit[2]);
        if (timeSplit[1].equalsIgnoreCase("Jun")) day += 31;
        String[] HourMin = timeSplit[3].split(":");
        int hour = Integer.parseInt(HourMin[0]);
        int min = Integer.parseInt(HourMin[1]);
        int result = ((day * 24) + hour) * 60 + min;
        return result;
    }
}