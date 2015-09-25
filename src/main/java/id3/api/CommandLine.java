package id3.api;

import id3.api.domain.Rule;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * @author kristian
 *         Created 25.09.15.
 */
public class CommandLine {

    public static final int
            FILE_PATH_INDEX = 0,
            TARGET_COL_INDEX = 1,
            VALIDATION_PERCENTAGE_INDEX = 2,
            HAS_HEADER_ROW_INDEX = 3;

    private static Runner runner;

    public static void main(String[] args) throws FileNotFoundException {

        print("Started program with arguments:");
        for (String s : args) {
            print(" - " + s);
        }

        String filePath = args[FILE_PATH_INDEX];
        Integer targetColumnIndex = Integer.parseInt(args[TARGET_COL_INDEX]);
        Double validationPercentage = Double.parseDouble(args[VALIDATION_PERCENTAGE_INDEX]);
        boolean dataHasHeaderRow = Boolean.parseBoolean(args[HAS_HEADER_ROW_INDEX]);

        List<Rule> rules = null;

        if (filePathIsValid(filePath)) {
            runner = new Runner(filePath, targetColumnIndex, validationPercentage * 100);
            rules = runner.getRules();

            print("------- Created the following rules (after pruning) ----------");
            for (Rule r : rules) {
                print(r.toString());
            }

            printAccuracy();
        }
    }

    private static void printAccuracy() {
        print("----- Performance after pruning: -----");
        print("Accuracy:    " + runner.getAccuracy());
        print("Sensitivity: " + runner.getSensitivity());
        print("Specificity: " + runner.getSpecificity());
    }

    private static String fixFilePath(String path) {
        return CommandLine.class.getResource(path).getPath();
    }

    private static boolean filePathIsValid(String filepath) {
        try {
            print("checking file path " + filepath);
            new FileInputStream(filepath);
            return true;
        } catch (FileNotFoundException e) {
            print("Could not find file at following path: " + filepath);
            print("Path is relative to the applications working directory");
            return false;
        }
    }

    private static void print(String s) {
        System.out.println(s);
    }

}
