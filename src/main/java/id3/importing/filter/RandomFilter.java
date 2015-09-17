package id3.importing.filter;

import id3.domain.Sample;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author kristian
 *         Created 17.09.15.
 */
public class RandomFilter {

    /**
     * Creates a random subset of samples.
     *
     * @param percentage The size of the subset,
     *                   expressed as a percentage of the unfiltered set (i.e 10 = 10 % of the full set)
     * @param unfiltered The complete, unfiltered set.
     * @return
     */
    public List<Sample> randomlySelectSamples(int percentage, List<Sample> unfiltered) {
        int targetSize = calculateTargetSize(percentage, unfiltered);

        List<Sample> filtered = new ArrayList<Sample>();

        while (filtered.size() < targetSize) {
            filtered.add(selectRandomFromList(unfiltered));
        }

        return filtered;
    }

    private Sample selectRandomFromList(List<Sample> samples) {
        Random r = new Random();
        return samples.get(r.nextInt(samples.size()));
    }

    private int calculateTargetSize(int percentage, List<Sample> unfiltered) {
        if (percentage > 100 || percentage < 0) {
            percentage = 100;
        }
        return (BigDecimal.valueOf(unfiltered.size())
                .divide(BigDecimal.valueOf(percentage), 0, BigDecimal.ROUND_HALF_UP)).intValue();
    }
}
