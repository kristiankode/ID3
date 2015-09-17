package id3.importing.filter;

import id3.domain.Sample;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author kristian
 *         Created 17.09.15.
 */
public class RandomFilter {

    public List<Sample> randomlySelectSamples(List<Sample> unfiltered){

        List<Sample> filtered = new ArrayList<Sample>();

        for(Sample s : unfiltered) {
            if(new Random().nextBoolean()){
                filtered.add(s);
            }
        }

        return filtered;
    }
}
