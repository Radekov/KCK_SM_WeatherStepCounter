package pl.pb.r.kcksm.http;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Locale;

/**
 * Created by Radosław Naruszewicz on 2016-11-26.
 * https://github.com/survivingwithandroid/Swa-app/tree/master/WeatherApp/src/com/survivingwithandroid/weatherapp
 */

public class WeatherHttpClient {
    private static String IMG_URL = "http://openweathermap.org/img/w/%s.png";

    private final static String API_KEY = "e64ed702a71529f3da697c8c68941ff8";
    private final static String QUERY = "http://api.openweathermap.org/data/2.5/weather?lat=%f&lon=%f&units=metric&APPID=%s";


    public String getWeatherData(float lat, float lon) {
        HttpURLConnection con = null ;
        InputStream is = null;

        try {
            String queryWeather = String.format(Locale.US,QUERY,lat,lon,API_KEY);
            con = (HttpURLConnection) ( new URL(queryWeather)).openConnection();
            con.setRequestMethod("GET");
            con.setDoInput(true);
            con.setDoOutput(true);
            con.connect();

            // Let's read the response
            StringBuffer buffer = new StringBuffer();
            is = con.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line = null;
            while (  (line = br.readLine()) != null )
                buffer.append(line + "\r\n");

            is.close();
            con.disconnect();
            return buffer.toString();
        }
        catch(Throwable t) {
            t.printStackTrace();
        }
        finally {
            try { is.close(); } catch(Throwable t) {}
            try { con.disconnect(); } catch(Throwable t) {}
        }
        return null;

    }

    public static String getImgUrl(String code){
        return String.format(IMG_URL,code);
    }
}
