package com.ui;

import com.automaton.Automaton;
import com.automaton.grid.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Window1D extends Window {
    private ArrayList<Grid> steps = new ArrayList<Grid>();
    private int stepNb;
    private int shift;
    
    public Window1D(Automaton automaton){
        super(automaton);
        stepNb = 0;
        shift = 0;

        frame = new JFrame("Automate cellulaire à 1 dimension");
        frame.setSize(automaton.getGrid().getSize()*cellSize + 16, cellSize*12);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());

        drawPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                steps.add(automaton.getGrid());
                for(int s = 0; s <= stepNb; s++){
                    for(int i = 0; i < steps.get(s+shift).getSize(); i++){
                        if(steps.get(s+shift).getCell(i).getState() == automaton.getAlphabet()[0]){
                            g.setColor(Color.WHITE);
                        }else{
                            g.setColor(Color.BLACK);
                        }
                        g.fillRect(i*cellSize, (s-1)*cellSize, cellSize, cellSize);
                    }
                }
                if(stepNb < 16){
                    stepNb++;
                }
            }
        };
        drawPanel.setPreferredSize(new Dimension(automaton.getGrid().getSize()*cellSize, cellSize*10));

        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(automaton.getGrid().getSize()*cellSize, cellSize));
        
        JButton upButton = new JButton("Up");
        upButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(shift > 0){
                    shift--;
                }
                drawPanel.repaint();
            }
        });
        controlPanel.add(upButton);

        JButton downButton = new JButton("Down");
        downButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(shift < 5){
                    shift++;
                }
                drawPanel.repaint();
            }
        });
        controlPanel.add(downButton);

        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        controlPanel.add(quitButton);

        frame.add(drawPanel);
        frame.add(controlPanel);
    }
}