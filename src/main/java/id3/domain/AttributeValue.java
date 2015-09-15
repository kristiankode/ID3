package id3.domain;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public interface AttributeValue {

    AttributeClass getAttributeClass();

    String getValue();

    String getLabel();
}
