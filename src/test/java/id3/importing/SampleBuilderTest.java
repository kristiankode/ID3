package id3.importing;

import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import id3.importing.build.AttributeExtractor;
import id3.importing.build.SampleBuilder;
import id3.importing.read.DataReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

/**
 * @author kristian
 *         Created 17.09.15.
 */
@RunWith(MockitoJUnitRunner.class)
public class SampleBuilderTest {

    @Mock
    DataReader reader;

    final String
            head1 = "Edible?",
            head2 = "Color",
            head3 = "Taste";

    final String
            row1col1 = "Edible",
            row1col2 = "Red",
            row1col3 = "Tasty";

    final String
            row2col1 = "Venomous poision",
            row2col2 = "Blue",
            row2col3 = "Disgusting";

    SampleBuilder instance = new SampleBuilder();

    @Before
    public void setup() {
        when(reader.readHeaderRow()).thenReturn(new String[]{head1, head2, head3});
        when(reader.readDataRows()).thenReturn(
                new String[]{row1col1, row1col2, row1col3},
                new String[]{row2col1, row2col2, row2col3},
                null);
    }

    @Test
    public void buildSamples_givenHeaderAndData_sampleShouldContainCorrectLabelAndAttributes(){
        // todo: separate test for attr.extractor
        AttributeExtractor attributeExtractor = new AttributeExtractor();
        List<AttributeClass> attributes = attributeExtractor.getAllAttributes(reader);

        List<Sample> samples = instance.buildSamples(reader, attributes);

        Sample firstSample = samples.get(0);
        assertThat(firstSample.getAttributes().get(0).getLabel(), is(head1));
        assertThat(firstSample.getAttributes().get(1).getLabel(), is(head2));
        assertThat(firstSample.getAttributes().get(2).getLabel(), is(head3));

        assertThat(firstSample.getAttributes().get(0).getValue(), is(row1col1));
        assertThat(firstSample.getAttributes().get(1).getValue(), is(row1col2));
        assertThat(firstSample.getAttributes().get(2).getValue(), is(row1col3));

        Sample secondSample = samples.get(1);
        assertThat(secondSample.getAttributes().get(0).getLabel(), is(head1));
        assertThat(secondSample.getAttributes().get(1).getLabel(), is(head2));
        assertThat(secondSample.getAttributes().get(2).getLabel(), is(head3));
        
        assertThat(secondSample.getAttributes().get(0).getValue(), is(row2col1));
        assertThat(secondSample.getAttributes().get(1).getValue(), is(row2col2));
        assertThat(secondSample.getAttributes().get(2).getValue(), is(row2col3));
    }

    @Test
    public void buildSamples_givenTwoRow_shouldProduceTwoSamplesWithoutErrors()
            throws Exception {
        List<AttributeClass> attributes = new AttributeExtractor().getAllAttributes(reader);
        List<Sample> samples = instance.buildSamples(reader, attributes);

        assertThat(samples.size(), is(2));
    }
}