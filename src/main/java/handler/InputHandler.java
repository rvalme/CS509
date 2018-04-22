package handler;
import java.util.Scanner;
import airport.Airports;
import airport.Airport;
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

    //initializing scanner
    public void initialize() {
        mReader = new Scanner(System.in);  // Reading from System.in
    }

    //closing scanner
    public void close() {
        mReader.close();
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
        }
    }

    public void validateDepartureTime() {
        boolean accepted = false;
        while(!accepted){
            System.out.println("Enter the desired departure date (e.g. format YYYY_MM_DD): ");
            String dep_date = mReader.nextLine(); // Scans the line for the string.
            String [] sdep_date = dep_date.split("_");
            if(sdep_date.length != 3){
                System.out.println("Improper date format");
                continue;
            }
            int month = Integer.parseInt(sdep_date[1]);
            int day = Integer.parseInt(sdep_date[2]);
            int year = Integer.parseInt(sdep_date[0]);

            //if((month == 5) && (year == 2018) && (day <= 31)){
            if((year == 2018) && (day <= 31)){
                // has to be between in May
                this.departure_date = dep_date;
                accepted = true;
            }
        }

    }

    public void validateAirportCode(Airports airports){
        // need the list of airports
        // check what information is returned by the airport
        // run read from here
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        int found_equal_dep_air = 0;
        int found_equal_arr_air = 0;
        while(found_equal_dep_air == 0) {
            System.out.println("Enter the departure airport code number: ");
            String dep_airport_code = reader.nextLine(); // Scans the line for the string.
            //compare all airports to make sure entered string is in the list
            for (Airport airport : airports) {
                //TODO MAKE SURE THE AIRPORTS CANT EQUAL
                if (airport.code().equals(dep_airport_code)) {
                    found_equal_dep_air = 1;
                    this.departure_airport_code = dep_airport_code;
                }
            }
            if (found_equal_dep_air == 0) {
                System.out.println("Airport not found!");
            }
        }
        //once finished
        while(found_equal_arr_air == 0) {
            System.out.println("Enter the arrival airport code number: ");
            String arr_airport_code = reader.nextLine(); // Scans the line for the string.
            //compare all airports to make sure entered string is in the list
            for (Airport airport : airports) {
                if (airport.code().equals(arr_airport_code)) {
                    found_equal_arr_air = 1;
                    this.arrival_airport_code = arr_airport_code;
                }
            }
            if (found_equal_arr_air == 0) {
                System.out.println("Airport not found!");
            }
        }
        reader.close();
        //System.out.println(this.arrival_airport_code);
        //System.out.println(this.departure_airport_code);
}

    public void validateTripType(){
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        int found_trip_type = 0;
        while(found_trip_type == 0) {
            System.out.println("Enter the desired trip type (round trip or one-way): ");
            String t_type = reader.nextLine(); // Scans the line for the string.
            //compare all airports to make sure entered string is in the list
            if (t_type.equals("round trip") || t_type.equals("one-way")) {
                    found_trip_type = 1;
                    this.trip_type = t_type; // Scans the line for the string.
                }
            if (found_trip_type == 0) {
                System.out.println("Trip type not found!");
            }
        }
        reader.close();
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
        Scanner reader = new Scanner(System.in);  // Reading from System.in
        int found_arr_date = 0;
        while(found_arr_date == 0){
            System.out.println("Enter the desired arrival date (e.g. format 05_05_2018): ");
            String arr_date = reader.nextLine(); // Scans the line for the string.
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
        reader.close();
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
