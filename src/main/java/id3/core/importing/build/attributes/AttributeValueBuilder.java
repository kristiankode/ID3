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
    private static final Logger log = LoggerFactory.getLogger(AttributeValueBuilder.class);

    private static final String MISSING_ATTRIBUTE = "?";
    ReplaceMissingWithCommon missingAttributeFixer;

    public AttributeValue build(AttributeClass attributeClass, String value) {

        AttributeValue attributeValue;

        if (missingAttributeFixer != null && isAttributeMissing(value)) {
            attributeValue = missingAttributeFixer.getReplacementFor(attributeClass);
        } else {
            attributeValue = new AttrValueImpl(attributeClass, value);
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


    private class AttrValueImpl implements AttributeValue {

        private final AttributeClass attributeClass;
        private final String value;

        public AttrValueImpl(AttributeClass attrClass, String value) {
            this.attributeClass = attrClass;
            this.value = value;
        }

        public AttributeClass getAttributeClass() {
            return attributeClass;
        }

        public String getValue() {
            return value;
        }

        public String getLabel() {
            return attributeClass.getLabel();
        }

        public String toString() {
            return getLabel() + " = " + value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            AttrValueImpl attrValue = (AttrValueImpl) o;

            if (!attributeClass.equals(attrValue.attributeClass)) return false;
            return !(value != null ? !value.equals(attrValue.value) : attrValue.value != null);

        }

        @Override
        public int hashCode() {
            int result = attributeClass.hashCode();
            result = 31 * result + (value != null ? value.hashCode() : 0);
            return result;
        }
    }

}
