package driver; /**
 * 
 */

import java.util.Collections;

import airport.Airport;
import airport.Airports;
import airplane.Airplane;
import airplane.Airplanes;
import flight.Flight;
import flight.Flights;
import flight.Trip;
import flight.Trips;
import dao.DaoTrip;
import dao.ServerInterface;
import handler.InputHandler;
import utils.TimeConversion;
import java.util.Comparator;
import java.util.Scanner;

/**
 * @author blake
 *
 */
//Need to: InputHandler asking user to input data in sequence
public class Driver {

	private static String teamName;
	private static Airports airports;
	private static Airplanes airplanes;
    private static Comparator<Trip> durationSort, departureTimeSort, arrivalTimeSort, priceSort;

    private static Trips departTrips = new Trips();
    private static Trips returnTrips = new Trips();

    private static boolean filtering;

	/**
	 * Entry point for CS509 sample code driver
	 * 
	 * This driver will retrieve the list of airports from the CS509 server and print the list 
	 * to the console sorted by 3 character airport code
	 * 
	 * @param args is the arguments passed to java vm in format of "CS509.sample teamName" where teamName is a valid team
	 */
	public static void main(String[] args) {

		//TODO list of airports validate airport codes or strings???
/*		if (args.length != 1) {
			System.err.println("usage: CS509.sample teamName");
			System.exit(-1);
			return;
		}*/
		teamName = "Man-Month";
		System.out.println("Please wait for system initializing");
		// Try to get a list of airports
		airports = ServerInterface.INSTANCE.getAirports(teamName);
		Collections.sort(airports);
		for (Airport airport : airports) {
			System.out.println(airport.toString());
		}

		airplanes = ServerInterface.INSTANCE.getAirplanes(teamName);
		Collections.sort(airplanes);

        durationSort = new Comparator<Trip> () {
            @Override
            public int compare(Trip trip1, Trip trip2) {
                return trip1.getTotalTravelTime() - trip2.getTotalTravelTime();
            }
        };

        departureTimeSort = new Comparator<Trip> () {
            @Override
            public int compare(Trip trip1, Trip trip2) {
                return trip1.get(0).getmDepartureTime().compareToIgnoreCase(trip2.get(0).getmDepartureTime());
            }
        };

        arrivalTimeSort = new Comparator<Trip> () {
            @Override
            public int compare(Trip trip1, Trip trip2) {
                return trip1.get(trip1.size()-1).getmArrivalTime().compareToIgnoreCase(trip2.get(trip2.size()-1).getmArrivalTime());
            }
        };

        priceSort = new Comparator<Trip> () {
            @Override
            public int compare(Trip trip1, Trip trip2) {
                return (int)(trip1.getTotalPrice() - trip2.getTotalPrice());
            }
        };

		InputHandler input = InputHandler.INSTANCE;
        input.initialize();
        input.validateDepartureAirport(airports);
        input.validateArrivalAirport(airports);
        input.validateSeatType();
        input.validateTripType();
        input.validateDepartureTime();
        if (input.getTrip_type().equals("round trip")) {
            input.validateArrivalTime();
        }
        //input.close();

		departTrips = DaoTrip.getTripsFromParameters(teamName, input.getDeparture_airport_code(), input.getArrival_airport_code(), input.getDeparture_date());

        if (input.getTrip_type().equals("round trip")) {
            returnTrips = DaoTrip.getTripsFromParameters(teamName, input.getArrival_airport_code(), input.getDeparture_airport_code(), input.getArrival_date());
        }

        if (departTrips.size() == 0) {
            System.out.println("No departing trips found");
            return;
        }

        displayTrips();

        boolean displayingTrips = true;

        while (displayingTrips) {

            System.out.println("Choose your option then press Enter:");
            System.out.println("1 - Sort trips by total travel time");
            System.out.println("2 - Sort trips by departure time");
            System.out.println("3 - Sort trips by arrival time");
            System.out.println("4 - Sort trips by total price");
            System.out.println("5 - Filter trips with time windows");
            System.out.println("6 - Remove filtering");
            System.out.println("7 - Reserve trips");

            String option = InputHandler.INSTANCE.readNextInputLine();

            switch (option) {
                case "1":
                    Collections.sort(departTrips, durationSort);
                    if (input.getTrip_type().equals("round trip")) {
                        Collections.sort(returnTrips, durationSort);
                    }
                    displayTrips();
                    break;
                case "2":
                    Collections.sort(departTrips, departureTimeSort);
                    if (input.getTrip_type().equals("round trip")) {
                        Collections.sort(returnTrips, departureTimeSort);
                    }
                    displayTrips();
                    break;
                case "3":
                    Collections.sort(departTrips, arrivalTimeSort);
                    if (input.getTrip_type().equals("round trip")) {
                        Collections.sort(returnTrips, arrivalTimeSort);
                    }
                    displayTrips();
                    break;
                case "4":
                    Collections.sort(departTrips, priceSort);
                    if (input.getTrip_type().equals("round trip")) {
                        Collections.sort(returnTrips, priceSort);
                    }
                    displayTrips();
                    break;
                case "5":
                    filtering = true;
                    InputHandler.INSTANCE.validateDepartingFilter();
                    if (InputHandler.INSTANCE.getTrip_type().equals("round trip")) {
                        InputHandler.INSTANCE.validateReturningFilter();
                    }
                    displayTrips();
                    break;
                case "6":
                    filtering = false;
                    displayTrips();
                    break;
                case "7":
                    displayingTrips = false;
                    break;
                default:
                    System.out.println("Invalid option");
                    break;
            }


        }

        makeReservation();

	}
    /**
     * Update the database with a reservation for a number of departing and returning Trips
     *
     *
     */
	private static void makeReservation() {
        int departTripNum = 0;
        int returnTripNum = 0;
        boolean orderValid = false;

        while(!orderValid) {
            boolean valid = false;
            while (!valid) {
                System.out.println("Type the departing trip number you want to reserve: ");
                String tripNumber = InputHandler.INSTANCE.readNextInputLine();
                try {
                    departTripNum = Integer.parseInt(tripNumber);
                } catch (Exception e) {
                    System.out.println("Trip number not valid");
                    continue;
                }
                if ((0<departTripNum) && (departTripNum <= departTrips.size())) {
                    valid = true;
                    System.out.println("You selected:");
                    System.out.println(departTrips.get(departTripNum-1).toString());
                }
                else
                    System.out.println("Trip number not valid");
            }

            if (InputHandler.INSTANCE.getTrip_type().equals("round trip")) {
                valid = false;

                while (!valid) {
                    System.out.println("Type the returning trip number you want to reserve: ");
                    String tripNumber = InputHandler.INSTANCE.readNextInputLine();
                    try {
                        returnTripNum = Integer.parseInt(tripNumber);
                    } catch (Exception e) {
                        System.out.println("Trip number not valid");
                        continue;
                    }
                    if ((0<returnTripNum) && (returnTripNum <= returnTrips.size())) {
                        valid = true;
                        System.out.println("You selected:");
                        System.out.println(returnTrips.get(returnTripNum-1).toString());
                    }
                    else
                        System.out.println("Trip number not valid");
                }

                Trip departTrip = departTrips.get(departTripNum-1);
                Trip returnTrip = returnTrips.get(returnTripNum-1);
                if (departTrip.get(departTrip.size()-1).getmArrivalTime().compareTo(returnTrip.get(returnTrip.size()-1).getmDepartureTime()) < 0) orderValid = true;
                else {
                    System.out.println("You cannot return before you land at your destination");
                }
            } else {
                orderValid = true;
            }

            System.out.println("Type \"Yes\" to confirm your reservation");
            System.out.println("Or anything else to enter different trips to reserve");
            String answer = InputHandler.INSTANCE.readNextInputLine();
            if (!answer.equals("Yes")) orderValid = false;
        }

        ServerInterface.INSTANCE.lock(teamName);

        Trip departTrip = departTrips.get(departTripNum-1);
        ServerInterface.INSTANCE.reserve(teamName, departTrip.reserveString());
        if (InputHandler.INSTANCE.getTrip_type().equals("round trip")) {
            Trip returnTrip = returnTrips.get(returnTripNum-1);
            ServerInterface.INSTANCE.reserve(teamName, returnTrip.reserveString());
        }

        ServerInterface.INSTANCE.unlock(teamName);

    }

