package id3.training.algorithms.selectors.attribute;

import id3.training.algorithms.gain.InformationGainCalc;
import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Selects the attribute that offers the highest information gain.
 *
 * @author kristian
 *         Created 15.09.15.
 */
public class InformationGainSelector {
    final static Logger log = LoggerFactory.getLogger(InformationGainSelector.class);
    final InformationGainCalc informationGainCalc;
    final AttributeValue targetAttribute;

    public InformationGainSelector(AttributeValue targetAttribute) {
        this.targetAttribute = targetAttribute;
        informationGainCalc = new InformationGainCalc(targetAttribute);
    }

    public AttributeClass selectAttribute(List<Sample> samples, List<AttributeClass> attributeClasses) {

        Double highestInfoGain = 0.0;
        AttributeClass bestAttribute = null;

        for (AttributeClass attributeClass : attributeClasses) {
            Double predictedInfoGain = informationGainCalc.getGainFor(samples, attributeClass);

            if (predictedInfoGain >= highestInfoGain) {
                bestAttribute = attributeClass;
                highestInfoGain = predictedInfoGain;
            }
        }

        log.debug("{} was selected based on its infogain: {}", bestAttribute, highestInfoGain);
        return bestAttribute;
    }
}
