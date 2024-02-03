```mermaid
flowchart TB
 
    
    
    Utilisateur --- choisir_automate
    Utilisateur --- Lancer_simulation
    Utilisateur --- choisir_sauvegarde

    Lancer_simulation --<<\include>>--> choisir_automate




    subgraph app 
    choisir_un_automate_existant --> choisir_automate
    choisir_nouvel_Automate --> choisir_automate
    Lancer_simulation
    choisir_sauvegarde

    jeu_de_la_vie -->choisir_un_automate_existant
    foret  -->choisir_un_automate_existant
    majorite  -->choisir_un_automate_existant
    1D --> choisir_un_automate_existant




    end

%% choisir_automate : permet de choisir un automate
%% choisir_un_automate_existant : permet de choisir un automate deja existant
%% choisir_nouvel_Automate :permet de choisir le chemin vers un nouvel Automate
%% Lancer_simulation: permet de lancer la simulation d'un Automate
%% choisir_sauvegarde : permet de choisir une grille sauvgadée










    %% https://mermaid.js.org/syntax/flowchart.html

