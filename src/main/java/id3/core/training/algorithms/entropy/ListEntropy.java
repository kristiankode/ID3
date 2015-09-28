package id3.core.training.algorithms.entropy;

import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeClass;
import id3.api.domain.attr.AttributeValue;

import java.util.ArrayList;
import java.util.List;

import static id3.core.util.ValueAnalyzer.sampleMatchesTarget;

/**
 * Calculates entropy for a list of samples.
 */
public class ListEntropy {
    private final EntropyCalculator entropy = new EntropyCalculator();

    /**
     * Calculates the entropy of a list of samples with only two possible classifications.
     * @param samples The samples to find entropy of.
     * @param targetAttribute The positive value of the target attribute.
     * @return
     * @deprecated Replaced by the more general variant.
     */
    @Deprecated
    public double calculateEntropy(List<Sample> samples, AttributeValue targetAttribute) {
        int positive = countPositiveSamples(samples, targetAttribute);
        int negative = countNegativeSamples(samples, targetAttribute);

        return entropy.calc(positive, negative);
    }

    /**
     * Calculates the entropy of a list of samples with any number of possible classifications.
     * @param samples The samples to find entropy of.
     * @param attribute The target attribute class (all possible values for target).
     * @return
     */
    public double calculateEntropy(List<Sample> samples, AttributeClass attribute) {
        return entropy.calc(getSubsetSizes(samples, attribute));
    }

    /**
     * Divides samples into subsets with the same attribute value, returns the size of each subset.
     */
    private Integer[] getSubsetSizes(List<Sample> samples, AttributeClass attribute) {
        int numberOfSubsets = attribute.getPossibleValues().size();

        List<Integer> subsetSizes = new ArrayList<Integer>();

        for (AttributeValue value : attribute.getPossibleValues()) {
            Integer subsetSize = 0;
            for (Sample sample : samples) {
                if (sampleMatchesTarget(sample, value)) {
                    subsetSize++;
                }
            }
            subsetSizes.add(subsetSize);
        }

        return subsetSizes.toArray(new Integer[numberOfSubsets]);
    }

    private int countPositiveSamples(List<Sample> samples, AttributeValue target) {
        int count = 0;
        for (Sample sample : samples) {
            if (sampleMatchesTarget(sample, target)) {
                count++;
            }
        }
        return count;
    }

    private int countNegativeSamples(List<Sample> samples, AttributeValue target) {
        int count = 0;
        for (Sample sample : samples) {
            if (!sampleMatchesTarget(sample, target)) {
                count++;
            }
        }
        return count;
    }
}
