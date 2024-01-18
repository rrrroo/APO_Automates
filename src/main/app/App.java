package main.app;
import main.automaton.Automaton;

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
        Simulation simulation = new Simulation(auto);
        simulation.getAutomaton().getGrid().setAllRandom(simulation.getAutomaton().getAlphabet());
        simulation.run();
	}

	public static Automaton menu() {
		Scanner scanner = new Scanner(System.in);
        Automaton auto = null;
        String filename = null;

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
                System.out.println("0 : 1D (entrer règle)");
                for(int i = 0; i < files.size(); i++) {
                    System.out.println(i + 1 + " : " + files.get(i).replace(".json", ""));
                }

                System.out.print("Votre choix : ");
                choice = scanner.nextInt();
                while(choice < 0 || choice >= files.size() + 1) {
                    System.out.println("Choix incorrect");
                    System.out.print("Votre choix : ");
                    choice = scanner.nextInt();
                }
                if (choice == 0){
                    System.out.print("Entrez la règle (0 - 255) : ");
                    int rule = scanner.nextInt();
                    while(auto == null){
                        try {
                            auto = new Automaton(rule);
                        } catch (Exception e) {
                            System.out.println("L'automate n'a pas pu être créé car " + e.getMessage());
                            System.out.print("Entrez la règle : ");
                            rule = scanner.nextInt();
                        }
                    }
                }
                else {
                    filename = "data/" + files.get(choice - 1);
                    
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
