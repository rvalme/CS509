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

/**
 * @author blake
 *
 */
//Need to: InputHandler asking user to input data in sequence
public class Driver {

	public static String teamName;
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
		// Try to get a list of airports
		Airports airports = ServerInterface.INSTANCE.getAirports(teamName);
		Collections.sort(airports);
		for (Airport airport : airports) {
			System.out.println(airport.toString());
		}

		Airplanes airplanes = ServerInterface.INSTANCE.getAirplanes(teamName);
		Collections.sort(airplanes);
		for (Airplane airplane: airplanes) {
			System.out.println(airplane.toString());
		}

		InputHandler inpH1 = new InputHandler();
		inpH1.initialize();
		inpH1.validateDepartureTime();
		inpH1.validateDepartureAirport(airports);
		inpH1.validateArrivalAirport(airports);
		inpH1.close();

		Flights flights = ServerInterface.INSTANCE.getFlightsFromDepartureOnDate(teamName, inpH1.getDeparture_airport_code(), inpH1.getDeparture_date());
		Collections.sort(flights);
//		for (Flight flight: flights) {
//			System.out.println(flight.toString());
//			System.out.print("\n");
//		}

		Trips trips = DaoTrip.getTripsFromParameters(inpH1.getDeparture_airport_code(), inpH1.getArrival_airport_code(), inpH1.getDeparture_date());
		System.out.println("found" + trips.size() + " trips");

		for(Trip trip: trips) {
			System.out.print("printing trips\n");
			System.out.println(trip.toString());
			System.out.print("\n");
		}

		TimeConversion tconv = new TimeConversion(42.3601, -71.0589);
		System.out.println("gmt time: " + flights.get(0).getmArrivalTime() + " local time: " + tconv.gmtToLocal(flights.get(0).getmArrivalTime(), tconv.getTimeZone()));

	}

	public static String getTeamName() {
		return teamName;
	}
}
