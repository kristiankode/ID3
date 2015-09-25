package id3.core.importing.filter;

import id3.api.domain.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;

/**
 * @author kristian
 *         Created 17.09.15.
 */
public class RandomFilter {
    private final Logger log = LoggerFactory.getLogger(RandomFilter.class);

    /**
     * Creates a random subset of samples.
     *
     * @param percentage The size of the subset,
     *                   expressed as a percentage of the unfiltered set (i.e 10 = 10 % of the full set)
     * @param unfiltered The complete, unfiltered set.
     * @return
     */
    public List<Sample> randomlySelectSamples(Double percentage, final List<Sample> unfiltered) {
        int targetSize = calculateTargetSize(percentage, unfiltered.size());
        log.debug("Selecting {}% of {} samples, target size is {}", percentage, unfiltered.size(), targetSize);

        List<Sample> filtered = new ArrayList<Sample>(unfiltered);

        while (filtered.size() != targetSize) {
            filtered.remove(selectRandomFromList(new ArrayList<Sample>(unfiltered)));
        }

        return filtered;
    }

    private Sample selectRandomFromList(List<Sample> samples) {
        Random r = new Random();
        return samples.get(r.nextInt(samples.size()-1));
    }

    int calculateTargetSize(Double percentage, int numberOfUnfiltered) {
        if (percentage > 100.0 || percentage < 0) {
            percentage = 100.0;
        }

        BigDecimal percentageFactor = valueOf(percentage).divide(valueOf(100), 6, ROUND_HALF_UP);

        return (valueOf(numberOfUnfiltered)
                .multiply(percentageFactor)).setScale(0, ROUND_HALF_UP).intValue();
    }
}
