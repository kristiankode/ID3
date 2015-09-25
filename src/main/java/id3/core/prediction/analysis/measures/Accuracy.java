package id3.core.prediction.analysis.measures;

import id3.api.domain.attr.AttributeValue;
import id3.api.domain.tree.NodeClass;
import id3.core.prediction.Prediction;

import static id3.core.analysis.ValueAnalyzer.sampleMatchesTarget;

/**
 * Measures prediction accuracy.
 *
 * @author kristian
 *         Created 24.09.15.
 */
public class Accuracy extends PerformanceEvaluator {

    @Override
    protected boolean isCorrectlyClassified(Prediction prediction, AttributeValue target) {
        return predictionMatchesActual(prediction, target);
    }

    @Override
    protected boolean isWronglyClassified(Prediction prediction, AttributeValue target) {
        return !predictionMatchesActual(prediction, target);
    }

    private boolean predictionMatchesActual(Prediction prediction, AttributeValue target) {
        NodeClass correctPrediction =
                NodeClass.fromBoolean(sampleMatchesTarget(prediction.getSample(), target));

        return correctPrediction.equals(prediction.getPredictedValue());
    }

}
