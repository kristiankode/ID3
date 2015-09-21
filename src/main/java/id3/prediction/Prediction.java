package id3.prediction;

import id3.domain.Sample;
import id3.domain.tree.NodeClass;

/**
 * @author kristian
 *         Created 21.09.15.
 */
public class Prediction {

    final Sample sample;
    final NodeClass predictedValue;


    public Prediction(Sample sample, NodeClass predictedValue) {
        this.sample = sample;
        this.predictedValue = predictedValue;
    }

    public Sample getSample() {
        return sample;
    }

    public NodeClass getPredictedValue() {
        return predictedValue;
    }
}
