package org.rpi.plugin.lastfm;

import org.junit.Test;
import org.rpi.os.OSManager;

import java.io.File;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by triplem on 21.02.14.
 */
public class LastFmTest {

    @Test
    public void testDebugOsManager() {
        OSManager manager = OSManager.getInstance();

        String filePath = manager.getFilePath(this.getClass(), false);
        String pathSuffix = manager.getOhnetLibDir();

        String fullPath = filePath + File.separator + pathSuffix;

        assertTrue("fullPath must exist: " + fullPath, new File(fullPath).exists());

    }
}
