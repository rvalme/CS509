package dao;

import flight.Flight;
import flight.Flights;
import flight.Trip;
import flight.Trips;
import driver.Driver;
import java.util.TreeSet;

public class DaoTrip {

    public static Trips getTripsFromParameters (String from, String to, String onDate) {
        Trips trips = new Trips();

        trips.addAll(getOneFlightTrips(from,to,onDate));
        trips.addAll(getTwoFlightTrips(from,to,onDate));
        trips.addAll(getThreeFlightTrips(from,to,onDate));
//        System.out.println("Next day: " + getNextDate(onDate));
//        System.out.println("Next two day: " + getNextDate(getNextDate(onDate)));

        return trips;
    }

    public static Trips getOneFlightTrips (String from, String to, String onDate) {
        Trips trips = new Trips();

        Flights flights = ServerInterface.INSTANCE.getFlightsFromDepartureOnDate(Driver.getTeamName(), from, onDate);
        for (Flight flight: flights) {
            if (flight.getmArrivalAirport().equals(to)) {
                Trip tripInstance = new Trip();
                tripInstance.add(flight);
                if (tripInstance.isValid()) {
                    trips.add(tripInstance);
                }
            }
        }

        return trips;
    }

    public static Trips getTwoFlightTrips (String from, String to, String onDate) {
        Trips trips = new Trips();

        Flights fromDeparture = ServerInterface.INSTANCE.getFlightsFromDepartureOnDate(Driver.getTeamName(), from, onDate);
        Flights toArrival = ServerInterface.INSTANCE.getFlightsFromArrivalOnDate(Driver.getTeamName(), to, onDate);
        toArrival.addAllFlights(ServerInterface.INSTANCE.getFlightsFromArrivalOnDate(Driver.getTeamName(), to, getNextDate(onDate)));

        TreeSet<String> intersection = new TreeSet<String>();
        intersection.addAll(fromDeparture.getArrivals());
        intersection.retainAll(toArrival.getDepartures());

        for (String layover: intersection) {
            Flights firstLegs = fromDeparture.getFlightsTo(layover);
            Flights secondLegs = toArrival.getFlightsFrom(layover);
            for (Flight firstLeg: firstLegs) {
                for (Flight secondLeg: secondLegs) {
                    Trip tripInstance = new Trip();
                    tripInstance.add(firstLeg);
                    tripInstance.add(secondLeg);
                    if (tripInstance.isValid()) {
                        trips.add(tripInstance);
                    }
                }
            }
        }

        return trips;
    }

    public static Trips getThreeFlightTrips(String from, String to, String onDate) {
        Trips trips = new Trips();

        Flights fromDeparture = ServerInterface.INSTANCE.getFlightsFromDepartureOnDate(Driver.getTeamName(), from, onDate);
        Flights toArrival = ServerInterface.INSTANCE.getFlightsFromArrivalOnDate(Driver.getTeamName(), to, onDate);
        toArrival.addAllFlights(ServerInterface.INSTANCE.getFlightsFromArrivalOnDate(Driver.getTeamName(), to, getNextDate(onDate)));
        toArrival.addAllFlights(ServerInterface.INSTANCE.getFlightsFromArrivalOnDate(Driver.getTeamName(), to, getNextDate(getNextDate(onDate))));

        Flights secondLegsFromDepartures = new Flights();
        Flights secondLegsFromArrivals = new Flights();

        //For each airport flights arrive to from the original airport
        for(String airport: fromDeparture.getArrivals()) {
            secondLegsFromDepartures.addAllFlights(ServerInterface.INSTANCE.getFlightsFromDepartureOnDate(Driver.getTeamName(), airport, onDate));
            secondLegsFromDepartures.addAllFlights(ServerInterface.INSTANCE.getFlightsFromDepartureOnDate(Driver.getTeamName(), airport, getNextDate(onDate)));
        }

        //For each airport flights that fly to the destination take off from
        for(String airport: toArrival.getDepartures()) {
            secondLegsFromArrivals.addAllFlights(ServerInterface.INSTANCE.getFlightsFromArrivalOnDate(Driver.getTeamName(), airport, onDate));
            secondLegsFromArrivals.addAllFlights(ServerInterface.INSTANCE.getFlightsFromArrivalOnDate(Driver.getTeamName(), airport, getNextDate(onDate)));
        }

        Flights secondLegs = new Flights();
        for (Flight flight: secondLegsFromDepartures) {
                if (secondLegsFromArrivals.getFlightNumbers().contains(flight.getmFlightNumber())) {
                    secondLegs.add(flight);
                }
        }

        for (Flight secondLeg: secondLegs) {
            Flights firstLegs = fromDeparture.getFlightsTo(secondLeg.getmDepartureAirport());
            Flights thirdLegs = toArrival.getFlightsFrom(secondLeg.getmArrivalAirport());
            for (Flight firstLeg: firstLegs) {
                for (Flight thirdLeg: thirdLegs) {
                    Trip tripInstance = new Trip();
                    tripInstance.add(firstLeg);
                    tripInstance.add(secondLeg);
                    tripInstance.add(thirdLeg);
                    if (tripInstance.isValid()) {
                        trips.add(tripInstance);
                    }
                }
            }
        }

        return trips;
    }

    static String getNextDate(String onDate) {
        String[] dateString = onDate.split("_");
        int month = Integer.parseInt(dateString[1]);
        int day = Integer.parseInt(dateString[2]);

        day += 1;
        if (day > 31) {
            day = 1;
            month += 1;
        }

        String result = dateString[0] + ((month<10)?"_0":"_") + month + ((day<10)?"_0":"_") + day;

        return result;
    }
}
