package id3.domain.tree;

/**
 * @author kristian
 *         Created 15.09.15.
 */
public enum NodeClass {

    POSITIVE(1), NEGATIVE(-1);

    private final int i;

    public static NodeClass fromBoolean(boolean bool) {
        if (bool) {
            return POSITIVE;
        } else {
            return NEGATIVE;
        }
    }

    NodeClass(int i) {
        this.i = i;
    }
}
