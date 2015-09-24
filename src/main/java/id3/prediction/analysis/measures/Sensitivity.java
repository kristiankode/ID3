package id3.prediction.analysis.measures;

import id3.domain.attr.AttributeValue;
import id3.domain.tree.NodeClass;
import id3.prediction.Prediction;

/**
 * Measures sensitivity of a prediction.
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
