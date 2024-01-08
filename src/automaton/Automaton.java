package automaton;

import automaton.grid.Coordinate;
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
	private static List<Coordinate> getNeighbourhoodFromSettings(JSONObject settings, String filename)
			throws JSONException {
		try {
			JSONArray array = settings.getJSONArray("neighbourhood");
			JSONArray subArray;
			int[] coordinates;
			List<Coordinate> neighbourhood = new ArrayList<>();
			for (int i = 0; i < array.length(); i++) {
				subArray = array.getJSONArray(i);
				coordinates = new int[subArray.length()];
				for (int j = 0; j < subArray.length(); j++) {
					coordinates[j] = subArray.getInt(j);
				}
				neighbourhood.add(new Coordinate(coordinates));
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
					List<Object> neig = arrNeig.toList();
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


	// === METHODS === //

	/**
	 * Displays the automaton by calling the display method of the grid.
	 */
	public void display() {
		this.grid.display();
	}

	public void evaluate() {
		Grid newGrid = new Grid(this.dimension, this.grid.getSize(), this.alphabet[0]);
		switch (this.dimension) {
			case ONE_D:
				for(int i = 0; i < this.grid.getSize(); i++)
					for(Rule rule : this.rules)
						grid.setCellState(i, 0, rule.apply(this.grid.getNeighboursState(i, 0, this.neighbourhood)));
				break;
			case TWO_D:
				// TODO
				break;
			case H:
				// TODO
				break;
		}
		this.grid = newGrid;
	}
}
