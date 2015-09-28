package id3.core.prediction;

import id3.api.domain.Model;
import id3.api.domain.Rule;
import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeValue;
import id3.core.prediction.analysis.measures.Accuracy;
import id3.core.pruning.rules.RuleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Predicts a model by converting it to rules. Necessary predicting after pruning.
 */
public class PredictUsingRules {
    private final RuleBuilder ruleBuilder = new RuleBuilder();
    private final Accuracy accuracy = new Accuracy();

    public List<Prediction> predict(Model model, List<Sample> unseenSamples) {
        List<Rule> rules = ruleBuilder.build(model);
        List<Prediction> predictions = predict(rules, unseenSamples);

        accuracy.evaluate(predictions, model.getTargetAttribute());

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
