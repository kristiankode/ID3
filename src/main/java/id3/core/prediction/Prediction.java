package id3.core.prediction;

import id3.api.domain.Rule;
import id3.api.domain.Sample;
import id3.api.domain.tree.NodeClass;

/**
 * Class containing the result of a prediction.
 * The class includes the sample that was predicted, the predicted value,
 * and the rule that was used as basis for the prediction
 */
public class Prediction {

    private final Sample sample;
    private final NodeClass predictedValue;
    private final Rule reason;

    public Prediction(Sample sample, NodeClass predictedValue) {
        this.sample = sample;
        this.predictedValue = predictedValue;
        reason = null;
    }

    public Prediction(Sample sample, NodeClass predictedValue, Rule reason) {
        this.sample = sample;
        this.predictedValue = predictedValue;
        this.reason = reason;
    }

    public Sample getSample() {
        return sample;
    }

    public NodeClass getPredictedValue() {
        return predictedValue;
    }

    public String toString() {
        return sample + " predicted as " + predictedValue + " because of rule [" + reason + "]";
    }
}
