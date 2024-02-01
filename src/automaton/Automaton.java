package automaton;

import automaton.grid.Grid;
import automaton.rule.*;
import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONException;


/**
 * This class represents a cellular automaton.
 */
public class Automaton {

	// === ATTRIBUTES === //

	/**
	 * The dimension of the automaton.
	 */
	private Dimension dimension;

	/**
	 * The alphabet of the automaton.
	 */
	private char[] alphabet;

	/**
	 * The neighbourhood of the automaton.
	 */
	private List<int[]> neighbourhood;

	/**
	 * The grid of the automaton.
	 */
	private Grid grid;

	/**
	 * The rules of the automaton.
	 */
	private List<Rule> rules;


	// === CONSTRUCTORS === //

	/**
	 * The constructor of the class.
	 *
	 * @param filename the name of the file containing the automaton settings
	 */
	public Automaton(String filename) throws IOException, JSONException, IllegalArgumentException {
		JSONObject settings = getSettings(filename);

		String dim;
		try {
			dim = settings.getString("dimension");
		} catch (JSONException e) {
			throw new JSONException("il manque le paramètre dimension dans le fichier " + filename);
		}
		this.dimension = Dimension.fromString(dim);

		this.alphabet = getAlphabetFromSettings(settings, filename);

		this.neighbourhood = getNeighbourhoodFromSettings(settings, filename);

		int size;
		try {
			size = settings.getInt("size");
		} catch (JSONException e) {
			throw new JSONException("il manque le paramètre size dans le fichier " + filename);
		}
		if(this.dimension == Dimension.H && size % 2 != 0)
			throw new IllegalArgumentException("la taille de la grille doit être paire pour un automate hexagonal");
		this.grid = new Grid(this.dimension, size, this.alphabet[0]);

		this.rules = getRulesFromSettings(settings, filename);
	}

	/**
	 * The constructor of the class.
	 *
	 * @param rule the rule number of the 1D automaton
	 */
	public Automaton(int ruleNb) throws IOException, JSONException, IllegalArgumentException {
		String filename = "data/configs/1D.json";
		if(ruleNb < 0 || ruleNb > 255) {
			throw new IllegalArgumentException("la règle doit être comprise entre 0 et 255");
		}
		JSONObject settings = getSettings(filename);
		this.dimension = Dimension.ONE_D;
		this.alphabet = getAlphabetFromSettings(settings, filename);
		this.neighbourhood = getNeighbourhoodFromSettings(settings, filename);
		int size;
		try {
			size = settings.getInt("size");
		} catch (JSONException e) {
			throw new JSONException("il manque le paramètre size dans le fichier data/configs/1D.json");
		}

		this.grid = new Grid(this.dimension, size, this.alphabet[0]);
		
		List<Rule> newRules = new ArrayList<>();
		StringBuilder res = new StringBuilder(Integer.toBinaryString(ruleNb));
		while(res.length() < 8) {
			res.append("0" + res);
		}
		for(int i = 0; i < res.length(); i++) {
			if(res.charAt(7-i) == '1') {
				newRules.add(new NeighbourhoodRule(this.alphabet[(i/2)%2], this.alphabet[1], new char[] {this.alphabet[(i/4)%2], this.alphabet[i%2]}));
				System.out.println(this.alphabet[(i/4)%2] + " " + this.alphabet[(i/2)%2] + " " + this.alphabet[i%2] + " -> " + this.alphabet[1]);
			}
			else {
				newRules.add(new NeighbourhoodRule(this.alphabet[(i/2)%2], this.alphabet[0], new char[] {this.alphabet[(i/4)%2], this.alphabet[i%2]}));
				System.out.println(this.alphabet[(i/4)%2] + " " + this.alphabet[(i/2)%2] + " " + this.alphabet[i%2] + " -> " + this.alphabet[0]);
			}
			
		}
		this.rules = newRules;
	}

	/**
	 * Returns the settings of the automaton.
	 *
	 * @param filename the name of the file containing the automaton settings
	 * @return the settings of the automaton
	 * @throws IOException   if the file does not exist
	 * @throws JSONException if the file does not contain valid JSON
	 */
	private JSONObject getSettings(String filename) throws IOException, JSONException {
		try {
			String content = new String(Files.readAllBytes(Paths.get(filename)));
			return new JSONObject(content);
		} catch (IOException e) {
			throw new IOException("le fichier " + filename + " n'existe pas.");
		} catch (JSONException e) {
			throw new JSONException("le fichier " + filename + " ne contient pas de JSON valide.");
		}
	}

