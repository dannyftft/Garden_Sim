package ui;

import game.Game;

import javax.swing.JDialog;
import javax.swing.JFrame;

public class DebugWindow extends JDialog{

    private Game game;
    private RefreshListener listener;

    public DebugWindow(JFrame parent, Game game, RefreshListener listener) {
        super(parent,"Debug Console", false);
    }
}
