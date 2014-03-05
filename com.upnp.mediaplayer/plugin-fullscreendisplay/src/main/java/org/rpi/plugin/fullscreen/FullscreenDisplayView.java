package org.rpi.plugin.fullscreen;

import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 */
public class FullscreenDisplayView extends JFrame {

    private static final Logger LOGGER = Logger.getLogger(FullscreenDisplayView.class);

    private JDesktopPane d_pane;

    private MarqueePanel trackPanel;
    private MarqueePanel albumPanel;
    private MarqueePanel artistPanel;

    public FullscreenDisplayView() throws HeadlessException {

        this.setLayout(new BorderLayout());
        this.setSize(656, 416);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setUndecorated(true);

        d_pane = new JDesktopPane();
        d_pane.setBackground(Color.BLACK); // prevent unexpected LaF settings

        String trackText = "A track title, make it longer and longer and longer, to see some scrolling, ... is it...";
        JLabel track = new JLabel(trackText);
        track.setFont(new Font(Font.SANS_SERIF, Font.ITALIC, 40));

        trackPanel = this.addTextToPane(track, 10, 10, 636, 45);
        d_pane.add(trackPanel);

//        try {
//            BufferedImage image = ImageIO.read(new URL("http://192.168.0.150:9790/minimserver/*/remote_converted/electronic/various/compost_community/02-beanfield-the_season_(swag_vocal_mix).flac/$!picture-1777-63574.jpg"));
//            image = Scalr.resize(image, Scalr.Method.QUALITY, Scalr.Mode.FIT_TO_WIDTH, 300, Scalr.OP_ANTIALIAS);
//            ImageIcon icon = new ImageIcon(image);
//            JLabel label = new JLabel(icon);
//            label.setBounds(new Rectangle(new Point(10, 50), label.getPreferredSize()));
//
//            d_pane.add(label);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        // artist text
        String artistText = "Hubert von Goisern & die Alpinkatzen - Geht das eigentlich noch länger.....?????";
        JLabel artist = new JLabel(artistText);
        artist.setFont(new Font(Font.SERIF, Font.BOLD, 40));

        artistPanel = this.addTextToPane(artist, 320, 65, 326, 45);
        d_pane.add(artistPanel);

        // album text
//        String albumText = "A very very very Long Albumtitle.....";
        String albumText = "A short Text";
        JLabel album = new JLabel(albumText);
        album.setFont(new Font(Font.SANS_SERIF, Font.PLAIN, 30));

        albumPanel = this.addTextToPane(album, 320, 115, 326, 45);
        d_pane.add(albumPanel);

        this.add(d_pane);
    }

    private MarqueePanel addTextToPane(JLabel label, int x, int y, int width, int height) {
        label.setForeground(Color.YELLOW);
        label.setBackground(Color.BLACK);

        MarqueePanel mp = new MarqueePanel(10, 10, label);
        mp.setBounds(x, y, width, height);
        mp.setBackground(Color.BLACK);
        mp.setForeground(Color.BLACK);
        mp.setWrap(true);
        mp.setBorder(BorderFactory.createEmptyBorder());

        mp.add(label);

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
}