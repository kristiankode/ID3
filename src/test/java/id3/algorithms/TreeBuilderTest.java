package id3.algorithms;

import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import id3.importing.ImportFromCsv;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static id3.domain.attr.TestDataFactory.*;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public class TreeBuilderTest {

    DecisionTreeBuilder instance = new DecisionTreeBuilder();

    @Test
    public void isItFriday() throws Exception {
        instance.build(getSunnyFridaySamples(), friday(), getSunnyFridayAttributes());
    }

    @Test
    public void isItMonday() {
        instance.build(getSunnyFridaySamples(), monday(), getSunnyFridayAttributes());
    }

    @Test
    public void isItCloudy(){
        instance.build(getSunnyFridaySamples(), cloudy(), getSunnyFridayAttributes());
    }

    @Test
    public void isItNiceForTennis(){
        instance.build(getTennisSamples(), niceDayForTennis(), getTennisAttributes());
    }

    @Test
    public void isItPoisonous() throws FileNotFoundException, UnsupportedEncodingException {

        String filePath = ImportFromCsv.class.getClassLoader().getResource("shrooms.csv").getPath();

        ImportFromCsv csvImport = new ImportFromCsv(filePath);
        List<AttributeClass> attributes = csvImport.retrieveAttributes();
        List<Sample> shrooms = csvImport.retrieveSamples(attributes);

        instance.build(shrooms, attributes.get(0).getPossibleValues().get(0), attributes);
    }
}