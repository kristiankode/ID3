package id3.algorithms.gain;

import id3.algorithms.entropy.ListEntropy;
import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;

import java.math.RoundingMode;
import java.util.List;

import static id3.filter.SampleFilter.filterByAttributeValue;
import static java.math.BigDecimal.valueOf;

/**
 * Calculates information gain by checking the reduction in entropy by knowing the value of a given attribute.
 *
 * @author kristian
 *         Created 16.09.15.
 */
public class InformationGainCalc implements GainCalculator {

    static final ListEntropy entropyCalc = new ListEntropy();
    public static final int PRECISION = 4;

    public double getGainFor(List<Sample> samples, AttributeClass attributeClass) {
        Double initialEntropy = entropyCalc.calculateEntropy(samples);
        Double expectedEntropy = getExpectedEntropy(samples, attributeClass);

        return initialEntropy - expectedEntropy;
    }

    private Double getExpectedEntropy(List<Sample> samples, AttributeClass attributeClass) {
        Double expectedEntropy = 0.0;

        // find subsets of samples for a given attribute value
        for (AttributeValue val : attributeClass.getPossibleValues()) {
            List<Sample> subset = filterByAttributeValue(samples, val);

            // find entropy of this subset
            Double subsetEntropy = entropyCalc.calculateEntropy(subset);

            // adjust entropy for subset fraction size
            Double subsetWeight =
                    valueOf(subset.size()).divide(valueOf(samples.size()), PRECISION, RoundingMode.HALF_UP)
                            .doubleValue();

            // accumulate to total expected entropy
            expectedEntropy += subsetEntropy * subsetWeight;
        }
        return expectedEntropy;
    }
}
