package id3.importing.read;

/**
 * @author kristian
 *         Created 17.09.15.
 */
public interface DataReader {

    String[] readHeaderRow();

    String[] readDataRows();

    void close();
}
