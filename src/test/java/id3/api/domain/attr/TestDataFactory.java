package id3.api.domain.attr;

import id3.api.domain.Sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public class TestDataFactory {

    public final static AttributeClass
            weather = new AttributeClass("Weather", "Rainy", "Sunny", "Cloudy");
    private final static AttributeClass temperature = new AttributeClass("Temperature", "Hot", "Cool", "Mild");
    private final static AttributeClass wind = new AttributeClass("Wind", "Weak", "Strong");
    private final static AttributeClass humidity = new AttributeClass("Humidity", "High", "Normal");
    private final static AttributeClass playTennis = new AttributeClass("Tennis potential", "a nice day for tennis", "a horrible day for tennis");
    private final static AttributeClass weekday = new AttributeClass("Weekday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");


    public static List<Sample> getTennisSamples() {

        return Arrays.asList(
                        new Sample(sunny(), hot(), highHumidity(), weakWind(), noTennis()),
                new Sample(sunny(), hot(), highHumidity(), strongWind(), noTennis()),
                new Sample(cloudy(), hot(), highHumidity(), weakWind(), niceDayForTennis()),
                new Sample(rainy(), mild(), highHumidity(), weakWind(), niceDayForTennis()),
                new Sample(rainy(), cool(), normalHumidity(), weakWind(), niceDayForTennis()),
                new Sample(rainy(), cool(), normalHumidity(), strongWind(), noTennis()),
                new Sample(cloudy(), cool(), normalHumidity(), strongWind(), niceDayForTennis()),
                new Sample(sunny(), mild(), highHumidity(), weakWind(), noTennis()),
                new Sample(sunny(), cool(), normalHumidity(), weakWind(), niceDayForTennis()),
                new Sample(rainy(), mild(), normalHumidity(), weakWind(), niceDayForTennis()),
                new Sample(sunny(), mild(), normalHumidity(), strongWind(), niceDayForTennis()),
                new Sample(cloudy(), mild(), highHumidity(), strongWind(), niceDayForTennis()),
                new Sample(cloudy(), hot(), normalHumidity(), weakWind(), niceDayForTennis()),
                new Sample(rainy(), mild(), highHumidity(), strongWind(), noTennis())
        );

    }

    public static AttributeValue noTennis() {
        return playTennis.getPossibleValues().get(1);
    }

    public static AttributeValue niceDayForTennis() {
        return playTennis.getPossibleValues().get(0);
    }

    public static List<AttributeClass> getTennisAttributes() {
        return new ArrayList<AttributeClass>(Arrays.asList(weather, temperature, humidity, wind, playTennis));
    }

    public static List<AttributeClass> getSunnyFridayAttributes() {
        return new ArrayList<AttributeClass>(Arrays.asList(weather, temperature, weekday));
    }

    private static Sample rainyMonday() {
        return new Sample(rainy(), monday(), randomTemp());
    }

    public static Sample cloudyMonday() {
        return new Sample(cloudy(), monday(), randomTemp());
    }

    private static Sample sunnyFriday() {
        return new Sample(sunny(), friday(), randomTemp());
    }

    public static AttributeValue monday() {
        return weekday.getPossibleValues().get(0);
    }

    public static AttributeValue friday() {
        return weekday.getPossibleValues().get(4);
    }

    private static AttributeValue rainy() {
        return weather.getPossibleValues().get(0);
    }

    public static AttributeValue cloudy() {
        return weather.getPossibleValues().get(2);
    }

    public static AttributeValue sunny() {
        return weather.getPossibleValues().get(1);
    }

    public static AttributeValue hot() {
        return temperature.getPossibleValues().get(0);
    }

    private static AttributeValue cool() {
        return temperature.getPossibleValues().get(1);
    }

    private static AttributeValue mild() {
        return temperature.getPossibleValues().get(2);
    }

    private static AttributeValue weakWind() {
        return wind.getPossibleValues().get(0);
    }

    public static AttributeValue strongWind() {
        return wind.getPossibleValues().get(1);
    }

    public static AttributeValue highHumidity() {
        return humidity.getPossibleValues().get(0);
    }

    private static AttributeValue normalHumidity() {
        return humidity.getPossibleValues().get(1);
    }

    public static AttributeValue randomTemp() {
        return temperature.getPossibleValues().get(new Random().nextInt(2));
    }

    public static List<Sample> getSunnyFridaySamples() {
        List<Sample> mondaySucks = new ArrayList<Sample>();

        int i = 0, limit = 10;
        while (i < limit) {
            mondaySucks.add(rainyMonday());
            mondaySucks.add(sunnyFriday());
            mondaySucks.add(cloudyMonday());
            i++;
        }
        return mondaySucks;
    }
}
