# Projet APO : automates cellulaires

[Lien du GitHub](https://github.com/rrrroo/APO_Automates)

## TODO

- [ ] Modélisation
    - [ ] cas d'utilisation
    - [ ] diagramme de classe
    - [ ] diagramme de séquence initialisation
    - [ ] diagramme de séquence évolution
    - [ ] diagramme d'activité menu
- [x] Automates
    - [x] définition formelle
    - [x] 1D
        - [x] affichage 2D (espace * temps)
        - [x] affichage numéro de règle
    - [x] règle de la majorité
        - [x] 1D
        - [x] 2D
        - [x] 3D
        - [x] H
        - [x] voisinage carré
        - [x] voisinage rectangulaire
    - [x] jeu de la vie
    - [x] feu de forêt
        - [x] 2D classique
        - [x] 2D avec probabilité
        - [x] 2D avec vent
        - [x] H
        - [x] 4 voisins
        - [x] 8 voisins
- [ ] Tronc commun
    - [x] menu
        - [x] choisir un automate
        - [x] créer un automate
        - [x] charger une configuration de départ
    - [ ] configuration de départ
        - [x] aléatoire
        - [ ] dessin
        - [x] fichier
    - [x] grille
        - [x] 1D
        - [x] 2D
        - [x] 3D
        - [x] H
    - [ ] JavaDoc
- [ ] Extensions
    - [x] Git
    - [x] Ant
    - [ ] Tests unitaires
    - [x] fichiers de configuration
    - [x] sauvegarde
        - [x] utiliser les toString
        - [x] adapter le constructeur de Grid aux toString
    - [ ] interface graphique
        - [x] 1D
        - [x] 2D
        - [ ] 3D
        - [ ] H
        - [ ] menu
        - [ ] dessin
        - [x] next step
        - [ ] play/pause
    - [ ] statistiques



## Description

L’objectif du projet est d’implémenter et d’étudier certains automates cellulaires. Nous avons fait le choix d'implémenter en premier un automate entièrement paramétrable, puis d'implémenter des automates spécifiques en utilisant cet automate paramétrable.
Tous les automates implémentés sont au format `.json`. Ainsi, il est facile de les modifier ou d'en créer de nouveaux.

## Architecture

```
├── bin                     // fichiers .class
├── data
│   ├── configs             // fichiers de configuration des automates .json
│   └── saves               // fichiers de sauvegarde des grilles .txt
├── doc
│   ├── javadoc             // documentation Java
│   ├── uml                 // diagrammes UML
│   └── syntaxeAutomate.md  // guide de la syntaxe desfichiers de configuration
├── exe                     // exécutable .jar
├── lib                     // librairies
├── src
│   ├── main                // code source
│   └── test                // tests unitaires
├── build.xml               // fichier de configuration Ant
└── README.md
```

## Documentation

La documentation du projet est disponible dans le dossier `doc/` :
- `doc/javadoc/` : documentation Java au format .html
- `doc/uml/` : diagrammes UML au format .md et .png
- `doc/syntaxeAutomate.md` : guide de la syntaxe des fichiers de configuration des automates

> Pour visualiser les diagrammes UML, vous pouvez utiliser l'extension [Markdown Preview Mermaid Support](https://marketplace.visualstudio.com/items?itemName=bierner.markdown-mermaid) de Visual Studio Code.

## Librairies

Le projet utilise les librairies suivantes :
- [org.json](https://repo1.maven.org/maven2/org/json/json/20231013/json-20231013.jar) : pour la lecture et l'écriture de fichiers JSON

Les librairies sont à placer dans le dossier `lib/`.

## Compilation et exécution

Le projet peut être compilé à l'aide de ant. Pour cela il faut [télécharger ant](https://ant.apache.org/bindownload.cgi) et l'ajouter au PATH.

Les commandes suivantes sont disponibles :
- `ant clean` : vide les dossiers `bin/` et `exe/`
- `ant compile` : compile les fichiers .java dans `src/` et place les .class dans `bin/`
- `ant jar` : crée un .jar exécutable dans `exe/`
- `ant run` : exécute le .jar
- `ant javadoc` : crée la documentation Java dans `doc/javadoc/`

## Tests unitaires

Les tests unitaires sont disponibles dans le dossier `src/test/`. Ils peuvent être exécutés grace à JUnit.