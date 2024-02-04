# Syntaxe des fichiers de configuration des automates

Ces fichiers sont au format JSON. Ils contiennent les informations nécessaires à la création d'un automate.
Ils sont rangés dans le dossier `data/configs/`.


## I. Dimension

La chaîne de caractères `dimension` permet de choisir la dimension de l'espace dans lequel se déroule l'automate. Pour des raisons de simplicité d'affichage, on ne peut choisir que entre trois choix :
- "1D" : grille, de cellules carrées, à une dimension
- "2D" : grille, de cellules carrées, à deux dimensions
- "3D" : grille, de cellules carrées, à trois dimensions
- "H" : grille, de cellules hexagonales, à deux dimensions

L'entier naturel `size` permet de choisir la taille de la grille. Il est utilisé différemment selon la dimension :
- "1D" : le nombre de cellules.
- "2D" : le nombre de cellules par ligne et par colonne.
- "H" : le nombre de cellules par côté.


## II. Alphabet

Le tableau de chaîne de caractères `alphabet` permet de choisir les états possibles des cellules.
*ex : \["0", "1"]*
Le premier état est l'état initial des cellules.

> Attention !
> Le caractère `' '` (espace) ne peut pas être utilisé comme état.


## III. Voisinage

L'attribut `neighbourhood` représente le voisinage de chaque cellule. Son contenu dépend de la dimension :
- "1D" : un tableau d'entier représentant les coordonnées relatives des voisins.
    *ex : [-1, 1]*
- "2D" : un tableau de couples d'entiers représentant les coordonnées relatives des voisins.
    *ex : \[[0, -1], [1, -1], [1, 0], [1, 1], [0, 1], [-1, 1], [-1, 0], [-1, -1]]*
- "H" : un tableau de couples d'entiers représentant les coordonnées relatives des voisins.
    *ex : \[[-1, -1], [-1, 0], [0, -1], [0, 1], [1, -1], [1, 0]]*


## IV. Règle

L'attribut `rule` est un objet qui contient :
- un entier `type` indiquant si la règle est de type voisinnage (1), transition (2) ou de type vent (4)
- un tableau de règles `rules` qui dépend du type de règle choisi

### Règles de type voisinage

Si `type` est 1, `rules` est un tableau d'objets qui contiennent :
- une chaîne de caractères `state` : l'état de la cellule
- un tableau `neighbours` de chaînes de caractères : les états des voisins
- une chaîne de caractères `result` : l'état de la cellule après la transition

*ex : {"state": "0", "neighbours": \["1", "1", "0"], "result": "1"}*

### Règles de type transition

Si `type` est 2, `rules` est un tableau d'objets qui contiennent :
- une chaîne de caractères `state` : l'état de la cellule
- un tableau d'entiers `neighbours` : les différents nombres de voisins possibles
- une chaîne de caractères `neighbourState` : l'état des voisins
- une chaîne de caractères `result` : l'état de la cellule après la transition

Il peut contenir un entier `probability` : la  probabilité de la transition.
*ex : {"state": "0", "neighbours": \[1, 2], "neighbourState": "1","probability": 0.1, "result": "1"}*

### Règles de type Vent

Si `type` est 4, `rules` est un tableau d'objets qui contiennent :
- une chaîne de caractères `state` : l'état de la cellule
- un tableau d'entiers `neighbours` : les différents nombres de voisins possibles
- une chaîne de caractères `neighbourState` : l'état des voisins
- une chaîne de caractères `result` : l'état de la cellule après la transition

Il peut contenir un entier `probability` : la  probabilité de la transition.
Il peut contenir un tableau de double `wind` : la  force du vent pour chaque voisin

*ex : {"state": "0", "neighbours": \[1, 2], "neighbourState": "1","probability": 0.1, "result": "1","vent": [0, 0.1]}*