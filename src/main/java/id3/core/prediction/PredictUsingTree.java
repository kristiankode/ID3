package id3.core.prediction;

import id3.api.domain.Model;
import id3.api.domain.Sample;
import id3.api.domain.tree.Node;
import id3.api.domain.tree.NodeClass;
import id3.core.analysis.ValueAnalyzer;
import id3.core.prediction.analysis.measures.Accuracy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kristian
 *         Created 21.09.15.
 */
public class PredictUsingTree {
    private final Accuracy accuracy = new Accuracy();

    public List<Prediction> predict(Model model, List<Sample> unseenSamples) {

        List<Prediction> predictions = new ArrayList<Prediction>();

        for (Sample sample : unseenSamples) {
            Prediction result = predictSample(model, sample);
            predictions.add(result);
        }

        accuracy.evaluate(predictions, model.getTargetAttribute());

        return predictions;
    }

    public Prediction predictSample(Model model, Sample sample) {

        return new Prediction(sample, predictRecursively(model.getTree(), sample));
    }

    private NodeClass predictRecursively(Node model, Sample sample) {

        NodeClass classification = null;

        if (model.isLeaf()) {
            classification = model.getClassification();

        } else {
            for (Node child : model.getChildren()) {
                if (ValueAnalyzer.sampleMatchesTarget(sample, child.getAttributeValue())) {
                    classification = predictRecursively(child, sample);
                    break;
                }
            }
        }
        return classification;
    }
}
