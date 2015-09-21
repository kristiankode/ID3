package id3.prediction;

import id3.analysis.ValueAnalyzer;
import id3.domain.Sample;
import id3.domain.tree.Node;
import id3.domain.tree.NodeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kristian
 *         Created 21.09.15.
 */
public class Predict {
    Logger log = LoggerFactory.getLogger(Predict.class);

    public NodeClass predictClass(Node model, Sample sample) {

        NodeClass classification = null;

        if (model.isLeaf()) {
            classification = model.getClassification();

        } else {
            if (model.getChildren() == null || model.getChildren().isEmpty()) {
                log.debug("{} was a childless non-leaf", model.description());
            }

            boolean found = false;
            for (Node child : model.getChildren()) {
                log.debug("Checking child: {}", child.description());

                if (ValueAnalyzer.sampleMatchesTarget(sample, child.getAttributeValue())) {
                    found = true;
                    log.debug("Sample matches node {} (leaf={}), expanding subtree",
                            child.description(), child.isLeaf());

                    classification = predictClass(child, sample);
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
