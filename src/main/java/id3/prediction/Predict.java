package id3.prediction;

import id3.analysis.ValueAnalyzer;
import id3.domain.Model;
import id3.domain.Sample;
import id3.domain.tree.Node;
import id3.domain.tree.NodeClass;
import id3.prediction.analysis.PredictionAccuracy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kristian
 *         Created 21.09.15.
 */
public class Predict {
    Logger log = LoggerFactory.getLogger(Predict.class);

    public NodeClass predictClass(Model model, Sample sample) {

        NodeClass prediction = predictRecursively(model.getTree(), sample);

        PredictionAccuracy.isPredictionCorrect(prediction, sample, model.getTargetAttribute());

        return prediction;
    }

    private NodeClass predictRecursively(Node model, Sample sample) {

        NodeClass classification = null;

        if (model.isLeaf()) {
            classification = model.getClassification();

        } else {
            boolean found = false;
            for (Node child : model.getChildren()) {
                if (ValueAnalyzer.sampleMatchesTarget(sample, child.getAttributeValue())) {
                    found = true;
                    log.debug("Sample matches node {} (leaf={}), expanding subtree",
                            child.description(), child.isLeaf());

                    classification = predictRecursively(child, sample);
                    break;
                }
            }

            if (!found) {
                log.debug("Found no samples matching given attribute");
            }
        }
        return classification;
    }
}
