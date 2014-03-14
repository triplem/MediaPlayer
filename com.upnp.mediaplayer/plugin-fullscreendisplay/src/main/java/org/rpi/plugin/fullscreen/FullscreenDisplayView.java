package org.rpi.plugin.fullscreen;

import org.apache.log4j.Logger;
import org.imgscalr.Scalr;
import org.rpi.utils.Utils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

/**
 *
 */
public class FullscreenDisplayView extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(FullscreenDisplayView.class);

    private JDesktopPane d_pane;

    private MarqueePanel trackPanel;
    private MarqueePanel albumPanel;
    private MarqueePanel artistPanel;
    private MarqueePanel genrePanel;

    private JLabel imageLabel;
    private JLabel playTimeLabel;
    private JLabel trackDurationLabel;

    public FullscreenDisplayView() throws HeadlessException {

        this.setLayout(new BorderLayout());
        this.setSize(656, 416);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);

        d_pane = new JDesktopPane();
        d_pane.setBackground(Color.BLACK); // prevent unexpected LaF settings

        // track text
        String trackText = "TrackTitle";
        trackPanel = this.addTextToPane(trackText, null, 10, 10, 636, 45);
        d_pane.add(trackPanel);

        // Image Panel
        try {
            ImageIcon icon = this.calculateImageLabel(getClass().getResource("/mediaplayer-original.png"));
            imageLabel = new JLabel(icon);
            imageLabel.setBounds(new Rectangle(new Point(10, 65), imageLabel.getPreferredSize()));

            d_pane.add(imageLabel);
        } catch (IOException e) {
            LOGGER.error("Cannot determine Track Image", e);
        }

        // artist text
        String artistText = "Artist";
        artistPanel = this.addTextToPane(artistText, null, 320, 65, 326, 45);
        d_pane.add(artistPanel);


        Font smallFont = new Font(Font.SANS_SERIF, Font.PLAIN, 30);

        // album text
        String albumText = "Album";
        albumPanel = this.addTextToPane(albumText, smallFont, 320, 115, 326, 45);
        d_pane.add(albumPanel);

        // album text
        String genreText = "Genre";
        genrePanel = this.addTextToPane(genreText, smallFont, 320, 165, 326, 45);
        d_pane.add(genrePanel);

        Font timeFont = new Font(Font.SANS_SERIF, Font.PLAIN, 20);
        String initialTime = "00:00:00";

        // label with playing time
        playTimeLabel = new JLabel(initialTime);
        playTimeLabel.setForeground(Color.YELLOW);
        playTimeLabel.setBackground(Color.BLACK);
        playTimeLabel.setFont(timeFont);
        playTimeLabel.setBounds(10, 350, 140, 45);
        d_pane.add(playTimeLabel);

        // label with track time
        trackDurationLabel = new JLabel(initialTime);
        trackDurationLabel.setForeground(Color.YELLOW);
        trackDurationLabel.setBackground(Color.BLACK);
        trackDurationLabel.setFont(timeFont);
        trackDurationLabel.setBounds(220, 350, 140, 45);
        d_pane.add(trackDurationLabel);

        this.add(d_pane);
    }

    private MarqueePanel addTextToPane(String initialText, Font font, int x, int y, int width, int height) {
        MarqueePanel mp = new MarqueePanel(8, 15);
        mp.setBounds(x, y, width, height);
        mp.setBackground(Color.BLACK);
        mp.setForeground(Color.BLACK);
        mp.setWrap(true);
        mp.setBorder(BorderFactory.createEmptyBorder());

        if (font != null) {
            mp.getLabel().setFont(font);
        }

        if (!Utils.isEmpty(initialText)) {
            mp.getLabel().setText(initialText);
        }

        return mp;
    }

    public MarqueePanel getTrackPanel() {
        return trackPanel;
    }

    public MarqueePanel getAlbumPanel() {
        return albumPanel;
    }

    public MarqueePanel getArtistPanel() {
        return artistPanel;
    }

    public JLabel getPlayTimeLabel() {
        return playTimeLabel;
    }

    public JLabel getTrackDurationLabel() {
        return trackDurationLabel;
    }

    public MarqueePanel getGenrePanel() {
        return genrePanel;
    }

    public ImageIcon calculateImageLabel(URL url) throws IOException {
        BufferedImage image = ImageIO.read(url);
        image = Scalr.resize(image, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, 300, Scalr.OP_ANTIALIAS);
        ImageIcon icon = new ImageIcon(image);

        return icon;
    }

    public void setImage(String urlString) throws IOException {
        URL url = new URL(urlString);

        ImageIcon icon = this.calculateImageLabel(url);
        this.imageLabel.setIcon(icon);
    }
}