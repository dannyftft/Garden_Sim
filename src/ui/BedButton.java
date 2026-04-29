package ui;

import bed.GardenBed;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.io.InputStream;

public class BedButton extends JButton {

    private GardenBed bed;

    public BedButton(GardenBed bed) {
        this.bed = bed;
        setFocusPainted(false); // removes the dotted border when clicked
        refresh();
    }

    public void refresh() {
        setIcon(loadImage("/images/withered.png")); // placeholder until plant logic is done
        repaint();
    }

    //placeholder
    private ImageIcon loadImage(String path) {
        try {
            InputStream stream = getClass().getResourceAsStream(path);
            return new ImageIcon(ImageIO.read(stream));
        } catch (Exception ex) {
            return null; // shows nothing if the image is missing
        }
    }

    public GardenBed getBed() {
        return bed;
    }
}