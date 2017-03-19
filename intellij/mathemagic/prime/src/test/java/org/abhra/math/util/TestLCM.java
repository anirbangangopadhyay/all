package org.abhra.math.util;

import com.abhra.writer.Writer;
import org.junit.After;
import org.junit.Test;

/**
 * Test GCF
 */
public class TestLCM {

    @After
    public void cleanup() {
        Writer.flush();
    }

    @Test
    public void testEuclidLCM() {
        LCM.getEuclidLCM(32, 28, 64);
    }

    @Test
    public void testCommonFactorLCM() {
        LCM.getCommonFactorLCM(32, 28, 64);
    }

    @Test
    public void testLadderLCM() {
        LCM.getLadderLCM(32, 28, 64);
    }
}
