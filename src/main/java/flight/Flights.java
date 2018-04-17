package flight;

import java.util.ArrayList;
import java.util.TreeSet;

public class Flights extends ArrayList<Flight> {
    private static final long serialVersionUID = 3L;

    private TreeSet<String> departures = new TreeSet<String>();
    private TreeSet<String> arrivals  = new TreeSet<String>();
    private TreeSet<String> flightNumbers = new TreeSet<String>();

    public TreeSet<String> getDepartures() {
        return departures;
    }

    public TreeSet<String> getArrivals() {
        return arrivals;
    }

    public TreeSet<String> getFlightNumbers() {
        return flightNumbers;
    }

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

    public boolean addAllFlights(Flights flights) {
        boolean result = addAll(flights);

        arrivals.addAll(flights.getArrivals());
        departures.addAll(flights.getDepartures());
        flightNumbers.addAll(flights.getFlightNumbers());
        return result;
    }

    public void printDepartures() {
        for (String departure: departures) {
            System.out.println(departure);
        }

    }

    public void printArrivals() {
        for (String arrival: arrivals) {
            System.out.println(arrival);
        }
    }

    public Flights getFlightsFrom(String departureAirport) {
        Flights flights = new Flights();
        for (Flight flight: this) {
            if (flight.getmDepartureAirport().equals(departureAirport)) {
                flights.add(flight);
            }
        }

        return flights;
    }

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
