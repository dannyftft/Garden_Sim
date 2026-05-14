package ui;

import game.Game;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JSpinner;
import javax.swing.JPanel;
import javax.swing.SpinnerNumberModel;
import java.awt.FlowLayout;

public class DebugWindow extends JDialog {

    private Game game;
    private MainWindow mainWindow;
    private JSpinner bedSpinner;

    public DebugWindow(JFrame parent, Game game, MainWindow mainWindow) {
        super(parent, "Debug", false);
        this.game = game;
        this.mainWindow = mainWindow;

        setSize(300, 100);
        setResizable(false);
        setLocationRelativeTo(parent);

        bedSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 7, 1)); // picks which bed to target

        JButton addMoneyBtn = new JButton("+$1000");
        addMoneyBtn.addActionListener(e -> {
            game.addDebugMoney(1000);
            mainWindow.refreshUI();
            addMoneyBtn.setBorderPainted(false);
            addMoneyBtn.setFocusPainted(false);
        });

        JButton advanceBtn = new JButton("Advance Stage");
        advanceBtn.addActionListener(e -> {
            game.debugAdvanceStage((int) bedSpinner.getValue());
            mainWindow.refreshUI();
            mainWindow.refreshUI();
            advanceBtn.setBorderPainted(false);
            advanceBtn.setFocusPainted(false);
        });

        JButton witherBtn = new JButton("Force Wither");
        witherBtn.addActionListener(e -> {
            game.debugForceWither((int) bedSpinner.getValue());
            mainWindow.refreshUI();
            mainWindow.refreshUI();
            witherBtn.setBorderPainted(false);
            witherBtn.setFocusPainted(false);
        });

        JPanel panel = new JPanel(new FlowLayout());
        panel.add(bedSpinner);
        panel.add(addMoneyBtn);
        panel.add(advanceBtn);
        panel.add(witherBtn);

        add(panel);
    }
}