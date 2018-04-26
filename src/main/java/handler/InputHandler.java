package handler;
import java.util.Scanner;
import airport.Airports;
import airport.Airport;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Done: Finished reading input capability for user specified options.
// Need to: Validate User data
//          Write script that gets info from user, checks if info is valid, and stores.
public enum InputHandler {

    INSTANCE;

    String departure_airport_code;
    String arrival_airport_code;
    String departure_date;
    String arrival_date;
    String trip_type;
    String seat_type;

    String departTakeOffRange;
    String departLandRange;
    String returnTakeOffRange;
    String returnLandRange;

    Scanner mReader;

    //Getters to get input from outside classes
    public String getDeparture_airport_code() {
        return departure_airport_code;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public String getArrival_airport_code() {
        return arrival_airport_code;
    }

    public String getArrival_date() {
        return arrival_date;
    }

    public String getTrip_type() {
        return trip_type;
    }

    public String getSeat_type() {
        return seat_type;
    }

    public String getDepartTakeOffRange() {
        return departTakeOffRange;
    }

    public String getDepartLandRange() {
        return departLandRange;
    }

    public String getReturnTakeOffRange() {
        return returnTakeOffRange;
    }

    public String getReturnLandRange() {
        return returnLandRange;
    }

    //initializing scanner
    public void initialize() {
        mReader = new Scanner(System.in);  // Reading from System.in
    }

    //closing scanner
    public void close() {
        mReader.close();
    }

    public String readNextInputLine() {
        return mReader.nextLine();
    }

    public void validateDepartingFilter() {
        boolean valid = false;

        System.out.println("Filter departing trips:");

        while (!valid) {
            System.out.println("Take off time range: (HH:MM-HH:MM)");
            String from = InputHandler.INSTANCE.readNextInputLine();

            if (validateTimeRange(from)) {
                departTakeOffRange = from;
                valid = true;
            } else {
                System.out.println("Time not valid");
            }
        }

        valid = false;

        while (!valid) {
            System.out.println("Landing time range: (HH:MM-HH:MM)");
            String to = InputHandler.INSTANCE.readNextInputLine();

            if (validateTimeRange(to)) {
                departLandRange = to;
                valid = true;
            } else {
                System.out.println("Time range not valid");
            }
        }
    }

    public void validateReturningFilter() {
        boolean valid = false;

        System.out.println("Filter returning trips:");

        while (!valid) {
            System.out.println("Take off time range: (HH:MM-HH:MM)");
            String from = InputHandler.INSTANCE.readNextInputLine();

            if (validateTimeRange(from)) {
                returnTakeOffRange = from;
                valid = true;
            } else {
                System.out.println("Time not valid");
            }
        }

        valid = false;

        while (!valid) {
            System.out.println("Landing time range: (HH:MM-HH:MM)");
            String to = InputHandler.INSTANCE.readNextInputLine();

            if (validateTimeRange(to)) {
                returnLandRange = to;
                valid = true;
            } else {
                System.out.println("Time range not valid");
            }
        }
    }

    private boolean validateTimeRange(String timeRange) {
        if (timeRange.length() != 11) {
            return false;
        }

        String[] rangeSplit = timeRange.split("-");

        if (rangeSplit.length != 2) {
            return false;
        }

        String rangeFrom = rangeSplit[0];
        String rangeTo = rangeSplit[1];

        String[] fromSplit = rangeFrom.split(":");
        String[] toSplit = rangeTo.split(":");

        if ((fromSplit.length != 2) || (toSplit.length != 2)) {
            return false;
        }

        int fromHour = 0;
        int fromMin = 0;
        int toHour = 0;
        int toMin = 0;

        try {
            fromHour = Integer.parseInt(fromSplit[0]);
            fromMin = Integer.parseInt(fromSplit[1]);
            toHour = Integer.parseInt(toSplit[0]);
            toMin = Integer.parseInt(toSplit[1]);
        } catch (Exception E) {
            return false;
        }

        if ((0 <= fromHour) && (fromHour <= 23) && (0 <= fromMin) && (fromMin <= 59) && (0<=toHour) && (toHour <= 23) && (0 <= toMin) && (toMin <= 59)) {
            if (fromHour < toHour)
            return true;
            else if ((fromHour == toHour) && (fromMin < toMin)) return true;
        }

        return false;
    }

    public void validateDepartureAirport(Airports airports) {

        boolean found = false;
        while(!found) {
            System.out.println("Enter the departure airport 3-letter code: ");
            String airport_code = mReader.nextLine(); // Scans the line for the string
            for (Airport airport : airports) {
                if (airport.code().equals(airport_code)) {
                    found = true;
                    this.departure_airport_code = airport_code;
                    break;
                }
            }
            if (!found) {
                System.out.println("Airport doesn't exist.");
                System.out.println("Please refer to the list of airports in our database above");
            }
        }
    }

    public void validateArrivalAirport(Airports airports) {

        boolean found = false;
        while(!found) {
            System.out.println("Enter the arrival airport 3-letter code: ");
            String airport_code = mReader.nextLine(); // Scans the line for the string
            if (airport_code.equals(departure_airport_code)) {
                System.out.println("Arrival airport cannot be the same as departure airport");
                continue;
            }
            for (Airport airport : airports) {
                if (airport.code().equals(airport_code)) {
                    found = true;
                    this.arrival_airport_code = airport_code;
                    break;
                }
            }
            if (!found) {
                System.out.println("Airport doesn't exist.");
                System.out.println("Please refer to the list of airports in our database above");
            }
        }
    }

    public void validateDepartureTime() {
        boolean accepted = false;

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy_MM_dd");
        LocalDate localDate = LocalDate.now();
        String currentDate = dtf.format(localDate);

        while(!accepted){
            System.out.println("Enter the desired departure date (e.g. format YYYY_MM_DD): ");
            String dep_date = mReader.nextLine(); // Scans the line for the string.

            String [] sdep_date = dep_date.split("_");
            if(sdep_date.length != 3){
                System.out.println("Improper date format");
                continue;
            }

            if (currentDate.compareTo(dep_date) > 0) {
                System.out.println("The departure date has to be today or later");
                continue;
            }

            int month = Integer.parseInt(sdep_date[1]);
            int day = Integer.parseInt(sdep_date[2]);
            int year = Integer.parseInt(sdep_date[0]);

            //if((month == 5) && (year == 2018) && (day <= 31)){
            if((year == 2018) && (month == 5) && (day <= 31)){
                // has to be between in May
                this.departure_date = dep_date;
                accepted = true;
            }
            else {
                System.out.println("Invalid date");
            }
        }
    }

    public void validateArrivalTime() {
        boolean accepted = false;

        while(!accepted){
            System.out.println("Enter the desired return date (e.g. format YYYY_MM_DD): ");
            String dep_date = mReader.nextLine(); // Scans the line for the string.

            String [] sdep_date = dep_date.split("_");
            if(sdep_date.length != 3){
                System.out.println("Improper date format");
                continue;
            }

            if (dep_date.compareTo(departure_date) < 0) {
                System.out.println("The return date has to be the departure date or later");
                continue;
            }

            int month = Integer.parseInt(sdep_date[1]);
            int day = Integer.parseInt(sdep_date[2]);
            int year = Integer.parseInt(sdep_date[0]);

            if((year == 2018) && (month == 5) && (day <= 31)){
                // has to be between in May
                this.arrival_date = dep_date;
                accepted = true;
            }
            else {
                System.out.println("Invalid date");
            }
        }
    }

    public void validateTripType(){
        int found_trip_type = 0;
        while(found_trip_type == 0) {
            System.out.println("Enter the desired trip type (round trip or one way): ");
            String t_type = mReader.nextLine(); // Scans the line for the string.
            //compare all airports to make sure entered string is in the list
            if (t_type.equals("round trip") || t_type.equals("one way")) {
                    found_trip_type = 1;
                    this.trip_type = t_type; // Scans the line for the string.
            }
            else {
                System.out.println("Trip type not found!");
            }
        }
   }

    public void validateDepartureDate(){
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        int found_dep_date = 0;
        while(found_dep_date == 0){
            System.out.println("Enter the desired departure date (e.g. format 05_05_2018): ");
            String dep_date = reader.nextLine(); // Scans the line for the string.
            String [] sdep_date = dep_date.split("_");
            if(sdep_date.length != 3){
                System.out.println("Improper date format");
                continue;
            }
            int month = Integer.parseInt(sdep_date[0]);
            int day = Integer.parseInt(sdep_date[1]);
            int year = Integer.parseInt(sdep_date[2]);
            if(month == 5 && year == 2018 && day <= 31){
                // has to be between in May
                this.departure_date = dep_date;
                found_dep_date = 1;
            }
            if (found_dep_date == 0) {
                System.out.println("Date out of range");
            }
        }
        reader.close();
    }

    public void validateArrivalDate(){
        int found_arr_date = 0;
        while(found_arr_date == 0){
            System.out.println("Enter the desired arrival date (e.g. format 05_05_2018): ");
            String arr_date = mReader.nextLine(); // Scans the line for the string.
            String [] sarr_date = arr_date.split("_");
            if(sarr_date.length != 3){
                System.out.println("Improper date format");
                continue;
            }
            int month = Integer.parseInt(sarr_date[0]);
            int day = Integer.parseInt(sarr_date[1]);
            int year = Integer.parseInt(sarr_date[2]);
            if(month == 5 && year == 2018 && day <= 31){
                // has to be between in May
                this.arrival_date = arr_date;
                found_arr_date = 1;
            }
            if (found_arr_date == 0) {
                System.out.println("Date out of range");
            }
        }
    }

    public void validateSeatType(){
        int found_seat_type = 0;
        while(found_seat_type == 0) {
            System.out.println("Enter the desired seat type (coach or first class): ");
            String s_type = mReader.nextLine(); // Scans the line for the string.
            if (s_type.equals("coach") || s_type.equals("first class")) {
                found_seat_type = 1;
                this.seat_type = s_type;
            }
            if (found_seat_type == 0) {
                System.out.println("Seat type not found!");
            }
        }
    }


}
