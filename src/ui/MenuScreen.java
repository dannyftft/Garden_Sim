package ui;

import data.SaveData;
import data.SaveManager;
import game.Game;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.BoxLayout;
import javax.swing.Box;
import javax.swing.BorderFactory;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

public class MenuScreen {

    private JFrame frame;
    private JButton newGameBtn;
    private JButton loadGameBtn;
    private JButton quitBtn;

    public MenuScreen() {
        this.frame = new JFrame("Garden Sim");
        this.newGameBtn = new JButton("New Game");
        this.loadGameBtn = new JButton("Load Game");
        this.quitBtn = new JButton("Quit");
    }

    public void showMenuScreen() {
        frame.setSize(480, 500);
        frame.setResizable(false); // stops the player from resizing the window
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // closes the app when X is clicked
        frame.setLocationRelativeTo(null); // centres the window on screen
        frame.setLayout(new BorderLayout());

        // header panel (dark green top section with title and tagline)
        JLabel title = new JLabel("Garden Sim"); //
        title.setFont(new Font("SansSerif", Font.BOLD, 36));
        title.setForeground(new Color(250, 245, 230));
        title.setAlignmentX(Component.CENTER_ALIGNMENT); // centres the label horizontally

        JLabel tagline = new JLabel("Plant. Grow. Sell. Repeat."); // small subtitle under the title
        tagline.setFont(new Font("SansSerif", Font.PLAIN, 14)); // smaller and not bold
        tagline.setForeground(new Color(140, 195, 110));
        tagline.setAlignmentX(Component.CENTER_ALIGNMENT); // centres it horizontally

        javax.swing.JPanel header = new javax.swing.JPanel(); // panel that holds the title area
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS)); // stacks title and tagline vertically
        header.setBackground(new Color(45, 90, 40));
        header.setBorder(BorderFactory.createEmptyBorder(36, 0, 28, 0)); // adds breathing room above and below the text
        header.add(title); // puts the title into the header
        header.add(Box.createVerticalStrut(8)); // adds a small gap between title and tagline
        header.add(tagline); // puts the tagline into the header

        // description text area
        JTextArea description = new JTextArea(
                "Buy seeds from the shop by clicking on a plot\n" +
                        "and sell your harvest for a profit. Every plant has its\n" +
                        "own growth time and value. Don't wait too long or\n" +
                        "your crops may wither!"
        );
        description.setEditable(false); // player cannot type in this box
        description.setFocusable(false); // clicking it won't move keyboard focus here
        description.setBackground(new Color(252, 245, 220));
        description.setForeground(new Color(80, 60, 38));
        description.setFont(new Font("SansSerif", Font.PLAIN, 12)); // normal readable size
        description.setAlignmentX(Component.CENTER_ALIGNMENT);// centres it in the panel
        description.setAlignmentY(Component.CENTER_ALIGNMENT);// centres it in the panel

        //New Game button style
        newGameBtn.setBackground(new Color(45, 90, 40));
        newGameBtn.setForeground(new Color(250, 245, 230));
        newGameBtn.setFont(new Font("SansSerif", Font.BOLD, 15)); // bold text
        newGameBtn.setFocusPainted(false); // removes the text border
        newGameBtn.setBorderPainted(false); // removes the button border while hovering
        newGameBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // changes cursor to a hand on hover
        newGameBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        newGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Load Game button style
        loadGameBtn.setBackground(new Color(90, 70, 40));
        loadGameBtn.setForeground(new Color(250, 245, 230));
        loadGameBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        loadGameBtn.setFocusPainted(false); // removes the text border
        loadGameBtn.setBorderPainted(false); // removes the button border while hovering
        loadGameBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // changes cursor to a hand on hover
        loadGameBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        loadGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // grey out the load button if no save file exists so the player knows nothing is saved
        if (!SaveManager.hasSave()) {
            loadGameBtn.setText("Load Game  (no save found)"); // tells the player why it's disabled
            loadGameBtn.setBackground(new Color(70, 60, 50));
            loadGameBtn.setEnabled(false); // prevents clicking it
        }

        // Quit button style
        quitBtn.setBackground(new Color(130, 45, 35));
        quitBtn.setForeground(new Color(250, 245, 230));
        quitBtn.setFont(new Font("SansSerif", Font.BOLD, 15));
        quitBtn.setFocusPainted(false); // removes the text border
        quitBtn.setBorderPainted(false); // removes the button border while hovering
        quitBtn.setCursor(new Cursor(Cursor.HAND_CURSOR)); // changes cursor to a hand on hover
        quitBtn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 42));
        quitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        // center panel that holds the description and buttons
        javax.swing.JPanel centre = new javax.swing.JPanel();
        centre.setLayout(new BoxLayout(centre, BoxLayout.Y_AXIS)); // stacks everything top to bottom
        centre.setBackground(new Color(252, 245, 220));
        centre.setBorder(BorderFactory.createEmptyBorder(30, 50, 20, 50)); // padding so things don't touch the edges
        centre.add(description); // adds the description paragraph
        centre.add(Box.createVerticalStrut(28)); // gap between description and buttons
        centre.add(newGameBtn); // first button
        centre.add(Box.createVerticalStrut(10)); // small gap between buttons
        centre.add(loadGameBtn); // second button
        centre.add(Box.createVerticalStrut(10)); // small gap between buttons
        centre.add(quitBtn); // third button

        // New Game button Listener
        newGameBtn.addActionListener(e -> {
            // warn before wiping an existing save so the player can't lose progress by accident
            if (SaveManager.hasSave()) {
                int choice = JOptionPane.showConfirmDialog(
                        frame,
                        "A saved game already exists.\nStarting a new game will erase it. Continue?",
                        "Overwrite Save?",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (choice != JOptionPane.YES_OPTION) {
                    return; // player changed their mind, do nothing
                }
                SaveManager.deleteSave(); // remove the old save before starting fresh
            }
            frame.dispose(); // close the menu window
            Game game = new Game(); // create a brand new game with default settings
            new MainWindow(game); // open the main garden window
        });

        // Load Game button Listener
        loadGameBtn.addActionListener(e -> {
            SaveData saveData = SaveManager.load(); // try to read the save file from disk
            if (saveData == null) {
                // something went wrong reading the file
                JOptionPane.showMessageDialog(
                        frame,
                        "Could not read the save file.",
                        "Load Failed",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            frame.dispose(); // close the menu window
            Game game = new Game(saveData); // build the game from the saved data
            new MainWindow(game); // open the main garden window with restored state
        });

        // Quit button listener
        quitBtn.addActionListener(e -> {
            System.exit(0); // close the entire application immediately
        });

        frame.add(header, BorderLayout.NORTH); // puts the green title area at the top
        frame.add(centre, BorderLayout.CENTER); // puts the buttons in the middle

        frame.setVisible(true); // makes the window appear on screen
    }
}