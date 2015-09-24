package id3.analysis;

import id3.domain.Sample;
import id3.domain.attr.AttributeValue;
import id3.domain.tree.NodeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static id3.domain.tree.NodeClass.NEGATIVE;
import static id3.domain.tree.NodeClass.POSITIVE;

/**
 * @author kristian
 *         Created 16.09.15.
 */
@SuppressWarnings("unused")
public class ValueAnalyzer {
    static Logger log = LoggerFactory.getLogger(ValueAnalyzer.class);

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
        return target != null && target.equals(sample.getAttribute(target.getAttributeClass()));
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
        return true;
    }

    public static boolean allSamplesNegative(List<Sample> samples, AttributeValue targetAttribute) {

        for (Sample sample : samples) {
            if (sampleMatchesTarget(sample, targetAttribute)) {
                return false;
            }
        }
        return true;
    }
}
