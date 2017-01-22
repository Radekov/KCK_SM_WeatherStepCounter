package pl.pb.r.kcksm;

/**
 * Created by Radosław Naruszewicz on 2017-01-22.
 */

public class Constans {

    public static final String DEGGRE_C = (char) 0x00B0 + "C";
    public static final String WIND_SPEED = "m/s";
    public static final String PRESSURE = "hPa" + (char) 0xF079;
    public static final String HUMIDITY = (char) 0xF07A + "";

    public static String getWeatherIco(Integer id){
        switch(id){
            /*Thunderstorm*/
            case 200:
            case 201:
            case 202:
            case 210:
            case 211:
            case 212:
            case 221:
            case 230:
            case 231:
            case 232:
                return (char) 0xF01E + "";
            /*Drizzle*/
            case 300:
            case 301:
            case 302:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 321:
                return (char) 0xF01C + "";
            /*Rain*/
            case 500:
            case 501:
            case 502:
            case 503:
            case 504:
            case 511:
            case 520:
            case 521:
            case 522:
            case 531:
                return (char)0xF019 +"";
            /*Snow*/
            case 600:
            case 601:
            case 602:
            case 611:
            case 612:
            case 615:
            case 616:
            case 620:
            case 621:
            case 622:
                return (char) 0xF01B + "";
            /*Atmosphere*/
            case 701:
            case 711:
            case 721:
            case 731:
            case 741:
            case 751:
            case 761:
            case 762:
            case 771:
            case 781:
                return (char) + 0xF014 + "";
            /*Clear*/
            case 800:
                return (char) + 0xF00D +"";
            /*Clouds*/
            case 801:
            case 802:
            case 803:
            case 804:
                return (char) + 0xF041 +"";
            /*Extreme*/
            case 900:
            case 901:
            case 902:
            case 903:
            case 904:
            case 905:
            case 906:
                return "";
            /*Additional*/
            case 951:
            case 952:
            case 953:
            case 954:
            case 955:
            case 956:
            case 957:
            case 958:
            case 959:
            case 960:
            case 961:
            case 962:
                return "";
        }
        return null;
    }

}
