package flight;

import handler.InputHandler;

import java.util.ArrayList;

public class Trip extends ArrayList<Flight> {
    private static final long serialVersionUID = 4L;

    private boolean isValid = true;
    private int totalTravelTime = 0;
    private double totalPrice = 0;

    public boolean isValid() {
        return isValid;
    }

    public int getTotalTravelTime() {
        return totalTravelTime;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

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