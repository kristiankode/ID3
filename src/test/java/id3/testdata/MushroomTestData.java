package id3.testdata;

import id3.domain.Model;
import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import id3.importing.ImportFromCsv;
import id3.importing.build.SampleBuilder;
import id3.importing.filter.RandomFilter;
import id3.training.algorithms.DecisionTreeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kristian
 *         Created 21.09.15.
 */
public class MushroomTestData {
    private static final double DEFAULT_PERCENTAGE_FOR_VALIDATION = 25.0;
    private final Logger log = LoggerFactory.getLogger(MushroomTestData.class);

    @SuppressWarnings("ConstantConditions") private static final String filePath = ImportFromCsv.class.getClassLoader().getResource("shrooms.csv").getPath();
    private static final RandomFilter filter = new RandomFilter();

    private List<Sample> validationSet;
    private List<Sample> trainingSet;

    private List<AttributeClass> getMushroomAttributes()
            throws FileNotFoundException {
        ImportFromCsv csvImport = new ImportFromCsv(filePath);
        return csvImport.retrieveAttributes();
    }

    public List<Sample> getAllMushroomSamples()
            throws FileNotFoundException {

        ImportFromCsv csvImport = new ImportFromCsv(filePath);
        return csvImport.retrieveSamples(getMushroomAttributes());
    }

    /**
     * Imports training and validation data from file.
     */
    public void loadData(Double percentageForValidation)
            throws FileNotFoundException, UnsupportedEncodingException {
        List<Sample> allSamples = getAllMushroomSamples();

        validationSet = new ArrayList<Sample>(allSamples);
        validationSet = filter.randomlySelectSamples(percentageForValidation, validationSet);

        trainingSet = new ArrayList<Sample>(allSamples);

        log.debug("Removing {} samples from training set {}", validationSet.size(), trainingSet.size());
        trainingSet.removeAll(validationSet);
        log.debug("Loaded {} samples for validation, {} for training", validationSet.size(), trainingSet.size());
    }

    private void loadDataIfNecessary() throws FileNotFoundException, UnsupportedEncodingException {
        if (trainingSet == null) {
            loadData(DEFAULT_PERCENTAGE_FOR_VALIDATION);
        }
    }

    private List<Sample> getTrainingSet() throws FileNotFoundException, UnsupportedEncodingException {
        loadDataIfNecessary();
        return trainingSet;
    }

    public List<Sample> getValidationSet() {
        return validationSet;
    }

    public Model getMushroomModel() throws FileNotFoundException, UnsupportedEncodingException {
        List<AttributeClass> attributes = getMushroomAttributes();
        DecisionTreeBuilder treeBuilder = new DecisionTreeBuilder();
        return treeBuilder.build(getTrainingSet(), attributes.get(0).getPossibleValues().get(0), attributes);
    }

    public Sample sampleWithHabitatU()
            throws FileNotFoundException {
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
