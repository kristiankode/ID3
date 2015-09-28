package id3.core.training.algorithms.entropy;

import java.math.BigDecimal;

import static java.math.BigDecimal.valueOf;
import static java.math.RoundingMode.HALF_UP;

/**
 * General entropy calculator.
 */
public class EntropyCalculator {

    /**
     * Calculates entropy for a data set.
     * <p>
     * Formula used:
     * Entropy = -(partition / totalSamples) * log2(partition / totalSamples);
     *
     * @param partitionedSamples The sizes of all subsets the data can be divided into by attribute value.
     * @return entropy
     */
    public double calc(Integer... partitionedSamples) {
        Integer totalSamples = calculateTotalSamples(partitionedSamples);
        if (totalSamples == 0) {
            return 0; // avoid division by zero
        }

        Double entropy = 0.0;

        for (Integer partition : partitionedSamples) {
            entropy += entropyForPartition(partition, totalSamples);
        }

        return entropy;
    }

    /**
     * Calculates entropy for a partition of the data set.
     * <p>
     * Formula used:
     * Entropy = -(partition / totalSamples) * log2(partition / totalSamples);
     *
     * @param partition    The size of a subset of the data
     * @param totalSamples The size of the complete data set
     * @return entropy
     */
    private Double entropyForPartition(Integer partition, Integer totalSamples) {

        BigDecimal partitionFraction = valueOf(partition).divide(valueOf(totalSamples), 6, HALF_UP);

        return valueOf(-1).multiply(partitionFraction)
                .multiply(valueOf(log2(partitionFraction.doubleValue())))
                .doubleValue();
    }

    Integer calculateTotalSamples(Integer... partitionedSamples) {
        Integer total = 0;
        for (Integer samplePartition : partitionedSamples) {
            total += samplePartition;
        }

        return total;
    }

    private double log2(double number) {
        if (number == 0) { // log2 of 0 is defined to be zero.
            return 0;
        } else {
            return Math.log(number) / Math.log(2);
        }
    }
}
