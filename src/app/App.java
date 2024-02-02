package app;

import automaton.Automaton;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class App {
	public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
		Automaton auto = menu(scanner);
        Simulation simulation = new Simulation(auto);
        // simulation.getAutomaton().getGrid().setAllRandom(simulation.getAutomaton().getAlphabet(), new Random());
        simulation.run();
        menuSauvegarde(auto, scanner);
        scanner.close();
	}

	public static Automaton menu(Scanner scanner) {
        Automaton auto = null;
        String filename = null;

        System.out.println("1: Choisir un automate");
        System.out.println("2: Entrer un chemin de fichier");
        System.out.println("3: Charger une sauvegarde");
        System.out.print("Votre choix : ");
        int choice = scanner.nextInt();
        while(choice < 1 || choice > 3) {
            System.out.println("Choix incorrect");
            System.out.print("Votre choix : ");
            choice = scanner.nextInt();
        }
        switch(choice) {
            case 1:
                List<String> files = null;
                try (Stream<Path> paths = Files.walk(Paths.get("data/configs"))) {
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
                    filename = "data/configs/" + files.get(choice - 1);
                    
                    while(auto == null){
                        try {
                            auto = new Automaton(filename);
                        } catch (Exception e) {
                            System.out.println("L'automate n'a pas pu être créé car " + e.getMessage());
                            System.out.print("Votre choix : ");
                            choice = scanner.nextInt();
                            filename = "data/configs/" + files.get(choice);
                        }
                    }
                }
                auto.getGrid().setAllRandom(auto.getAlphabet(), new Random());
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
                auto.getGrid().setAllRandom(auto.getAlphabet(), new Random());
                return auto;

            case 3:
                files = null;
                try (Stream<Path> paths = Files.walk(Paths.get("data/saves"))) {
                    files = paths
                        .filter(Files::isRegularFile)
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .collect(Collectors.toList());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for(int i = 0; i < files.size(); i++) {
                    System.out.println(i + 1 + " : " + files.get(i).replace(".txt", "").replace("save_", ""));
                }

                System.out.print("Votre choix : ");
                choice = scanner.nextInt();
                while(choice < 1 || choice >= files.size() + 1) {
                    System.out.println("Choix incorrect");
                    System.out.print("Votre choix : ");
                    choice = scanner.nextInt();
                }
                String gridFilename = "data/saves/" + files.get(choice - 1);

                files = null;
                try (Stream<Path> paths = Files.walk(Paths.get("data/configs"))) {
                    files = paths
                        .filter(Files::isRegularFile)
                        .map(Path::getFileName)
                        .map(Path::toString)
                        .collect(Collectors.toList());
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for(int i = 0; i < files.size(); i++) {
                    System.out.println(i + 1 + " : " + files.get(i).replace(".json", ""));
                }

                System.out.print("Votre choix : ");
                choice = scanner.nextInt();
                while(choice < 1 || choice >= files.size() + 1) {
                    System.out.println("Choix incorrect");
                    System.out.print("Votre choix : ");
                    choice = scanner.nextInt();
                }
                String configFilename = "data/configs/" + files.get(choice - 1);

                while(auto == null){
                    try {
                        auto = new Automaton(configFilename, gridFilename);
                    } catch (Exception e) {
                        System.out.println("L'automate n'a pas pu être créé car " + e.getMessage());
                        System.out.print("Votre choix : ");
                        choice = scanner.nextInt();
                        gridFilename = "data/saves/" + files.get(choice);
                    }
                }
                return auto;

            default:
                return null;
        }
    }

    public static void menuSauvegarde(Automaton auto, Scanner scanner) {
        System.out.println("Sauvegarder l'automate ? (o/n)");
        String choice = scanner.next();
        while(!choice.equals("o") && !choice.equals("n")) {
            System.out.println("Valeur incorrecte");
            System.out.print("Votre choix : ");
            choice = scanner.next();
        }
        if(choice.equals("o")) {
            System.out.print("Entrez le nom du fichier (laisser vide pour nom par défaut):");
            scanner.nextLine();
            String filename = scanner.nextLine();
            try {
                if(filename.equals("")) {
                    auto.save();
                } else {
                    auto.save(filename);
                }
            } catch (IOException e) {
                System.out.println("L'automate n'a pas pu être sauvegardé car " + e.getMessage());
            }
        }
    }
}
