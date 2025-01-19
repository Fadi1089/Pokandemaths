import extensions.File;
import extensions.CSVFile;

class Pokandemaths extends Program {

    final String POKEDEX = "Pokedex.csv";

    // algorithm => lancer le mode de jeu // _algorithm => lancer les tests
    void _algorithm(){
        println("Poke&e-Maths : Entrez votre prénom");
        String joueur = readString();
        String saveFile = joueur + ".save";
        Inventaire inventaire = chargerOuCreerInventaire(saveFile);
        
        println("\nPoke&e-Maths : Bonjour " + joueur);

        boolean continuer = true;
        while (continuer) {
            println("\nPoke&e-Maths : Sélectionnez un mode de jeu : (1) Campagne ou (2) Ouverture classique");
            println("Vous pouvez aussi consulter votre Inventaire (3) ou vos Statistiques (4).");
            
            //choix d'action de l'utilisateur
            
            int choix = readInt();

            if (choix == 1) {
                //jouerCampagne(); => mode qui fait partie de nos fonctionnalités à realiser si nous avions plus de temps, nous allons l'ajouter pour la version finale.
            } else if (choix == 2) {
                ouvrirBoosters(saveFile, inventaire);
            }  else if (choix == 3) {
                afficherInventaire(inventaire);
            }  else if (choix == 4) {
                afficherStatistiques(inventaire);
            }  else {
                 println("Choix invalide. Veuillez réessayer.");
            }

            println("\nVoulez-vous revenir au menu principal ? (1: oui/2: non)");
            choix = readInt();
            if (choix == 1) {
                continuer = true;
            } else {
                continuer = false;
            }
        }
        println("\nPoke&e-Maths : Merci d'avoir joué ! À bientôt, " + joueur + " !");
    }

  Inventaire chargerOuCreerInventaire(String fileName) {
    String[] files = getAllFilesFromCurrentDirectory();
    for (int i = 0; i < files.length; i++) {
        if (files[i].equals(fileName)) {
            return chargerInventaire(fileName);
        }
    }
    return newInventaire();
}


    Inventaire chargerInventaire(String fileName) {
        File file = newFile(fileName);
        Inventaire inventaire = newInventaire();
        while (ready(file)) {
            String line = readLine(file);
            String[] parts = customSplit(line, ",");
            Pokemon pokemon = newPokemon(parts[0], "");
            Rarete rarete = newRarete(parts[1], parts[2], stringToInt(parts[3]));
            Carte carte = newCarte(pokemon, rarete);
            int nbPossession = stringToInt(parts[4]);
            ajouterCarteInventaire(inventaire, carte, nbPossession);
        }
        return inventaire;
    }

    

    void sauvegarderInventaire(String fileName, Inventaire inventaire) {
        String[][] content = new String[inventaire.slots.length][1];
        for (int i = 0; i < inventaire.slots.length; i++) {
            Slot slot = inventaire.slots[i];
            content[i][0] = slot.carte.pokemon.nom + "," + slot.carte.rarete.nom + "," + slot.carte.rarete.label + "," + slot.carte.rarete.difficulte + "," + slot.nbPossession;
        }
        saveCSV(content, fileName);

        println("\nPoke&e-Maths : Vos cartes ont bien été enregistrées.");
    }

void ouvrirBoosters(String filename, Inventaire inventaire) {
    
    println("\nPoke&e-Maths : Préparez-vous à ouvrir un booster de 10 cartes");
    Carte[] booster = genererBooster(2);

    for (int i = 0; i < booster.length; i++) {
        Carte carte = booster[i];
        println("\nCarte obtenue : " + carte.pokemon.nom + " (" + carte.rarete.nom + ")");
        Question question = genererQuestionAutomatique(carte.rarete);
        println("Question (Difficulté " + question.difficulte + ", Temps imparti : " + question.duree + "s) : " + question.texte);

        long startTime = System.currentTimeMillis();
        boolean answered = false;
        int reponseJoueur = 0;

        //nous n'arrivons pas à bien réaliser le système de gestion du temps via IJava, nous allons y reflechir davantage
        //et proposer une verison plus fonctionnel pour le rendu final.
        while (System.currentTimeMillis() - startTime < question.duree * 1000) {
            if (isInputAvailable()) {
                reponseJoueur = readInt();
                answered = true;
                break;
            }
            delay(100);
        }

        if (answered) {
            if (Math.abs(reponseJoueur - question.reponse) < 0.01) {
                println("Bonne réponse ! Vous obtenez la carte " + carte.pokemon.nom + " (" + carte.rarete.nom + ")");
                ajouterCarteInventaire(inventaire, carte, 1);
            } else {
                println("Mauvaise réponse. Vous ne recevez pas la carte " + carte.pokemon.nom + " (" + carte.rarete.nom + ")");
            }
        } else {
            println("Temps écoulé. Vous ne recevez pas la carte " + carte.pokemon.nom + " (" + carte.rarete.nom + ")");
        }
    }
    println("\nPoke&e-Maths : Fin de l'ouverture du booster.");

    //enregistrement des cartes
    sauvegarderInventaire(filename, inventaire);
    
}



void afficherInventaire(Inventaire inventaire) {
    println("\nPoke&e-Maths : Votre inventaire contient :");
    for (int i = 0; i < inventaire.slots.length; i++) {
        Slot slot = inventaire.slots[i];
        println("- " + slot.carte.pokemon.nom + " (" + slot.carte.rarete.nom + ") x" + slot.nbPossession);
    }
}


