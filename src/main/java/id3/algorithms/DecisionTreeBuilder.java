package id3.algorithms;

import id3.algorithms.selectors.attribute.InformationGainSelector;
import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;
import id3.domain.tree.Node;
import id3.domain.tree.NodeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static id3.analysis.ValueAnalyzer.*;
import static id3.domain.tree.NodeClass.NEGATIVE;
import static id3.domain.tree.NodeClass.POSITIVE;
import static id3.filter.SampleFilter.filterByAttributeValue;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public class DecisionTreeBuilder {
    final static Logger log = LoggerFactory.getLogger(DecisionTreeBuilder.class);

    InformationGainSelector attributeSelector;

    public Node build(List<Sample> allSamples, AttributeValue targetAttribute, List<AttributeClass> attributes) {
        System.out.println("Building decision tree for answering: Is it " + targetAttribute.getValue() + "?");
        System.out.println("----------------------------------------------------------------");

        attributeSelector = new InformationGainSelector(targetAttribute);
        sanitizeAttributes(attributes, targetAttribute);

        Node rootNode = new Node();
        Node decisionTree = id3Recursion(allSamples, targetAttribute, attributes, rootNode);

        System.out.println("------ Final decision tree: ----------");
        decisionTree.print();

        return decisionTree;
    }

    private void sanitizeAttributes(List<AttributeClass> attributes, AttributeValue target) {
        int i = -1;
        if (attributes.contains(target.getAttributeClass())) {
            i = attributes.indexOf(target.getAttributeClass());
            log.info("Removed target ({}) from attribute list", target.getAttributeClass());
        }
        if (i >= 0) {
            attributes.remove(i);
        }
    }

    private Node id3Recursion(List<Sample> allSamples, AttributeValue targetAttribute, List<AttributeClass> attributes,
                              Node root) {

        // check if all samples are positive or negative
        if (allSamplesPositive(allSamples, targetAttribute)) {
            root.setClassification(POSITIVE);
        } else if (allSamplesNegative(allSamples, targetAttribute)) {
            root.setClassification(NEGATIVE);
        } else if (attributes.isEmpty()) {
            root.setClassification(mostCommonValueIn(allSamples, targetAttribute));
        }

        if (!root.isLeaf()) { // value has not been set

            AttributeClass bestAttr = attributeSelector.selectAttribute(allSamples, attributes);

            for (AttributeValue possibleValue : bestAttr.getPossibleValues()) {

                Node attributeNode = new Node();
                attributeNode.setAttributeValue(possibleValue);

                List<Sample> matchingSamples = filterByAttributeValue(allSamples, possibleValue);

                if (matchingSamples.isEmpty()) {
                    NodeClass mostCommon = mostCommonValueIn(allSamples, targetAttribute);
                    attributeNode.makeLeaf(possibleValue, mostCommon);
                    log.debug("Found no matching samples for attr {}, added leaf {}",
                            mostCommon,
                            attributeNode.description());
                } else {

                    List<AttributeClass> remainingAttributes = new ArrayList<AttributeClass>(attributes);
                    remainingAttributes.remove(bestAttr);

                    id3Recursion(matchingSamples, targetAttribute, remainingAttributes, attributeNode);
                }
                root.addChild(attributeNode);
            }
        }
        return root;
    }
}