	/**
	 * Retrieves the alphabet from the settings JSON object.
	 * 
	 * @param settings The JSON object containing the settings.
	 * @param filename The name of the file being processed.
	 * @return The alphabet as an array of characters.
	 * @throws JSONException If the "alphabet" parameter is missing in the settings
	 *                       JSON object.
	 */
	private static char[] getAlphabetFromSettings(JSONObject settings, String filename) throws JSONException {
		try {
			JSONArray array = settings.getJSONArray("alphabet");
			char[] alphabet = new char[array.length()];
			for (int i = 0; i < array.length(); i++) {
				alphabet[i] = array.getString(i).charAt(0);
			}
			return alphabet;
		} catch (JSONException e) {
			throw new JSONException("il manque le paramètre alphabet dans le fichier " + filename);
		}
	}

	/**
	 * Retrieves the neighbourhood coordinates from the given settings JSON object.
	 * 
	 * @param settings The JSON object containing the settings.
	 * @param filename The name of the file being processed.
	 * @return The list of neighbourhood coordinates.
	 * @throws JSONException If the "neighbourhood" parameter is missing in the
	 *                       settings JSON object.
	 */
	private static List<int[]> getNeighbourhoodFromSettings(JSONObject settings, String filename)
			throws JSONException {
		try {
			JSONArray array = settings.getJSONArray("neighbourhood");
			JSONArray subArray;
			int[] coordinates;
			List<int[]> neighbourhood = new ArrayList<>();
			for (int i = 0; i < array.length(); i++) {
				subArray = array.getJSONArray(i);
				coordinates = new int[subArray.length()];
				for (int j = 0; j < subArray.length(); j++) {
					coordinates[j] = subArray.getInt(j);
				}
				neighbourhood.add(coordinates);
			}
			return neighbourhood;
		} catch (JSONException e) {
			throw new JSONException("il manque le paramètre neighbourhood dans le fichier " + filename);
		}
	}

	/**
	 * Retrieves the rules from the given settings JSON object.
	 *
	 * @param settings The JSON object containing the settings.
	 * @param filename The name of the file being processed.
	 * @return The list of rules.
	 * @throws JSONException If the "rules" parameter is missing in the settings
	 *                       JSON object.
	 */
	private static List<Rule> getRulesFromSettings(JSONObject settings, String filename) throws JSONException {
		try {
			JSONObject data = settings.getJSONObject("rule");
			JSONArray array = data.getJSONArray("rules");
			List<Rule> rules = new ArrayList<>();
			JSONObject rule;
			if (data.getBoolean("type")) {
				for (int i = 0; i < array.length(); i++) {
					rule = array.getJSONObject(i);
					char state = rule.getString("state").charAt(0);
					char result = rule.getString("result").charAt(0);
					JSONArray arrNeig = rule.getJSONArray("neighbours");
					char[] neighbours = new char[arrNeig.length()];
					for (int j = 0; j < arrNeig.length(); j++) {
						neighbours[j] = arrNeig.getString(j).charAt(0);
					}
					rules.add(new NeighbourhoodRule(state, result, neighbours));
				}
			} else {
				for (int i = 0; i < array.length(); i++) {
					rule = array.getJSONObject(i);
					JSONArray arrNeig = rule.getJSONArray("neighbours");
					int[] neighbours = new int[arrNeig.length()];
					for (int j = 0; j < arrNeig.length(); j++) {
						neighbours[j] = arrNeig.getInt(j);
					}
					char state = rule.getString("state").charAt(0);
					char result = rule.getString("result").charAt(0);
					char neighbourState = rule.getString("neighbourState").charAt(0);
					rules.add(new TransitionRule(state, result, neighbours, neighbourState));

				}
			}
			return rules;
		} catch (JSONException e) {
			throw new JSONException("il manque le paramètre rules dans le fichier " + filename);
		}
	}

	/**
	 * The constructor of the class.
	 *
	 * @param configFilename the name of the file containing the automaton settings
	 * @param gridFilename   the name of the file containing the grid
	 * @throws IOException   if the file does not exist
	 * @throws JSONException if the file does not contain valid JSON
	 * @throws IllegalArgumentException if the grid file does not match the settings
	 */
	public Automaton(String configFilename, String gridFilename) throws IOException, JSONException, IllegalArgumentException {
		JSONObject settings = getSettings(configFilename);

		String dim;
		try {
			dim = settings.getString("dimension");
		} catch (JSONException e) {
			throw new JSONException("il manque le paramètre dimension dans le fichier " + configFilename);
		}
		this.dimension = Dimension.fromString(dim);

		this.alphabet = getAlphabetFromSettings(settings, configFilename);

		this.neighbourhood = getNeighbourhoodFromSettings(settings, configFilename);

		this.grid = new Grid(this.dimension, gridFilename, this.alphabet);
		if(this.dimension == Dimension.H && this.grid.getSize() % 2 != 0)
			throw new IllegalArgumentException("la taille de la grille doit être paire pour un automate hexagonal");

		this.rules = getRulesFromSettings(settings, configFilename);
	}


