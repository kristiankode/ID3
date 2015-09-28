package id3.api.domain;

import id3.api.domain.attr.AttributeValue;
import id3.api.domain.tree.NodeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kristian
 *         Created 22.09.15.
 */
public class Rule {

    public static final String AND = " AND ";
    private NodeClass postCondition;
    private final List<AttributeValue> preconditions = new ArrayList<AttributeValue>();
    private final AttributeValue targetValue;

    public Rule(AttributeValue target) {
        this.targetValue = target;
    }

    public Rule(Rule rule) {
        this.addPreconditions(rule.getPreconditions());
        this.targetValue = rule.getTargetValue();
    }

    public void addPrecondition(AttributeValue precondition) {
        if (precondition != null) {
            this.preconditions.add(0, precondition); // insert at beginning
        }
    }

    public void addPreconditions(List<AttributeValue> preconditions) {
        this.preconditions.addAll(new ArrayList<AttributeValue>(preconditions));
    }

    public void setPostCondition(NodeClass postCondition) {
        this.postCondition = postCondition;
    }

    public List<AttributeValue> getPreconditions() {
        return preconditions;
    }

    public NodeClass getPostCondition() {
        return postCondition;
    }

    public AttributeValue getTargetValue() {
        return targetValue;
    }

    public String toString() {

        StringBuilder sb = new StringBuilder();

        for (AttributeValue val : preconditions) {
            sb.append("<").append(val).append(">")
                    .append(AND);
        }
        if (sb.indexOf(AND) >= 0) {
            sb.delete(sb.lastIndexOf(AND), sb.length());
        }

        sb.append(" --> ").append(targetValue).append(" = ").append(postCondition);

        return sb.toString();

    }
}