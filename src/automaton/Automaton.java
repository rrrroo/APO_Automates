package automaton;

import automaton.grid.Grid;
import automaton.rule.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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


	// === CONSTRUCTOR === //

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
		this.grid = new Grid(this.dimension, size, this.alphabet[0]);

		this.rules = getRulesFromSettings(settings, filename);
	}

	/**
	 * The constructor of the class.
	 *
	 * @param rule the rule number of the 1D automaton
	 */
	public Automaton(int ruleNb) throws IOException, JSONException, IllegalArgumentException {
		JSONObject settings = getSettings("data/1D.json");
		this.dimension = Dimension.ONE_D;
		this.alphabet = getAlphabetFromSettings(settings, "data/1D.json");
		this.neighbourhood = getNeighbourhoodFromSettings(settings, "data/1D.json");
		int size;
		try {
			size = settings.getInt("size");
		} catch (JSONException e) {
			throw new JSONException("il manque le paramètre size dans le fichier data/1D.json");
		}

		this.grid = new Grid(this.dimension, size, this.alphabet[0]);
		
		List<Rule> rules = new ArrayList<>();
		String res = Integer.toBinaryString(ruleNb);
		while(res.length() < 8) {
			res = "0" + res;
		}
		for(int i = 0; i < res.length(); i++) {
			if(res.charAt(7-i) == '1') {
				rules.add(new NeighbourhoodRule(this.alphabet[(i/2)%2], this.alphabet[1], new char[] {this.alphabet[(i/4)%2], this.alphabet[i%2]}));
				System.out.println(this.alphabet[(i/2)%2] + " " + this.alphabet[(i/4)%2] + " " + this.alphabet[i%2] + " -> " + this.alphabet[1]);
			}
			else {
				rules.add(new NeighbourhoodRule(this.alphabet[(i/2)%2], this.alphabet[0], new char[] {this.alphabet[(i/4)%2], this.alphabet[i%2]}));
				System.out.println(this.alphabet[(i/2)%2] + " " + this.alphabet[(i/4)%2] + " " + this.alphabet[i%2] + " -> " + this.alphabet[0]);
			}
			
		}
		this.rules = rules;
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


	// === GETTERS === //

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


	// === METHODS === //

	/**
	 * Displays the automaton by calling the display method of the grid.
	 */
	public void display() {
		this.grid.display();
	}

	public void evaluate() {
		Grid newGrid = new Grid(this.grid);
		char newState;
		try {
			switch (this.dimension) {
				case ONE_D:
					for(int i = 0; i < this.grid.getSize(); i++) {
						for(Rule rule : this.rules) {
							newState = rule.apply(this.grid.getCellState(i, 0), this.grid.getNeighboursState(i, 0, this.neighbourhood));
							if(newState != newGrid.getCellState(i, 0)) {
								newGrid.setCellState(i, 0, newState);
								break;
							}
						}
					}
					break;
				case TWO_D:
					for(int i = 0; i < this.grid.getSize(); i++)
						for(int j = 0; j < this.grid.getSize(); j++)
							for(Rule rule : this.rules)
								grid.setCellState(i, j, rule.apply(this.grid.getCellState(i, j), this.grid.getNeighboursState(i, j, this.neighbourhood)));
					break;
				case H:
					// TODO
					break;
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		this.grid = newGrid;
	}
}
