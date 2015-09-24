package id3.prediction;

import id3.domain.Rule;
import id3.domain.Sample;
import id3.domain.tree.NodeClass;

/**
 * @author kristian
 *         Created 21.09.15.
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

    public String toString(){
        return sample + " predicted as " + predictedValue + " because of rule [" + reason +"]";
    }
}
