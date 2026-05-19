package ui;

import bed.GardenBed;
import plant.RegularPlant;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.*;
import java.io.InputStream;

public class BedButton extends JButton {

    private GardenBed bed;

    public BedButton(GardenBed bed) {
        this.bed = bed;
        setFocusPainted(false);
        setBorderPainted(false);
        setBackground(new Color(100, 65, 40));
        refresh();
    }
    /** updates the button image to match the current state of the bed */
    public void refresh() {
        String path;

        if (bed.isEmpty()) {
            path = "/images/Empty.png";
        } else {
            RegularPlant plant = bed.getPlant();

            if (plant.isWithered()) {
                path = "/images/withered.png";
            } else {
                int stage = plant.getCurrentStage() + 1;
                path = "/images/" + plant.getId() + "/" + plant.getId() + "_0" + stage + ".png";
            }
        }

        ImageIcon icon = loadImage(path);

        if (icon != null) {
            Image scaled = icon.getImage().getScaledInstance(120, 120,Image.SCALE_SMOOTH);
            setIcon(new ImageIcon(scaled));
        } else {
            setIcon(null);
        }

        repaint();
    }
    /**
     * loads an image from the given resource path
     * @return an ImageIcon or null if the image could not be loaded
     */
    private ImageIcon loadImage(String path) {
        try {
            InputStream stream = getClass().getResourceAsStream(path);
            if (stream == null) return null;
            return new ImageIcon(ImageIO.read(stream));
        } catch (Exception ex) {
            return null;
        }
    }

    public GardenBed getBed() {
        return bed;
    }
}