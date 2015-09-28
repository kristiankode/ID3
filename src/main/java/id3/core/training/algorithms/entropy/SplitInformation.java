package id3.core.training.algorithms.entropy;

import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeClass;

import java.util.List;

/**
 * Calculates the split information for a list of samples.
 */
public class SplitInformation {

    private final ListEntropy entropyCalculator = new ListEntropy();

    public Double calculate(List<Sample> samples, AttributeClass attribute) {
        return entropyCalculator.calculateEntropy(samples, attribute);
    }
}
