package id3.api.domain.attr;

import java.util.List;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public interface AttributeClass {
    List<AttributeValue> getPossibleValues();

    String getLabel();
}
