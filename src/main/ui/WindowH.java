package main.ui;

import javax.swing.*;

import main.automaton.Automaton;

import java.awt.*;
import java.awt.event.*;


public class WindowH extends Window {

    private int currentX;
    private int currentY;

    public WindowH(Automaton automaton, int cellSize){
        super(automaton, cellSize);
        this.frame = new JFrame("Automate cellulaire hexagonal");
        this.frame.setSize((int) ((automaton.getGrid().getSize() + 0.5) * cellSize * Math.sqrt(3)),
                (int) ((automaton.getGrid().getSize()-1) * 1.5 * cellSize + 2 * cellSize + 100));

        this.frame.setResizable(false);
        this.frame.setLayout(new FlowLayout());

        this.frame.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                int x = e.getX() - 8;
                int y = e.getY() - 35;
                currentX = (int) (x / (cellSize * Math.sqrt(3)));
                currentY = (int) (y / (cellSize * 1.5));
                drawPanel.repaint();
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
                int height = cellSize * 2;
                int width = (int) (cellSize * Math.sqrt(3));
                for(int i = 0; i < automaton.getGrid().getSize(); i++){
                    for(int j = 0; j < automaton.getGrid().getSize(); j++){
                        int centerX = (int) ((j + 1) * width + (i%2) * width/2);
                        int centerY = (int) ((i * 0.75 + 0.5) * height);
                        int[] pointsX = {centerX, centerX - width/2, centerX - width/2, centerX, centerX + width/2, centerX + width/2};
                        int[] pointsY = {centerY - height/2, centerY - height/4, centerY + height/4, centerY + height/2, centerY + height/4, centerY - height/4};
                        Polygon hexagon = new Polygon(pointsX, pointsY, 6);
                        char state = automaton.getGrid().getCell(i, j, 0).getState();
                        g.setColor(new Color(colorsMap.get(state)[0], colorsMap.get(state)[1], colorsMap.get(state)[2]));
                        if(currentX == j && currentY == i)
                            if(g.getColor().equals(Color.BLACK))
                                g.setColor(Color.GRAY);
                            else
                                g.setColor(g.getColor().darker());
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

        JButton nextButton = new JButton("Next step");
        nextButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                automaton.evaluate();
                drawPanel.repaint();
            }
        });
        controlPanel.add(nextButton);

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
            public void actionPerformed(ActionEvent e){
                frame.dispose();
            }
        });
        controlPanel.add(quitButton);

        this.frame.add(controlPanel);
    }
}