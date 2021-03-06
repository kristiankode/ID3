package id3.core.prediction.analysis.measures;

import id3.api.domain.attr.AttributeValue;
import id3.core.prediction.Prediction;

import java.math.RoundingMode;
import java.util.List;

import static java.math.BigDecimal.valueOf;

/**
 * @author kristian
 *         Created 24.09.15.
 */
public abstract class PerformanceEvaluator {

    public static final int precision = 10;

    public Double evaluate(List<Prediction> predictions, AttributeValue target) {
        int correct = 0, wrong = 0;
        for (Prediction p : predictions) {
            if (isCorrectlyClassified(p, target)) {
                correct++;
            } else if (isWronglyClassified(p, target)) {
                wrong++;
            }
        }

        return rate(correct, (correct + wrong));
    }

    protected abstract boolean isCorrectlyClassified(Prediction prediction, AttributeValue target);

    protected abstract boolean isWronglyClassified(Prediction prediction, AttributeValue target);

    private Double rate(int count, int total) {
        if(total == 0){
            return 0.0; // avoid division by zero
        }
        return valueOf(count).divide(valueOf(total), precision, RoundingMode.HALF_UP)
                .multiply(valueOf(100)).doubleValue();
    }

}
