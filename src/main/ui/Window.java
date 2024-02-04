package main.ui;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
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

    /**
     * Reads the colors configuration from a JSON file and returns a map of characters and their corresponding RGB color values.
     * The JSON file must be located at "data/colors.json" relative to the current working directory.
     * The RGB color values must be integers between 0 and 255 (inclusive).
     *
     * @return a map of characters and their corresponding RGB color values
     * @throws IOException   if there is an error reading the colors configuration file
     * @throws JSONException if the colors configuration file is invalid or contains invalid color values
     */
    protected static Map<Character, int[]> getColors() throws IOException, JSONException {
        try {
            String content = new String(Files.readAllBytes(Paths.get("data/colors.json")));
            JSONObject json = new JSONObject(content);
            Map<Character, int[]> colorsMap = new HashMap<>();
            Iterator<String> keys = json.keys();
            String key;
            char character;
            JSONArray value;
            int[] color;

            while (keys.hasNext()) {
                key = keys.next();
                character = key.charAt(0);
                value = json.getJSONArray(key);
                color = new int[3];
                for (int i = 0; i < 3; i++) {
                    color[i] = value.getInt(i);
                    if(color[i] < 0 || color[i] > 255)
                        throw new JSONException("Les valeurs des couleurs doivent être comprises entre 0 et 255");
                }
                colorsMap.put(character, color);
            }

            return colorsMap;
        } catch (IOException e) {
            throw new IOException("Impossible de lire le fichier de configuration des couleurs");
        } catch (JSONException e) {
            throw new JSONException("Le fichier de configuration des couleurs est invalide" + (e.getMessage() != null ? " car " + e.getMessage() : ""));
        }
    }
}