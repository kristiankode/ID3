package id3.core.training.algorithms.gain;

import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeClass;

import java.util.List;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public interface GainCalculator {

    double getGainFor(List<Sample> samples, AttributeClass attributeClass);
}
