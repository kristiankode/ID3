package id3.core.importing.build.attributes;

import id3.api.domain.attr.AttributeClass;
import id3.api.domain.attr.AttributeValue;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public class AttrValueImpl implements AttributeValue {

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
