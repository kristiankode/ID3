package id3.prediction.analysis;

import id3.analysis.ValueAnalyzer;
import id3.domain.Sample;
import id3.domain.attr.AttributeValue;
import id3.domain.tree.NodeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kristian
 *         Created 21.09.15.
 */
public class PredictionAccuracy {
    static Logger log = LoggerFactory.getLogger(PredictionAccuracy.class);

    public static boolean isPredictionCorrect(NodeClass prediction, Sample sample, AttributeValue target) {
        NodeClass correctPrediction = NodeClass.fromBoolean(ValueAnalyzer.sampleMatchesTarget(sample, target));

        log.debug("Sample was classified as {}, correct classification was {}", prediction, correctPrediction);

        return correctPrediction.equals(prediction);
    }
}
