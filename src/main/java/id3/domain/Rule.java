package id3.domain;

import id3.domain.attr.AttributeValue;
import id3.domain.tree.NodeClass;

import java.util.List;

/**
 * @author kristian
 *         Created 22.09.15.
 */
public interface Rule {

    List<AttributeValue> getPreconditions();

    NodeClass getPostCondition();

    AttributeValue getTargetValue();

    void setPostCondition(NodeClass classification);
}
