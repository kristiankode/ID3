package id3.domain.tree;

import id3.domain.attr.AttributeValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public class Node {

    List<Node> subtree = new ArrayList<Node>();

    NodeClass classification;

    AttributeValue attributeValue; // the attribute represented by this node

    public AttributeValue getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(AttributeValue attributeValue) {
        this.attributeValue = attributeValue;
    }

    public boolean isLeaf() {
        return classification != null;
    }

    public void setClassification(NodeClass classification) {
        this.classification = classification;
        System.out.println("Classification was set to : " + classification);
    }

    public NodeClass getClassification() {
        return classification;
    }

    public void addChild(Node node) {
        subtree.add(node);
    }


    public void addLeaf(AttributeValue attributeValue, AttributeValue targetValue) {
        Node leaf = new Node();
        leaf.attributeValue = attributeValue;
        if (attributeValue.equals(targetValue)) {
            leaf.classification = NodeClass.POSITIVE;
        } else {
            leaf.classification = NodeClass.NEGATIVE;
        }
        this.addChild(leaf);
    }

    public void print() {
        print("");
    }

    private void print(String prefix) {
        out(prefix + description());

        for (Node n : subtree) {
            n.print(prefix + "   ");
        }
    }

    private void out(String s) {
        if (s != null && !s.trim().isEmpty()) {
            System.out.println(s);
        }
    }

    public String description() {
        String nodeString = "";
        if (this.attributeValue != null) {
            nodeString = this.attributeValue.getLabel() + ": " + this.attributeValue.getValue();
        }

        if (this.isLeaf()) {
            nodeString += String.valueOf(">> " + this.classification);
        }

        return nodeString;
    }

}
