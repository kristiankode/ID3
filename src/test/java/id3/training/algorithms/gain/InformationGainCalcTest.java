package id3.training.algorithms.gain;

import id3.domain.attr.TestDataFactory;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class InformationGainCalcTest {

    public static final double acceptableError = 0.001;
    InformationGainCalc instance = new InformationGainCalc(TestDataFactory.niceDayForTennis());

    @Test
    public void infoGain_givenTennisDataAndWeatherAttribute_shouldReturn0246() {
        Double expected = 0.246,
                actual = instance.getGainFor(TestDataFactory.getTennisSamples(), TestDataFactory.weather);

        assertThat(actual, closeTo(expected, acceptableError));
    }

}