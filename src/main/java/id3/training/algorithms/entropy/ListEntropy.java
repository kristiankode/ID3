package id3.training.algorithms.entropy;

import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static id3.analysis.ValueAnalyzer.sampleMatchesTarget;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class ListEntropy {
    final static Logger log = LoggerFactory.getLogger(ListEntropy.class);

    private final EntropyCalculator entropy = new EntropyCalculator();

    @Deprecated
    public double calculateEntropy(List<Sample> samples, AttributeValue targetAttribute) {
        int positive = countPositiveSamples(samples, targetAttribute);
        int negative = countNegativeSamples(samples, targetAttribute);

        return entropy.calc(positive, negative);
    }

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
