package id3.core.analysis;

import id3.api.domain.Sample;
import id3.core.importing.filter.RandomFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Divides a list in two parts; one for training and one for validation
 *
 * @author kristian
 *         Created 24.09.15.
 */
public class DataSplitter {
    Logger log = LoggerFactory.getLogger(DataSplitter.class);

    private final List<Sample> allSamples;
    private final Double validationPercentage;

    private List<Sample> trainingSet, validationSet;

    public DataSplitter(List<Sample> allSamples, Double validationPercentage) {
        this.allSamples = allSamples;
        this.validationPercentage = validationPercentage;

        split();
    }

    private void split() {
        validationSet = new ArrayList<Sample>(allSamples);
        trainingSet = new ArrayList<Sample>(allSamples);

        RandomFilter filter = new RandomFilter();
        validationSet = filter.randomlySelectSamples(validationPercentage, validationSet);
        trainingSet.removeAll(validationSet);


        log.debug("Divided {} samples. Training set: {}, validation set {}",
                allSamples.size(), trainingSet.size(), validationSet.size());

    }

    public List<Sample> getTrainingSet() {
        return trainingSet;
    }

    public List<Sample> getValidationSet() {
        return validationSet;
    }
}
