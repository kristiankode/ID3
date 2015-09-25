package id3.api.domain;

import id3.api.domain.attr.AttributeClass;
import id3.api.domain.attr.AttributeValue;

import java.util.List;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public interface Sample {

    boolean hasAttribute(AttributeClass attribute);

    boolean hasAttributeValue(AttributeValue val);

    AttributeValue getAttribute(AttributeClass label);
    List<AttributeValue> getAttributes();

}
