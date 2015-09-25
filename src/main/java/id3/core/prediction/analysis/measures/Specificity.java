package id3.core.prediction.analysis.measures;

import id3.api.domain.attr.AttributeValue;
import id3.api.domain.tree.NodeClass;
import id3.core.prediction.Prediction;

/**
 * Measures specificity of a prediction.
 * <p>
 * Specificity (also called the true negative rate) measures the proportion of negatives that are correctly
 * identified as such (e.g., the percentage of healthy people who are correctly identified as not having the condition)
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
