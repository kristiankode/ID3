package id3.domain.attr;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public class AttrClassImpl implements AttributeClass{

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

    public List<AttributeValue> getPossibleValues() {
        return possibleValues;
    }

    public String getLabel() {
        return label;
    }
}
