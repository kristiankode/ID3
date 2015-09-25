package id3.api.domain.tree;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public enum NodeClass {

    POSITIVE, NEGATIVE;

    public static NodeClass fromBoolean(boolean bool) {
        if (bool) {
            return POSITIVE;
        } else {
            return NEGATIVE;
        }
    }
}
