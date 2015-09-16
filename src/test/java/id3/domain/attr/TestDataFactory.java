package id3.domain.attr;

import id3.domain.Sample;
import id3.domain.SampleImpl;

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
            weather = new AttrClassImpl("Weather", "Rainy", "Sunny", "Cloudy"),
            temperature = new AttrClassImpl("Temperature", "Hot", "Cool", "Mild"),
            wind = new AttrClassImpl("Wind", "Weak", "Strong"),
            humidity = new AttrClassImpl("Humidity", "High", "Normal"),
            playTennis = new AttrClassImpl("Tennis potential", "a nice day for tennis", "a horrible day for tennis"),
            weekday = new AttrClassImpl("Weekday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");


    public static List<Sample> getTennisSamples() {

        return Arrays.asList(
                (Sample)
                        new SampleImpl(sunny(), hot(), highHumidity(), weakWind(), noTennis()),
                new SampleImpl(sunny(), hot(), highHumidity(), strongWind(), noTennis()),
                new SampleImpl(cloudy(), hot(), highHumidity(), weakWind(), niceDayForTennis()),
                new SampleImpl(rainy(), mild(), highHumidity(), weakWind(), niceDayForTennis()),
                new SampleImpl(rainy(), cool(), normalHumidity(), weakWind(), niceDayForTennis()),
                new SampleImpl(rainy(), cool(), normalHumidity(), strongWind(), noTennis()),
                new SampleImpl(cloudy(), cool(), normalHumidity(), strongWind(), niceDayForTennis()),
                new SampleImpl(sunny(), mild(), highHumidity(), weakWind(), noTennis()),
                new SampleImpl(sunny(), cool(), normalHumidity(), weakWind(), niceDayForTennis()),
                new SampleImpl(rainy(), mild(), normalHumidity(), weakWind(), niceDayForTennis()),
                new SampleImpl(sunny(), mild(), normalHumidity(), strongWind(), niceDayForTennis()),
                new SampleImpl(cloudy(), mild(), highHumidity(), strongWind(), niceDayForTennis()),
                new SampleImpl(cloudy(), hot(), normalHumidity(), weakWind(), niceDayForTennis()),
                new SampleImpl(rainy(), mild(), highHumidity(), strongWind(), noTennis())
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

    public static List<AttributeClass> getAttributes() {
        return new ArrayList<AttributeClass>(Arrays.asList(weather, temperature, weekday));
    }

    public static Sample rainyMonday() {
        return new SampleImpl(rainy(), monday(), randomTemp());
    }

    public static Sample cloudyMonday() {
        return new SampleImpl(cloudy(), monday(), randomTemp());
    }

    public static Sample sunnyFriday() {
        return new SampleImpl(sunny(), friday(), randomTemp());
    }

    public static AttributeValue monday() {
        return weekday.getPossibleValues().get(0);
    }

    public static AttributeValue friday() {
        return weekday.getPossibleValues().get(4);
    }

    public static AttributeValue rainy() {
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

    public static AttributeValue cool() {
        return temperature.getPossibleValues().get(1);
    }

    public static AttributeValue mild() {
        return temperature.getPossibleValues().get(2);
    }

    public static AttributeValue weakWind() {
        return wind.getPossibleValues().get(0);
    }

    public static AttributeValue strongWind() {
        return wind.getPossibleValues().get(1);
    }

    public static AttributeValue highHumidity() {
        return humidity.getPossibleValues().get(0);
    }

    public static AttributeValue normalHumidity() {
        return humidity.getPossibleValues().get(1);
    }

    private static AttributeValue randomTemp() {
        return temperature.getPossibleValues().get(new Random().nextInt(2));
    }

    public static List<Sample> getSamples() {
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
