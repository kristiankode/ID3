package id3.domain;

import id3.domain.attr.AttributeClass;
import id3.domain.attr.AttributeValue;
import id3.domain.tree.Node;

import java.util.List;

/**
 * Contains a trained decision tree, ready for use in prediction.
 *
 * @author kristian
 *         Created 21.09.15.
 */
public class Model {

    final Node tree;
    final List<AttributeClass> attributes;
    final AttributeValue targetAttribute;

    public Model(Node tree, List<AttributeClass> attributes, AttributeValue targetAttribute) {
        this.tree = tree;
        this.attributes = attributes;
        this.targetAttribute = targetAttribute;
    }

    public Node getTree() {
        return tree;
    }

    public List<AttributeClass> getAttributes() {
        return attributes;
    }

    public AttributeValue getTargetAttribute() {
        return targetAttribute;
    }
}
