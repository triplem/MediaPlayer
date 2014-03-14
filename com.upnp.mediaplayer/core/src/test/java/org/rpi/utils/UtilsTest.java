package org.rpi.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by triplem on 14.03.14.
 */
public class UtilsTest {

    @Test
    public void testParseDurationString() {
        assertEquals(new Long(154000), Utils.parseDurationString("0:02:34.000"));
        assertEquals(new Long(154000), Utils.parseDurationString("00:02:34.000"));
        assertEquals(new Long(154000), Utils.parseDurationString("00:02:34"));
    }

    @Test
    public void testPrintTimeString() {
        assertEquals("00:02:34", Utils.printTimeString(154000L));
    }
}
