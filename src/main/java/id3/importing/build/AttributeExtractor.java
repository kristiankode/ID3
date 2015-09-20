package id3.importing.build;

import id3.domain.attr.AttrClassImpl;
import id3.domain.attr.AttrValueImpl;
import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;
import id3.importing.read.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kristian
 *         Created 20.09.15.
 */
public class AttributeExtractor {
    final static Logger log = LoggerFactory.getLogger(SampleBuilder.class);

    List<AttributeClass> attributes = new ArrayList<AttributeClass>();

    public List<AttributeClass> getAllAttributes(DataReader reader) {

        // load labels from header
        attributes = identifyAttributes(reader.readHeaderRow());

        // update list with new possible values
        String[] row;
        while ((row = reader.readDataRows()) != null) {

            for (int columnIndex = 0; columnIndex < attributes.size(); columnIndex++) {
                AttributeClass columnHeader = attributes.get(columnIndex);
                AttributeValue columnValue = new AttrValueImpl(columnHeader, row[columnIndex]);

                updatePossibleValuesIfNecessary(columnValue, columnIndex);
            }
        }

        return attributes;
    }

    private void updatePossibleValuesIfNecessary(AttributeValue val, int index) {
        // add value to possible values, if not already there
        AttributeClass existingClass = attributes.get(index);
        if (!existingClass.getPossibleValues().contains(val)) {
            val.getAttributeClass().getPossibleValues().add(val);
            log.debug("Updated attribute {}, now contains {} possible values",
                    existingClass, attributes.get(index).getPossibleValues().size());
        }
    }

    List<AttributeClass> identifyAttributes(String[] attributeLabels) {
        List<AttributeClass> attrClasses = new ArrayList<AttributeClass>();

        for (String s : attributeLabels) {
            attrClasses.add(new AttrClassImpl(s));
        }
        return attrClasses;
    }
}
