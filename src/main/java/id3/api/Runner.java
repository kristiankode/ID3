package id3.api;

import id3.api.domain.Model;
import id3.api.domain.Rule;
import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeClass;
import id3.api.domain.attr.AttributeValue;
import id3.core.analysis.DataSplitter;
import id3.core.importing.ImportFromCsv;
import id3.core.prediction.PredictUsingRules;
import id3.core.prediction.PredictUsingTree;
import id3.core.prediction.Prediction;
import id3.core.prediction.analysis.measures.Accuracy;
import id3.core.prediction.analysis.measures.PerformanceEvaluator;
import id3.core.prediction.analysis.measures.Sensitivity;
import id3.core.prediction.analysis.measures.Specificity;
import id3.core.pruning.RulePruner;
import id3.core.training.algorithms.DecisionTreeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author kristian
 *         Created 24.09.15.
 */
public class Runner {
    Logger log = LoggerFactory.getLogger(Runner.class);
    private DataSplitter data;
    private AttributeValue target;
    private Model model;
    private PredictUsingTree predictorWithoutPruning = new PredictUsingTree();
    private PredictUsingRules predictorWithPruning = new PredictUsingRules();
    private List<Rule> rules;

    private final PerformanceEvaluator
            accuracy = new Accuracy(),
            sensitivity = new Sensitivity(),
            specificity = new Specificity();
    private List<Prediction> predictions;

    public Runner(String filePath, int targetColumn, Double validationPercentage)
            throws FileNotFoundException {
        run(filePath, targetColumn, validationPercentage);
    }

    private List<Rule> run(String csv, int targetColumn, double validationPercentage)
            throws FileNotFoundException {

        DecisionTreeBuilder treeBuilder = new DecisionTreeBuilder();

        ImportFromCsv csvImporter = new ImportFromCsv(csv);

        List<AttributeClass> attributes = csvImporter.retrieveAttributes();
        List<Sample> allSamples = csvImporter.retrieveSamples(attributes);
        data = new DataSplitter(allSamples, validationPercentage);

        target = attributes.get(targetColumn).getPossibleValues().get(0);

        log.info("Loaded {} training samples and {} attributes", data.getTrainingSet().size(), attributes.size());
        model = treeBuilder.build(data.getTrainingSet(), target, attributes);

        RulePruner pruner = new RulePruner();
        pruner.setPerformanceEvaluator(accuracy);

        rules = pruner.pruneRepeatedly(model, data.getValidationSet());

        predictions = predictorWithPruning.predict(rules, data.getValidationSet());
        return rules;
    }

    public List<Prediction> getTrainingPredictionWithoutPruning() {
        return predictorWithoutPruning.predict(getModel(), data.getTrainingSet());
    }

    public List<Prediction> getValidationPredictionWithoutPruning() {
        return predictorWithoutPruning.predict(getModel(), data.getValidationSet());
    }

    public List<Prediction> getTrainingPredictionWithPruning() {
        return predictorWithPruning.predict(getRules(), data.getTrainingSet());
    }

    public List<Prediction> getValidationPredictionWithPruning() {
        return predictorWithPruning.predict(getRules(), data.getValidationSet());
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

    public List<Rule> getRules() {
        return rules;
    }
}