	// === GETTERS === //

	/**
	 * Returns the dimension of this automaton.
	 *
	 * @return the dimension of this automaton
	 */
	public Dimension getDimension() {
		return this.dimension;
	}

	/**
	 * Returns the alphabet associated with this automaton.
	 *
	 * @return the alphabet associated with this automaton
	 */
	public char[] getAlphabet() {
		return this.alphabet;
	}

	/**
	 * Returns the grid associated with this automaton.
	 *
	 * @return the grid associated with this automaton
	 */
	public Grid getGrid() {
		return this.grid;
	}

	/**
	 * Returns the neighbourhood associated with this automaton.
	 *
	 * @return the neighbourhood associated with this automaton
	 */
	public List<int[]> getNeighbourhood() {
		return this.neighbourhood;
	}


	// === METHODS === //

	/**
	 * Calculates the rule number for the 1D automaton.
	 * 
	 * @return The rule number.
	 * @throws IllegalArgumentException if the automaton is not 1D.
	 */
	public int getRuleNumber() throws IllegalArgumentException {
		if(this.dimension != Dimension.ONE_D)
			throw new IllegalArgumentException("la règle ne peut être calculée que pour les automates 1D");
		
		int res = 0;
		for(int i = 0; i < this.rules.size(); i++) {
			if(this.rules.get(i).getResult() != this.alphabet[0])
				res += Math.pow(2, i);
		}
		return res;
	}

	/**
	 * Evaluates the automaton by applying the rules to each cell in the grid.
	 * The evaluation process depends on the dimension of the automaton.
	 * If an exception occurs during the evaluation process, the error message is printed to the console.
	 * After the evaluation, the grid is updated with the new grid.
	 */
	public void evaluate() {
		Grid newGrid = new Grid(this.grid);
		try {
			switch (this.dimension) {
				case ONE_D:
					for(int i = 0; i < this.grid.getSize(); i++)
						applyRules(newGrid, i, 0, 0);
					break;

				case TWO_D:
				case H:
					for(int i = 0; i < this.grid.getSize(); i++)
						for(int j = 0; j < this.grid.getSize(); j++)
							applyRules(newGrid, j, i, 0);
					break;

				case THREE_D:
					for(int i = 0; i < this.grid.getSize(); i++)
						for(int j = 0; j < this.grid.getSize(); j++)
							for(int k = 0; k < this.grid.getSize(); k++)
								applyRules(newGrid, k, j, i);
					break;

				default:
					throw new IllegalArgumentException("la dimension de l'automate n'est pas valide");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		this.grid = newGrid;
	}

	/**
	 * Applies the rules of the automaton to update the state of a cell in the new grid.
	 * 
	 * @param newGrid The new grid where the updated cell state will be set.
	 * @param x The x-coordinate of the cell.
	 * @param y The y-coordinate of the cell.
	 */
	private void applyRules(Grid newGrid, int x, int y, int z) {
		char newState;
		for(Rule rule : this.rules) {
			newState = rule.apply(this.grid.getCellState(x, y, z), this.grid.getNeighboursState(x, y, z, this.neighbourhood));
			if(newState != newGrid.getCellState(x, y, z)) {
				newGrid.setCellState(x, y, z, newState);
				break;
			}
		}
	}

	/**
	 * Turns the automaton into a string.
	 * 
	 * @return the automaton as a string
	 */
	@Override
	public String toString() {
		try {
			if(this.dimension == Dimension.ONE_D)
				return "Règle n°" + this.getRuleNumber() + "\n" + this.grid.toString() + "\n";
			else
				return this.grid.toString() + "\n";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	/**
	 * Saves the automaton to a file.
	 *
	 * @param filename the name of the file to save the automaton to
	 * @throws IOException if an error occurs while writing the file
	 */
	public void save(String filename) throws IOException {
		try (FileWriter writer = new FileWriter(filename)) {
			writer.write(this.toString());
		} catch (IOException e) {
			throw new IOException("une erreur est survenue lors de l'écriture du fichier " + filename);
		}
	}

	/**
	 * Saves the current state of the automaton to a file.
	 * The file name is generated based on the current dimension and the current date and time.
	 * The automaton is saved as a string representation in the file.
	 * If an error occurs while writing to the file, an error message is printed.
	 */
	public void save() throws IOException {
		SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		String filename = "data/saves/save_"
			+ this.dimension.toString()
			+ "_"
			+ date.format(new Date())
			+ ".txt";

		try {
			this.save(filename);
		} catch (IOException e) {
			throw new IOException(e.getMessage());
		}
	}
}
