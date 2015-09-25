package id3.core.importing.read;

/**
 * @author kristian
 *         Created 17.09.15.
 */
public interface DataReader {

    void startFromTop();

    String[] readHeaderRow();

    String[] readDataRows();

    void close();
}
