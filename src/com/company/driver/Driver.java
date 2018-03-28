/**
 * 
 */
package com.company.driver;

import java.util.Collections;

import com.company.airport.Airport;
import com.company.airport.Airports;
import com.company.airplane.Airplane;
import com.company.airplane.Airplanes;
import com.company.flight.Flight;
import com.company.flight.Flights;
import com.company.dao.ServerInterface;
import com.company.handler.InputHandler;

/**
 * @author blake
 *
 */
//Need to: InputHandler asking user to input data in sequence
public class Driver {

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
		
		String teamName = "Man-Month";
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
		inpH1.validateAirport(airports);
		inpH1.close();

		Flights flights = ServerInterface.INSTANCE.getFlightsFromDepartureOnDate(teamName, inpH1.getDeparture_airport_code(), inpH1.getDeparture_date());
		Collections.sort(flights);
		for (Flight flight: flights) {
			System.out.println(flight.toString());
			System.out.print("\n");
		}
	}
}
