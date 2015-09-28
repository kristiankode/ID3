package id3.core.training.algorithms.gain;

import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeClass;
import id3.api.domain.attr.AttributeValue;
import id3.core.training.algorithms.entropy.SplitInformation;

import java.util.List;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;

/**
 * Calculates gain by dividing gain ratio on split information.
 */
public class GainRatio implements GainCalculator {

    private final InformationGainCalc gainCalc;
    private final SplitInformation splitInformation = new SplitInformation();

    public GainRatio(AttributeValue targetValue) {
        this.gainCalc = new InformationGainCalc(targetValue);
    }

    public double getGainFor(List<Sample> samples, AttributeClass attributeClass) {

        Double splitInfo = splitInformation.calculate(samples, attributeClass);
        if (splitInfo == 0) {
            splitInfo = 1.0; // avoid division by zero
        }

        return valueOf(gainCalc.getGainFor(samples, attributeClass))
                .divide(valueOf(splitInfo), 6, ROUND_HALF_UP)
                .doubleValue();
    }
}
