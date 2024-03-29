package main.ui;
import main.automaton.Automaton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * This class represents a 2D cellular automaton window.
 * It extends the Window class and provides functionality for displaying and interacting with the automaton.
 */
public class Window2D extends Window {

    /**
     * The current x position of the mouse
     */
    private int currentX;

    /**
     * The current y position of the mouse
     */
    private int currentY;

    /**
     * Constructor for the Window2D class.
     * @param automaton The automaton to be displayed in the window.
     * @param cellSize The size of the cells in the automaton.
     */
    public Window2D(Automaton automaton, int cellSize){
        super(automaton, cellSize);
        this.frame = new JFrame("Automate cellulaire à 2 dimensions");
        this.frame.setSize(automaton.getGrid().getSize()*cellSize + 16, (automaton.getGrid().getSize()+1)*cellSize + 90);
        this.frame.setResizable(false);
        this.frame.setLayout(new FlowLayout());

        this.currentX = 0;
        this.currentY = 0;

        this.frame.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                int x = e.getX() - 8;
                int y = e.getY() - 35;
                currentX = x/cellSize;
                currentY = y/cellSize;
                drawPanel.repaint();
                frame.setTitle("Current cell : (" + currentX + ", " + currentY + ") " + x + ", " + y);
            }
        });

        this.frame.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getButton() == MouseEvent.BUTTON1){
                    automaton.getGrid().getCell(currentY, currentX, 0).setState(getNextState(automaton.getGrid().getCell(currentY, currentX, 0).getState()));
                    drawPanel.repaint();
                }
            }
        });

        this.drawPanel = new JPanel(){
            @Override
            public void paintComponent(Graphics g){
                for(int i = 0; i < automaton.getGrid().getSize(); i++){
                    for(int j = 0; j < automaton.getGrid().getSize(); j++){
                        char state = automaton.getGrid().getCell(i, j, 0).getState();
                        g.setColor(new Color(colorsMap.get(state)[0], colorsMap.get(state)[1], colorsMap.get(state)[2]));
                        if(currentX == j && currentY == i)
                            if(g.getColor().equals(Color.BLACK))
                                g.setColor(Color.GRAY);
                            else
                                g.setColor(g.getColor().darker());
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

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                frame.dispose();
            }
        });
        this.controlPanel.add(quitButton);

        this.frame.add(this.drawPanel);
        this.frame.add(this.controlPanel);
    }
}