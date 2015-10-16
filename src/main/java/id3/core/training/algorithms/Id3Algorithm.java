package id3.core.training.algorithms;

import id3.api.domain.Model;
import id3.api.domain.Sample;
import id3.api.domain.attr.AttributeClass;
import id3.api.domain.attr.AttributeValue;
import id3.api.domain.tree.Node;
import id3.api.domain.tree.NodeClass;
import id3.core.training.algorithms.gain.GainCalculator;
import id3.core.training.algorithms.gain.GainRatio;
import id3.core.training.algorithms.selectors.attribute.AttributeSelector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static id3.api.domain.tree.NodeClass.NEGATIVE;
import static id3.api.domain.tree.NodeClass.POSITIVE;
import static id3.core.util.ValueAnalyzer.*;
import static id3.core.util.SampleFilter.filterByAttributeValue;

/**
 * Builds a decision tree based on the id3 algorithm.
 */
public class Id3Algorithm {
    private final static Logger log = LoggerFactory.getLogger(Id3Algorithm.class);

    private AttributeSelector attributeSelector;

    public Model build(List<Sample> allSamples, AttributeValue targetAttribute, List<AttributeClass> attributes) {
        System.out.println("Building decision tree for answering: Is " + targetAttribute + "?");

        GainCalculator gainCalculator = new GainRatio(targetAttribute);
        attributeSelector = new AttributeSelector(gainCalculator);
        sanitizeAttributes(attributes, targetAttribute);

        Node rootNode = new Node();
        Node decisionTree = id3Recursion(allSamples, targetAttribute, attributes, rootNode);

        return new Model(rootNode, attributes, targetAttribute);
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

            // selects best attribute
            AttributeClass bestAttr = attributeSelector.selectAttribute(allSamples, attributes);

            for (AttributeValue possibleValue : bestAttr.getPossibleValues()) {

                // create new node for this attribute
                Node attributeNode = new Node(root);
                attributeNode.setAttributeValue(possibleValue);

                List<Sample> matchingSamples = filterByAttributeValue(allSamples, possibleValue);

                if (matchingSamples.isEmpty()) { // no samples matches attribute value
                    NodeClass mostCommon = mostCommonValueIn(allSamples, targetAttribute);
                    attributeNode.makeLeaf(possibleValue, mostCommon);
                    log.debug("Found no matching samples for attr {}, added leaf {}",
                            mostCommon,
                            attributeNode.description());
                } else {
                    // remove the recently evaluated attribute from the list, and repeat algorithm.
                    List<AttributeClass> remainingAttributes = new ArrayList<AttributeClass>(attributes);
                    remainingAttributes.remove(bestAttr);

                    id3Recursion(matchingSamples, targetAttribute, remainingAttributes, attributeNode);
                }
                root.addChild(attributeNode);
            }
        }
        return root;
    }


    /**
     * Removes target attribute from decision tree attributes.
     *
     * @param attributes All attributes.
     * @param target     The target attribute.
     */
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
}
