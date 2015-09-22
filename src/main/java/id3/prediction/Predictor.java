package id3.prediction;

import id3.domain.Model;
import id3.domain.Sample;
import id3.domain.tree.NodeClass;

import java.util.List;

/**
 * @author kristian
 *         Created 22.09.15.
 */
public interface Predictor {
    List<Prediction> predict(Model model, List<Sample> unseenSamples);

    Prediction predictSample(Model model, Sample sample);
}
