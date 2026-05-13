package ui;

import bed.GardenBed;
import game.Game;
import plant.RegularPlant;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

public class InfoWindow extends JDialog {

    private Game game;
    private int bedIndex;
    private JButton sellButton;
    private JButton closeButton;

    public InfoWindow(JFrame parent, Game game, int bedIndex) {
        super(parent, "Plant Info", true);
        this.game = game;
        this.bedIndex = bedIndex;

        setSize(380, 250);
        setResizable(false);
        setLocationRelativeTo(parent);

        GardenBed bed = game.getBeds().get(bedIndex);
        RegularPlant plant = bed.getPlant();

        // header: plant name on the left
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(45, 89, 40));
        header.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        JLabel nameLabel = new JLabel(plant.getName());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        nameLabel.setForeground(new Color(250, 245, 230));

        header.add(nameLabel,BorderLayout.WEST);

        // main info area
        JPanel info = new JPanel();
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        info.setBackground(new Color(252, 245, 220));
        info.setBorder(BorderFactory.createEmptyBorder(12, 20, 8, 20));

        // stage row
        info.add(buildRow("Growth stage:", (plant.getCurrentStage() + 1) + " / " + plant.getStageCount()));
        info.add(Box.createVerticalStrut(6));

        // time remaining row
        info.add(buildRow("Time left:", plant.getEstimatedTimeLeft()));
        info.add(Box.createVerticalStrut(6));

        // sell price row green normally and red when withered
        JPanel priceRow = new JPanel(new BorderLayout());
        priceRow.setBackground(new Color(252, 245, 220));
        priceRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 24));
        priceRow.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel priceKey = new JLabel("Current sell value:");
        priceKey.setFont(new Font("SansSerif", Font.BOLD, 12));
        priceKey.setForeground(new Color(100, 78, 48));

        JLabel priceVal = new JLabel("$" + plant.getCurrentPrice());
        priceVal.setFont(new Font("SansSerif", Font.BOLD, 15));
        if (plant.isWithered()) {
            priceVal.setForeground(new Color(180, 40, 40));
        } else {
            priceVal.setForeground(new Color(28, 108, 28));
        }

        priceRow.add(priceKey,BorderLayout.WEST);
        priceRow.add(priceVal,BorderLayout.EAST);
        info.add(priceRow);

        // close button
        closeButton = new JButton("Close");
        closeButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        closeButton.setBackground(new Color(130, 45, 35));
        closeButton.setForeground(new Color(250, 245, 230));
        closeButton.setFocusPainted(false);
        closeButton.setBorderPainted(false);
        closeButton.setOpaque(true);
        closeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        closeButton.addActionListener(e -> dispose());

        // sell button
        sellButton = new JButton("Sell for $" + plant.getCurrentPrice());
        sellButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        sellButton.setBackground(new Color(45, 89, 40));
        sellButton.setForeground(new Color(250, 245, 230));
        sellButton.setFocusPainted(false);
        sellButton.setBorderPainted(false);
        sellButton.setOpaque(true);
        sellButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        sellButton.addActionListener(e -> {
            game.sellPlant(bedIndex); // removes the plant and adds money to the player
            dispose();
        });

        // bottom bar with close on the left and sell on the right
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        bottom.setBackground(new Color(236, 228, 204));
        bottom.setBorder(BorderFactory.createEmptyBorder(4, 10, 4, 10));
        bottom.add(closeButton);
        bottom.add(sellButton);

        setLayout(new BorderLayout());
        add(header,BorderLayout.NORTH);
        add(info,BorderLayout.CENTER);
        add(bottom,BorderLayout.SOUTH);
    }

    // builds a simple row with a label on the left and a value on the right
    private JPanel buildRow(String label, String value) {
        JPanel row = new JPanel(new BorderLayout());
        row.setBackground(new Color(252, 245, 220));
        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 22));
        row.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel labelText = new JLabel(label);
        labelText.setFont(new Font("SansSerif", Font.PLAIN, 12));
        labelText.setForeground(new Color(120, 98, 68));

        JLabel valueText = new JLabel(value);
        valueText.setFont(new Font("SansSerif", Font.PLAIN, 12));
        valueText.setForeground(new Color(40, 30, 20));

        row.add(labelText,BorderLayout.WEST);
        row.add(valueText,BorderLayout.EAST);
        return row;
    }
}