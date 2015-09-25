package id3.core.prediction.analysis.measures;

import id3.api.domain.attr.AttributeValue;
import id3.api.domain.tree.NodeClass;
import id3.core.prediction.Prediction;

/**
 * Measures sensitivity of a prediction.
 * <p>
 * Sensitivity (also called the true positive rate, or the recall in some fields) measures the
 * proportion of positives that are correctly identified as such (e.g., the percentage of
 * sick people who are correctly identified as having the condition).
 *
 * @author kristian
 *         Created 24.09.15.
 */
public class Sensitivity extends PerformanceEvaluator {

    @Override
    protected boolean isCorrectlyClassified(Prediction prediction, AttributeValue target) {
        return isTruePositive(prediction, target);
    }

    @Override
    protected boolean isWronglyClassified(Prediction prediction, AttributeValue target) {
        return isFalsePositive(prediction, target);
    }

    private boolean isTruePositive(Prediction p, AttributeValue target) {
        return p.getPredictedValue().equals(NodeClass.POSITIVE)
                && p.getSample().hasAttributeValue(target);
    }

    private boolean isFalsePositive(Prediction p, AttributeValue target) {
        return p.getPredictedValue().equals(NodeClass.POSITIVE)
                && !p.getSample().hasAttributeValue(target);
    }
}
