package org.rpi.plugin.lastfm;

import org.junit.Test;
import org.rpi.os.OSManager;

import static org.junit.Assert.assertFalse;

/**
 * Created by triplem on 21.02.14.
 */
public class LastFmTest {

    @Test
    public void testDebugOsManager() {
        OSManager manager = OSManager.getInstance();

        assertFalse(manager.isRaspi());
    }
}
