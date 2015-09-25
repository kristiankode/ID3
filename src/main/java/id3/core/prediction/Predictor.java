package id3.core.prediction;

import id3.api.domain.Model;
import id3.api.domain.Sample;

import java.util.List;

/**
 * @author kristian
 *         Created 22.09.15.
 */
public interface Predictor {
    List<Prediction> predict(Model model, List<Sample> unseenSamples);

    Prediction predictSample(Model model, Sample sample);
}
