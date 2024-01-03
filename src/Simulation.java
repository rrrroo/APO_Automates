import automaton.Automaton;

/**
 * The Simulation class represents a simulation of an automaton.
 */
public class Simulation {
    /**
     * Represents an automaton.
     */
    private Automaton automaton;

    /**
     * Constructs a Simulation object with the specified automaton.
     * 
     * @param automaton the automaton to be simulated
     */
    public Simulation(Automaton automaton) {
        this.automaton = automaton;
    }

    /**
     * Runs the simulation.
     */
    public void run() {
        boolean stop = false;
        int step = 0;

        display();
        while(!stop) {
            step();
            display();

            // condition d'arrêt temporaire
            step++;
            stop = (step > 10);
        }
    }

    private void step() {
        // TODO
    }

    /**
     * Displays the automaton.
     */
    private void display() {
        this.automaton.display();
    }
}