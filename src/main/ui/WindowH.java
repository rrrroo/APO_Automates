package main.ui;

import javax.swing.*;

import main.automaton.Automaton;

import java.awt.*;
import java.awt.event.*;


public class WindowH extends Window {
    public WindowH(Automaton automaton, int cellSize){
        super(automaton, cellSize);
        this.frame = new JFrame("Automate cellulaire hexagonal");
        this.frame.setSize((int) ((automaton.getGrid().getSize() + 0.5) * cellSize * Math.sqrt(3)),
                (int) ((automaton.getGrid().getSize()-1) * 1.5 * cellSize + 2 * cellSize + 100));

        this.frame.setResizable(false);
        this.frame.setLayout(new FlowLayout());

        this.drawPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                int height = cellSize * 2;
                int width = (int) (cellSize * Math.sqrt(3));
                for(int i = 0; i < automaton.getGrid().getSize(); i++){
                    for(int j = 0; j < automaton.getGrid().getSize(); j++){
                        int centerX = (int) ((j + 1) * width + (i%2) * width/2);
                        int centerY = (int) ((i * 0.75 + 0.5) * height);
                        int[] pointsX = {centerX, centerX - width/2, centerX - width/2, centerX, centerX + width/2, centerX + width/2};
                        int[] pointsY = {centerY - height/2, centerY - height/4, centerY + height/4, centerY + height/2, centerY + height/4, centerY - height/4};
                        Polygon hexagon = new Polygon(pointsX, pointsY, 6);
                        if(i%2 == 0){
                            if(j%2 == 0){
                                g.setColor(Color.WHITE);
                            }else{
                                g.setColor(Color.GREEN);
                            }
                        }else{
                            if(j%2 == 0){
                                g.setColor(Color.RED);
                            }else{
                                g.setColor(Color.BLACK);
                            }
                        }
                        g.fillPolygon(hexagon);
                    }
                }
            }
        };
        drawPanel.setPreferredSize(new Dimension((int) ((automaton.getGrid().getSize() + 0.5) * cellSize * Math.sqrt(3)),
                (int) ((automaton.getGrid().getSize() - 1) * 1.5 * cellSize + 2 * cellSize)));
        this.frame.add(drawPanel);

        this.controlPanel = new JPanel();
        this.controlPanel.setPreferredSize(new Dimension((int) ((automaton.getGrid().getSize() + 0.5) * cellSize * Math.sqrt(3)), 100));

        JButton drawButton = new JButton("Dessiner");
        drawButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                drawPanel.repaint();
            }
        });
        controlPanel.add(drawButton);

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try{
                    automaton.save();
                }catch(Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        this.controlPanel.add(saveButton);

        JButton quitButton = new JButton("Quitter");
        quitButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                frame.dispose();
            }
        });
        controlPanel.add(quitButton);

        this.frame.add(controlPanel);
    }
}