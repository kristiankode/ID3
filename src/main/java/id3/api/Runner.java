package id3.api;

import id3.core.analysis.DataSplitter;
import id3.api.domain.Model;
import id3.api.domain.Rule;
import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeClass;
import id3.api.domain.attr.AttributeValue;
import id3.core.importing.ImportFromCsv;
import id3.core.pruning.RulePruner;
import id3.core.training.algorithms.DecisionTreeBuilder;

import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author kristian
 *         Created 24.09.15.
 */
public class Runner {

    public List<Rule> findRules(String csv, int targetColumn, double validationPercentage)
            throws FileNotFoundException {

        DecisionTreeBuilder treeBuilder = new DecisionTreeBuilder();

        ImportFromCsv csvImporter = new ImportFromCsv(csv);

        List<AttributeClass> attributes = csvImporter.retrieveAttributes();
        List<Sample> allSamples = csvImporter.retrieveSamples(attributes);
        DataSplitter data = new DataSplitter(allSamples, validationPercentage);

        AttributeValue target = attributes.get(targetColumn).getPossibleValues().get(0);

        Model model = treeBuilder.build(data.getTrainingSet(), target, attributes);

        RulePruner pruner = new RulePruner();

        return pruner.pruneRepeatedly(model, data.getValidationSet());
    }
}
