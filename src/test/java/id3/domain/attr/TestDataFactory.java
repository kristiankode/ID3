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

    private final static AttributeClass
            weather = new AttrClassImpl("Weather", "Rainy", "Sunny", "Cloudy"),
            temperature = new AttrClassImpl("Temperature", "18", "4", "10", "9"),
            weekday = new AttrClassImpl("Weekday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday");

    public static List<AttributeClass> getAttributes() {
        return Arrays.asList(weather, temperature, weekday);
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

    private static AttributeValue randomTemp() {
        return temperature.getPossibleValues().get(new Random().nextInt(4));
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
