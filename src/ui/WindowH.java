package ui;

import automaton.Automaton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


public class WindowH extends Window {
    public WindowH(Automaton automaton){
        super(automaton);
        this.frame = new JFrame("Automate cellulaire hexagonal");
    }
}