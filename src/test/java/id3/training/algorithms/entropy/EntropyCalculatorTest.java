package id3.training.algorithms.entropy;

import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class EntropyCalculatorTest {

    private final EntropyCalculator instance = new EntropyCalculator();

    private final static double acceptableError = 0.001;

    @Test
    public void calc_given9PositiveAnd5Negative_shouldReturn0940() {

        Double expected = 0.940,
                actual = instance.calc(9, 5);

        assertThat(actual, closeTo(expected, acceptableError));
    }

    @Test
    public void calc_givenOnlyPositiveSamples_entropyShouldBeZero() {

        Double expected = 0.0,
                actual = instance.calc(13, 0);

        assertThat(actual, closeTo(expected, acceptableError));
    }

    @Test
    public void calc_givenEqualDistribution_entropyShouldBeOne() {
        Double expected = 1.0,
                actual = instance.calc(13, 13);

        assertThat(actual, closeTo(expected, acceptableError));
    }

    @Test
    public void cal_totalSamples_given9And5_shouldReturn14() {
        int expected = 14,
                actual = instance.calculateTotalSamples(9, 5);

        assertThat(actual, is(expected));
    }
}