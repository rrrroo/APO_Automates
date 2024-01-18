package app;
import automaton.Automaton;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Window1D extends Window{
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
                for(int i = 0; i < automaton.getGrid().getSize(); i++){
                    if(automaton.getGrid().getCell(i).getState() == automaton.getAlphabet()[0]){
                        g.setColor(Color.WHITE);
                    }else{
                        g.setColor(Color.BLACK);
                    }
                    g.fillRect(i*cellSize, stepNb*cellSize, cellSize, cellSize);
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