package ui;

import game.Game;
import plant.PlantData;

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
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

public class ShopWindow extends JDialog {

    private Game game;
    private int bedIndex;
    private ArrayList<JButton> buyButtons;
    private ArrayList<PlantData> listedPlants;
    private JLabel statusLabel;

    public ShopWindow(JFrame parent, Game game, int bedIndex) {
        super(parent, "Seed Shop", true);
        this.game = game;
        this.bedIndex = bedIndex;
        this.buyButtons = new ArrayList<>();
        this.listedPlants = new ArrayList<>();

        setSize(800, 720);
        setResizable(false);
        setLocationRelativeTo(parent);

        // dark green header with shop title and current balance
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(45, 90, 40));
        header.setBorder(BorderFactory.createEmptyBorder(12, 18, 12, 18));

        JLabel titleLabel = new JLabel("Seed Shop");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        titleLabel.setForeground(new Color(250, 245, 230));

        JLabel balanceLabel = new JLabel("Balance: $" + game.getPlayer().getMoney());
        balanceLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        balanceLabel.setForeground(new Color(218, 165, 32)); // gold for the money amount

        header.add(titleLabel,BorderLayout.WEST);
        header.add(balanceLabel,BorderLayout.EAST);

        // split plants into normal and special lists
        ArrayList<PlantData> normal = new ArrayList<>();
        ArrayList<PlantData> special = new ArrayList<>();

        for (int i = 0; i < game.getGameData().plants.size(); i++) {
            PlantData plant = game.getGameData().plants.get(i);
            if (plant.special) {
                special.add(plant);
            } else {
                normal.add(plant);
            }
        }

        // figure out which two specials to show this hour
        long currentHour = System.currentTimeMillis() / 3600000L; //L make java read as long and not int idk why its like this :(
        int pair = (int) ((currentHour % 2)); // gives 0 or 1
        int startIndex = pair * 2; // pair 0 = index 0+1, pair 1 = index 2+3

        // outer panel stacks both sections top to bottom
        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBackground(new Color(252, 245, 220)); // cream background
        content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // standard seeds section
        JLabel normalHeader = new JLabel("Standard Seeds");
        normalHeader.setFont(new Font("SansSerif", Font.BOLD, 12));
        normalHeader.setForeground(new Color(45, 90, 40));
        normalHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(normalHeader);
        content.add(Box.createVerticalStrut(6));

        // GridLayout puts all normal plants in a 3-column grid
        int normalRows = (int) Math.ceil(normal.size() / 3.0); // enough rows to hold all plants
        JPanel normalGrid = new JPanel(new GridLayout(normalRows, 3, 8, 8)); // 8px gaps between cards
        normalGrid.setBackground(new Color(252, 245, 220));
        normalGrid.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (int i = 0; i < normal.size(); i++) {
            normalGrid.add(buildPlantCard(normal.get(i)));
        }

        // GridLayout requires every cell to be filled so add blank panels if the last row is incomplete (it will be)
        int remainder = normal.size() % 3;
        if (remainder != 0) {
            for (int i = 0; i < 3 - remainder; i++) {
                JPanel blank = new JPanel();
                blank.setBackground(new Color(252, 245, 220)); // invisible filler cell
                normalGrid.add(blank);
            }
        }

        content.add(normalGrid);

        // special offers section
        content.add(Box.createVerticalStrut(12));

        JLabel specialHeader = new JLabel("Special Offers");
        specialHeader.setFont(new Font("SansSerif", Font.BOLD, 12));
        specialHeader.setForeground(new Color(120, 80, 20));
        specialHeader.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(specialHeader);

        JLabel rotateNote = new JLabel("Refreshes every hour - check back later for more.");
        rotateNote.setFont(new Font("SansSerif", Font.PLAIN, 11));
        rotateNote.setForeground(new Color(140, 100, 40));
        rotateNote.setAlignmentX(Component.LEFT_ALIGNMENT);
        content.add(rotateNote);
        content.add(Box.createVerticalStrut(6));

        // specials use a 2-column grid since there are only 2 shown at a time
        JPanel specialGrid = new JPanel(new GridLayout(1, 2, 8, 8));
        specialGrid.setBackground(new Color(252, 245, 220));
        specialGrid.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (int i = startIndex; i < startIndex + 2 && i < special.size(); i++) {
            specialGrid.add(buildPlantCard(special.get(i)));
        }

        content.add(specialGrid);

        // bottom bar with status message and cancel button
        statusLabel = new JLabel("Choose a seed to plant.");
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        statusLabel.setForeground(new Color(40, 30, 20));

