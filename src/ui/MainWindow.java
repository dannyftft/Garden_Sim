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

public class MainWindow implements RefreshListener {

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
        this.moneyLabel = new JLabel(formatMoney(game.getPlayer().getMoney()));
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

        // left side of the top bar: secret debug button + coin icon + money
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

        // small gold coin
        JPanel coinIcon = new JPanel() {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // smooth edges
                int cx = getWidth() / 2;
                int cy = getHeight() / 2;
                int r  = 10;
                g2.setColor(new Color(200, 148, 20)); // darker gold for the outer ring of the coin
                g2.fillOval(cx - r, cy - r, r * 2, r * 2);
                g2.setColor(new Color(240, 195, 60)); // brighter gold for the inner face of the coin
                g2.fillOval(cx - r + 3, cy - r + 3, r * 2 - 6, r * 2 - 6);
                g2.setColor(new Color(160, 110, 10)); // dark brown for the dollar sign on the coin
                g2.setFont(new Font("SansSerif", Font.BOLD, 10));
                g2.drawString("$", cx - 2, cy + 4);
            }
        };
        coinIcon.setPreferredSize(new Dimension(28, 48));
        coinIcon.setBackground(new Color(45, 90, 40));

        left.add(coinIcon);

        // two-line money display: small "COINS" label above, large gold money number below
        JPanel moneyStack = new JPanel();
        moneyStack.setLayout(new BoxLayout(moneyStack, BoxLayout.Y_AXIS)); // stacks the two labels vertically
        moneyStack.setBackground(new Color(45, 90, 40));
        moneyStack.setBorder(BorderFactory.createEmptyBorder(8, 6, 8, 0)); // padding above and below

        JLabel coinsLabel = new JLabel("COINS");
        coinsLabel.setFont(new Font("SansSerif", Font.BOLD, 9));
        coinsLabel.setForeground(new Color(140, 170, 110));
        coinsLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        moneyLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        moneyLabel.setForeground(new Color(218, 165, 32));
        moneyLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        moneyStack.add(coinsLabel);
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
        saveButton.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(80, 130, 65), 1), // thin green outline
                BorderFactory.createEmptyBorder(6, 16, 6, 16) // inner padding so the text isn't cramped
        ));
        saveButton.setCursor(new Cursor(Cursor.HAND_CURSOR)); // hand cursor on hover

        //TODO make saving actually save
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

        bar.add(left,  BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);

        JPanel grid = new JPanel(new GridLayout(2, 4, 10, 10)); // 2 rows, 4 columns, 10px gaps
        grid.setBackground(new Color(72, 130, 62)); // green between the beds
        grid.setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16)); // padding around the edge

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
        frame.add(bar,  BorderLayout.NORTH); // top bar always sits at the top
        frame.add(grid, BorderLayout.CENTER);

        frame.setVisible(true); // show the window
    }

    public void onRefreshNeeded() {
        refreshUI(); // called by DebugWindow after commands that change game state
    }

    // updates the money label and repaints every bed button
    private void refreshUI() {
        moneyLabel.setText(formatMoney(game.getPlayer().getMoney())); // show the latest balance
        for (int i = 0; i < bedButtons.size(); i++) {
            bedButtons.get(i).refresh(); // tell each bed to redraw itself based on current plant state
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