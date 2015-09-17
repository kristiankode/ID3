package id3.importing.read;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @author kristian
 *         Created 03.09.15.
 */
public class BufferedCsvReader implements DataReader {
    private final static Logger log = LoggerFactory.getLogger(BufferedCsvReader.class);

    final String inputFilePath;
    BufferedReader bufferedReader;

    public static final String
            BLANK_SPACE_BABY = " ",
            COMMA_CHAMELEON = ",",
            EMPTY_STRING = "";
    private boolean readingStarted = false;

    public BufferedCsvReader(String inputFilePath)
            throws FileNotFoundException, UnsupportedEncodingException {
        this.inputFilePath = inputFilePath;
        Reader reader = new InputStreamReader(
                new FileInputStream(inputFilePath));
        bufferedReader = new BufferedReader(reader);
    }

    private void resetReader() {
        Reader reader = null;
        try {
            reader = new InputStreamReader(
                    new FileInputStream(inputFilePath));
            bufferedReader = new BufferedReader(reader);
            readingStarted = false;
        } catch (FileNotFoundException e) {
            // ignore, exception occurs in constructor if file does not exist
        }
    }

    public String[] readHeaderRow() {
        if (hasReadingStarted()) {
            resetReader();
        }
        return readNextRow();
    }

    public String[] readDataRows() {
        if (!hasReadingStarted()) {
            readHeaderRow();
        }
        return readNextRow();
    }

    private boolean hasReadingStarted() {
        return readingStarted;
    }

    private String[] readNextRow() {
        String[] row = null;
        try {
            String line = bufferedReader.readLine();
            readingStarted = true;
            if (line != null) {
                line = sanitizeLine(line);
                row = convertToArray(line);
            }
        } catch (IOException e) {
            log.error("Error while trying to read line: {}", e.getMessage());
        }
        return row;
    }

    String[] convertToArray(String line) {
        String[] row = line.split(COMMA_CHAMELEON);
        return row.length > 1 ? row : null;
    }

    String sanitizeLine(String line) {
        return line.replace(BLANK_SPACE_BABY, EMPTY_STRING);
    }

    public void close() {
        try {
            bufferedReader.close();
        } catch (IOException e) {
            log.error("Unable to close reader: {}", e.getMessage());
        }
    }
}
