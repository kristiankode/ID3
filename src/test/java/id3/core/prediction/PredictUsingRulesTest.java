package id3.core.prediction;

import id3.api.domain.Model;
import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeClass;
import id3.api.domain.attr.AttributeValue;
import id3.api.domain.tree.NodeClass;
import id3.core.importing.build.SampleImpl;
import id3.core.prediction.analysis.measures.Accuracy;
import id3.testdata.MushroomTestData;
import id3.core.training.algorithms.DecisionTreeBuilder;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import static id3.api.domain.attr.TestDataFactory.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.number.IsCloseTo.closeTo;
import static org.junit.Assert.assertThat;

/**
 * @author kristian
 *         Created 21.09.15.
 */
public class PredictUsingRulesTest {

    private static final Logger log = LoggerFactory.getLogger(PredictUsingRulesTest.class);

    private static final double acceptableError = 0.001;
    private final DecisionTreeBuilder treeBuilder = new DecisionTreeBuilder();
    private final Predictor instance = new PredictUsingRules();
    private final Accuracy accuracy = new Accuracy();

    @Test
    public void predictFriday_givenSunny_shouldReturnPositive() {

        List<Sample> samples = getSunnyFridaySamples();
        List<AttributeClass> attributes = getSunnyFridayAttributes();
        AttributeValue target = friday();

        Model model = treeBuilder.build(samples, target, attributes);

        Sample predictThis = new SampleImpl(sunny(), randomTemp());

        NodeClass expected = NodeClass.POSITIVE,
                actual = instance.predictSample(model, predictThis).getPredictedValue();

        assertThat(actual, is(expected));
    }

    @Test
    public void predictTennis_givenTrainingData_expect100Percent() {
        Model model = treeBuilder.build(getTennisSamples(), niceDayForTennis(), getTennisAttributes());

        List<Sample> predictThis = getTennisSamples();

        List<Prediction> predictions = instance.predict(model, predictThis);

        Double expectedAccuracy = 100.0,
                actual = accuracy.evaluate(predictions, model.getTargetAttribute());

        assertThat(actual, closeTo(expectedAccuracy, acceptableError));
    }

    @Test
    public void predictTennis_givenOneOutlier_expect14Of15Correct() {
        Model model = treeBuilder.build(getTennisSamples(), niceDayForTennis(), getTennisAttributes());

        List<Sample> predictThis = new ArrayList<Sample>(getTennisSamples());
        Sample outlier = new SampleImpl(cloudy(), hot(), highHumidity(), strongWind(), noTennis());
        predictThis.add(outlier);

        List<Prediction> predictions = instance.predict(model, predictThis);

        Double expectedAccuracy = 93.333,
                actual = accuracy.evaluate(predictions, model.getTargetAttribute());

        log.debug("Predictions:");
        for (Prediction p : predictions) {
            log.debug(p.toString());
        }

        assertThat(actual, closeTo(expectedAccuracy, acceptableError));
    }


    @Test
    public void predictFriday_givenCloudy_shouldReturnNegative() {
        Model model = treeBuilder.build(
                getSunnyFridaySamples(), friday(), getSunnyFridayAttributes());

        Sample predictThis = cloudyMonday();

        NodeClass expected = NodeClass.NEGATIVE,
                actual = instance.predictSample(model, predictThis).getPredictedValue();
        assertThat(actual, is(expected));
    }

    @Test
    public void predictPoisonous_givenHabitatU_shouldReturnNegative()
            throws FileNotFoundException, UnsupportedEncodingException {
        MushroomTestData data = new MushroomTestData();
        data.loadData(0.1);

        NodeClass expected = NodeClass.NEGATIVE,
                actual = instance.predictSample(data.getMushroomModel(), data.sampleWithHabitatU()).getPredictedValue();

        assertThat(actual, is(expected));
    }


}