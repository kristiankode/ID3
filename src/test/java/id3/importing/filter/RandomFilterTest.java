package id3.importing.filter;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author kristian
 *         Created 24.09.15.
 */
public class RandomFilterTest {

    RandomFilter instance = new RandomFilter();

    @Test
    public void calculateTargetSize_given10PercentOf100Unfiltered_expect10() {

        Integer expected = 10,
                actual = instance.calculateTargetSize(10.0, 100);

        assertThat(actual, is(expected));
    }

    @Test
    public void calculateTargetSize_given1PercentOf8150Unfiltered_expect82() {

        Integer expected = 82,
                actual = instance.calculateTargetSize(1.0, 8150);

        assertThat(actual, is(expected));
    }

    @Test
    public void calculateTargetSize_givenNegativePercent_shouldReturnUnfiltered() {

        int unfilteredSize = 421;
        Integer expected = unfilteredSize,
                actual = instance.calculateTargetSize(-10.0, unfilteredSize);

        assertThat(actual, is(expected));
    }
}