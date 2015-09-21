package id3.prediction;

import id3.algorithms.DecisionTreeBuilder;
import id3.domain.Sample;
import id3.domain.SampleImpl;
import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;
import id3.domain.tree.Node;
import id3.domain.tree.NodeClass;
import id3.testdata.MushroomTestData;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static id3.domain.attr.TestDataFactory.*;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author kristian
 *         Created 21.09.15.
 */
public class PredictTest {

    DecisionTreeBuilder treeBuilder = new DecisionTreeBuilder();
    Predict instance = new Predict();

    @Test
    public void predictFriday_givenSunny_shouldReturnPositive() {

        List<Sample> samples = getSunnyFridaySamples();
        List<AttributeClass> attributes = getSunnyFridayAttributes();
        AttributeValue target = friday();

        Node model = treeBuilder.build(samples, target, attributes);

        Sample predictThis = new SampleImpl(sunny(), randomTemp());

        NodeClass expected = NodeClass.POSITIVE,
                actual = instance.predictClass(model, predictThis);

        assertThat(actual, is(expected));
    }

    @Test
    public void predictFriday_givenCloudy_shouldReturnNegative() {
        Node model = treeBuilder.build(
                getSunnyFridaySamples(), friday(), getSunnyFridayAttributes());

        Sample predictThis = cloudyMonday();

        NodeClass expected = NodeClass.NEGATIVE,
                actual = instance.predictClass(model, predictThis);
        assertThat(actual, is(expected));
    }

    @Test
    public void predictPoisonous_givenHabitatU_shouldReturnNegative()
            throws FileNotFoundException, UnsupportedEncodingException {
        MushroomTestData data = new MushroomTestData();

        NodeClass expected = NodeClass.NEGATIVE,
                actual = instance.predictClass(data.getMushroomModel(), data.sampleWithHabitatU());

        assertThat(actual, is(expected));
    }


}