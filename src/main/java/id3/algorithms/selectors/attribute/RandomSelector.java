package id3.algorithms.selectors.attribute;

import id3.domain.Sample;
import id3.domain.attr.AttributeClass;

import java.util.List;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public class RandomSelector implements AttributeSelector {
    public AttributeClass selectAttribute(List<Sample> samples, List<AttributeClass> attributeClasses) {

        return attributeClasses.get(0);
    }
}
