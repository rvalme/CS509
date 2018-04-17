package flight;

import java.util.ArrayList;

public class Trip extends ArrayList<Flight> {
    private static final long serialVersionUID = 4L;
    private boolean isValid = true;

    public boolean isValid() {
        return isValid;
    }

    public String toString() {
        StringBuffer as = new StringBuffer();

        for (int i = 0; i < this.size(); i++) {
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
        return result;
    }

    private int getLayoverTime(Flight first, Flight second) {
        String land = first.getmArrivalTime();
        String takeoff = second.getmDepartureTime();

        int landTime = getTimeSinceStartOfMay(land);
        int takeOffTime = getTimeSinceStartOfMay(takeoff); //time since start of May

        int difference = takeOffTime - landTime;

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