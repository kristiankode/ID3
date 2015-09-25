package id3.core.analysis;

import id3.api.domain.Sample;
import id3.core.importing.filter.RandomFilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Divides a list in two parts; one for training and one for validation
 *
 * @author kristian
 *         Created 24.09.15.
 */
public class DataSplitter {

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
    }

    public List<Sample> getTrainingSet() {
        return trainingSet;
    }

    public List<Sample> getValidationSet() {
        return validationSet;
    }
}
