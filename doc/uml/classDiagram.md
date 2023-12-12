```mermaid
classDiagram
direction TB

    class App {
        + main(args[]: String) void$
        + menu() void
    }

    class Simulation {
        - automaton: Automaton

        + Simulation(a: Automaton)
    }

    class Automaton {
        - dimension: short
        - alphabet: Alphabet
        - neighborhood: List~Coordinates~
        - grid: Grid

        + Automaton(d: short, a: Alphabet, length: int)
        %% + rule(coordinates: Coordinates) Alphabet
    }

    class Alphabet {
        %% marche peut-être pas en tant qu'enum
        <<enumeration>>
    }

    namespace grid {
        class Grid {
            - dimension: short
            - grid: List~Cell~

            + Grid(dimension: short, length: int)
            + getState(coordinates: Coordinates) Alphabet
            + setState(coordinates: Coordinates, value: enum) void
        }

        class Cell {
            - state: Alphabet

            ~ getState() Alphabet
            ~ setState(state: Alphabet) void
        }
    }

    class Coordinates {
        - coordinates: List~int~

        + Coordinates(args[]: int)
        + getCoordinates() List~int~
    }


    %% Links :
    Automaton        o-- "1"    Alphabet
    Automaton "1"    *-- "1..n" Coordinates
    Automaton "1"    *-- "1"    Grid
    Grid      "1"    *-- "1..n" Cell
    Grid             -->        Coordinates
```
```mermaid
sequenceDiagram
    actor User
    participant App

    User->> App: Start
    create participant Menu
    App ->> Menu: Menu()
    App ->> Menu: getUserChoice()
    activate Menu
    User ->> Menu: Automaton type
    Menu ->> App: Automaton
    deactivate Menu
    create participant Simulation
    App ->>+ Simulation: Simulation(Automaton)
    
```