package main.ui;

import javax.swing.*;

import main.automaton.Automaton;

import java.awt.*;
import java.awt.event.*;


public class Window3D extends Window {

    private int depth;

    private void getTheRightColor2(char state, Graphics g){
        if(state == automaton.getAlphabet()[0]){
            g.setColor(Color.WHITE);
        }else{
            g.setColor(Color.BLACK);
        }
    }

    private void getTheRightColor3(char state, Graphics g){
        if(state == automaton.getAlphabet()[0]){
            g.setColor(Color.WHITE);
        }else if(state == automaton.getAlphabet()[1]){
            g.setColor(Color.RED);
        }else{
            g.setColor(Color.BLACK);
        }
    }

    private void getTheRightColor4(char state, Graphics g){
        if(state == automaton.getAlphabet()[0]){
            g.setColor(Color.WHITE);
        }else if(state == automaton.getAlphabet()[1]){
            g.setColor(Color.GREEN);
        }else if(state == automaton.getAlphabet()[2]){
            g.setColor(Color.RED);
        }else{
            g.setColor(Color.GRAY);
        }
    }

    public Window3D(Automaton automaton){
        super(automaton);
        this.frame = new JFrame("Automate cellulaire à 3 dimensions");
        this.frame.setSize(automaton.getGrid().getSize()*cellSize + 16, (automaton.getGrid().getSize())*cellSize + 100);
        this.frame.setResizable(false);
        this.frame.setLayout(new FlowLayout());

        this.depth = 0;

        this.drawPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                for(int i = 0; i < automaton.getGrid().getSize(); i++){
                    for(int j = 0; j < automaton.getGrid().getSize(); j++){
                        char state = automaton.getGrid().getCell(i, j, depth).getState();
                        int alphabetSize = automaton.getAlphabet().length;
                        switch (alphabetSize){
                            case 2:
                                getTheRightColor2(state, g);
                                break;
                            case 3:
                                getTheRightColor3(state, g);
                                break;
                            case 4:
                                getTheRightColor4(state, g);
                                break;
                            default:
                                g.setColor(Color.BLACK);
                                break;
                        }
                        g.fillRect(j*cellSize, i*cellSize, cellSize, cellSize);
                    }
                }
            }
        };

        this.drawPanel.setPreferredSize(new Dimension(automaton.getGrid().getSize()*cellSize, automaton.getGrid().getSize()*cellSize));

        this.controlPanel = new JPanel();
        this.controlPanel.setPreferredSize(new Dimension(automaton.getGrid().getSize()*cellSize, 64));

        JButton nextButton = new JButton("Next step");
        nextButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                automaton.evaluate();
                drawPanel.repaint();
            }
        });
        this.controlPanel.add(nextButton);

        JButton upButton = new JButton("Up");
        upButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(depth > 0){
                    depth--;
                }
                drawPanel.repaint();
            }
        });
        this.controlPanel.add(upButton);

        JButton downButton = new JButton("Down");
        downButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(depth + 1 < automaton.getGrid().getSize()){
                    depth++;
                }
                drawPanel.repaint();
            }
        });
        this.controlPanel.add(downButton);

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