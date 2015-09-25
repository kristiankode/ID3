package id3.core.importing;

import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeClass;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author kristian
 *         Created 17.09.15.
 */
public class ImportFromCsvTest {

    private ImportFromCsv instance;

    @Before
    public void setup() throws FileNotFoundException, UnsupportedEncodingException {
        //noinspection ConstantConditions
        instance = new ImportFromCsv(ImportFromCsv.class.getClassLoader().getResource("shrooms.csv").getPath());
    }

    @Test
    public void retrieveSamples_givenShroomsFile_shouldProduceSamplesWithoutError()
            throws FileNotFoundException, UnsupportedEncodingException {

        List<AttributeClass> attributes = instance.retrieveAttributes();
        List<Sample> samples = instance.retrieveSamples(attributes);

        assertThat(samples.size(), is(8124)); // file is 8124 lines + header
    }
}