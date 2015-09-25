package id3.training.algorithms.gain;

import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;
import id3.training.algorithms.entropy.ListEntropy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.RoundingMode;
import java.util.List;

import static id3.training.filter.SampleFilter.filterByAttributeValue;
import static java.math.BigDecimal.valueOf;

/**
 * Calculates information gain by checking the reduction in entropy by knowing the value of a given attribute.
 *
 * @author kristian
 *         Created 16.09.15.
 */
public class InformationGainCalc implements GainCalculator {
    final static Logger log = LoggerFactory.getLogger(InformationGainCalc.class);

    private static final ListEntropy entropyCalc = new ListEntropy();
    private static final int PRECISION = 4;

    private final AttributeValue targetAttribute;

    public InformationGainCalc(AttributeValue targetAttribute) {
        this.targetAttribute = targetAttribute;
    }

    public double getGainFor(List<Sample> samples, AttributeClass attributeClass) {
        Double initialEntropy = entropyCalc.calculateEntropy(samples, targetAttribute.getAttributeClass());
        Double expectedEntropy = getExpectedEntropy(samples, attributeClass);

        return initialEntropy - expectedEntropy;
    }

    private Double getExpectedEntropy(List<Sample> samples, AttributeClass attributeClass) {
        Double expectedEntropy = 0.0;

        // find subsets of samples for a given attribute value
        for (AttributeValue val : attributeClass.getPossibleValues()) {
            List<Sample> subset = filterByAttributeValue(samples, val);

            // find entropy of this subset
            Double subsetEntropy = entropyCalc.calculateEntropy(subset, targetAttribute);

            // adjust entropy for subset fraction size
            Double subsetWeight =
                    valueOf(subset.size()).divide(valueOf(samples.size()), PRECISION, RoundingMode.HALF_UP)
                            .doubleValue();

            Double subsetWeightedEntropy = (subsetEntropy * subsetWeight);
            // accumulate to total expected entropy
            expectedEntropy += subsetWeightedEntropy;
        }
        return expectedEntropy;
    }
}
