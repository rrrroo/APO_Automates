package automaton;

import automaton.grid.Coordinate;
import automaton.grid.Grid;
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
	private List<Coordinate> neighbourhood;

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
		this.alphabet = getAlphabetFromSettings(settings, filename);
		this.neighbourhood = getNeighbourhoodFromSettings(settings, filename);
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
		}
		catch (IOException e) {
			throw new IOException("le fichier " + filename + " n'existe pas.");
		}
		catch (JSONException e) {
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
		}
		catch (JSONException e) {
			throw new JSONException("il manque le paramètre " + key + " dans le fichier " + filename);
		}
	}

	/**
	 * Retrieves the alphabet from the settings JSON object.
	 * 
	 * @param settings The JSON object containing the settings.
	 * @param filename The name of the file being processed.
	 * @return The alphabet as an array of characters.
	 * @throws JSONException If the "alphabet" parameter is missing in the settings JSON object.
	 */
	private static char[] getAlphabetFromSettings(JSONObject settings, String filename) throws JSONException {
		try {
			JSONArray array = settings.getJSONArray("alphabet");
			char[] alphabet = new char[array.length()];
			for(int i = 0; i < array.length(); i++) {
				alphabet[i] = array.getString(i).charAt(0);
			}
			return alphabet;
		}
		catch (JSONException e) {
			throw new JSONException("il manque le paramètre alphabet dans le fichier " + filename);
		}
	}

	/**
	 * Retrieves the neighbourhood coordinates from the given settings JSON object.
	 * 
	 * @param settings The JSON object containing the settings.
	 * @param filename The name of the file being processed.
	 * @return The list of neighbourhood coordinates.
	 * @throws JSONException If the "neighbourhood" parameter is missing in the settings JSON object.
	 */
	private static List<Coordinate> getNeighbourhoodFromSettings(JSONObject settings, String filename) throws JSONException {
		try {
			JSONArray array = settings.getJSONArray("neighbourhood");
			JSONArray subArray;
			int[] coordinates;
			List<Coordinate> neighbourhood = new ArrayList<>();
			for(int i = 0; i < array.length(); i++) {
				subArray = array.getJSONArray(i);
				coordinates = new int[subArray.length()];
				for(int j = 0; j < subArray.length(); j++) {
					coordinates[j] = subArray.getInt(j);
				}
				neighbourhood.add(new Coordinate(coordinates));
			}
			return neighbourhood;
		}
		catch (JSONException e) {
			throw new JSONException("il manque le paramètre neighbourhood dans le fichier " + filename);
		}
	}
}
