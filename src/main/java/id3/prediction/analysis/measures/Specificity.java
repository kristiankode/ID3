package id3.prediction.analysis.measures;

import id3.domain.attr.AttributeValue;
import id3.domain.tree.NodeClass;
import id3.prediction.Prediction;

/**
 * Measures specificity of a prediction.
 *
 * @author kristian
 *         Created 24.09.15.
 */
public class Specificity extends PerformanceEvaluator {

    @Override
    protected boolean isCorrectlyClassified(Prediction prediction, AttributeValue target) {
        return isTrueNegative(prediction, target);
    }

    @Override
    protected boolean isWronglyClassified(Prediction prediction, AttributeValue target) {
        return isFalseNegative(prediction, target);
    }

    private boolean isTrueNegative(Prediction p, AttributeValue target) {
        return p.getPredictedValue().equals(NodeClass.NEGATIVE)
                && !p.getSample().hasAttributeValue(target);
    }

    private boolean isFalseNegative(Prediction p, AttributeValue target) {
        return p.getPredictedValue().equals(NodeClass.NEGATIVE)
                && p.getSample().hasAttributeValue(target);
    }
}
