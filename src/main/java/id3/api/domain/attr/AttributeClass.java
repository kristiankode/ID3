package id3.api.domain.attr;

import id3.core.importing.build.attributes.AttributeValueBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains the name of an attribute and all its possible values.
 */
public class AttributeClass {

    private final List<AttributeValue> possibleValues;
    private final String label;

    public AttributeClass(String label, String... possibleValues) {
        this.label = label;

        AttributeValueBuilder attributeValueBuilder = new AttributeValueBuilder();

        List<AttributeValue> tmpVals = new ArrayList<AttributeValue>();
        for (String value : possibleValues) {
            AttributeValue val = attributeValueBuilder.build(this, value);
            tmpVals.add(val);
        }
        this.possibleValues = tmpVals;
    }

    public List<AttributeValue> getPossibleValues() {
        return possibleValues;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AttributeClass attrClass = (AttributeClass) o;

        return label.equals(attrClass.label);

    }

    @Override
    public int hashCode() {
        return label.hashCode();
    }

    @Override
    public String toString() {
        return label;
    }
}