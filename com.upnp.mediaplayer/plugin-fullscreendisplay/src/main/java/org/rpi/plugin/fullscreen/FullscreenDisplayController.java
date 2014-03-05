package org.rpi.plugin.fullscreen;

import org.apache.log4j.Logger;
import org.rpi.player.PlayManager;
import org.rpi.player.events.EventBase;
import org.rpi.player.events.EventTrackChanged;
import org.rpi.player.events.EventUpdateTrackMetaText;
import org.rpi.playlist.CustomTrack;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Observable;
import java.util.Observer;

/**
 *
 */
public class FullscreenDisplayController implements PropertyChangeListener {

    private static final Logger LOGGER = Logger.getLogger(FullscreenDisplayController.class);

    private TrackModel model;
    private FullscreenDisplayView view;

    private SwingWorker<Void, Void> worker;

    public FullscreenDisplayController(FullscreenDisplayView view, TrackModel model) {
        this.model = model;
        this.view = view;

        this.model.addPropertyChangeListener(this);
    }

    public void propertyChange(PropertyChangeEvent e) {
        String propertyName = e.getPropertyName();
        String newValue = (String)e.getNewValue();

        System.out.println("PropertyName: " + propertyName);
        if (propertyName.equals("albumTitle")) {
            view.getAlbumPanel().stopScrolling();
            view.getAlbumPanel().getLabel().setText(newValue);
            view.getAlbumPanel().startScrolling();
        }
        else if (propertyName.equals("artist")) {
            view.getArtistPanel().stopScrolling();
            view.getArtistPanel().getLabel().setText(newValue);
            view.getArtistPanel().startScrolling();
        }
        else if (propertyName.equals("trackTitle")) {
            view.getTrackPanel().stopScrolling();
            view.getTrackPanel().getLabel().setText(newValue);
            view.getTrackPanel().startScrolling();
        }
    }

}
