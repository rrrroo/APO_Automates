package ui;

import automaton.Automaton;
import automaton.grid.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Window1D extends Window {
    private ArrayList<Grid> steps = new ArrayList<Grid>();
    private int stepNb;
    
    public Window1D(Automaton automaton){
        super(automaton);
        this.stepNb = 0;

        this.frame = new JFrame("Automate cellulaire à 1 dimension");
        this.frame.setSize(automaton.getGrid().getSize()*cellSize + 16, cellSize*14);
        this.frame.setResizable(false);
        this.frame.setLayout(new FlowLayout());

        this.drawPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                steps.add(automaton.getGrid());
                for(int s = 0; s <= stepNb; s++){
                    for(int i = 0; i < steps.get(s).getSize(); i++){
                        if(steps.get(s).getCell(i).getState() == automaton.getAlphabet()[0]){
                            g.setColor(Color.WHITE);
                        }else{
                            g.setColor(Color.BLACK);
                        }
                        g.fillRect(i*cellSize, s*cellSize, cellSize, cellSize);
                    }
                }
                stepNb++;
            }
        };
        this.drawPanel.setPreferredSize(new Dimension(automaton.getGrid().getSize()*cellSize, cellSize*12));

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