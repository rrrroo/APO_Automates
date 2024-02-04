package main.ui;

import javax.swing.*;

import main.automaton.Automaton;

import java.util.List;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * This class is a subclass of the Window class and is used to create the main GUI menu of the application.
 * It allows the user to select an automaton and optionally a save file, and to start the simulation.
 */
public class WindowMenu extends Window {
    /**
     * Constructor for the WindowMenu class.
     */
    public WindowMenu(){
        super(null, 0);
        this.frame = new JFrame("Menu");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setSize(500, 400);
        this.frame.setResizable(false);
        this.frame.setLayout(new FlowLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JPanel configPanel = new JPanel();
        configPanel.setPreferredSize(new Dimension(300, 100));
        configPanel.setLayout(new BoxLayout(configPanel, BoxLayout.Y_AXIS));
        Label label = new Label("Automaton:");
        label.setAlignment(Label.CENTER);
        configPanel.add(label);
        
        JPanel savePanel = new JPanel();
        savePanel.setPreferredSize(new Dimension(300, 200));
        savePanel.setLayout(new BorderLayout());

        JPanel saveSelectionPanel = new JPanel();
        saveSelectionPanel.setVisible(false);
        saveSelectionPanel.setLayout(new BoxLayout(saveSelectionPanel, BoxLayout.Y_AXIS));

        JPanel actionPanel = new JPanel();
        actionPanel.setLayout(new FlowLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));

        Checkbox c = new Checkbox("Load a save");

        // Show the save selection panel when the checkbox is checked
        c.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                saveSelectionPanel.setVisible(e.getStateChange() == 1);
            }
        });
        
        JPanel cellSizePanel = new JPanel();
        cellSizePanel.setLayout(new BorderLayout());
        cellSizePanel.setMaximumSize(new Dimension(100, 20));

        savePanel.add(c, BorderLayout.NORTH);
        savePanel.add(saveSelectionPanel, BorderLayout.CENTER);

        topPanel.add(configPanel);
        topPanel.add(savePanel);

        mainPanel.add(topPanel);
        mainPanel.add(cellSizePanel);
        mainPanel.add(actionPanel);

        this.frame.add(mainPanel);
        
        List<String> configFiles = getConfigFiles();
        List<String> saveFiles = getSaveFiles();

        // Create a radio button for each configuration and save file

        JRadioButton[] configButtons = new JRadioButton[configFiles.size()];
        ButtonGroup configButtonsGroup = new ButtonGroup();
        for(int i = 0; i < configFiles.size(); i++){
            configButtons[i] = new JRadioButton(configFiles.get(i).replace(".json", ""));
            configButtons[i].setActionCommand(configFiles.get(i));
            configButtons[i].setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
            configButtonsGroup.add(configButtons[i]);
            configPanel.add(configButtons[i]);
        }

        JRadioButton[] saveButtons = new JRadioButton[saveFiles.size()];
        ButtonGroup saveButtonsGroup = new ButtonGroup();
        for(int i = 0; i < saveFiles.size(); i++){
            saveButtons[i] = new JRadioButton(saveFiles.get(i).replace(".txt", "").replace("save_", ""));
            saveButtons[i].setActionCommand(saveFiles.get(i));
            saveButtons[i].setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
            saveButtonsGroup.add(saveButtons[i]);
            saveSelectionPanel.add(saveButtons[i]);
        }

        
        // Enable the start button when a configuration is selected
        JButton startButton = new JButton("Start");
        startButton.setEnabled(false);
        for (JRadioButton button : configButtons) {
            button.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        startButton.setEnabled(true);
                    }
                }
            });
        }

        // Add a text field to select the cell size
        cellSizePanel.add(new Label("cellSize"), BorderLayout.WEST);
        JTextField cellSize = new JTextField("10");
        cellSize.setMaximumSize(new Dimension(100, 20));
        cellSizePanel.add(cellSize);

        
        startButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                String configFilename = "data/configs/" + configButtonsGroup.getSelection().getActionCommand();
                String gridFilename = null;
                if(c.getState()) {
                    gridFilename = "data/saves/" + saveButtonsGroup.getSelection().getActionCommand();
                }
                int cellSizeInt = Integer.parseInt(cellSize.getText());

                try {
                    Automaton auto = null;
                    if (gridFilename == null) {
                        auto = new Automaton(configFilename);
                        auto.getGrid().setAllRandom(auto.getAlphabet(), new Random());
                    } else {
                        auto = new Automaton(configFilename, gridFilename);
                    }
                    Window window = null;
                    switch(auto.getDimension()) {
                        case ONE_D:
                            window = new Window1D(auto, cellSizeInt);
                            break;
                        case TWO_D:
                            window = new Window2D(auto, cellSizeInt);
                            break;
                        case THREE_D:
                            window = new Window3D(auto, cellSizeInt);
                            break;
                        case H:
                            window = new WindowH(auto, cellSizeInt);
                            break;
                    }
                    window.display();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        actionPanel.add(startButton);

        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                System.exit(0);
            }
        });
        actionPanel.add(quitButton);
    }

    /**
     * Display the window
     */
    @Override
    public void display() {
        this.frame.setVisible(true);
    }

    /**
     * Get the list of configuration files in the data/configs directory
     * @return a list of configuration file names
     */
    public static List<String> getConfigFiles() {
        List<String> configFiles = null;
        try (Stream<Path> paths = Files.walk(Paths.get("data/configs"))) {
            configFiles = paths
                .filter(Files::isRegularFile)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
            } catch (IOException e) {
            e.printStackTrace();
        }
        return configFiles;
    }

    /**
     * Get the list of save files in the data/saves directory
     * @return a list of save file names
     */
    public static List<String> getSaveFiles() {
        List<String> saveFiles = null;
        try (Stream<Path> paths = Files.walk(Paths.get("data/saves"))) {
            saveFiles = paths
                .filter(Files::isRegularFile)
                .map(Path::getFileName)
                .map(Path::toString)
                .collect(Collectors.toList());
            } catch (IOException e) {
            e.printStackTrace();
        }
        return saveFiles;
    }
}