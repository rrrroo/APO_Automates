import java.util.Scanner;

public class App {

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
                return new Automaton(filename);
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

    public static void main(String[] args) {
        Automaton auto = menu();
        System.out.println(auto);

    }
}
