package id3.pruning;

import id3.domain.Model;
import id3.domain.Rule;
import id3.domain.Sample;
import id3.prediction.PredictUsingRules;
import id3.prediction.Prediction;
import id3.prediction.analysis.measures.Accuracy;
import id3.testdata.MushroomTestData;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author kristian
 *         Created 24.09.15.
 */
public class RulePrunerTest {
    private final Logger log = LoggerFactory.getLogger(RulePrunerTest.class);

    private final RulePruner pruner = new RulePruner();
    private final PredictUsingRules predictor = new PredictUsingRules();
    private final MushroomTestData data = new MushroomTestData();
    private final Accuracy accuracy = new Accuracy();

    @Test
    public void prune_givenValidationDataSameAsTrainingData_expect100Percent()
            throws FileNotFoundException, UnsupportedEncodingException {

        Model model = data.getMushroomModel();
        List<Sample> trainingSet = data.getAllMushroomSamples();
        List<Prediction> originalPrediction = predictor.predict(model, trainingSet);
        Double originalAccuracy =
                accuracy.evaluate(originalPrediction, model.getTargetAttribute());

        List<Rule> prunedRules = pruner.pruneRepeatedly(model, trainingSet);
        List<Prediction> prunedPrediction = predictor.predict(prunedRules, trainingSet);
        Double prunedAccuracy =
                accuracy.evaluate(prunedPrediction, model.getTargetAttribute());

        log.debug("Accuracy before pruning = {}, Accuracy after pruning = {}", originalAccuracy, prunedAccuracy);

        assertThat(originalAccuracy, is(prunedAccuracy));
    }

    @Test
    public void prune_given25PercentValidationSet_expectNoDecreaseInAccuracy()
            throws FileNotFoundException, UnsupportedEncodingException {

        data.loadData(25.0);

        Model model = data.getMushroomModel();
        List<Sample> validationSet = data.getValidationSet();

        List<Prediction> originalPrediction = predictor.predict(model, validationSet);
        Double originalAccuracy = accuracy.evaluate(originalPrediction, model.getTargetAttribute());

        List<Rule> prunedRules = pruner.pruneRepeatedly(model, validationSet);
        List<Prediction> prunedPrediction = predictor.predict(prunedRules, validationSet);
        Double prunedAccuracy = accuracy.evaluate(prunedPrediction, model.getTargetAttribute());

        log.debug("Accuracy before pruning = {}%, Accuracy after pruning = {}%", originalAccuracy, prunedAccuracy);

        assertThat(prunedAccuracy, Matchers.greaterThanOrEqualTo(originalAccuracy));

    }

}