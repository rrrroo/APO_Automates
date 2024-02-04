package main.ui;
import main.automaton.Automaton;
import main.automaton.grid.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;


public class Window1D extends Window {
    private ArrayList<Grid> steps = new ArrayList<Grid>();
    private int stepNb;
    private int shift;
    private int currentX;
    private int currentY;
    
    public Window1D(Automaton automaton, int cellSize){
        super(automaton, cellSize);
        steps.add(automaton.getGrid());
        stepNb = 0;
        shift = 0;
        currentX = 0;
        currentY = 0;

        this.frame = new JFrame("Automate cellulaire à 1 dimension");
        this.frame.setSize(automaton.getGrid().getSize()*cellSize + 16, cellSize*11 + 90);
        this.frame.setResizable(false);
        this.frame.setLayout(new FlowLayout());

        this.frame.addMouseMotionListener(new MouseMotionAdapter(){
            @Override
            public void mouseMoved(MouseEvent e){
                int x = e.getX() - 8;
                int y = e.getY() - 35;
                currentX = x/cellSize;
                currentY = y/cellSize;
                drawPanel.repaint();
            }
        });

        this.frame.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                if(e.getButton() == MouseEvent.BUTTON1 && currentY + shift == stepNb){
                    automaton.getGrid().getCell(currentX, 0, 0).setState(getNextState(automaton.getGrid().getCell(currentX, 0, 0).getState()));
                    steps.set(stepNb, automaton.getGrid());
                    drawPanel.repaint();
                }
            }
        });

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
                        char state = steps.get(s).getCell(i, 0, 0).getState();
                        g.setColor(new Color(colorsMap.get(state)[0], colorsMap.get(state)[1], colorsMap.get(state)[2]));
                        if(currentX == i && currentY == s-shift && s == stepNb){
                            if(g.getColor().equals(Color.BLACK)){
                                g.setColor(Color.GRAY);
                            }else{
                                g.setColor(g.getColor().darker());
                            }
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
                while(shift + 9 <= stepNb){
                    shift++;
                }
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
        controlPanel.add(quitButton);

        this.frame.add(drawPanel);
        this.frame.add(controlPanel);
    }
}