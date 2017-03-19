package org.abhra.math.util;

import com.abhra.writer.Writer;
import org.junit.After;
import org.junit.Test;

/**
 * Test GCF
 */
public class TestGCF {

    @After
    public void cleanup() {
        Writer.flush();
    }

    @Test
    public void testEuclidGCF() {
        GCF.getEuclidGCF(32, 28, 64);
        GCF.getEuclidGCF(3, 10, 19);
        GCF.getEuclidGCF(143, 110);
    }

    @Test
    public void testCommonFactorGCF() {
        GCF.getCommonFactorGCF(32, 28, 64);
        GCF.getCommonFactorGCF(143, 110);
    }

    @Test
    public void testLadderGCF() {
        GCF.getLadderGCF(32, 28, 64);
        GCF.getLadderGCF(143, 110);
    }
}
