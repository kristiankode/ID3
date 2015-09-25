package id3.training.algorithms.entropy;

import id3.domain.Sample;
import id3.domain.attr.AttributeClass;

import java.util.List;

/**
 * @author kristian
 *         Created 25.09.15.
 */
public class SplitInformation {

    private final ListEntropy entropyCalculator = new ListEntropy();

    public Double calculate(List<Sample> samples, AttributeClass attribute) {
        return entropyCalculator.calculateEntropy(samples, attribute);
    }
}