    /**
     * Display the available trips for the user to select.
     *
     *
     */
	private static void displayTrips() {
        System.out.println("*-----------------------------------------------------*");
        System.out.println("|                   DEPARTING TRIPS                   |");
        System.out.println("*-----------------------------------------------------*\n");
        String takeOffRange, landRange;
        boolean displaying;

        for(Trip trip: departTrips) {
            displaying = true;
            if (filtering) {
                takeOffRange = InputHandler.INSTANCE.getDepartTakeOffRange();
                landRange = InputHandler.INSTANCE.getDepartLandRange();

                String[] takeOffRangeSplit = takeOffRange.split("-");
                String[] landRangeSplit = landRange.split("-");

                String takeOffTime = (trip.get(0).getmDepartureTimeLocal().split(" "))[3];
                String landTime = (trip.get(trip.size() - 1).getmArrivalTimeLocal().split(" "))[3];

                if (takeOffTime.length() < 5) {
                    takeOffTime = "0" + takeOffTime;
                }

                if (landTime.length() < 5) {
                    landTime = "0" + landTime;
                }

                if ((takeOffTime.compareTo(takeOffRangeSplit[0]) < 0) ||
                        (takeOffTime.compareTo(takeOffRangeSplit[1]) > 0) ||
                        (landTime.compareTo(landRangeSplit[0]) < 0) ||
                        (landTime.compareTo(landRangeSplit[1]) > 0)) displaying = false;
            }
            if (displaying) {
                System.out.print("~~~ DEPARTING TRIP NUMBER " + (departTrips.indexOf(trip)+1) + " ~~~\n");
                System.out.println(trip.toString());
                System.out.print("\n");
            }
        }

        if (InputHandler.INSTANCE.getTrip_type().equals("round trip")) {
            System.out.println("*-----------------------------------------------------*");
            System.out.println("|                   RETURNING TRIPS                   |");
            System.out.println("*-----------------------------------------------------*\n");
            for(Trip trip: returnTrips) {
                displaying = true;
                if (filtering) {
                    takeOffRange = InputHandler.INSTANCE.getReturnTakeOffRange();
                    landRange = InputHandler.INSTANCE.getReturnLandRange();

                    String[] takeOffRangeSplit = takeOffRange.split("-");
                    String[] landRangeSplit = landRange.split("-");

                    String takeOffTime = (trip.get(0).getmDepartureTimeLocal().split(" "))[3];
                    String landTime = (trip.get(trip.size() - 1).getmArrivalTimeLocal().split(" "))[3];

                    if (takeOffTime.length() < 5) {
                        takeOffTime = "0" + takeOffTime;
                    }

                    if (landTime.length() < 5) {
                        landTime = "0" + landTime;
                    }

                    if ((takeOffTime.compareTo(takeOffRangeSplit[0]) < 0) ||
                            (takeOffTime.compareTo(takeOffRangeSplit[1]) > 0) ||
                            (landTime.compareTo(landRangeSplit[0]) < 0) ||
                            (landTime.compareTo(landRangeSplit[1]) > 0)) displaying = false;
                }

                if (displaying) {
                    System.out.print("~~~ RETURNING TRIP NUMBER " + (returnTrips.indexOf(trip)+1) + " ~~~\n");
                    System.out.println(trip.toString());
                    System.out.print("\n");
                }
            }
        }
    }

    /**
     * Get the name of the team
     *
     * @return Team name
     */
	public static String getTeamName() {
		return teamName;
	}

    /**
     * Get an airport object for the particular airport
     *
     * @param airportCode the desired airport
     * @return Airport object
     */
	public static Airport getAirport(String airportCode) {
		Airport result = new Airport();
		for (Airport airport: airports) {
			if (airport.code().equals(airportCode)) {
				result = airport;
				break;
			}
		}
		return result;
	}

    /**
     * Get an airplane object for the particular airport
     *
     * @param airplaneCode the desired airplane
     * @return Airplane object
     */
	public static Airplane getAirplane(String airplaneCode) {
		Airplane result = new Airplane();
		for (Airplane airplane: airplanes) {
			if (airplane.getmModel().equals(airplaneCode)) {
				result = airplane;
				break;
			}
		}
		return result;
	}
}