    void afficherStatistiques(Inventaire inventaire) {
        println("Poke&e-Maths : Statistiques en développement. Revenez bientôt !");
    }

  void ajouterCarteInventaire(Inventaire inventaire, Carte carte, int nbPossession) {
    for (int i = 0; i < inventaire.slots.length; i++) {
        Slot slot = inventaire.slots[i];
        if (slot.carte.pokemon.nom.equals(carte.pokemon.nom) && slot.carte.rarete.nom.equals(carte.rarete.nom)) {
            slot.nbPossession += nbPossession;
            return;
        }
    }
    Slot newSlot = new Slot();
    newSlot.carte = carte;
    newSlot.nbPossession = nbPossession;
    inventaire.slots = customAppend(inventaire.slots, newSlot);
}


    Inventaire newInventaire() {
        Inventaire inventaire = new Inventaire();
        inventaire.slots = new Slot[0];
        return inventaire;
    }

    Question genererQuestionSelonRarete(Rarete rarete) {
        if (rarete.difficulte == 1) {
            return newQuestion(1, 5, "2 + 3 = ?", 10);
        } else if (rarete.difficulte == 2) {
            return newQuestion(2, 40, "5 x 8 = ?", 15);
        } else {
            return newQuestion(3, 126, "42 * 3 = ?", 25);
        }
    }

 Question genererQuestionAutomatique(Rarete rarete) {
        int difficulte = rarete.difficulte;
        String texte;
        double reponse;

        if (difficulte == 1) {
            int a = (int) (random() * 10) + 1;
            int b = (int) (random() * 10) + 1;
            texte = a + " + " + b + " = ?";
            reponse = a + b;
            return newQuestion(difficulte, reponse, texte, 10);
        } else if (difficulte == 2) {
            int a = (int) (random() * 20) + 1;
            int b = (int) (random() * 10) + 1;
            texte = a + " x " + b + " = ?";
            reponse = a * b;
            return newQuestion(difficulte, reponse, texte, 15);
        } else {
            int a = (int) (random() * 50) + 1;
            int b = (int) (random() * 50) + 1;
            texte = a + " * " + b + " = ?";
            reponse = a * b;
            return newQuestion(difficulte, reponse, texte, 20);
        }
    }
    // --- Fonctions pour les types ---

    Pokemon newPokemon(String nom, String description){
        Pokemon pokemon = new Pokemon();
        pokemon.nom = nom;
        pokemon.description = description;
        return pokemon;
    }

    Rarete newRarete(String nom, String label, int difficulte){
        Rarete rarete = new Rarete();
        rarete.nom = nom;
        rarete.label = label;
        rarete.difficulte = difficulte;
        return rarete;
    }

    Carte newCarte(Pokemon pokemon, Rarete rarete){
        Carte carte = new Carte();
        carte.pokemon = pokemon;
        carte.rarete = rarete;
        return carte;
    }

    Question newQuestion(int difficulte, double reponse, String texte, int duree){
        Question question = new Question();
        question.difficulte = difficulte;
        question.reponse = reponse;
        question.texte = texte;
        question.duree = duree;
        return question;
    }

    // --- Fonctions de génération ---

    Pokemon getPokemonByID(int pokeID){
        CSVFile pokedex = loadCSV(POKEDEX);
        String nom = getCell(pokedex, pokeID - 1, 1);
        String description = getCell(pokedex, pokeID - 1, 2);
        return newPokemon(nom, description);
    }

