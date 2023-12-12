```mermaid
sequenceDiagram
    title Diagramme de séquence intialisation de la simulation
    actor User
    participant App

    User->> App: Start
    App ->> App: Menu()

    %% à détailler

    User ->> App: Automaton type
    create participant Simulation
    App ->>+ Simulation: Simulation(Automaton)
```