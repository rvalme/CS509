/**
 * 
 */
package com.company.driver;

import java.util.Collections;

import com.company.airport.Airport;
import com.company.airport.Airports;
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
		if (args.length != 1) {
			System.err.println("usage: CS509.sample teamName");
			System.exit(-1);
			return;
		}
		
		String teamName = args[0];
		// Try to get a list of airports
		/*
		Airports airports = ServerInterface.INSTANCE.getAirports(teamName);
		Collections.sort(airports);
		for (Airport airport : airports) {
			System.out.println(airport.toString());
		}
		*/
		InputHandler inpH1 = new InputHandler();
		inpH1.validateDepartureDate();
	}
}