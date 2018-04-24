/**
 * 
 */
package utils;

/**
 * @author blake
 * @version 1.2
 *
 */
public class QueryFactory {
	
	/**
	 * Return a query string that can be passed to HTTP URL to request list of airports
	 * 
	 * @param teamName is the name of the team to specify the data copy on server
	 * @return the query String which can be appended to URL to form HTTP GET request
	 */
	public static String getAirports(String teamName) {
		return "?team=" + teamName + "&action=list&list_type=airports";
	}
	
	/**
	 * Lock the server database so updates can be written
	 * 
	 * @param teamName is the name of the team to acquire the lock
	 * @return the String written to HTTP POST to lock server database 
	 */
	public static String lock (String teamName) {
		return "team=" + teamName + "&action=lockDB";
	}

	/**
	 * Reset the database to its initial state
	 *
	 * @param teamName is the name of the team to reset the database
	 * @return the String written to HTTP POST to reset server database
	 */
	public static String reset (String teamName) {
		return "?team=" + teamName + "&action=resetDB";
	}

	/**
	 * Unlock the server database after updates are written
	 * 
	 * @param teamName is the name of the team holding the lock
	 * @return the String written to the HTTP POST to unlock server database
	 */
	public static String unlock (String teamName) {
		return "team=" + teamName + "&action=unlockDB";
	}

	/**
	 * Return a query string that can be passed to HTTP URL to request list of airplanes
	 *
	 * @param teamName is the name of the team to specify the data copy on server
	 * @return the query String which can be appended to URL to form HTTP GET request
	 */
	public static String getAirplanes (String teamName) {
		return "?team=" + teamName + "&action=list&list_type=airplanes";
	}

	/**
	 * Return a query string that can be passed to HTTP URL to request list of flights departing an airport
	 *
	 * @param teamName is the name of the team to specify the data copy on server
	 * @param airportCode is the 3-letter code of the departure airport
	 * @return the query String which can be appended to URL to form HTTP GET request
	 */
	public static String getFlightsFromDeparture (String teamName, String airportCode) {
		return "?team=" + teamName + "&action=list&list_type=departing&airport=" + airportCode;
	}

	/**
	 * Return a query string that can be passed to HTTP URL to request list of flights departing an airport on a
	 * specific day
	 *
	 * @param teamName is the name of the team to specify the data copy on server
	 * @param airportCode is the 3-letter code of the airport
	 * @param date is the specified departing day in the form yyyy_mm_dd
	 * @return the query String which can be appended to URL to form HTTP GET request
	 */
	public static String getFlightsFromDepartureOnDate (String teamName, String airportCode, String date) {
		return "?team=" + teamName + "&action=list&list_type=departing&airport=" + airportCode + "&day=" + date;
	}

	/**
	 * Return a query string that can be passed to HTTP URL to request list of flights departing an airport
	 *
	 * @param teamName is the name of the team to specify the data copy on server
	 * @param airportCode is the 3-letter code of the arrival airport
	 * @return the query String which can be appended to URL to form HTTP GET request
	 */
	public static String getFlightsFromArrival (String teamName, String airportCode) {
		return "?team=" + teamName + "&action=list&list_type=arriving&airport=" + airportCode;
	}

	/**
	 * Return a query string that can be passed to HTTP URL to request list of flights departing an airport on a
	 * specific day
	 *
	 * @param teamName is the name of the team to specify the data copy on server
	 * @param airportCode is the 3-letter code of the arrival airport
	 * @param date is the specified arriving day in the form yyyy_mm_dd
	 * @return the query String which can be appended to URL to form HTTP GET request
	 */
	public static String getFlightsFromArrivalOnDate (String teamName, String airportCode, String date) {
		return "?team=" + teamName + "&action=list&list_type=arriving&airport=" + airportCode + "&day=" + date;
	}

	/**
	 * Reserve flights
	 *
	 * @param teamName is the name of the team holding the lock
	 * @param xmlFlights is the xml string specifying the flight numbers and seating types to book
	 * @return the String written to HTTP POST to lock server database
	 */
	public static String reserve (String teamName, String xmlFlights) {
		return "team=" + teamName + "&action=buyTickets&flightData=" + xmlFlights;
	}
}
