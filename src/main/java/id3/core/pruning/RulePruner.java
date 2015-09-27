package id3.core.pruning;

import id3.api.domain.Model;
import id3.api.domain.Rule;
import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeValue;
import id3.core.prediction.PredictUsingRules;
import id3.core.prediction.Prediction;
import id3.core.prediction.analysis.measures.Accuracy;
import id3.core.pruning.rules.RuleBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kristian
 *         Created 22.09.15.
 */
public class RulePruner {
    private final Logger log = LoggerFactory.getLogger(RulePruner.class);

    private final PredictUsingRules predictor = new PredictUsingRules();
    private final RuleBuilder ruleBuilder = new RuleBuilder();
    private final Accuracy accuracy = new Accuracy();

    public List<Rule> pruneRepeatedly(Model model, List<Sample> pruningSet) {
        List<Rule> allRules = ruleBuilder.build(model);
        List<PruningResult> results = initResults(allRules),
                tempResults = new ArrayList<PruningResult>(results);

        log.debug("Starting pruning, initial performance = {}",
                accuracy.evaluate(predictor.predict(allRules, pruningSet), model.getTargetAttribute()));

        while (!tempResults.isEmpty()) {
            tempResults = prune(results, pruningSet);

            updateResults(tempResults, results);
        }

        log.debug("Accuracy after pruning = {}",
                accuracy.evaluate(predictor.predict(extractRules(results), pruningSet), model.getTargetAttribute()));

        return extractRules(results);
    }

    private List<PruningResult> prune(List<PruningResult> prevResult, List<Sample> pruningSet) {
        List<PruningResult> newResults = new ArrayList<PruningResult>();
        for (PruningResult pr : prevResult) {
            Rule doublePruned =
                    pruneRule(pr.getPrunedRule(), extractRules(prevResult), pruningSet, pr.getPrunedRule().getTargetValue());

            if (doublePruned != null) {
                newResults.add(new PruningResult(pr.getUnprunedRule(), doublePruned));
            }
        }
        return newResults;
    }

    private void updateResults(List<PruningResult> tempResults, List<PruningResult> resultsToUpdate) {
        for (PruningResult pr : tempResults) {
            PruningResult existing = findResultByRule(resultsToUpdate, pr.unprunedRule);
            assert existing != null;
            existing.setPrunedRule(pr.prunedRule);
        }

    }

    private List<PruningResult> initResults(List<Rule> allRules) {
        List<PruningResult> results = new ArrayList<PruningResult>();
        for (Rule rule : allRules) {
            results.add(new PruningResult(rule, rule));
        }
        return results;
    }

    private PruningResult findResultByRule(List<PruningResult> results, Rule rule) {
        for (PruningResult pr : results) {
            if (pr.getUnprunedRule().equals(rule)) {
                return pr;
            }
        }
        return null;
    }

    private void printRules(List<Rule> rules) {
        for (Rule r : rules) {
            log.debug(r.toString());
        }
    }

    /**
     * Prune a single rule, if possible. Returns null if rule cannot be pruned further.
     */
    private Rule pruneRule(Rule ruleToPrune, List<Rule> allRules, List<Sample> validationSet, AttributeValue target) {

        List<Prediction> originalPrediction = predictor.predict(allRules, validationSet);
        double originalPerformance =
                accuracy.evaluate(originalPrediction, target);

        Map<Rule, Double> performanceMap = new HashMap<Rule, Double>();

        final double performanceThreshold = 0.00000;

        for (AttributeValue val : ruleToPrune.getPreconditions()) {
            Rule prunedRule = ruleBuilder.build(ruleToPrune, val);

            // replace unpruned rule with pruned rule
            allRules.remove(ruleToPrune);
            allRules.add(prunedRule);

            // check new performance
            List<Prediction> newPrediction = predictor.predict(allRules, validationSet);
            double newPerformance = accuracy.evaluate(newPrediction, target);

            double performanceIncrease = newPerformance - originalPerformance;

            // check that the pruning did not make the model perform worse
            if (!(performanceIncrease < performanceThreshold)) {

                // save pruning
                performanceMap.put(prunedRule, performanceIncrease);
            }
        }

        return findBestPruning(performanceMap);
    }

    private Rule findBestPruning(Map<Rule, Double> performanceMap) {
        Double bestPerformance = 0.0;
        Rule bestRule = null;
        for (Rule rule : performanceMap.keySet()) {
            Double rulePerformance = performanceMap.get(rule);
            if (rulePerformance >= bestPerformance) {
                bestPerformance = rulePerformance;
                bestRule = rule;
            }
        }

        return bestRule;
    }

    private List<Rule> extractRules(List<PruningResult> results) {
        List<Rule> rules = new ArrayList<Rule>();
        for (PruningResult pr : results) {
            rules.add(pr.getPrunedRule());
        }
        return rules;
    }


}
