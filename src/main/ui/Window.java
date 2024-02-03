package main.ui;

import javax.swing.*;

import main.automaton.Automaton;

public class Window {
    protected Automaton automaton;
    protected JFrame frame;
    protected JPanel drawPanel;
    protected JPanel controlPanel;

    protected int cellSize;

    public Window(Automaton automaton, int cellSize){
        this.automaton = automaton;
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setAlwaysOnTop(true);
        this.cellSize = cellSize;
    }

    public void display(){
        this.drawPanel.repaint();
        this.frame.setVisible(true);
    }
}