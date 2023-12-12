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