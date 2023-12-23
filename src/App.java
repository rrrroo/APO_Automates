import automaton.Automaton;

import org.json.JSONException;
import java.io.IOException;
import java.util.Scanner;

public class App {
	public static void main(String[] args) {
		// Automaton auto = menu();
		// System.out.println(auto);

		// TODO: Il faut mettre ça quelque part dans le menu pour redemander le nom du fichier si ça marche pas :
		String filename = "data/gameOfLife.json";
		try {
			Automaton auto = new Automaton(filename);
		}
		catch (Exception e) {
			System.out.println("L'automate n'a pas pu être créé car " + e.getMessage());
		}
	}

	public static Automaton menu() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("1. Create new automaton");
		System.out.println("2. Choose between preexisting automaton");
		System.out.println("3. Load automaton from file");
		int choice = 0;
		while (choice != 1 && choice != 2 && choice != 3) {
			System.out.print("Choice: ");
			choice = scanner.nextInt();
		}
		switch (choice) {
			case 1:
				scanner.close();
				return createAutomaton();
			case 2:
				scanner.close();
				return chooseAutomaton();
			case 3:
				System.out.print("Filename: ");
				String filename = scanner.next();
				scanner.close();
				// return new Automaton(filename);
			default:
				scanner.close();
				System.out.println("Invalid choice");
				return null;
		}
	}

	public static Automaton createAutomaton() {
		return null;
	}

	public static Automaton chooseAutomaton() {
		return null;
	}
}
