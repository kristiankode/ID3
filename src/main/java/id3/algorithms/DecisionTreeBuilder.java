package id3.algorithms;

import id3.algorithms.selectors.attribute.AttributeSelector;
import id3.algorithms.selectors.attribute.InformationGainSelector;
import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;
import id3.domain.tree.Node;

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

    final AttributeSelector attributeSelector = new InformationGainSelector();

    public Node build(List<Sample> allSamples, AttributeValue targetAttribute, List<AttributeClass> attributes) {

        System.out.println("Building decision tree for answering: Is it " + targetAttribute.getValue() + "?");

        Node decisionTree = id3Recursion(allSamples, targetAttribute, attributes);

        System.out.println("------ Final decision tree: ----------");
        decisionTree.print();

        return decisionTree;
    }

    private Node id3Recursion(List<Sample> allSamples, AttributeValue targetAttribute, List<AttributeClass> attributes) {

        Node root = new Node();

        // check if all samples are positive or negative
        if (allSamplesPositive(allSamples, targetAttribute)) {
            root.setClassification(POSITIVE);
        } else if (allSamplesNegative(allSamples, targetAttribute)) {
            root.setClassification(NEGATIVE);
        } else if (attributes.isEmpty()) {
            root.setClassification(mostCommonValueIn(allSamples));
        }

        if (!root.isLeaf()) { // value has not been set

            AttributeClass bestAttr = attributeSelector.selectAttribute(allSamples, attributes);

            for (AttributeValue possibleValue : bestAttr.getPossibleValues()) {
                Node attributeNode = new Node();
                attributeNode.setAttributeValue(possibleValue);

                List<Sample> matchingSamples = filterByAttributeValue(allSamples, possibleValue);

                if (matchingSamples.isEmpty()) {
                    AttributeValue mostCommon = mostCommonValueOfAttrInSample(bestAttr, allSamples);
                    attributeNode.addLeaf(mostCommon, targetAttribute);
                } else {
                    List<AttributeClass> remainingAttributes = new ArrayList<AttributeClass>(attributes);
                    remainingAttributes.remove(bestAttr);

                    Node subtree = id3Recursion(matchingSamples, targetAttribute, remainingAttributes);
                    attributeNode.addChild(subtree);
                }
                root.addChild(attributeNode);
            }
        }
        return root;
    }
}
