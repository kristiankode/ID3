package id3.prediction;

import id3.domain.Model;
import id3.domain.Rule;
import id3.domain.Sample;
import id3.domain.attr.AttributeValue;
import id3.prediction.analysis.PredictionEvaluator;
import id3.pruning.rules.RuleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kristian
 *         Created 22.09.15.
 */
public class PredictUsingRules implements Predictor {
    Logger log = LoggerFactory.getLogger(PredictUsingRules.class);

    final RuleBuilder ruleBuilder = new RuleBuilder();

    public List<Prediction> predict(Model model, List<Sample> unseenSamples) {
        List<Rule> rules = ruleBuilder.build(model);
        List<Prediction> predictions = predict(rules, unseenSamples);

        PredictionEvaluator.evaluatePredictionAccuracy(predictions, model.getTargetAttribute());

        return predictions;
    }

    public List<Prediction> predict(List<Rule> rules, List<Sample> samples) {
        List<Prediction> predictions = new ArrayList<Prediction>();
        for (Sample sample : samples) {
            predictions.add(predictSample(rules, sample));
        }

        return predictions;
    }

    private Prediction predictSample(List<Rule> rules, Sample sample) {
        Prediction prediction = null;
        for (Rule rule : rules) {
            if (sampleMatchesRule(sample, rule)) {
                prediction = new Prediction(sample, rule.getPostCondition(), rule);
            }
        }

        return prediction;
    }


    public Prediction predictSample(Model model, Sample sample) {
        List<Rule> rules = ruleBuilder.build(model);

        return predictSample(rules, sample);
    }

    private boolean sampleMatchesRule(Sample sample, Rule rule) {
        for (AttributeValue precondition : rule.getPreconditions()) {
            if (!sample.hasAttributeValue(precondition)) {
                return false;
            }
        }
        return true;
    }
}
