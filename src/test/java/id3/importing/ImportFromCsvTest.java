package id3.importing;

import id3.domain.Sample;
import org.hamcrest.Matchers;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author kristian
 *         Created 17.09.15.
 */
public class ImportFromCsvTest {

    ImportFromCsv instance = new ImportFromCsv();

    @Test
    public void retrieveSamples_givenShroomsFile_shouldProduceSamplesWithoutError()
            throws FileNotFoundException, UnsupportedEncodingException {

        String path = ImportFromCsvTest.class.getClassLoader().getResource("shrooms.csv").getPath();
        List<Sample> samples = instance.retrieveSamples(path);

        assertThat(samples.size(), is(8124)); // file is 8124 lines + header
    }
}