    Pokemon genererPokemonAleatoire(){
        int rand = (int) (1 + random() * 10);
        return getPokemonByID(rand);
    }

    Carte[] genererBooster(int niveauDiff){
        Carte[] booster = new Carte[10];
        for (int i = 0; i < booster.length; i++) {
            Pokemon pokemon = genererPokemonAleatoire();
            Rarete rarete = genererRareteSelonDifficulte(niveauDiff);
            booster[i] = newCarte(pokemon, rarete);
        }
        return booster;
    }

   
    Rarete genererRareteSelonDifficulte(int difficulte){
        if (difficulte == 1) {
            return newRarete("Commun", "C", 1);
        } else if (difficulte == 2) {
            return newRarete("Rare", "R", 2);
        } else {
            return newRarete("Légendaire", "L", 3);
        }
    }

    boolean isInputAvailable() {
        // Vérifie si l'entrée utilisateur est disponible dans un environnement interactif.
        // Implémentation simplifiée ou à adapter selon le framework utilisé.
        return true; // Placeholder
    }

    Slot[] customAppend(Slot[] array, Slot element) {
        Slot[] newArray = new Slot[length(array) + 1];
        for (int i = 0; i < length(array); i++) {
            newArray[i] = array[i];
        }
        newArray[length(array)] = element;
        return newArray;
    }
    String[] customSplit(String line, String delimiter) {
        int count = 1;
        for (int i = 0; i < length(line); i++) {
            if (charAt(line, i) == charAt(delimiter, 0)) {
                count++;
            }
        }

        String[] parts = new String[count];
        int currentPart = 0;
        String currentString = "";

        for (int i = 0; i < length(line); i++) {
            if (charAt(line, i) == charAt(delimiter, 0)) {
                parts[currentPart] = currentString;
                currentString = "";
                currentPart++;
            } else {
                currentString += charAt(line, i);
            }
        }
        parts[currentPart] = currentString;

        return parts;
    }




     // --- Fonctions de test ---

    void testNewPokemon() {
        Pokemon p = newPokemon("Pikachu", "Un Pokémon électrique");
        assertEquals("Pikachu", p.nom);
        assertEquals("Un Pokémon électrique", p.description);
    }

    void testNewRarete() {
        Rarete r = newRarete("Rare", "R", 2);
        assertEquals("Rare", r.nom);
        assertEquals("R", r.label);
        assertEquals(2, r.difficulte);
    }

    void testNewCarte() {
        Pokemon p = newPokemon("Charizard", "Un Pokémon de feu");
        Rarete r = newRarete("Légendaire", "L", 3);
        Carte c = newCarte(p, r);
        assertEquals(p, c.pokemon);
        assertEquals(r, c.rarete);
    }

    void testGenererQuestionAutomatique() {
        Rarete r1 = newRarete("Commun", "C", 1);
        Question q1 = genererQuestionAutomatique(r1);
        assertEquals(1, q1.difficulte);
        assertTrue(q1.texte.contains("+"));

        Rarete r2 = newRarete("Rare", "R", 2);
        Question q2 = genererQuestionAutomatique(r2);
        assertEquals(2, q2.difficulte);
        assertTrue(q2.texte.contains("x"));

        Rarete r3 = newRarete("Légendaire", "L", 3);
        Question q3 = genererQuestionAutomatique(r3);
        assertEquals(3, q3.difficulte);
        assertTrue(q3.texte.contains("*"));
    }

    void testAjouterCarteInventaire() {
        Inventaire inv = newInventaire();
        Pokemon p = newPokemon("Bulbasaur", "Un Pokémon plante");
        Rarete r = newRarete("Commun", "C", 1);
        Carte c = newCarte(p, r);
        ajouterCarteInventaire(inv, c, 1);

        assertEquals(1, inv.slots.length);
        assertEquals(c, inv.slots[0].carte);
        assertEquals(1, inv.slots[0].nbPossession);

        ajouterCarteInventaire(inv, c, 2);
        assertEquals(1, inv.slots.length);
        assertEquals(3, inv.slots[0].nbPossession);

        Pokemon p2 = newPokemon("Squirtle", "Un Pokémon eau");
        Rarete r2 = newRarete("Rare", "R", 2);
        Carte c2 = newCarte(p2, r2);
        ajouterCarteInventaire(inv, c2, 1);

        assertEquals(2, inv.slots.length);
        assertEquals(c2, inv.slots[1].carte);
        assertEquals(1, inv.slots[1].nbPossession);
    }
}