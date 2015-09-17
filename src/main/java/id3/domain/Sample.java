package id3.domain;

import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;

import java.util.List;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public interface Sample {

    boolean isPositive();

    boolean isAttributesEmpty();

    boolean hasAttribute(AttributeClass attribute);

    AttributeValue getAttribute(AttributeClass label);
    List<AttributeValue> getAttributes();

    /**
     * Gets the attribute that is the most effective for classifying the sample.
     */
    AttributeClass getBestAttribute();
}
