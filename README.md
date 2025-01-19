PokAnd-eMaths
===========

Développé par Dimitri Kaimakliotis, Fadi Sultan 
Contacts : dimitri.kaimakliotis.etu@univ-lille.fr, fadi.sultan.etu@univ-lille.fr

# Présentation de Pokendymaths

**Pokendymaths** est un jeu éducatif et divertissant qui combine l'ouverture de boosters de cartes inspirées des Pokémon avec des défis mathématiques. À chaque carte obtenue, le joueur doit résoudre une question mathématique en un temps limité. Les cartes sont classées en fonction de leur rareté, et les questions varient en difficulté.

Des captures d'écran illustrant le fonctionnement du logiciel sont proposées dans le répertoire `shots`.

# Utilisation de Pokendymaths

Afin d'utiliser le projet, il suffit de taper les commandes suivantes dans un terminal :

./compile.sh
Permet la compilation des fichiers présents dans le répertoire `src` et la création des fichiers `.class` dans le répertoire `classes`.

./run.sh

Permet le lancement du jeu.

# Fonctionnalités principales

- **Ouverture de boosters** : Chaque booster contient 10 cartes générées aléatoirement selon un niveau de difficulté.
- **Défis mathématiques** : Les questions varient selon la rareté des cartes (opérations simples pour les cartes communes, multiplications complexes pour les légendaires).
- **Gestion de l'inventaire** : Les cartes obtenues sont automatiquement ajoutées à l'inventaire du joueur.
- **Test unitaire intégré** : Vérification automatique des principales fonctionnalités via des assertions.

# Dépendances

Le jeu utilise IJava pour fonctionner. Assurez-vous d'avoir l'environnement compatible installé.

# Structure du projet

- `src` : Contient le code source du projet.
- `classes` : Répertoire où sont générés les fichiers `.class` après compilation.
- `shots` : Captures d'écran et images illustratives.
- `compile.sh` : Script pour compiler le projet.
- `run.sh` : Script pour exécuter le jeu.

# Auteur

Ce projet a été conçu pour rendre les mathématiques amusantes tout en offrant une expérience de jeu engageante.
