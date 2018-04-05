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
    public void getTimeZone(){
        try{
            //GeoApiContext context = new GeoApiContext().setApiKey("AIzaSyCrfc0lAr1FdBqJcPI6M3TQ6C297FvuEfs");
            TimeZone tz = TimeZoneApi.getTimeZone(gcontext, airport_loc).await();
            System.out.println(tz.getDisplayName());
        } catch (Exception e) {
            System.out.println("Could not find Timezone");
        }

    }
}

