package org.rpi.plugin.fullscreen;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
  */
public class TrackModel {

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener changeListener) {
        this.pcs.addPropertyChangeListener(changeListener);
    }

    public void removePropertyChangeListener(PropertyChangeListener changeListener) {
        this.pcs.removePropertyChangeListener(changeListener);
    }

    private String trackTitle;

    private String albumTitle;

    private String artist;

    private String albumArtist;

    private String imageURI;

    private Long playTime;

    private Long trackDuration;

    private String genre;

    public String getTrackTitle() {
        return trackTitle;
    }

    public void setTrackTitle(String trackTitle) {
        String oldValue = this.trackTitle;
        this.trackTitle = trackTitle;
        this.pcs.firePropertyChange("trackTitle", oldValue, trackTitle);
    }

    public String getAlbumTitle() {
        return albumTitle;
    }

    public void setAlbumTitle(String albumTitle) {
        String oldValue = this.albumTitle;
        this.albumTitle = albumTitle;
        this.pcs.firePropertyChange("albumTitle", oldValue, albumTitle);
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        String oldValue = this.artist;
        this.artist = artist;
        this.pcs.firePropertyChange("artist", oldValue, artist);
    }

    public String getAlbumArtist() {
        return albumArtist;
    }

    public void setAlbumArtist(String albumArtist) {
        String oldValue = this.albumArtist;
        this.albumArtist = albumArtist;
        this.pcs.firePropertyChange("albumArtist", oldValue, albumArtist);
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String imageURI) {
        String oldValue = this.imageURI;
        this.imageURI = imageURI;
        this.pcs.firePropertyChange("imageURI", oldValue, imageURI);
    }

    public Long getTrackDuration() {
        return trackDuration;
    }

    public void setTrackDuration(Long trackDuration) {
        Long oldValue = this.trackDuration;
        this.trackDuration = trackDuration;
        this.pcs.firePropertyChange("trackDuration", oldValue, trackDuration);
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        String oldValue = this.genre;
        this.genre = genre;
        this.pcs.firePropertyChange("genre", oldValue, genre);
    }

    public Long getPlayTime() {
        return playTime;
    }

    public void setPlayTime(Long playTime) {
        Long oldValue = this.playTime;
        this.playTime = playTime;
         this.pcs.firePropertyChange("playTime", oldValue, playTime);
    }
}
