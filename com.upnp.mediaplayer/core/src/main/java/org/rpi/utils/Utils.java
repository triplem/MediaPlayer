package org.rpi.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.openhome.net.device.IDvInvocation;

public class Utils {

	private static Logger log = Logger.getLogger(Utils.class);

	public static String getLogText(IDvInvocation paramIDvInvocation) {
		if (!log.isDebugEnabled())
			return "";
		String sp = " ";
		StringBuilder sb = new StringBuilder();
		try {
			sb.append(sp);
			sb.append("Adapter: ");
			sb.append(getAdapterIP(paramIDvInvocation.getAdapter()));
			sb.append(sp);
			sb.append("uriPrefix: ");
			sb.append(paramIDvInvocation.getResourceUriPrefix());
			sb.append(sp);
			sb.append("Version:");
			sb.append(paramIDvInvocation.getVersion());
			sb.append(sp);
		} catch (Exception e) {
		}
		return sb.toString();
	}

	private static String getAdapterIP(int ip) {
		String ipStr = String.format("%d.%d.%d.%d", (ip >>> 24 & 0xff), (ip >>> 16 & 0xff), (ip >>> 8 & 0xff), (ip & 0xff));
		return ipStr;
	}

	/**
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean containsString(String source, ArrayList<String> target) {
		if (source == null || target == null)
			return false;
		for (String s : target) {
			if (containsString(source, s)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 * @param source
	 * @param target
	 * @return
	 */
	public static boolean containsString(String source, String target) {
		if (source.toUpperCase().contains(target.toUpperCase())) {
			return true;
		}
		return false;
	}

	public static String[] execute(String command) throws Exception {
		ArrayList<String> list = new ArrayList<String>();
		Process pa = Runtime.getRuntime().exec(command);
		pa.waitFor();
		BufferedReader reader = new BufferedReader(new InputStreamReader(pa.getInputStream()));
		String line;
		while ((line = reader.readLine()) != null) {
			line = line.trim();
			log.debug("Result of " + command + " : " + line);
			list.add(line);
		}
		reader.close();
		pa.getInputStream().close();
		return list.toArray(new String[list.size()]);
	}

    /**
     * Checks if the given string is empty. Empty means, it is null, has no content or has not length.
     *
     * @param string
     * @return
     */
    public static boolean isEmpty(String string) {
        if (string == null || string.equals("") || string.length() == 0) {
            return true;
        }

        return false;
    }

}
