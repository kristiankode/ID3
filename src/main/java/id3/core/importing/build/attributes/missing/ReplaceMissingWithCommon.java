package id3.core.importing.build.attributes.missing;

import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeClass;
import id3.api.domain.attr.AttributeValue;
import id3.core.util.ValueAnalyzer;
import id3.core.importing.build.SampleBuilder;
import id3.core.importing.build.attributes.AttributeExtractor;
import id3.core.importing.read.DataReader;

import java.util.ArrayList;
import java.util.List;

public class ReplaceMissingWithCommon {
    private final DataReader reader;
    private List<AttributeValue> replacementValues;

    public ReplaceMissingWithCommon(DataReader reader) {
        this.reader = reader;
        init();
    }

    public void init() {
        reader.startFromTop();
        List<AttributeClass> attributes = retrieveAttributes();
        List<Sample> unprocessedSamples = retrieveSamples(attributes);

        replacementValues = findReplacementValues(unprocessedSamples, attributes);
    }

    public AttributeValue getReplacementFor(AttributeClass attribute) {
        for (AttributeValue value : replacementValues) {
            if (value.getAttributeClass().equals(attribute)) {
                return value;
            }
        }
        return null;
    }

    private List<AttributeClass> retrieveAttributes() {
        AttributeExtractor attributeExtractor = new AttributeExtractor();

        return attributeExtractor.getAllAttributes(reader);
    }

    private List<Sample> retrieveSamples(List<AttributeClass> attributes) {

        SampleBuilder builder = new SampleBuilder();
        return builder.buildSamples(reader, attributes);
    }

    private List<AttributeValue> findReplacementValues(List<Sample> samples, List<AttributeClass> attributes) {

        List<AttributeValue> replacementValues = new ArrayList<AttributeValue>();

        for (AttributeClass attributeClass : attributes) {
            AttributeValue replacementValue = ValueAnalyzer.mostCommonValueOfAttribute(samples, attributeClass);
            replacementValues.add(replacementValue);
        }

        return replacementValues;
    }

}