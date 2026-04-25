package ui;

import bed.GardenBed;

import javax.swing.JButton;
import java.awt.Graphics;

public class BedButton extends JButton {

    private GardenBed bed;
    private String nameLine;
    private String subLine;
    private int filledStages;
    private int totalStages;

    public BedButton(GardenBed bed) {
    }

    public void refresh() {
    }

    public GardenBed getBed() {
        return null;
    }

    protected void paintComponent(Graphics g) {
    }
}
