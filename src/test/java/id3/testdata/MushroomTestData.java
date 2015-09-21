package id3.testdata;

import id3.training.algorithms.DecisionTreeBuilder;
import id3.domain.Model;
import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import id3.importing.ImportFromCsv;
import id3.importing.build.SampleBuilder;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author kristian
 *         Created 21.09.15.
 */
public class MushroomTestData {

    static final String filePath = ImportFromCsv.class.getClassLoader().getResource("shrooms.csv").getPath();

    public List<AttributeClass> getMushroomAttributes()
            throws FileNotFoundException, UnsupportedEncodingException {
        ImportFromCsv csvImport = new ImportFromCsv(filePath);
        return csvImport.retrieveAttributes();
    }

    public List<Sample> getMushroomSamples()
            throws FileNotFoundException, UnsupportedEncodingException {

        ImportFromCsv csvImport = new ImportFromCsv(filePath);
        return csvImport.retrieveSamples(getMushroomAttributes());

    }

    public Model getMushroomModel() throws FileNotFoundException, UnsupportedEncodingException {
        List<AttributeClass> attributes = getMushroomAttributes();
        DecisionTreeBuilder treeBuilder = new DecisionTreeBuilder();
        return treeBuilder.build(getMushroomSamples(), attributes.get(0).getPossibleValues().get(0), attributes);
    }

    public Sample sampleWithHabitatU()
            throws FileNotFoundException, UnsupportedEncodingException {
        SampleBuilder sampleBuilder = new SampleBuilder();
        String
                classification = "e",
                capShape = "x",
                capSurface = "s",
                capColor = "n",
                bruises = "t",
                odor = "n",
                gillAttachment = "f",
                gillSpacing = "c",
                gillSize = "n",
                gillColor = "k",
                stalkShape = "e",
                stalkRoot = "e",
                stalkSurfaceAboveRing = "s",
                stalkSurfaceBelowRing = "s",
                stalkColorAboveRing = "w",
                stalkColorBelowRing = "w",
                veilType = "p",
                veilColor = "w",
                ringNumber = "o",
                ringType = "p",
                sporePrintColor = "w",
                population = "v",
                habitat = "u";

        return sampleBuilder.buildSample(new String[]{
                classification, capShape, capSurface, capColor, bruises, odor, gillAttachment, gillSpacing, gillSize,
                gillColor, stalkShape, stalkRoot, stalkSurfaceAboveRing, stalkSurfaceBelowRing, stalkColorAboveRing,
                stalkColorBelowRing, veilType, veilColor, ringNumber, ringType, sporePrintColor, population, habitat
        }, this.getMushroomAttributes());
    }
}
