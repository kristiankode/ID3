package id3.api;

import id3.api.domain.Model;
import id3.api.domain.Rule;
import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeClass;
import id3.api.domain.attr.AttributeValue;
import id3.core.pruning.rules.RuleBuilder;
import id3.core.util.DataSplitter;
import id3.core.importing.ImportFromCsv;
import id3.core.prediction.PredictUsingRules;
import id3.core.prediction.PredictUsingTree;
import id3.core.prediction.Prediction;
import id3.core.prediction.analysis.measures.Accuracy;
import id3.core.prediction.analysis.measures.PerformanceEvaluator;
import id3.core.prediction.analysis.measures.Sensitivity;
import id3.core.prediction.analysis.measures.Specificity;
import id3.core.pruning.RulePruner;
import id3.core.training.algorithms.Id3Algorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * Runs the decision tree algorithm on a csv file.
 */
public class Runner {
    Logger log = LoggerFactory.getLogger(Runner.class);
    private DataSplitter data;
    private AttributeValue target;
    private Model model;
    private PredictUsingTree predictorWithoutPruning = new PredictUsingTree();
    private PredictUsingRules predictorWithPruning = new PredictUsingRules();
    private List<Rule> unprunedRules, prunedRules;

    private final PerformanceEvaluator
            accuracy = new Accuracy(),
            sensitivity = new Sensitivity(),
            specificity = new Specificity();
    private List<Prediction> predictions;

    /**
     * Algorithm entry point
     *
     * @param filePath             Absolute path to file
     * @param targetColumn         Zero-based index of which column to use as target.
     * @param validationPercentage A number 0-100 representing how much of the data set should be reserved for validation.
     * @throws FileNotFoundException
     */
    public Runner(String filePath, int targetColumn, Double validationPercentage)
            throws FileNotFoundException {
        run(filePath, targetColumn, validationPercentage);
    }

    private List<Rule> run(String csvFilePath, int targetColumn, double validationPercentage)
            throws FileNotFoundException {

        Id3Algorithm treeBuilder = new Id3Algorithm();

        ImportFromCsv csvImporter = new ImportFromCsv(csvFilePath);

        List<AttributeClass> attributes = csvImporter.retrieveAttributes();
        List<Sample> allSamples = csvImporter.retrieveSamples(attributes);
        data = new DataSplitter(allSamples, validationPercentage);

        target = attributes.get(targetColumn).getPossibleValues().get(0);

        log.info("Loaded {} training samples and {} attributes", data.getTrainingSet().size(), attributes.size());
        model = treeBuilder.build(data.getTrainingSet(), target, attributes);
        RuleBuilder ruleBuilder = new RuleBuilder();
        unprunedRules = ruleBuilder.build(model);

        RulePruner pruner = new RulePruner();
        pruner.setPerformanceEvaluator(accuracy);

        prunedRules = pruner.pruneRepeatedly(model, data.getValidationSet());

        predictions = predictorWithPruning.predict(prunedRules, data.getValidationSet());
        return prunedRules;
    }

    public List<Prediction> getTrainingPredictionWithoutPruning() {
        return predictorWithoutPruning.predict(getModel(), data.getTrainingSet());
    }

    public List<Prediction> getValidationPredictionWithoutPruning() {
        return predictorWithoutPruning.predict(getModel(), data.getValidationSet());
    }

    public List<Prediction> getTrainingPredictionWithPruning() {
        return predictorWithPruning.predict(getPrunedRules(), data.getTrainingSet());
    }

    public List<Prediction> getValidationPredictionWithPruning() {
        return predictorWithPruning.predict(getPrunedRules(), data.getValidationSet());
    }

    public AttributeValue getTarget() {
        return target;
    }

    public Model getModel() {
        return model;
    }

    public Double getAccuracy(List<Prediction> predictions) {
        return accuracy.evaluate(predictions, target);
    }

    public Double getSensitivity(List<Prediction> predictions) {
        return sensitivity.evaluate(predictions, target);
    }

    public Double getSpecificity(List<Prediction> predictions) {
        return specificity.evaluate(predictions, target);
    }

    public List<Rule> getPrunedRules() {
        return prunedRules;
    }

    public List<Rule> getUnprunedRules() {
        return unprunedRules;
    }
}
