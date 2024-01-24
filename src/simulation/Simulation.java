package simulation;

// IMPORTS
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;


// APP CLASS
public class Simulation extends JFrame implements KeyListener{

    // CONSTANTS
    public static final int GRID_SIZE = 25;
    public static final int CELL_SIZE = 32;
    public static final int speed = 10;

    // VARIABLES
           
    public static boolean running = false;

    public static boolean[][] grid = new boolean[GRID_SIZE][GRID_SIZE];
    public static boolean[][] nextGrid = new boolean[GRID_SIZE][GRID_SIZE];

    public static int currentX = 0;
    public static int currentY = 0;
    
    public static JPanel drawPanel = new JPanel(){
            @Override
            protected void paintComponent(Graphics g){
                super.paintComponent(g);
                for (int i = 0; i < grid.length; i++){
                    for (int j = 0; j < grid[i].length; j++){
                        if (grid[i][j]){
                            g.setColor(new Color(0, 0, 0, 255)); //Black
                            g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE);
                        }
                        if (i == currentY && j == currentX){
                            if(grid[i][j]){
                                g.setColor(new Color(50, 50, 50, 255)); //Dark Grey
                            }else{
                                g.setColor(new Color(205, 205, 205, 255)); //Light Grey
                            }
                            g.fillRect(j * CELL_SIZE, i * CELL_SIZE, CELL_SIZE, CELL_SIZE); //Cursor
                        }
                    }
                }
            }
    };

    public static JPanel buttonPanel = new JPanel();
    JButton playPauseButton = new JButton("Play");
    JButton nextButton = new JButton("Next Step");
    JButton resetButton = new JButton("Reset");
    JButton quitButton = new JButton("Quit");

    // METHODS

    public static void changeState(int x, int y){
        grid[x][y] = !grid[x][y];
    }

    public static int countNeighbors(int x, int y){
        int neighbors = 0;
        for (int i = x - 1; i <= x + 1; i++){
            for (int j = y - 1; j <= y + 1; j++){
                if (i >= 0 && i < grid.length && j >= 0 && j < grid[i].length){
                    if (grid[i][j] && !(i == x && j == y)){
                        neighbors++;
                    }
                }
            }
        }
        return neighbors;
    }

    public static void nextStep(){
        for (int i = 0; i < grid.length; i++){
            for (int j = 0; j < grid[i].length; j++){
                int neighbors = countNeighbors(i, j);
                if (grid[i][j]){
                    if (neighbors < 2 || neighbors > 3){
                        nextGrid[i][j] = false;
                    }else{
                        nextGrid[i][j] = true;
                    }
                }else{
                    if (neighbors == 3){
                        nextGrid[i][j] = true;
                    }else{
                        nextGrid[i][j] = false;
                    }
                }
            }
        }
        for (int i = 0; i < grid.length; i++){
            System.arraycopy(nextGrid[i], 0, grid[i], 0, grid[i].length);
        }
    }

    public static Timer timer = new Timer(1000/speed, new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e){
            if(running){
                nextStep();
                drawPanel.repaint();
            }
        }
    });

    public void init(){
        JFrame f = new JFrame();
        f.setTitle("Test");
        f.setSize(GRID_SIZE * CELL_SIZE + 16, (GRID_SIZE+1) * CELL_SIZE + 64);
        f.setResizable(false);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        
        drawPanel.setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, GRID_SIZE * CELL_SIZE));

        buttonPanel.setPreferredSize(new Dimension(GRID_SIZE * CELL_SIZE, 64));
        buttonPanel.setBackground(new Color(200, 200, 200, 255));
        nextButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                nextStep();
                drawPanel.repaint();
            }
        });

        playPauseButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if(running){
                    running = false;
                    playPauseButton.setText("Play");
                }else{
                    running = true;
                    playPauseButton.setText("Pause");
                }
            }
        });

        resetButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                for (int i = 0; i < grid.length; i++){
                    for (int j = 0; j < grid[i].length; j++){
                        grid[i][j] = false;
                    }
                }
                drawPanel.repaint();
            }
        });

        quitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });

        playPauseButton.setFocusable(false);
        nextButton.setFocusable(false);
        resetButton.setFocusable(false);
        quitButton.setFocusable(false);
        buttonPanel.add(playPauseButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(quitButton);

        f.add(drawPanel);
        f.add(buttonPanel);
        f.addKeyListener(this);
        f.setFocusable(true);
        f.setVisible(true);
        timer.start();
    }

    @Override
    public void keyTyped(KeyEvent e){
        //Mandatory for KeyListener interface
    }

    @Override
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case 37: //Left
                if(currentX > 0){
                    currentX--;
                }
                break;
            case 38: //Up
                if(currentY > 0){
                    currentY--;
                }
                break;
            case 39: //Right
                if(currentX < GRID_SIZE - 1){
                    currentX++;
                }
                break;
            case 40: //Down
                if(currentY < GRID_SIZE - 1){
                    currentY++;
                }
                break;
            case 32: //Space
                changeState(currentY, currentX);
                break;
        }
        drawPanel.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e){
        //Mandatory for KeyListener interface
    }

    public static void main(String[] args){
        Simulation main = new Simulation();
        main.init();
    }
}