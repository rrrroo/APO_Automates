```mermaid
sequenceDiagram
    title Sequence diagram Simulation creation
    actor User
    participant App

    User->> App: Start
    App ->> App: Menu()
    User ->> App: Automaton type
    create participant Simulation
    App ->>+ Simulation: Simulation(Automaton)
```