package id3.algorithms.entropy;

import id3.domain.Sample;

import java.util.List;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class ListEntropy {

    EntropyCalculator entropy = new EntropyCalculator();

    public double calculateEntropy(List<Sample> samples) {
        int positive = countPositiveSamples(samples);
        int negative = countNegativeSamples(samples);

        return entropy.calc(positive, negative);
    }

    int countPositiveSamples(List<Sample> samples) {
        int count = 0;
        for(Sample sample : samples) {
            if(sample.isPositive()) {
                count ++;
            }
        }
        return count;
    }

    int countNegativeSamples(List<Sample> samples) {
        int count = 0;
        for(Sample sample : samples) {
            if(!sample.isPositive()) {
                count ++;
            }
        }
        return count;
    }
}
