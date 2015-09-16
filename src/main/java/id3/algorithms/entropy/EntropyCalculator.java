package id3.algorithms.entropy;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class EntropyCalculator {

    public double calc(double positiveSamples, double negativeSamples) {
        double totalSamples = positiveSamples + negativeSamples;

        double posEn = -(positiveSamples / totalSamples) * log2(positiveSamples / totalSamples);
        double negEn = -(negativeSamples / totalSamples) * log2(negativeSamples / totalSamples);

        return posEn + negEn;
    }

    public double log2(double number) {
        if (number == 0) { // log2 of 0 is defined to be zero.
            return 0;
        } else {
            return Math.log(number) / Math.log(2);
        }
    }
}
