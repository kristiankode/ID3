package id3.importing;

import id3.domain.Sample;
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
    public List<Sample> retrieveSamples(String filePath)
            throws FileNotFoundException, UnsupportedEncodingException {
        BufferedCsvReader reader = new BufferedCsvReader(filePath);

        SampleBuilder builder = new SampleBuilder();
        return builder.buildSamples(reader);
    }
}
