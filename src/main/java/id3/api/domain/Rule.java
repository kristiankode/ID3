package id3.api.domain;

import id3.api.domain.attr.AttributeValue;
import id3.api.domain.tree.NodeClass;

import java.util.List;

/**
 * @author kristian
 *         Created 22.09.15.
 */
public interface Rule {

    List<AttributeValue> getPreconditions();

    NodeClass getPostCondition();

    AttributeValue getTargetValue();
}
