package id3.domain.tree;

import id3.domain.AttributeValue;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public class Node {

    List<Node> subtree;

    NodeClass classification;

    String label;

    AttributeValue attributeValue; // the attribute represented by this node

    public AttributeValue getAttributeValue() {
        return attributeValue;
    }

    public void setAttributeValue(AttributeValue attributeValue) {
        this.attributeValue = attributeValue;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isLeaf(){
        return classification != null;
    }

    public void setClassification(NodeClass classification){
        this.classification = classification;
    }

    public NodeClass getClassification() {
        return classification;
    }

    public void addChild(Node node) {
        initSubtreeIfNecessary();
        subtree.add(node);
    }


    public void addLeaf(AttributeValue attributeValue) {
        Node leaf = new Node();
        leaf.attributeValue = attributeValue;
        this.addChild(leaf);
    }

    private void initSubtreeIfNecessary() {
        if(subtree == null){
            subtree = new ArrayList<Node>();
        }
    }
}
