package id3.api.domain.attr;

/**
 * Contains a specific value for an attribute.
 */
public class AttributeValue {

    private final AttributeClass attributeClass;
    private final String value;

    public AttributeValue(AttributeClass attrClass, String value) {
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

        AttributeValue attrValue = (AttributeValue) o;

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
