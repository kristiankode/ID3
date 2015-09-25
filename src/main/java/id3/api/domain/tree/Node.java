package id3.api.domain.tree;

import id3.api.domain.attr.AttributeValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public class Node {

    private List<Node> subtree = new ArrayList<Node>();
    private final Node parent;

    private NodeClass classification;

    private AttributeValue attributeValue; // the attribute represented by this node

    public Node() {
        // root node, no parent
        this.parent = null;
    }

    public Node(Node parent) {
        this.parent = parent;
    }

    public AttributeValue getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(AttributeValue attributeValue) {
        this.attributeValue = attributeValue;
    }

    public boolean isRoot() {
        return !this.isLeaf() && attributeValue == null;
    }

    public boolean isLeaf() {
        return classification != null;
    }

    public void setClassification(NodeClass classification) {
        this.classification = classification;
    }

    public NodeClass getClassification() {
        return classification;
    }

    public void addChild(Node node) {
        subtree.add(node);
    }

    public List<Node> getChildren() {
        return subtree;
    }

    public Node getParent(){
        return parent;
    }

    public void makeLeaf(AttributeValue attributeValue, NodeClass classification) {
        this.classification = classification;
        this.attributeValue = attributeValue;
        this.subtree = new ArrayList<Node>();

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
        String nodeString = "(root)";
        if (this.attributeValue != null) {
            nodeString = this.attributeValue.getLabel() + ": " + this.attributeValue.getValue();
        }

        if (this.isLeaf()) {
            nodeString += String.valueOf(">> " + this.classification);
        }

        return nodeString;
    }

    public String toString() {
        return this.description();
    }

}
