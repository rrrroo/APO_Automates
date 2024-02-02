package ui;

import automaton.Automaton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class Window2D extends Window {
    public Window2D(Automaton automaton){
        super(automaton);
        this.frame = new JFrame("Automate cellulaire à 2 dimensions");
        this.frame.setSize(automaton.getGrid().getSize()*cellSize + 16, (automaton.getGrid().getSize()+2)*cellSize);
        this.frame.setResizable(false);
        this.frame.setLayout(new FlowLayout());

        this.drawPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                super.paintComponent(g);
                for(int i = 0; i < automaton.getGrid().getSize(); i++){
                    for(int j = 0; j < automaton.getGrid().getSize(); j++){
                        if(automaton.getGrid().getCell(j,i,0).getState() == automaton.getAlphabet()[0]){
                            g.setColor(Color.WHITE);
                        }else{
                            g.setColor(Color.BLACK);
                        }
                        g.fillRect(i*cellSize, j*cellSize, cellSize, cellSize);
                    }
                }
            }
        };
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