package ui;

import game.Game;
import plant.PlantData;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.util.ArrayList;

public class ShopWindow extends JDialog {

    private Game game;
    private int bedIndex;
    private ArrayList<JButton> buyButtons;
    private ArrayList<PlantData> listedPlants;

    public ShopWindow(JFrame parent, Game game, int bedIndex) {
        super(parent, "Seed Shop", true);
    }
}
