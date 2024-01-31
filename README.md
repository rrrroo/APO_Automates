# Projet APO : automates cellulaires

[lien du GitHub](https://github.com/rrrroo/APO_Automates)

## TODO

- [ ] Automates
    - [x] définition formelle
    - [x] 1D
        - [x] affichage 2D (espace * temps)
        - [x] affichage numéro de règle
    - [ ] règle de la majorité
        - [x] 1D
        - [x] 2D
        - [x] 3D
        - [ ] H
        - [x] voisinage carré
        - [ ] voisinage rectangulaire
    - [x] jeu de la vie
    - [ ] feu de forêt
        - [x] 2D classique
        - [ ] 2D avec probabilité
        - [ ] 2D avec vent
        - [ ] H
        - [x] 4 voisins
        - [x] 8 voisins
- [ ] Tronc commun
    - [x] menu
        - [x] choisir un automate
        - [x] créer un automate
        - [ ] charger une configuration de départ
    - [ ] configuration de départ
        - [x] aléatoire
        - [ ] manuelle
        - [x] fichier
    - [ ] grille
        - [x] 1D
        - [x] 2D
        - [x] 3D
        - [ ] H
    - [ ] JavaDoc
- [ ] Extensions
    - [x] Git
    - [x] Ant
    - [ ] Tests unitaires
    - [x] fichiers de configuration
    - [ ] sauvegarde
        - [ ] utiliser les toString
        - [ ] adapter le constructeur de Grid aux toString
    - [ ] interface graphique
    - [ ] statistiques



## Description

L’objectif du projet est d’implémenter et d’étudier certains automates cellulaires.

## Documentation

La documentation du projet est disponible dans le dossier `doc/` :
- `doc/javadoc/` : documentation Java au format .html
- `doc/uml/` : diagrammes UML au format .md et .png

> Pour visualiser les diagrammes UML, vous pouvez utiliser l'extension Markdown Preview Mermaid Support de Visual Studio Code.

## Librairies

Le projet utilise les librairies suivantes :
- [org.json](https://repo1.maven.org/maven2/org/json/json/20231013/json-20231013.jar) : pour la lecture et l'écriture de fichiers JSON

Les librairies sont à placer dans le dossier `lib/`.

## Compilation

Le projet peut être compilé à l'aide de ant. Pour cela il faut [télécharger ant](https://ant.apache.org/bindownload.cgi) et l'ajouter au PATH.

Les commandes suivantes sont disponibles :
- `ant clean` : vide les dossiers `bin/` et `exe/`
- `ant compile` : compile les fichiers .java dans `src/` et place les .class dans `bin/`
- `ant jar` : crée un .jar exécutable dans `exe/`
- `ant run` : exécute le .jar
- `ant javadoc` : crée la documentation Java dans `doc/javadoc/`

## Projet Java

### Folder Structure

The workspace contains two folders by default, where:

- `src`: the folder to maintain sources
- `lib`: the folder to maintain dependencies

Meanwhile, the compiled output files will be generated in the `bin` folder by default.

> If you want to customize the folder structure, open `.vscode/settings.json` and update the related settings there.

### Dependency Management

The `JAVA PROJECTS` view allows you to manage your dependencies. More details can be found [here](https://github.com/microsoft/vscode-java-dependency#manage-dependencies).