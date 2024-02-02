package com.ui;

import com.automaton.Automaton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window {
    protected Automaton automaton;
    protected JFrame frame;
    protected JPanel drawPanel;
    protected JPanel controlPanel;

    protected int cellSize;

    public Window(Automaton automaton){
        this.automaton = automaton;
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.cellSize = 50;
    }

    public void display(){
        this.frame.setVisible(true);
        this.drawPanel.repaint();
    }
}