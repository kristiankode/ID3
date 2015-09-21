package id3.training.algorithms.gain;

import id3.training.algorithms.entropy.EntropyCalculator;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class EntropyCalculatorTest {

    EntropyCalculator instance = new EntropyCalculator();

    final static double acceptableError = 0.001;

    @Test
    public void calc_given9PositiveAnd5Negative_shouldReturn0940() {

        Double expected = 0.940,
                actual = instance.calc(9, 5);

        assertThat(actual, closeTo(expected, acceptableError));
    }

    @Test
    public void calc_givenOnlyPositiveSamples_entropyShouldBeZero(){

        Double expected = 0.0,
                actual = instance.calc(13, 0);

        assertThat(actual, closeTo(expected, acceptableError));
    }

    @Test
    public void calc_givenEqualDistribution_entropyShouldBeOne(){
        Double expected = 1.0,
                actual = instance.calc(13, 13);

        assertThat(actual, closeTo(expected, acceptableError));
    }
}