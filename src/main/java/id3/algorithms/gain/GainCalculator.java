package id3.algorithms.gain;

import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;

import java.util.List;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public interface GainCalculator {

    double getGainFor(List<Sample> samples, AttributeClass attributeClass);
}
