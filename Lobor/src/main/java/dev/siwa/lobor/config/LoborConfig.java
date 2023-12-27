package dev.siwa.lobor.config;

import dev.siwa.lobor.Lobor;
import dev.siwa.lobor.modele.boutons.TypesBouton;
import org.bukkit.World;

import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoborConfig {

    protected Map<String, TypesBouton> associationMondeBouton;
    protected boolean LoborActif;
    protected boolean LoborActifSurTousLesMondes;
    protected TypesBouton typeDeBoutonParDefaut;

    protected static LoborConfig instance;

    protected LoborConfig(boolean isLoborActif, boolean isLoborActifSurTousLesMondes, TypesBouton typeDeBoutonParDefaut) {
        this.associationMondeBouton = new HashMap<>();
        this.LoborActif = isLoborActif;
        this.LoborActifSurTousLesMondes = isLoborActifSurTousLesMondes;
        this.typeDeBoutonParDefaut = typeDeBoutonParDefaut;
    }

    protected LoborConfig(boolean LoborActif, boolean loborActifSurTousLesMondes) {
        this.associationMondeBouton = new HashMap<>();
        this.LoborActif = LoborActif;
        this.LoborActifSurTousLesMondes = loborActifSurTousLesMondes;

        // Valeur par défaut du bouton :
        this.typeDeBoutonParDefaut = TypesBouton.selleChevalV1;
    }

    protected LoborConfig() {
        this.associationMondeBouton = new HashMap<>();
        this.LoborActif = false;
        this.LoborActifSurTousLesMondes = false;
    }

    public static LoborConfig getInstance() {
        LoborConfig temp = LoborConfig.instance;
        if (temp == null) {
            temp = new LoborConfig();
        }
        return  temp;
    }

    public static void chargerConfig(boolean isLoborActif, boolean isLoborActifSurTousLesMondes, TypesBouton typeDeBoutonParDefaut) {
        if (LoborConfig.instance == null) {
            LoborConfig.instance = new LoborConfig(isLoborActif, isLoborActifSurTousLesMondes, typeDeBoutonParDefaut);
        }
    }

    public boolean isLoborActif() {
        return this.LoborActif;
    }

    public boolean isLoborActifSurTousLesMondes() {
        return LoborActifSurTousLesMondes;
    }

    public TypesBouton getTypeDeBoutonParDefaut() {
        return this.typeDeBoutonParDefaut;
    }

    public boolean isWorldPrisEnCharge(World w) {
        return this.associationMondeBouton.containsKey(w);
    }

    public void ajouterMondeAGerer(World w, TypesBouton b) {
        this.associationMondeBouton.put(w.getName(), b);
    }

    public void ajouterMondeAGerer(World w) {
        this.associationMondeBouton.put(w.getName(), this.typeDeBoutonParDefaut);
    }

    public void ajouterMondeAGerer(String w) {
        this.associationMondeBouton.put(w, this.typeDeBoutonParDefaut);
    }

    public void ajouterMondeAGerer(String w, TypesBouton b) {
        this.associationMondeBouton.put(w, b);
    }

    public TypesBouton getTypeBoutonAssocie(World w) {
        TypesBouton t = null;
        t = this.associationMondeBouton.get(w);
        return t;
    }

    public String toString() {
        StringBuilder msgBuilder = new StringBuilder(
                " Config de Lobor : \n" +
                " ----------------- \n" +
                " - Lobor actif : " + this.isLoborActif() + "\n" +
                " - Bouton par défaut : " + this.getTypeDeBoutonParDefaut() + "\n" +
                " - Mondes pris en charge : " + "\n");

        for (Map.Entry<String, TypesBouton> e : this.associationMondeBouton.entrySet()) {
            msgBuilder.append("\t -> ").append(e.getKey()).append(" - ").append(e.getValue().toString()).append("\n");
        }

        return msgBuilder.toString();
    }
}
