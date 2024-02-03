package main.ui;

import javax.swing.*;

import main.automaton.Automaton;

/**
 * This class is a superclass for the different windows of the application.
 * It contains the common attributes and methods of the windows.
 */
public class Window {
    /**
     * The automaton to be displayed in the window.
     */
    protected Automaton automaton;

    /**
     * The frame of the window.
     */
    protected JFrame frame;

    /**
     * The panel where the automaton is drawn.
     */
    protected JPanel drawPanel;

    /**
     * The panel where the controls of the automaton are displayed.
     */
    protected JPanel controlPanel;

    /**
     * The draw size of the cells in pixels.
     */
    protected int cellSize;

    /**
     * Constructor for the Window class.
     * @param automaton The automaton to be displayed in the window.
     * @param cellSize The size of the cells in the automaton.
     */
    public Window(Automaton automaton, int cellSize){
        this.automaton = automaton;
        this.frame = new JFrame();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setAlwaysOnTop(true);
        this.cellSize = cellSize;
    }

    /**
     * Method to display the window.
     */
    public void display(){
        this.drawPanel.repaint();
        this.frame.setVisible(true);
    }

    /**
     * Function to get the next state in the alphabet of the automaton.
     * @param state The current state.
     * @return The next state in the alphabet.
     */
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