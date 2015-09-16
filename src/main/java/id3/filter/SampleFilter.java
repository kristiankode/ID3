package id3.filter;

import id3.domain.Sample;
import id3.domain.attr.AttributeValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class SampleFilter {

    public static List<Sample> filterByAttributeValue(List<Sample> unfiltered, AttributeValue attribute) {

        List<Sample> matchingSamples = new ArrayList<Sample>();
        for (Sample sample : unfiltered) {
            if (sample.hasAttribute(attribute.getAttributeClass())) {

                AttributeValue attrVal = sample.getAttribute(attribute.getAttributeClass());
                if (attrVal.equals(attribute)) {
                    matchingSamples.add(sample);
                }
            }
        }
        return matchingSamples;
    }

}
