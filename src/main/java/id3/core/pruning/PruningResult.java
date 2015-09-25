package id3.core.pruning;

import id3.api.domain.Rule;

/**
 * @author kristian
 *         Created 24.09.15.
 */
class PruningResult {
    final Rule unprunedRule;
    Rule prunedRule;

    public PruningResult(Rule unprunedRule, Rule prunedRule) {
        this.unprunedRule = unprunedRule;
        this.prunedRule = prunedRule;
    }

    public void setPrunedRule(Rule prunedRule) {
        this.prunedRule = prunedRule;
    }

    public Rule getUnprunedRule() {
        return unprunedRule;
    }

    public Rule getPrunedRule() {
        return prunedRule;
    }
}
