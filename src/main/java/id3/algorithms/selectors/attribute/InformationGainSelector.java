package id3.algorithms.selectors.attribute;

import id3.algorithms.gain.InformationGainCalc;
import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Selects the attribute that offers the highest information gain.
 *
 * @author kristian
 *         Created 15.09.15.
 */
public class InformationGainSelector implements AttributeSelector {
    final static Logger log = LoggerFactory.getLogger(InformationGainSelector.class);

    InformationGainCalc informationGainCalc = new InformationGainCalc();

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
