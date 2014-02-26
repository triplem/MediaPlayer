package org.rpi.main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
import java.util.TreeSet;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.RollingFileAppender;
import org.rpi.config.Config;
import org.rpi.log.CustomPatternLayout;

public class StartMe {

	private static boolean stop = false;
	private static Logger log = Logger.getLogger(StartMe.class);

	// private static PluginManager pm = null;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// NativeLibraryLoader.load("mssql", "mssqlserver.jar");
		// NativeLibraryLoader.load("pi4j", "libpi4j.so");
		Config.setStartTime();
		boolean bInput = false;
		for (String s : args) {
			if (s.equalsIgnoreCase("-input")) {
				bInput = true;
			}
		}
		getConfig();
		configureLogging();
		log.info("Starting......");
		try {
			log.info("Getting Network Interfaces");
			Enumeration e = NetworkInterface.getNetworkInterfaces();
			while (e.hasMoreElements()) {
				NetworkInterface n = (NetworkInterface) e.nextElement();
				Enumeration ee = n.getInetAddresses();
				log.info("Network Interface Name: " + n.getDisplayName());
				while (ee.hasMoreElements()) {
					InetAddress i = (InetAddress) ee.nextElement();
					log.info("IPAddress for Network Interface: " + n.getDisplayName() + " : " + i.getHostAddress());
				}
			}
		} catch (Exception e) {
			log.error("Error Getting IPAddress", e);
		}
		log.info("End Of Network Interfaces");
		log.info("JVM Version: " + System.getProperty("java.version"));
		printSystemProperties();
		SimpleDevice sd = new SimpleDevice();
		// loadPlugins();
		sd.attachShutDownHook();
		if (bInput) {

			BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
			String line = "";

			try {
				while (line.equalsIgnoreCase("quit") == false) {
					line = in.readLine();
				}
				in.close();
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		else {
			while (!stop) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					log.error("Error", e);
				}
			}
		}
		System.exit(0);
	}


	/***
	 * List all the files in this directory and sub directories.
	 * 
	 * @param directoryName
	 * @return
	 */
//	public static List<File> listFiles(String directoryName) {
//		File directory = new File(directoryName);
//		List<File> resultList = new ArrayList<File>();
//		File[] fList = directory.listFiles();
//		resultList.addAll(Arrays.asList(fList));
//		for (File file : fList) {
//			if (file.isFile()) {
//			} else if (file.isDirectory()) {
//				resultList.addAll(listFiles(file.getAbsolutePath()));
//			}
//		}
//		return resultList;
//	}

	/***
	 * Print out the System Properties.
	 */
	private static void printSystemProperties() {
		log.warn("#####Start of System Properties#########");
		Properties pr = System.getProperties();
		TreeSet propKeys = new TreeSet(pr.keySet());
		for (Iterator it = propKeys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			log.warn("" + key + "=" + pr.get(key));
		}
		log.warn("#####End of System Properties#########");
		log.warn("");
		Map<String, String> variables = System.getenv();
		log.warn("#####Start of System Variables#########");
		for (Map.Entry<String, String> entry : variables.entrySet()) {
			String name = entry.getKey();
			String value = entry.getValue();
			log.warn(name + "=" + value);
		}
		log.warn("#####End of System Variables#########");
		log.warn("");
	}

	/***
	 * Read the app.properties file from the current directory.
     *
     * If the app.properties file is not found there, it will try to load the app.properties from the classpath.
	 */
	private static void getConfig() {
		Properties pr = new Properties();
		try {
			File appProperties = new File("app.properties");
            if (!appProperties.exists()) {
                pr.load(StartMe.class.getClassLoader().getResourceAsStream("app.properties"));
            } else {
                pr.load(new FileInputStream("app.properties"));
            }

			Config.friendly_name = pr.getProperty("friendly.name");

			try {
				String playlists = pr.getProperty("mplayer.playlist");
				String[] splits = playlists.split(",");
				Config.playlists = Arrays.asList(splits);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Config.debug = pr.getProperty("openhome.debug.level");
			Config.logfile = pr.getProperty("log.file");
			Config.loglevel = pr.getProperty("log.file.level");
			Config.logconsole = pr.getProperty("log.console.level");
			Config.mplayer_path = pr.getProperty("mplayer.path");
			Config.setSaveLocalPlayList(pr.getProperty("save.local.playlist"));
			Config.port = Config.convertStringToInt(pr.getProperty("openhome.port"));
			Config.mplayer_cache = Config.convertStringToInt(pr.getProperty("mplayer.cache"));
			Config.mplayer_cache_min = Config.convertStringToInt(pr.getProperty("mplayer.cache_min"));
			Config.playlist_max = Config.convertStringToInt(pr.getProperty("playlist.max"), 1000);
			Config.mpd_host = pr.getProperty("mpd.host");
			Config.mpd_port = Config.convertStringToInt(pr.getProperty("mpd.port"), 6600);
			Config.mpd_preload_timer = Config.convertStringToInt(pr.getProperty("mpd.preload.timer"), 10);
			Config.player = pr.getProperty("player");
			Config.enableAVTransport = Config.convertStringToBoolean(pr.getProperty("enableAVTransport"), true);
			Config.enableReceiver = Config.convertStringToBoolean(pr.getProperty("enableReceiver"), true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/***
	 * Set up our logging
	 */
	private static void configureLogging() {

		try {
			CustomPatternLayout pl = new CustomPatternLayout();
			pl.setConversionPattern("%d [%t] %-5p [%-10c] %m%n");
			pl.activateOptions();
			// CustomRollingFileAppender fileAppender = new
			// CustomRollingFileAppender(pl,Config.logfile,".log",true);
			RollingFileAppender fileAppender = new RollingFileAppender();
			fileAppender.setAppend(true);
			fileAppender.setMaxFileSize("5mb");
			fileAppender.setMaxBackupIndex(5);
			fileAppender.setFile(Config.logfile);
			fileAppender.setThreshold(Config.getLogFileLevel());
			fileAppender.setLayout(pl);
			fileAppender.activateOptions();
			Logger.getRootLogger().addAppender(fileAppender);
			ConsoleAppender consoleAppender = new ConsoleAppender();
			consoleAppender.setLayout(pl);
			consoleAppender.activateOptions();
			consoleAppender.setThreshold(Config.getLogConsoleLevel());
			Logger.getRootLogger().addAppender(consoleAppender);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
