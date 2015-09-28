package id3.core.importing.build.attributes;

import id3.api.domain.attr.AttributeClass;
import id3.api.domain.attr.AttributeValue;
import id3.core.importing.build.attributes.missing.ReplaceMissingWithCommon;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author kristian
 *         Created 25.09.15.
 */
public class AttributeValueBuilder {

    private static final String MISSING_ATTRIBUTE = "?";
    ReplaceMissingWithCommon missingAttributeFixer;

    public AttributeValue build(AttributeClass attributeClass, String value) {

        AttributeValue attributeValue;

        if (missingAttributeFixer != null && isAttributeMissing(value)) {
            attributeValue = missingAttributeFixer.getReplacementFor(attributeClass);
        } else {
            attributeValue = new AttributeValue(attributeClass, value);
        }

        return attributeValue;
    }


    public ReplaceMissingWithCommon getMissingAttributeFixer() {
        return missingAttributeFixer;
    }

    public void setMissingAttributeFixer(ReplaceMissingWithCommon missingAttributeFixer) {
        this.missingAttributeFixer = missingAttributeFixer;
    }

    private boolean isAttributeMissing(String value) {
        return value.trim().equals(MISSING_ATTRIBUTE);
    }
}
