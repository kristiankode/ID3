package id3.importing.build.attributes;

import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public class AttrClassImpl implements AttributeClass {

    private final List<AttributeValue> possibleValues;
    private final String label;

    public AttrClassImpl(String label, String... possibleValues) {
        this.label = label;

        List<AttributeValue> tmpVals = new ArrayList<AttributeValue>();
        for(String value : possibleValues) {
            AttributeValue val = new AttrValueImpl(this, value);
            tmpVals.add(val);
        }
        this.possibleValues = tmpVals;
    }

    public void addPossibleValue(AttributeValue value) {
        possibleValues.add(value);
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

        AttrClassImpl attrClass = (AttrClassImpl) o;

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