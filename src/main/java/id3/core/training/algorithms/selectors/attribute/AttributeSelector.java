package id3.core.training.algorithms.selectors.attribute;

import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeClass;
import id3.core.training.algorithms.gain.GainCalculator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Selects the attribute that offers the highest information gain.
 *
 * @author kristian
 *         Created 15.09.15.
 */
public class AttributeSelector {
    private final static Logger log = LoggerFactory.getLogger(AttributeSelector.class);
    private final GainCalculator gainCalculator;

    public AttributeSelector(GainCalculator gainCalculator) {
        this.gainCalculator = gainCalculator;
    }

    public AttributeClass selectAttribute(List<Sample> samples, List<AttributeClass> attributeClasses) {

        Double highestInfoGain = 0.0;
        AttributeClass bestAttribute = null;

        for (AttributeClass attributeClass : attributeClasses) {
            Double predictedInfoGain = gainCalculator.getGainFor(samples, attributeClass);

            if (predictedInfoGain >= highestInfoGain) {
                bestAttribute = attributeClass;
                highestInfoGain = predictedInfoGain;
            }
        }

        log.debug("{} was selected based on its infogain: {}", bestAttribute, highestInfoGain);
        return bestAttribute;
    }
}
