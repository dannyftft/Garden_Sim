package ui;

import bed.GardenBed;
import data.SaveManager;
import game.Game;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.*;
import java.util.ArrayList;

public class MainWindow {

    private JFrame frame;
    private Game game;
    private ArrayList<BedButton> bedButtons;
    private JLabel moneyLabel;
    private JButton secretDebugBtn;
    private JButton saveButton;
    private DebugWindow debugWindow;

    public MainWindow(Game game) {
        this.game = game;
        this.bedButtons = new ArrayList<>();
        this.frame = new JFrame("Garden Sim");
        this.moneyLabel = new JLabel("Balance: $" + formatMoney(game.getPlayer().getMoney()));
        this.secretDebugBtn = new JButton();
        this.saveButton = new JButton("Save");
        this.debugWindow = null;
    }

    public void showMainWindow() {
        frame.setSize(700, 490);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // top bar
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(new Color(45, 90, 40));

        JPanel left = new JPanel(new FlowLayout(FlowLayout.LEFT));
        left.setBackground(new Color(45, 90, 40));

        // invisible button in the very top-left corner that opens the debug window
        secretDebugBtn.setPreferredSize(new Dimension(28, 48));
        secretDebugBtn.setContentAreaFilled(false); // removes the default gray fill
        secretDebugBtn.setBorderPainted(false); // no border
        secretDebugBtn.setFocusPainted(false); // no border while hover

        secretDebugBtn.addActionListener(e -> {
            if (debugWindow == null) {
                debugWindow = new DebugWindow(frame, game, this); // create it the first time
            }
            debugWindow.setVisible(true);
            debugWindow.toFront();
        });

        left.add(secretDebugBtn);

        JPanel moneyStack = new JPanel();
        moneyStack.setLayout(new BoxLayout(moneyStack, BoxLayout.Y_AXIS)); // stacks the two labels vertically
        moneyStack.setBackground(new Color(45, 90, 40));
        moneyStack.setBorder(BorderFactory.createEmptyBorder(8, 6, 8, 0)); // padding above and below

        moneyLabel = new JLabel("Balance: $" + formatMoney(game.getPlayer().getMoney()));
        moneyLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        moneyLabel.setForeground(new Color(218, 165, 32));
        moneyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        moneyStack.add(moneyLabel);
        left.add(moneyStack);

        // right side of the top bar: save button
        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 14, 12));
        right.setBackground(new Color(45, 90, 40));

        saveButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        saveButton.setBackground(new Color(50, 95, 40));
        saveButton.setForeground(new Color(210, 235, 190));
        saveButton.setFocusPainted(false); // removes box around text
        saveButton.setBorderPainted(true);
        saveButton.setBorder(BorderFactory.createLineBorder(new Color(80, 130, 65), 1)); // thin green outline
        saveButton.setBorder(BorderFactory.createEmptyBorder(6,16,6,16)); // inner padding so the text isn't cramped

        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // hand cursor on hover

        saveButton.addActionListener(e -> {
            boolean ok = SaveManager.save(game); // write the game state to disk
            if (ok) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Your garden has been saved.",
                        "Game Saved",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "Could not write the save file",
                        "Save Failed",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        right.add(saveButton);

        bar.add(left,BorderLayout.WEST);
        bar.add(right,BorderLayout.EAST);

        JPanel grid = new JPanel(new GridLayout(2, 4, 25, 30)); // 2 rows, 4 columns, 10px gaps
        grid.setBackground(new Color(72, 130, 62)); // green between the beds
        grid.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // padding around the edge

        ArrayList<GardenBed> beds = game.getBeds();
        for (int i = 0; i < beds.size(); i++) {
            GardenBed bed = beds.get(i);
            BedButton button = new BedButton(bed);
            int index = i;

            button.addActionListener(e -> {
                if (bed.isEmpty()) {
                    ShopWindow shop = new ShopWindow(frame, game, index);
                    shop.setVisible(true);
                } else {
                    InfoWindow info = new InfoWindow(frame, game, index);
                    info.setVisible(true);
                }
                refreshUI();
            });

            bedButtons.add(button);
            grid.add(button);
        }

        // refresh timer: refresh every bed every 5 seconds so growth stages update without clicking
        Timer timer = new Timer(5000, e -> refreshUI());
        timer.start();

        frame.setLayout(new BorderLayout());
        frame.add(bar,BorderLayout.NORTH); // top bar always sits at the top
        frame.add(grid,BorderLayout.CENTER);

        frame.setVisible(true); // show the window
    }

    // updates the money label and repaints every bed button
    public void refreshUI() {
        moneyLabel.setText("Balance: $" + formatMoney(game.getPlayer().getMoney()));
        for (int i = 0; i < bedButtons.size(); i++) {
            bedButtons.get(i).refresh();
        }
    }

    // makes money look nice
    private String formatMoney(int amount) {
        if (amount < 1000) {
            return "" + amount;
        }
        int thousands = amount / 1000;
        int remainder = amount % 1000;
        if (remainder < 10) {
            return thousands + ",00" + remainder;
        }
        if (remainder < 100) {
            return thousands + ",0" + remainder;
        }
        return thousands + "," + remainder;
    }
}