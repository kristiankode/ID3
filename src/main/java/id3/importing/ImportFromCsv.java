package id3.importing;

import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import id3.importing.build.attributes.AttributeExtractor;
import id3.importing.build.SampleBuilder;
import id3.importing.read.BufferedCsvReader;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author kristian
 *         Created 17.09.15.
 */
public class ImportFromCsv {

    final BufferedCsvReader reader;

    public ImportFromCsv(String filePath)
            throws FileNotFoundException, UnsupportedEncodingException {
        this.reader = new BufferedCsvReader(filePath);
    }

    public List<AttributeClass> retrieveAttributes() {
        AttributeExtractor attributeExtractor = new AttributeExtractor();

        return attributeExtractor.getAllAttributes(reader);
    }

    public List<Sample> retrieveSamples(List<AttributeClass> attributes) {

        SampleBuilder builder = new SampleBuilder();
        return builder.buildSamples(reader, attributes);
    }

}
