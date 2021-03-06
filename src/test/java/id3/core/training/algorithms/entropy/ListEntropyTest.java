package id3.core.training.algorithms.entropy;

import id3.api.domain.attr.TestDataFactory;
import org.junit.Test;

import static org.hamcrest.Matchers.closeTo;
import static org.junit.Assert.assertThat;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class ListEntropyTest {

    private final ListEntropy instance = new ListEntropy();
    private static final double acceptableError = 0.001;

    @Test
    public void entropyWithoutTarget_givenTennisData_shouldBe0940() {
        Double expected = 0.940,
                actual = instance.calculateEntropy(TestDataFactory.getTennisSamples(),
                        TestDataFactory.niceDayForTennis().getAttributeClass());

        assertThat(actual, closeTo(expected, acceptableError));
    }


}