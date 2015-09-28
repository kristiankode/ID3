package id3.api;

import id3.api.domain.Rule;
import id3.core.prediction.Prediction;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * Command line interface.
 */
public class CommandLine {

    public static final int
            FILE_PATH_INDEX = 0,
            TARGET_COL_INDEX = 1,
            VALIDATION_PERCENTAGE_INDEX = 2,
            HAS_HEADER_ROW_INDEX = 3;

    private static Runner runner;

    /**
     * Executes the id3 program through the command line.
     * @param args Arguments in order:
     *             Absolute file path of the csv file to read,
     *             0-based index of which column to use as target,
     *             A number between 0-1 representing the percentage to reserve for validation,
     *             A number 0 or 1 specifying if the data contains header columns.
     * @throws FileNotFoundException
     */
    public static void main(String[] args) throws FileNotFoundException {

        String filePath = args[FILE_PATH_INDEX];
        Integer targetColumnIndex = Integer.parseInt(args[TARGET_COL_INDEX]);
        Double validationPercentage = Double.parseDouble(args[VALIDATION_PERCENTAGE_INDEX]);
        boolean dataHasHeaderRow = Boolean.parseBoolean(args[HAS_HEADER_ROW_INDEX]);

        List<Rule> rules = null;

        if (filePathIsValid(filePath)) {
            runner = new Runner(filePath, targetColumnIndex, validationPercentage * 100);

            // print accuracy of training-set before and after pruning
            printAccuracy("Training set before pruning", runner.getTrainingPredictionWithoutPruning());
            printAccuracy("Training set after pruning", runner.getTrainingPredictionWithPruning());

            // print accuracy of validation set before and after pruning
            printAccuracy("Validation set before pruning", runner.getValidationPredictionWithoutPruning());
            printAccuracy("Validation set after pruning", runner.getValidationPredictionWithPruning());

            rules = runner.getRules();

            print("------- Created the following rules (after pruning) ----------");
            for (Rule r : rules) {
                print(r.toString());
            }
        }
    }

    private static void printAccuracy(String description, List<Prediction> predictions) {
        print("----- Performance of " + description + ": -----");
        print("Accuracy:    " + runner.getAccuracy(predictions));
        print("Sensitivity: " + runner.getSensitivity(predictions));
        print("Specificity: " + runner.getSpecificity(predictions));
    }

    private static boolean filePathIsValid(String filepath) {
        try {
            print("checking file path " + filepath);
            new FileInputStream(filepath);
            return true;
        } catch (FileNotFoundException e) {
            print("Could not find file at following path: " + filepath);
            print("Path must be absolute");
            return false;
        }
    }

    private static void print(String s) {
        System.out.println(s);
    }

}
