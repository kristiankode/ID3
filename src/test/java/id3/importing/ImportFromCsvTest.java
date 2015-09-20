package id3.importing;

import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.jar.Attributes;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author kristian
 *         Created 17.09.15.
 */
public class ImportFromCsvTest {

    ImportFromCsv instance;

    @Before
    public void setup() throws FileNotFoundException, UnsupportedEncodingException {
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