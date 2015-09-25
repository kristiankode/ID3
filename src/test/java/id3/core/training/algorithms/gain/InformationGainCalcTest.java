package id3.core.training.algorithms.gain;

import id3.api.domain.attr.TestDataFactory;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class InformationGainCalcTest {

    private static final double acceptableError = 0.001;
    private final InformationGainCalc instance = new InformationGainCalc(TestDataFactory.niceDayForTennis());

    @Test
    public void infoGain_givenTennisDataAndWeatherAttribute_shouldReturn0246() {
        Double expected = 0.246,
                actual = instance.getGainFor(TestDataFactory.getTennisSamples(), TestDataFactory.weather);

        assertThat(actual, closeTo(expected, acceptableError));
    }

}