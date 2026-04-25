package ui;

import game.Game;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class InfoWindow extends JDialog {

    private Game game;
    private int bedIndex;

    public InfoWindow(JFrame parent, Game game, int bedIndex) {
        super(parent, "Plant Info", true);
    }
}
