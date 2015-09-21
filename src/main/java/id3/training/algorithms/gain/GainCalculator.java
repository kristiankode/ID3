package id3.training.algorithms.gain;

import id3.domain.Sample;
import id3.domain.attr.AttributeClass;

import java.util.List;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public interface GainCalculator {

    double getGainFor(List<Sample> samples, AttributeClass attributeClass);
}
