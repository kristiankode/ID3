package id3.importing;

import id3.domain.Sample;
import id3.domain.SampleImpl;
import id3.domain.attr.AttrClassImpl;
import id3.domain.attr.AttrValueImpl;
import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;
import id3.importing.read.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class SampleBuilder {
    final static Logger log = LoggerFactory.getLogger(SampleBuilder.class);

    public List<Sample> buildSamples(DataReader reader) {

        // read first row (header), collect all column names (attribute classes)
        String[] attributeLabels = reader.readHeaderRow();
        Map<Integer, AttrClassImpl> attrClasses = identifyAttributes(attributeLabels);
        List<Sample> samples = new ArrayList<Sample>();

        String[] line;
        while ((line = reader.readDataRows()) != null) {
            Sample s = buildSample(line, attrClasses);
            log.debug("Created {}", s);
            samples.add(s);
        }

        return samples;

    }

    Sample buildSample(String[] row, Map<Integer, AttrClassImpl> attrClasses) {

        AttributeValue[] sampleValues = new AttributeValue[attrClasses.size()];

        for (int columnIndex = 0; columnIndex < attrClasses.size(); columnIndex++) {
            AttributeClass existingClass = attrClasses.get(columnIndex);

            AttributeValue val = new AttrValueImpl(existingClass, row[columnIndex]);
            sampleValues[columnIndex] = val;
            updateMapIfNecessary(attrClasses, val, columnIndex);
        }

        return new SampleImpl(sampleValues);
    }

    private void updateMapIfNecessary(Map<Integer, AttrClassImpl> map, AttributeValue val, int index) {
        // add value to possible values, if not already there
        AttrClassImpl existingClass = map.get(index);
        if (!existingClass.getPossibleValues().contains(val)) {
            val.getAttributeClass().getPossibleValues().add(val);

            log.debug("Updated attribute {}, now contains {} possible values",
                    existingClass, map.get(index).getPossibleValues().size());
        }
    }

    Map<Integer, AttrClassImpl> identifyAttributes(String[] attributeLabels) {
        Map<Integer, AttrClassImpl> attrClasses = new HashMap<Integer, AttrClassImpl>();

        int columnIndex = 0;

        for (String s : attributeLabels) {
            attrClasses.put(columnIndex, new AttrClassImpl(s));
            columnIndex++;
        }
        return attrClasses;
    }
}
