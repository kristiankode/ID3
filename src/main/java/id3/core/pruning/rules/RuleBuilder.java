package id3.core.pruning.rules;

import id3.api.domain.Model;
import id3.api.domain.Rule;
import id3.api.domain.attr.AttributeValue;
import id3.api.domain.tree.Node;
import id3.api.domain.tree.NodeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author kristian
 *         Created 22.09.15.
 */
public class RuleBuilder {

    private final Logger log = LoggerFactory.getLogger(RuleBuilder.class);

    public List<Rule> build(Model model) {

        List<Rule> rules = getRules(model.getTree(), model.getTargetAttribute());

        log.debug("--------- Created following rules: ----------");
        for (Rule r : rules) {
            log.debug(r.toString());
        }

        return rules;
    }

    /**
     * Creates a new rule based on an existing rule. Used for pruning.
     *
     * @param rule
     * @param attributesToRemove Attributes that should not be included in the new rule.
     * @return
     */
    public Rule build(Rule rule, AttributeValue... attributesToRemove) {
        Rule newRule = new Rule(rule.getTargetValue());
        newRule.getPreconditions().addAll(rule.getPreconditions());
        newRule.getPreconditions().removeAll(Arrays.asList(attributesToRemove));

        newRule.setPostCondition(rule.getPostCondition());

        return newRule;
    }

    private List<Rule> getRules(Node node, AttributeValue target) {
        List<Rule> rules = new ArrayList<Rule>();

        for (Node leaf : getLeafNodes(node, new ArrayList<Node>())) {
            rules.add(buildRule(leaf, target));
        }

        return rules;
    }

    private Rule buildRule(Node leaf, AttributeValue target) {
        Rule rule = new Rule(target);

        rule.addPrecondition(leaf.getAttributeValue());
        rule.setPostCondition(leaf.getClassification());

        Node parent = leaf.getParent();
        while (!parent.isRoot()) {
            rule.addPrecondition(parent.getAttributeValue());
            parent = parent.getParent();
        }

        return rule;
    }

    List<Node> getLeafNodes(Node tree, List<Node> leafs) {

        if (tree.isLeaf()) {
            leafs.add(tree);
        } else {
            for (Node child : tree.getChildren()) {
                leafs.addAll(getLeafNodes(child, new ArrayList<Node>()));
            }
        }
        return leafs;
    }

    int getNumberOfLeafs(Node tree) {
        int counter = 0;
        if (tree.isLeaf()) {
            counter++;
        } else {
            for (Node child : tree.getChildren()) {
                counter += getNumberOfLeafs(child);
            }
        }

        return counter;
    }
}
