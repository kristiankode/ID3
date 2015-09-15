package id3.algorithms;

import org.junit.Test;

import static id3.domain.attr.TestDataFactory.*;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public class TreeBuilderTest {

    DecisionTreeBuilder instance = new DecisionTreeBuilder();

    @Test
    public void isItFriday() throws Exception {
        instance.build(getSamples(), friday(), getAttributes());
    }

    @Test
    public void isItMonday() {
        instance.build(getSamples(), monday(), getAttributes());
    }

    @Test
    public void isItCloudy(){
        instance.build(getSamples(), cloudy(), getAttributes());
    }
}