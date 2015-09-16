package id3.domain;

import id3.csv.read.BufferedCsvReader;
import id3.domain.attr.AttrClassImpl;
import id3.domain.attr.AttrValueImpl;
import id3.domain.attr.AttributeValue;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author kristian
 *         Created 16.09.15.
 */
public class SampleBuilder {

    String filePath = "lol";
    BufferedCsvReader reader;

    public void initReader() {
        try {
            reader = new BufferedCsvReader(filePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void importFromFile() {
        // read all lines, identify each column and possible values
        // assume header row exists
        initReader();

        // read first row (header), collect all column names (attribute classes)
        String[] attributeLabels = reader.readNextRow();
        Map<Integer, AttrClassImpl> attrClasses = identifyAttributes(attributeLabels);
    }

    void readAttributeValuesFromRow(String[] row, Map<Integer, AttrClassImpl> attrClasses) {

        for (int columnIndex = 0; columnIndex < attrClasses.size(); columnIndex++) {
            AttributeValue val = new AttrValueImpl(attrClasses.get(columnIndex), row[columnIndex]);

        }
    }


    Map<Integer, AttrClassImpl> identifyAttributes(String[] attributeLabels) {
        Map<Integer, AttrClassImpl> attrClasses = new HashMap<Integer, AttrClassImpl>();

        int columnIndex = 0;

        for (String s : attributeLabels) {
            attrClasses.put(columnIndex, new AttrClassImpl(s));
            columnIndex++;
        }
        return attrClasses;
    }
}
