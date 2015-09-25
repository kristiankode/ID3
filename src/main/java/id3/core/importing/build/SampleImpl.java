package id3.core.importing.build;

import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeClass;
import id3.api.domain.attr.AttributeValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public class SampleImpl implements Sample {

    private List<AttributeValue> attributes = new ArrayList<AttributeValue>();

    public SampleImpl(AttributeValue... attributes) {
        this.attributes = Arrays.asList(attributes);
    }

    public boolean hasAttribute(AttributeClass attributeClass) {
        return getAttribute(attributeClass) != null;
    }

    public boolean hasAttributeValue(AttributeValue val) {
        for (AttributeValue value : attributes) {
            if (value.equals(val)) {
                return true;
            }
        }
        return false;
    }

    public AttributeValue getAttribute(AttributeClass label) {
        for (AttributeValue attr : attributes) {
            if (attr.getAttributeClass().equals(label)) {
                return attr;
            }
        }
        return null;
    }

    public List<AttributeValue> getAttributes() {
        return attributes;
    }

    public String toString() {
        String desc = "Sample [";

        for (AttributeValue val : attributes) {
            desc += val.getLabel() + ":" + val.getValue();
            desc += " ";
        }
        desc += "]";
        return desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SampleImpl)) return false;

        SampleImpl sample = (SampleImpl) o;

        return !(getAttributes() != null ? !getAttributes().equals(sample.getAttributes()) : sample.getAttributes() != null);

    }

    @Override
    public int hashCode() {
        return getAttributes() != null ? getAttributes().hashCode() : 0;
    }
}
