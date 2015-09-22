package id3.pruning.rules;

import id3.domain.Model;
import id3.domain.tree.Node;
import id3.testdata.MushroomTestData;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kristian
 *         Created 22.09.15.
 */
public class RuleBuilderTest {
    Logger log = LoggerFactory.getLogger(RuleBuilderTest.class);

    RuleBuilder instance = new RuleBuilder();
    MushroomTestData data = new MushroomTestData();
    Model model;

    @Before
    public void setUp() throws Exception {
        model = data.getMushroomModel();
    }

    @Test
    public void testRuleBuilder() throws FileNotFoundException, UnsupportedEncodingException {
        instance.build(model);
    }

    @Test
    public void numberOfLeafs_givenMushroomModel_expect33() throws FileNotFoundException, UnsupportedEncodingException {
        int leafs = instance.getNumberOfLeafs(model.getTree(), 0);
        System.out.println("Found " + leafs + " leafs");
    }

    @Test
    public void getLeafNodes_givenMushroomModel_expect33() {
        List<Node> leafs = instance.getLeafNodes(model.getTree(), new ArrayList<Node>());

        for (Node leaf : leafs) {
            log.debug(getParents(leaf) + leaf.toString());
        }
    }

    String getParents(Node leaf) {
        Node parent = leaf;

        String parents = "";

        while (!parent.isRoot()) {
            parent = parent.getParent();
            parents = parent + " --> " + parents;
        }
        return parents;
    }

}