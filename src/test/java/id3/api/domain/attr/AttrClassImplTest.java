package id3.api.domain.attr;

import id3.core.importing.build.attributes.AttrClassImpl;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public class AttrClassImplTest {

    @Test
    public void createAttributeClass_givenLabelAndThreeValues_shouldContainCorrectValues(){
        String label = "Manufacturer";
        String[] values = {"Ducati", "Triumph", "Piaggio"};

        AttributeClass attrClass = new AttrClassImpl(label, values);

        assertThat(attrClass.getLabel(), is(label));
        assertThat(attrClass.getPossibleValues().get(0).getValue(), is(values[0]));
    }

}