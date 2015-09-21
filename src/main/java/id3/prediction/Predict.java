package id3.prediction;

import id3.analysis.ValueAnalyzer;
import id3.domain.Model;
import id3.domain.Sample;
import id3.domain.tree.Node;
import id3.domain.tree.NodeClass;
import id3.prediction.analysis.PredictionEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kristian
 *         Created 21.09.15.
 */
public class Predict {
    Logger log = LoggerFactory.getLogger(Predict.class);

    public List<Prediction> predict(Model model, List<Sample> unseenSamples) {

        List<Prediction> predictions = new ArrayList<Prediction>();

        for (Sample sample : unseenSamples) {
            Prediction result = new Prediction(sample, predictClass(model, sample));
            predictions.add(result);
        }

        PredictionEvaluator.evaluatePredictionAccuracy(predictions, model.getTargetAttribute());

        return predictions;
    }

    public NodeClass predictClass(Model model, Sample sample) {

        return predictRecursively(model.getTree(), sample);
    }

    private NodeClass predictRecursively(Node model, Sample sample) {

        NodeClass classification = null;

        if (model.isLeaf()) {
            classification = model.getClassification();

        } else {
            for (Node child : model.getChildren()) {
                if (ValueAnalyzer.sampleMatchesTarget(sample, child.getAttributeValue())) {
                    log.debug("Sample matches node {} (leaf={}), expanding subtree",
                            child.description(), child.isLeaf());

                    classification = predictRecursively(child, sample);
                    break;
                }
            }
        }
        return classification;
    }
}
