package id3.pruning;

import id3.domain.Model;
import id3.domain.Rule;
import id3.domain.Sample;
import id3.domain.attr.AttributeValue;
import id3.prediction.PredictUsingRules;
import id3.prediction.Prediction;
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
public class RulePruning {
    Logger log = LoggerFactory.getLogger(RulePruning.class);

    final PredictUsingRules predictor = new PredictUsingRules();
    final RuleBuilder ruleBuilder = new RuleBuilder();

    public void prune(Model model, List<Sample> pruningSet) {

        List<Rule> rules = ruleBuilder.build(model);
        List<Rule> prunedRules = new ArrayList<Rule>();

        for (Rule rule : rules) {
            prunedRules.add(pruneRule(rule, new ArrayList<Rule>(rules), pruningSet, rule.getTargetValue()));
        }

    }

    public Rule pruneRule(Rule ruleToPrune, List<Rule> allRules, List<Sample> validationSet, AttributeValue target) {

        List<Prediction> originalPrediction = predictor.predict(allRules, validationSet);
        double originalPerformance =
                PredictionEvaluator.evaluatePredictionAccuracy(originalPrediction, target);

        double highestPerformanceIncrease = 0.0;
        Rule bestRule = ruleToPrune;

        for (AttributeValue val : ruleToPrune.getPreconditions()) {
            Rule prunedRule = ruleBuilder.build(ruleToPrune, val);

            // replace unpruned rule with pruned rule
            allRules.remove(ruleToPrune);
            allRules.add(prunedRule);

            // check new performance
            List<Prediction> newPrediction = predictor.predict(allRules, validationSet);
            double newPerformance = PredictionEvaluator.evaluatePredictionAccuracy(newPrediction, target);

            double performanceIncrease = newPerformance - originalPerformance;

            log.debug("Perfomance of pruned rule {} was {}", prunedRule, newPerformance);
            if (performanceIncrease >= highestPerformanceIncrease) {
                log.debug("Pruned rule {} to {}", ruleToPrune, prunedRule);
                highestPerformanceIncrease = performanceIncrease;
                bestRule = prunedRule;
            }
        }

        return bestRule;

    }


}
