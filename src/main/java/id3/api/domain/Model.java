package id3.api.domain;

import id3.api.domain.attr.AttributeClass;
import id3.api.domain.attr.AttributeValue;
import id3.api.domain.tree.Node;

import java.util.List;

/**
 * Contains a trained decision tree, ready for use in prediction.
 *
 * @author kristian
 *         Created 21.09.15.
 */
public class Model {

    private final Node tree;
    private final List<AttributeClass> attributes;
    private final AttributeValue targetAttribute;

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
