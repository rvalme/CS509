package dao;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import flight.Flight;
import flight.Flights;

public class DaoFlight {
    /**
     * Builds collection of flights from flights described in XML
     */
    public static Flights addAll (String xmlFlights) throws NullPointerException {
        Flights flights = new Flights();

        // Load the XML string into a DOM tree for ease of processing
        // then iterate over all nodes adding each airport to our collection
        Document docFlights = buildDomDoc (xmlFlights);
        NodeList nodesFlights = docFlights.getElementsByTagName("Flight");

        for (int i = 0; i < nodesFlights.getLength(); i++) {
            Element elementFlight = (Element) nodesFlights.item(i);
            Flight flight = buildFlight (elementFlight);

            if (flight.isSeatAvailable()) {
                flights.add(flight);
            }

        }

        return flights;
    }

    /**
     * Creates an Flight object from a DOM node
     *
     * Processes a DOM Node that describes an Flight, creates an Flight object from the information
     * @param nodeFlight is a DOM Node describing an Flight
     * @return Flight object created from the DOM Node representation of the Flight
     *
     * @pre nodeFlight is of format specified by CS509 server API
     */
    static private Flight buildFlight (Node nodeFlight) {

        String mAirplaneType;
        int mTravelTime;
        String mFlightNumber;
        String mDepartureAirport;
        String mDepartureTime;
        String mArrivalAirport;
        String mArrivalTime;
        double mFirstClassPrice;
        int mFirstClassReserved;
        double mCoachPrice;
        int mCoachReserved;

        //
        Element elementFlight = (Element) nodeFlight;
        mAirplaneType = elementFlight.getAttributeNode("Airplane").getValue();
        mTravelTime = Integer.parseInt(elementFlight.getAttributeNode("FlightTime").getValue());
        mFlightNumber = elementFlight.getAttributeNode("Number").getValue();

        //
        Element elementDeparture;
        Element elementCode;
        Element elementTime;
        elementDeparture = (Element)elementFlight.getElementsByTagName("Departure").item(0);
        elementCode = (Element)elementDeparture.getElementsByTagName("Code").item(0);
        mDepartureAirport = getCharacterDataFromElement(elementCode);
        elementTime = (Element)elementDeparture.getElementsByTagName("Time").item(0);
        mDepartureTime = getCharacterDataFromElement(elementTime);

        Element elementArrival;
        elementArrival = (Element)elementFlight.getElementsByTagName("Arrival").item(0);
        elementCode = (Element)elementArrival.getElementsByTagName("Code").item(0);
        mArrivalAirport = getCharacterDataFromElement(elementCode);
        elementTime = (Element)elementArrival.getElementsByTagName("Time").item(0);
        mArrivalTime = getCharacterDataFromElement(elementTime);

        Element elementSeating;
        elementSeating = (Element)elementFlight.getElementsByTagName("Seating").item(0);

        Element elementFirstClass;
        Element elementCoach;
        elementFirstClass = (Element)elementSeating.getElementsByTagName("FirstClass").item(0);
        mFirstClassPrice = Double.parseDouble(elementFirstClass.getAttributeNode("Price").getValue().split("\\$")[1].replaceAll(",",""));
        mFirstClassReserved = Integer.parseInt(getCharacterDataFromElement(elementFirstClass));

        elementCoach = (Element)elementSeating.getElementsByTagName("Coach").item(0);
        mCoachPrice = Double.parseDouble(elementCoach.getAttributeNode("Price").getValue().split("\\$")[1].replaceAll(",",""));
        mCoachReserved = Integer.parseInt(getCharacterDataFromElement(elementCoach));

        /**
         * Update the Flight object with values from XML node
         */
        Flight flight = new Flight(mAirplaneType,
                mTravelTime,
                mFlightNumber,
                mDepartureAirport,
                mDepartureTime,
                mArrivalAirport,
                mArrivalTime,
                mFirstClassPrice,
                mFirstClassReserved,
                mCoachPrice,
                mCoachReserved);

        return flight;
    }

    /**
     * Builds a DOM tree from an XML string
     *
     * Parses the XML file and returns a DOM tree that can be processed
     *
     * @param xmlString XML String containing set of objects
     * @return DOM tree from parsed XML or null if exception is caught
     */
    static private Document buildDomDoc (String xmlString) {
        /**
         * load the xml string into a DOM document and return the Document
         */
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            InputSource inputSource = new InputSource();
            inputSource.setCharacterStream(new StringReader(xmlString));

            return docBuilder.parse(inputSource);
        }
        catch (ParserConfigurationException e) {
            e.printStackTrace();
            return null;
        }
        catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        catch (SAXException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Retrieve character data from an element if it exists
     *
     * @param e is the DOM Element to retrieve character data from
     * @return the character data as String [possibly empty String]
     */
    private static String getCharacterDataFromElement (Element e) {
        Node child = e.getFirstChild();
        if (child instanceof CharacterData) {
            CharacterData cd = (CharacterData) child;
            return cd.getData();
        }
        return "";
    }
}
