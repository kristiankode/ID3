package id3.algorithms;

import id3.domain.Sample;
import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;
import id3.domain.tree.Node;
import id3.domain.tree.NodeClass;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static id3.domain.tree.NodeClass.NEGATIVE;
import static id3.domain.tree.NodeClass.POSITIVE;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public class DecisionTreeBuilder {

    public Node build(List<Sample> allSamples, AttributeValue targetAttribute, List<AttributeClass> attributes) {

        System.out.println("Building decision tree for answering: Is it " + targetAttribute.getValue() + "?");

        Node decisionTree = id3Recursion(allSamples, targetAttribute, attributes);

        System.out.println("------ Final decision tree: ----------");
        decisionTree.print();

        return decisionTree;
    }

    private Node id3Recursion(List<Sample> allSamples, AttributeValue targetAttribute, List<AttributeClass> attributes) {

        Node root = new Node();

        if (allTargetValuesEqual(allSamples, targetAttribute)) {
            root.setClassification(POSITIVE);
        } else if (allSamplesNegative(allSamples, targetAttribute)) {
            root.setClassification(NEGATIVE);
        } else if (attributes.isEmpty()) {
            root.setClassification(mostCommonValueIn(allSamples));
        }

        if (!root.isLeaf()) { // value has not been set

            AttributeClass bestAttr = getBestAttribute(attributes);

            for (AttributeValue possibleValue : bestAttr.getPossibleValues()) {
                Node attributeNode = new Node();
                attributeNode.setAttributeValue(possibleValue);

                List<Sample> matchingSamples = getSamplesWithMatchingAttribute(allSamples, possibleValue);

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

    List<Sample> getSamplesWithMatchingAttribute(List<Sample> unfiltered, AttributeValue attribute) {

        List<Sample> matchingSamples = new ArrayList<Sample>();
        for (Sample sample : unfiltered) {
            if (sample.hasAttribute(attribute.getAttributeClass())) {

                AttributeValue attrVal = sample.getAttribute(attribute.getAttributeClass());
                if (attrVal.equals(attribute)) {
                    matchingSamples.add(sample);
                }
            }
        }
        return matchingSamples;
    }

    AttributeClass getBestAttribute(List<AttributeClass> attributes) {
        return attributes.get(0);
    }

    AttributeValue mostCommonValueOfAttrInSample(AttributeClass attrClass, List<Sample> samples) {
        Map<AttributeValue, Long> counters = new HashMap<AttributeValue, Long>();

        // count
        for (AttributeValue val : attrClass.getPossibleValues()) {
            Long attrCount = 0l;

            for (Sample sample : samples) {
                if (sample.getAttribute(attrClass).equals(val)) {
                    attrCount++;
                }
            }

            counters.put(val, attrCount);
        }

        // find most common value
        AttributeValue mostCommon = null;
        Long topOccurence = 0l;
        for (AttributeValue val : counters.keySet()) {
            Long current = counters.get(val);
            if (current >= topOccurence) {
                topOccurence = current;
                mostCommon = val;
            }
        }

        return mostCommon;
    }

    NodeClass mostCommonValueIn(List<Sample> samples) {
        int positive = 0, negative = 0;

        for (Sample sample : samples) {
            if (sample.isPositive()) {
                positive++;
            } else {
                negative++;
            }
        }

        if (positive > negative) {
            return POSITIVE;
        } else {
            return NEGATIVE;
        }
    }

    /**
     * Checks if all samples have the same value for the target attribute
     *
     * @param samples
     * @param targetAttribute
     * @return
     */
    boolean allTargetValuesEqual(List<Sample> samples, AttributeValue targetAttribute) {
        AttributeValue prevValue = targetAttribute;

        for (Sample sample : samples) {
            AttributeValue currVal = sample.getAttribute(targetAttribute.getAttributeClass());

            if (!prevValue.equals(currVal)) {
                return false;
            }
        }
        System.out.println("All " + samples.size() + " values were equal to target (" + prevValue.getValue() + ")");
        return true;
    }

    boolean allSamplesNegative(List<Sample> samples, AttributeValue targetAttribute) {
        AttributeValue prevValue = targetAttribute;

        for (Sample sample : samples) {
            AttributeValue currVal = sample.getAttribute(targetAttribute.getAttributeClass());

            if (prevValue.equals(currVal)) {
                return false;
            }
        }
        System.out.println("All " + samples.size() + " values were dissimilar to target (" + prevValue.getValue() + ")");
        return true;
    }
}
