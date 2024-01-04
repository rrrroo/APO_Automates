import automaton.Automaton;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class App {
	public static void main(String[] args) {
		Automaton auto = menu();
	    System.out.println(auto);
	}

	public static Automaton menu() {
		Scanner scanner = new Scanner(System.in);
        Automaton auto = null;

        System.out.println("1: Choisir un automate");
        System.out.println("2: Entrer un chemin de fichier");
        System.out.print("Votre choix : ");
        int choice = scanner.nextInt();
        while(choice < 1 || choice > 2) {
            System.out.println("Choix incorrect");
            System.out.print("Votre choix : ");
            choice = scanner.nextInt();
        }
        switch(choice) {
            case 1:
                List<String> files = null;
                try (Stream<Path> paths = Files.walk(Paths.get("data"))) {
                    files = paths
                        .filter(Files::isRegularFile)
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .collect(Collectors.toList());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                System.out.println("Choisissez un automate :");
                for(int i = 0; i < files.size(); i++) {
                    System.out.println(i + " : " + files.get(i).replace(".json", ""));
                }

                System.out.print("Votre choix : ");
                choice = scanner.nextInt();
                while(choice < 0 || choice >= files.size()) {
                    System.out.println("Choix incorrect");
                    System.out.print("Votre choix : ");
                    choice = scanner.nextInt();
                }

                String filename = "data/" + files.get(choice);
                

                while(auto == null){
                    try {
                        auto = new Automaton(filename);
                    } catch (Exception e) {
                        System.out.println("L'automate n'a pas pu être créé car " + e.getMessage());
                        System.out.print("Votre choix : ");
                        choice = scanner.nextInt();
                        filename = "data/" + files.get(choice);
                    }
                }
                scanner.close();
                return auto;

            case 2:
                System.out.print("Entrez le chemin du fichier : ");
                filename = scanner.next();
                while(auto == null){
                    try {
                        auto = new Automaton(filename);
                    } catch (Exception e) {
                        System.out.println("L'automate n'a pas pu être créé car " + e.getMessage());
                        System.out.print("Entrez le chemin du fichier : ");
                        filename = scanner.next();
                    }
                }
            default:
                scanner.close();
                return null;
        }
    }
}
