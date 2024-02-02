package main.ui;

import javax.swing.*;

import main.automaton.Automaton;

import java.awt.*;
import java.awt.event.*;


public class Window3D extends Window {
    public Window3D(Automaton automaton){
        super(automaton);
        this.frame = new JFrame("Automate cellulaire à 3 dimensions");
        this.frame.setSize(automaton.getGrid().getSize()*cellSize + 16, (automaton.getGrid().getSize()+2)*cellSize);
        this.frame.setResizable(false);
        this.frame.setLayout(new FlowLayout());

        this.drawPanel = new JPanel();
        this.drawPanel.setPreferredSize(new Dimension(automaton.getGrid().getSize()*cellSize, automaton.getGrid().getSize()*cellSize));

        this.controlPanel = new JPanel();
        this.controlPanel.setPreferredSize(new Dimension(automaton.getGrid().getSize()*cellSize, cellSize));

        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        this.controlPanel.add(quitButton);

        this.frame.add(this.drawPanel);
        this.frame.add(this.controlPanel);
    }
}