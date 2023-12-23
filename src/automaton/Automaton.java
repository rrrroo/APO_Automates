package automaton;

import automaton.grid.Coordinate;
import automaton.grid.Grid;
import org.json.JSONObject;
import org.json.JSONException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.io.IOException;

/**
 * This class represents a cellular automaton.
 */
public class Automaton {
	/**
	 * The dimension of the automaton.
	 */
	private Dimension dimension;

	/**
	 * The alphabet of the automaton.
	 */
	private char[] alphabet;

	/**
	 * The neighborhood of the automaton.
	 */
	private List<Coordinate> neighborhood;

	/**
	 * The grid of the automaton.
	 */
	private Grid grid;

	/**
	 * The rules of the automaton.
	 */
	private List<Rule> rules;

	/**
	 * The constructor of the class.
	 *
	 * @param filename the name of the file containing the automaton settings
	 */
	public Automaton(String filename) throws IOException, JSONException, IllegalArgumentException {
		JSONObject settings = getSettings(filename);

		this.dimension = Dimension.fromString(getSetting(settings, "dimension", filename));
		this.alphabet = getSetting(settings, "alphabet", filename).toCharArray();
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
	 * Retrieves the value associated with the specified key from the given settings
	 * object.
	 * 
	 * @param settings the JSON object containing the settings
	 * @param key      the key to retrieve the value for
	 * @param filename the name of the file being processed
	 * @return the value associated with the key
	 * @throws JSONException if the key is missing in the settings object
	 */
	private static String getSetting(JSONObject settings, String key, String filename) throws JSONException {
		try {
			return settings.getString(key);
		} catch (JSONException e) {
			throw new JSONException("il manque le paramètre " + key + " dans le fichier " + filename);
		}
	}
}
