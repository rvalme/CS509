package utils;
import java.util.TimeZone;

import com.google.maps.GeoApiContext;
import com.google.maps.TimeZoneApi;
import com.google.maps.model.LatLng;

// convert inputted time of airport from GMT to local time of that airport using
// using the latitude and longitude of that airport
public class TimeConversion {

    LatLng airport_loc;          // latlng of desired timezone
    GeoApiContext gcontext;
    int military_time = 24;
    int conv_est = 4;
    int conv_cst = 3;
    int conv_mst = 2;
    int conv_pst = 1;

    //create and initialize class once then for different airports just set LatLng
    public TimeConversion (double latitude, double longitude) {
        airport_loc = new LatLng(latitude, longitude);
        gcontext = new GeoApiContext.Builder()
                .apiKey("AIzaSyCrfc0lAr1FdBqJcPI6M3TQ6C297FvuEfs")
                .build();
    }

    public void setLatLng(double latitude, double longitude) {
        airport_loc = new LatLng(latitude, longitude);

    }
    public String getTimeZone(){
        try{
            //GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyCrfc0lAr1FdBqJcPI6M3TQ6C297FvuEfs");
            TimeZone tz = TimeZoneApi.getTimeZone(gcontext, airport_loc).await();
            //System.out.println(tz.getDisplayName());
            return tz.getDisplayName();
        } catch (Exception e) {
            System.out.println("Could not find Timezone");
        }
        return "-1";

    }

    public String gmtToLocal(String flightTime, String timeZone) {
        //converts gmt to est, cst, mst, pst depending on the inputted timezone
        String[] flightTimeSplit = flightTime.split(" ");
        int Day = Integer.parseInt(flightTimeSplit[2]);
        String[] Hours_min = flightTimeSplit[3].split(":");
        String nTZ = "";
        //gmt to est, pst, cst, mst (gmt is 4 hours ahead of est)
        int Hours = Integer.parseInt(Hours_min[0]);
        if(timeZone.equals("Eastern Standard Time")) {
            Hours = Hours - conv_est;
            nTZ = "EST";
        }
        else if(timeZone.equals("Central Standard Time")){
            Hours = Hours - conv_cst;
            nTZ = "CST";
        }
        else if(timeZone.equals("Mountain Standard Time")){
            Hours = Hours - conv_mst;
            nTZ = "MST";
        }
        else if(timeZone.equals("Pacific Standard Time")){
            Hours = Hours - conv_pst;
            nTZ = "PST";
        }
        if(Hours < 0){
            Hours = military_time + Hours;
            Day = Day - 1;
        }

        String nHours_min = String.join(":", Integer.toString(Hours), Hours_min[1]);
        flightTimeSplit[2] = Integer.toString(Day);
        flightTimeSplit[3] = nHours_min;
        flightTimeSplit[4] = nTZ;
        String nFlightTime = String.join(" ", flightTimeSplit);
        return nFlightTime;
    }

    public String localToGMT(String flightTime, String timeZone) {
        //converts est, cst, mst, pst to gmt depending on the inputted timezone
        String[] flightTimeSplit = flightTime.split(" ");
        int Day = Integer.parseInt(flightTimeSplit[2]);
        String[] Hours_min = flightTimeSplit[3].split(":");
        int Hours = Integer.parseInt(Hours_min[0]);
        if(timeZone.equals("Eastern Standard Time")) {
            Hours = Hours + conv_est;
        }
        else if(timeZone.equals("Central Standard Time")){
            Hours = Hours + conv_cst;
        }
        else if(timeZone.equals("Mountain Standard Time")){
            Hours = Hours + conv_mst;
        }
        else if(timeZone.equals("Pacific Standard Time")){
            Hours = Hours + conv_pst;
        }
        if(Hours > 24){
            Hours = Hours - military_time;
            Day = Day + 1;
        }

        String nHours_min = String.join(":", Integer.toString(Hours), Hours_min[1]);
        flightTimeSplit[2] = Integer.toString(Day);
        flightTimeSplit[3] = nHours_min;
        String nFlightTime = String.join(" ", flightTimeSplit);
        return nFlightTime;
    }
}

