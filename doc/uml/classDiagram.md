```mermaid
classDiagram
direction TB

    class App {
        + main(args[]: String) void$
        + menu() Automaton$
    }

    class Simulation {
        - automaton: Automaton

        + Simulation(a: Automaton)
        + run() void
        - step() void
        - display() void
    }

    class Automaton {
        - dimension: Dimension
        - alphabet: char[]
        - neighborhood: List~Coordinate~
        - grid: Grid
        - rules: List~Rule~

        + Automaton(filename: String)
        + update() void
        - getNeighbours(coordinates: Coordinate) List~Cell~
    }

    class Automaton1D {
        + Automaton1D(ruleNumber: int, size: int)
    }

    class Dimension {
        <<enum>>

        1D
        2D
        H

        - Dimension(value: String)
        + fromString(value: String) Dimension$
    }

    namespace grid {
        class Grid {
            - dimension: Dimension
            - size: int
            - grid: List~Cell~

            + Grid(dimension: short, length: int)
            + getState(coordinates: Coordinate) char
            + setState(coordinates: Coordinate, value: char) void
        }

        class Cell {
            - state: char

            ~ getState() char
            ~ setState(state: char) void
        }
    }

    class Coordinate {
        - coordinates: int[]

        + Coordinate(coordinates[]: int)
        + getCoordinates() int[]
    }

    class Rule {
        - state: char
        - result: char

        + apply(Cell, List~Cell~)
    }

    class NeighbourhoodRule {
        - neighbours: List~String~

        + apply(Grid, Coordinate)
    }

    class TransitionRule {
        - neighbours: List~char~
        - neighbourState: char

        + apply(Grid, Coordinate)
    }


    %% Links :
    App              -->        Automaton
    App              -->        Simulation
    Simulation       o-- "1"    Automaton
    Automaton        <|--       Automaton1D
    Automaton        --         Dimension
    Automaton "1"    *-- "1..n" Coordinate
    Automaton "1"    *-- "1"    Grid
    Automaton "1"    *-- "1..n" Rule
    Grid             --         Dimension
    Grid      "1"    *-- "1..n" Cell
    Grid             -->        Coordinate
    Rule            <|--       NeighbourhoodRule
    Rule            <|--       TransitionRule
```