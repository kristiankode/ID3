package id3.training.algorithms.entropy;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class EntropyCalculator {

    public double calc(double positiveSamples, double negativeSamples) {
        double totalSamples = positiveSamples + negativeSamples;
        if(totalSamples == 0) {
            return 0; // avoid division by zero
        }

        double posEn = -(positiveSamples / totalSamples) * log2(positiveSamples / totalSamples);
        double negEn = -(negativeSamples / totalSamples) * log2(negativeSamples / totalSamples);

        return posEn + negEn;
    }

    private double log2(double number) {
        if (number == 0) { // log2 of 0 is defined to be zero.
            return 0;
        } else {
            return Math.log(number) / Math.log(2);
        }
    }
}
