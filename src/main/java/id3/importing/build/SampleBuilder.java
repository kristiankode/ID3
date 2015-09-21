package id3.importing.build;

import id3.domain.Sample;
import id3.domain.SampleImpl;
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
 *         Created 16.09.15.
 */
public class SampleBuilder {
    final static Logger log = LoggerFactory.getLogger(SampleBuilder.class);


    public List<Sample> buildSamples(DataReader reader, List<AttributeClass> attributes) {
        List<Sample> samples = new ArrayList<Sample>();

        reader.startFromTop();
        String[] line;
        while ((line = reader.readDataRows()) != null) {
            Sample s = buildSample(line, attributes);
            samples.add(s);
        }

        return samples;
    }

    public Sample buildSample(String[] row, List<AttributeClass> attrClasses) {

        AttributeValue[] sampleValues = new AttributeValue[attrClasses.size()];

        for (int columnIndex = 0; columnIndex < attrClasses.size(); columnIndex++) {
            AttributeClass existingClass = attrClasses.get(columnIndex);

            AttributeValue val = new AttrValueImpl(existingClass, row[columnIndex]);
            sampleValues[columnIndex] = val;
        }

        return new SampleImpl(sampleValues);
    }
}