        JButton cancelBtn = new JButton("Cancel");
        cancelBtn.setFont(new Font("SansSerif", Font.BOLD, 12));
        cancelBtn.setBackground(new Color(130, 45, 35));
        cancelBtn.setForeground(new Color(250, 245, 230));
        cancelBtn.setFocusPainted(false);
        cancelBtn.setBorderPainted(false);
        cancelBtn.setOpaque(true);
        cancelBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelBtn.addActionListener(e -> dispose()); // closes without buying

        JPanel bottom = new JPanel(new BorderLayout(8, 0));
        bottom.setBackground(new Color(236, 228, 204));
        bottom.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        bottom.add(statusLabel, BorderLayout.CENTER);
        bottom.add(cancelBtn,BorderLayout.EAST);

        setLayout(new BorderLayout());
        add(header,BorderLayout.NORTH);
        add(content,BorderLayout.CENTER);
        add(bottom,BorderLayout.SOUTH);
    }

    // builds one plant card with name, description, price range, grow time, and buy button
    private JPanel buildPlantCard(PlantData plant) {
        listedPlants.add(plant);

        double jitter = game.getGameData().economy.priceJitterPercent;
        int minSell = roundToNearest5((int) (plant.basePrice * (1.0 - jitter))); // lowest sell price
        int maxSell = roundToNearest5((int) (plant.basePrice * (1.0 + jitter))); // highest sell price
        long minutes = plant.growthDurationSeconds / 60;

        Color cardBackground;
        Color borderColor;
        Color nameColor;

        if (plant.special) {
            cardBackground = new Color(255, 248, 232); // warm gold tint for specials
            borderColor = new Color(200, 160, 48); // gold border
            nameColor = new Color(122, 74, 0); // dark gold name
        } else {
            cardBackground = new Color(245, 240, 225); // plain cream for normal plants
            borderColor = new Color(139, 197, 110); // green border
            nameColor = new Color(42, 90, 24); // dark green name
        }

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS)); // stacks top to bottom
        card.setBackground(cardBackground);
        card.setBorder(BorderFactory.createLineBorder(borderColor, 1));

        JLabel nameLabel = new JLabel(plant.name);
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 12));
        nameLabel.setForeground(nameColor);
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel descLabel = new JLabel(plant.description);
        descLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        descLabel.setForeground(new Color(85, 65, 40));
        descLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel sellPriceLabel = new JLabel("Sells $" + minSell + " - $" + maxSell);
        sellPriceLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        sellPriceLabel.setForeground(new Color(120, 95, 55));
        sellPriceLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel timeLabel = new JLabel("Grow time ~" + minutes + " min");
        timeLabel.setFont(new Font("SansSerif", Font.PLAIN, 10));
        timeLabel.setForeground(new Color(120, 95, 55));
        timeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton buyBtn = new JButton("Buy  $" + plant.seedCost);
        buyBtn.setFont(new Font("SansSerif", Font.BOLD, 11));
        if (plant.special) {
            buyBtn.setBackground(new Color(160, 120, 20)); // gold for specials
        } else {
            buyBtn.setBackground(new Color(45, 90, 40)); // green for normal plants
        }
        buyBtn.setForeground(new Color(250, 245, 230));
        buyBtn.setFocusPainted(false);
        buyBtn.setBorderPainted(false);
        buyBtn.setOpaque(true);
        buyBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        buyBtn.setMaximumSize(new Dimension(150, 26));

        buyBtn.addActionListener(e -> {
            boolean bought = game.buyAndPlant(bedIndex, plant.id);
            if (bought) {
                dispose(); // close on successful purchase
            } else {
                statusLabel.setForeground(new Color(180, 40, 40)); // turns red when not enough money
                statusLabel.setText("Not enough money! You have $" + game.getPlayer().getMoney() + ".");
            }
        });

        buyButtons.add(buyBtn);

        card.add(nameLabel);
        card.add(Box.createVerticalStrut(3));
        card.add(descLabel);
        card.add(Box.createVerticalStrut(3));
        card.add(sellPriceLabel);
        card.add(Box.createVerticalStrut(3));
        card.add(timeLabel);
        card.add(buyBtn);

        return card;
    }

    // rounds to nearest 5 so prices look cleaner e.g. 32 becomes 30, 38 becomes 40
    private int roundToNearest5(int value) {
        int remainder = value % 5;
        if (remainder < 3) {
            return value - remainder;
        }
        return value + (5 - remainder);
    }
}