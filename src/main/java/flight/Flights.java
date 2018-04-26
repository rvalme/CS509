package flight;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * This class aggregates a number of flights. Used to store similar flights (arriving at the same airport,
 * departing from the same airport)
 *
 */
public class Flights extends ArrayList<Flight> {
    private static final long serialVersionUID = 3L;

    //Set of airport codes that this set of flights depart from
    private TreeSet<String> departures = new TreeSet<String>();
    //Set of airport codes that this set of flights arrive at
    private TreeSet<String> arrivals  = new TreeSet<String>();
    //Set of flight numbers in this set of flights
    private TreeSet<String> flightNumbers = new TreeSet<String>();

    /**
     * get the set of airport codes that this set of flights depart from
     *
     * @return the set of airport codes
     */
    public TreeSet<String> getDepartures() {
        return departures;
    }

    /**
     * get the set of airport codes that this set of flights arrive at
     *
     * @return the set of airport codes
     */
    public TreeSet<String> getArrivals() {
        return arrivals;
    }

    /**
     * get the set flight numbers in this set of flights
     *
     * @return the set of airport codes
     */
    public TreeSet<String> getFlightNumbers() {
        return flightNumbers;
    }

    /**
     * add a single flight to this set of flights, updating the
     * departures, arrivals and flight numbers of the whole set
     *
     * @return true if added successfully
     */
    @Override
    public boolean add(Flight flight) {
        boolean result = super.add(flight);
        if (result) {
            departures.add(flight.getmDepartureAirport());
            arrivals.add(flight.getmArrivalAirport());
            flightNumbers.add(flight.getmFlightNumber());
        }
        return result;
    }

    /**
     * add multiple flights to this set of flights, updating the
     * departures, arrivals and flight numbers of the whole set
     *
     * @return true if added successfully
     */
    public boolean addAllFlights(Flights flights) {
        boolean result = addAll(flights);

        arrivals.addAll(flights.getArrivals());
        departures.addAll(flights.getDepartures());
        flightNumbers.addAll(flights.getFlightNumbers());
        return result;
    }

    /**
     * print out all departure airports
     *
     * @return
     */
    public void printDepartures() {
        for (String departure: departures) {
            System.out.println(departure);
        }

    }

    /**
     * print out all arrival airports
     *
     * @return
     */
    public void printArrivals() {
        for (String arrival: arrivals) {
            System.out.println(arrival);
        }
    }

    /**
     * Get all flights in this set of flights that depart from a specific airport
     *
     * @return the appropriate set of flights
     */
    public Flights getFlightsFrom(String departureAirport) {
        Flights flights = new Flights();
        for (Flight flight: this) {
            if (flight.getmDepartureAirport().equals(departureAirport)) {
                flights.add(flight);
            }
        }

        return flights;
    }

    /**
     * Get all flights in this set of flights that arrive at a specific airport
     *
     * @return the appropriate set of flights
     */
    public Flights getFlightsTo(String arrivalAirport) {
        Flights flights = new Flights();
        for (Flight flight: this) {
            if (flight.getmArrivalAirport().equals(arrivalAirport)) {
                flights.add(flight);
            }
        }

        return flights;
    }
}
