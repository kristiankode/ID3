package id3.core.importing.build;

import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeClass;
import id3.api.domain.attr.AttributeValue;
import id3.core.importing.build.attributes.AttributeValueBuilder;
import id3.core.importing.build.attributes.missing.ReplaceMissingWithCommon;
import id3.core.importing.read.DataReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class SampleBuilder {
    private AttributeValueBuilder valueBuilder = new AttributeValueBuilder();

    public void preProcess(DataReader reader){
        ReplaceMissingWithCommon missingAttributesFixer = new ReplaceMissingWithCommon(reader);
        valueBuilder.setMissingAttributeFixer(missingAttributesFixer);
    }

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

            AttributeValue val = valueBuilder.build(existingClass, row[columnIndex]);
            sampleValues[columnIndex] = val;
        }

        return new Sample(sampleValues);
    }

}
