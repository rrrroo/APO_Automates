package main.ui;

import javax.swing.*;

import main.automaton.Automaton;
import main.automaton.grid.Grid;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Window1D extends Window {
    private ArrayList<Grid> steps = new ArrayList<Grid>();
    private int stepNb;
    private int shift;
    
    public Window1D(Automaton automaton, int cellSize){
        super(automaton, cellSize);
        steps.add(automaton.getGrid());
        stepNb = 0;
        shift = 0;

        frame = new JFrame("Automate cellulaire à 1 dimension");
        frame.setSize(automaton.getGrid().getSize()*cellSize + 16, cellSize*11 + 64);
        frame.setResizable(false);
        frame.setLayout(new FlowLayout());

        drawPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                boolean addStep = false;
                if(automaton.getGrid() != steps.get(stepNb)){
                    steps.add(automaton.getGrid());
                    addStep = true;
                }
                for(int s = 0; s <= stepNb; s++){
                    for(int i = 0; i < steps.get(s).getSize(); i++){
                        if(steps.get(s).getCell(i, 0, 0).getState() == automaton.getAlphabet()[0]){
                            g.setColor(Color.WHITE);
                        }else{
                            g.setColor(Color.BLACK);
                        }
                        g.fillRect(i*cellSize, (s-shift)*cellSize, cellSize, cellSize);
                    }
                }
                if(addStep){
                    stepNb++;
                }
            }
        };
        drawPanel.setPreferredSize(new Dimension(automaton.getGrid().getSize()*cellSize, cellSize*10));

        controlPanel = new JPanel();
        controlPanel.setPreferredSize(new Dimension(automaton.getGrid().getSize()*cellSize, 64));
        
        JButton nextButtons = new JButton("Next step");
        nextButtons.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                automaton.evaluate();
                while(shift + 9 <= stepNb){
                    shift++;
                }
                drawPanel.repaint();
            }
        });
        controlPanel.add(nextButtons);

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
                if(shift + 10 <= stepNb){
                    shift++;
                }
                drawPanel.repaint();
            }
        });
        controlPanel.add(downButton);

        Timer timer = new Timer(100, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                automaton.evaluate();
                drawPanel.repaint();
            }
        });

        JButton playButton = new JButton("Play");
        JButton pauseButton = new JButton("Pause");

        Dimension buttonSize = new Dimension(70, 26);
        playButton.setPreferredSize(buttonSize);
        pauseButton.setPreferredSize(buttonSize);

        playButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                playButton.setVisible(false);
                pauseButton.setVisible(true);
                timer.start();
            }
        });
        this.controlPanel.add(playButton);

        
        pauseButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                playButton.setVisible(true);
                pauseButton.setVisible(false);
                timer.stop();
            }
        });
        pauseButton.setVisible(false);
        this.controlPanel.add(pauseButton);

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