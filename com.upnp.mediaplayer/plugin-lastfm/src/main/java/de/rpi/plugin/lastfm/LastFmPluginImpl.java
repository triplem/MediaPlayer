package de.rpi.plugin.lastfm;

import de.umass.lastfm.Authenticator;
import de.umass.lastfm.Caller;
import de.umass.lastfm.Session;
import de.umass.lastfm.Track;
import de.umass.lastfm.scrobble.ScrobbleResult;
import org.apache.log4j.Logger;
import org.rpi.os.OSManager;
import org.rpi.player.PlayManager;
import org.rpi.player.events.*;
import org.rpi.playlist.CustomTrack;
import org.rpi.utils.Utils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

/**
 *
 */
public class LastFmPluginImpl implements LastFmPluginInterface, Observer {

    private static Logger log = Logger.getLogger(LastFmPluginImpl.class);

    private static final String lastfm_api_key = "edaa51da6d7914f0b42c4bd8c9437d6c";
    private static final String lastfm_secret = "f985f258cc855f881a3bf337fe810456";
    private static final String userAgent = "MediaPlayer";

    private String lastfm_username = null;
    private String lastfm_password = null;

    private Boolean lastfm_debugmode = false;

    private Proxy.Type lastfm_proxymode = Proxy.Type.DIRECT;
    private String lastfm_proxy_ip = null;
    private Integer lastfm_proxy_port = null;

    private static Session session;

    public LastFmPluginImpl() {
        log.info("Init LastFmPluginImpl");

        getConfig();
        init();

        PlayManager.getInstance().observInfoEvents(this);
        PlayManager.getInstance().observeProductEvents(this);
    }

    @Override
    public void update(Observable o, Object e) {
        log.debug("Event: " + e.toString());
        EventBase base = (EventBase) e;

        switch (base.getType()) {
            case EVENTTRACKCHANGED:
                EventTrackChanged etc = (EventTrackChanged) e;
                CustomTrack track = etc.getTrack();
                if (track != null) {
                    String s = track.getFullDetails();
                    log.debug("TrackChanged: " + s);

                    int now = (int) System.currentTimeMillis() / 1000;
                    ScrobbleResult sres = Track.scrobble(track.getArtist(), track.getTitle(), now, session);

                    log.info("scrobbling ok? " + (sres.isSuccessful() && !sres.isIgnored()));
                } else {
                    log.debug("Track was NULL");
                }

                break;
            case EVENTUPDATETRACKMETATEXT:
                EventUpdateTrackMetaText et = (EventUpdateTrackMetaText) e;
                log.debug("Track Changed: " + et.getTitle() + " : " + et.getArtist());

                int now = (int) System.currentTimeMillis() / 1000;
                ScrobbleResult sres = Track.scrobble(et.getArtist(), et.getTitle(), now, session);

                log.info("scrobbling ok? " + (sres.isSuccessful() && !sres.isIgnored()));

                break;
        }
    }


    private void init() {
        Caller.getInstance().setUserAgent(userAgent);

        if (lastfm_proxymode != Proxy.Type.DIRECT) {
            SocketAddress sa = new InetSocketAddress(lastfm_proxy_ip, lastfm_proxy_port);
            Proxy proxy = new Proxy(lastfm_proxymode, sa);
            Caller.getInstance().setProxy(proxy);
        }

        session = Authenticator.getMobileSession(lastfm_username, lastfm_password, lastfm_api_key, lastfm_secret);
    }

    private void getConfig() {
        Properties props = new Properties();

        String class_name = this.getClass().getName();
        log.debug("Find Class, ClassName: " + class_name);

        String path = OSManager.getInstance().getFilePath(this.getClass(), false);
        log.debug("Getting lastfm.properties from Directory: " + path);

        try {
            InputStreamReader reader = new FileReader(new File(path, "lastfm.properties"));

            props.load(reader);

            lastfm_username = props.getProperty("lastfm.username");

            if (Utils.isEmpty(lastfm_username)) {
                log.error("cannot find a valid lastfm username");
                return;
            }

            lastfm_password = props.getProperty("lastfm.password");
            lastfm_debugmode = Boolean.valueOf(props.getProperty("lastfm.debugmode", "false"));

            String proxymode = props.getProperty("lastfm.proxy.mode", "DIRECT");
            lastfm_proxymode = Proxy.Type.valueOf(proxymode);

            lastfm_proxy_ip = props.getProperty("lastfm.proxy.ip");
            lastfm_proxy_port = Integer.parseInt(props.getProperty("lastfm.proxy.port", ""));

        } catch (FileNotFoundException e) {
            log.error("No properties for lastfm plugin found", e);
            return;
        } catch (IOException e) {
            log.error("Cannot load properties for lastfm plugin", e);
            return;
        }

    }



}
