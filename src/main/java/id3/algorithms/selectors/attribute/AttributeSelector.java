package id3.algorithms.selectors.attribute;

import id3.domain.Sample;
import id3.domain.attr.AttributeClass;

import java.util.List;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public interface AttributeSelector {

    AttributeClass selectAttribute(List<Sample> samples, List<AttributeClass> attributeClasses);
}
