package id3.analysis;

import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;
import id3.domain.tree.NodeClass;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static id3.domain.tree.NodeClass.NEGATIVE;
import static id3.domain.tree.NodeClass.POSITIVE;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class ValueAnalyzer {

    public static AttributeValue mostCommonValueOfAttrInSample(AttributeClass attrClass, List<Sample> samples) {
        Map<AttributeValue, Long> counters = new HashMap<AttributeValue, Long>();

        // count
        for (AttributeValue val : attrClass.getPossibleValues()) {
            Long attrCount = 0l;

            for (Sample sample : samples) {
                if (sample.getAttribute(attrClass).equals(val)) {
                    attrCount++;
                }
            }

            counters.put(val, attrCount);
        }

        // find most common value
        AttributeValue mostCommon = null;
        Long topOccurrence = 0l;
        for (AttributeValue val : counters.keySet()) {
            Long current = counters.get(val);
            if (current >= topOccurrence) {
                topOccurrence = current;
                mostCommon = val;
            }
        }

        return mostCommon;
    }

    /**
     * Finds the most frequent classification in a set.
     */
    public static NodeClass mostCommonValueIn(List<Sample> samples, AttributeValue target) {
        int positive = 0, negative = 0;

        for (Sample sample : samples) {
            if (sampleMatchesTarget(sample, target)) {
                positive++;
            } else {
                negative++;
            }
        }

        if (positive > negative) {
            return POSITIVE;
        } else {
            return NEGATIVE;
        }
    }

    public static boolean sampleMatchesTarget(Sample sample, AttributeValue target) {
        return target.equals(sample.getAttribute(target.getAttributeClass()));
    }

    /**
     * Checks if all samples have the same value for the target attribute
     *
     * @param samples
     * @param targetAttribute
     * @return
     */
    public static boolean allSamplesPositive(List<Sample> samples, AttributeValue targetAttribute) {

        for (Sample sample : samples) {
            if (!sampleMatchesTarget(sample, targetAttribute)) {
                return false;
            }
        }
        System.out.println("All " + samples.size() + " values were equal to target (" + targetAttribute.getValue() + ")");
        return true;
    }

    public static boolean allSamplesNegative(List<Sample> samples, AttributeValue targetAttribute) {

        for (Sample sample : samples) {
            if (sampleMatchesTarget(sample, targetAttribute)) {
                return false;
            }
        }
        System.out.println("All " + samples.size() + " values were dissimilar to target (" + targetAttribute.getValue() + ")");
        return true;
    }
}
