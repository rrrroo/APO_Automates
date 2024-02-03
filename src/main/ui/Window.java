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

    public char getNextState(char state){
        if(state == automaton.getAlphabet()[automaton.getAlphabet().length-1]){
            return automaton.getAlphabet()[0];
        }else{
            int actualIndex = 0;
            for(int i = 0; i < automaton.getAlphabet().length; i++){
                if(automaton.getAlphabet()[i] == state){
                    actualIndex = i;
                    break;
                }
            }
            return automaton.getAlphabet()[actualIndex + 1];
        }
    }
}