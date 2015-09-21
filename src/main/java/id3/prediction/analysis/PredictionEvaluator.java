package id3.prediction.analysis;

import id3.domain.attr.AttributeValue;
import id3.domain.tree.NodeClass;
import id3.prediction.Prediction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.RoundingMode;
import java.util.List;

import static id3.analysis.ValueAnalyzer.sampleMatchesTarget;
import static java.math.BigDecimal.valueOf;

/**
 * @author kristian
 *         Created 21.09.15.
 */
public class PredictionEvaluator {
    static Logger log = LoggerFactory.getLogger(PredictionEvaluator.class);

    public static boolean isPredictionCorrect(Prediction prediction, AttributeValue target) {
        NodeClass correctPrediction =
                NodeClass.fromBoolean(sampleMatchesTarget(prediction.getSample(), target));

        log.debug("Sample was classified as {}, correct classification was {}", prediction, correctPrediction);

        return correctPrediction.equals(prediction);
    }

    public static double evaluatePredictionAccuracy(List<Prediction> predictions, AttributeValue target) {
        int correct = 0, incorrect = 0;

        for (Prediction p : predictions) {
            if (isPredictionCorrect(p, target)) {
                correct++;
            } else {
                incorrect++;
            }
        }

        int total = correct + incorrect;

        Double accuracy = valueOf(correct).divide(valueOf(total), 5, RoundingMode.HALF_UP).doubleValue();

        log.info("Predicted {} of {} values correctly, accuracy = {}%", correct, total, accuracy);

        return accuracy;

    }
}
