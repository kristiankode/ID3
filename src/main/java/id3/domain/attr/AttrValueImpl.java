package id3.domain.attr;

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
}
