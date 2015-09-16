package id3.algorithms.entropy;

import id3.domain.Sample;
import id3.domain.attr.AttributeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static id3.analysis.ValueAnalyzer.sampleMatchesTarget;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class ListEntropy {
    final static Logger log = LoggerFactory.getLogger(ListEntropy.class);

    EntropyCalculator entropy = new EntropyCalculator();

    public double calculateEntropy(List<Sample> samples, AttributeValue targetAttribute) {
        log.debug("Calculating entropy for {} samples with target {}",
                samples.size(), targetAttribute.getValue());

        int positive = countPositiveSamples(samples, targetAttribute);
        int negative = countNegativeSamples(samples, targetAttribute);

        return entropy.calc(positive, negative);
    }

    int countPositiveSamples(List<Sample> samples, AttributeValue target) {
        int count = 0;
        for (Sample sample : samples) {
            if (sampleMatchesTarget(sample, target)) {
                count++;
            }
        }
        return count;
    }

    int countNegativeSamples(List<Sample> samples, AttributeValue target) {
        int count = 0;
        for (Sample sample : samples) {
            if (!sampleMatchesTarget(sample, target)) {
                count++;
            }
        }
        return count;
    }
}
