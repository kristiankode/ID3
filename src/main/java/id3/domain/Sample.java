package id3.domain;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public interface Sample {

    boolean isPositive();

    boolean isAttributesEmpty();

    boolean hasAttribute(AttributeClass attribute);

    AttributeValue getAttribute(AttributeClass label);

    /**
     * Gets the attribute that is the most effective for classifying the sample.
     */
    AttributeClass getBestAttribute();
}
