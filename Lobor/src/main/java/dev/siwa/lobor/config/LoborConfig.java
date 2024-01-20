package dev.siwa.lobor.config;

import dev.siwa.lobor.Lobor;
import dev.siwa.lobor.affichage.AfficheurDebug;
import dev.siwa.lobor.modele.boutons.TypesBouton;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import javax.lang.model.element.TypeElement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoborConfig {

    public Map<String, TypesBouton> associationMondeBouton;
    public boolean LoborActif;
    public boolean LoborActifSurTousLesMondes;
    public TypesBouton typeDeBoutonParDefaut;
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

    public static synchronized LoborConfig getInstance() {
        if (instance == null) {
            instance = new LoborConfig();
            System.out.println("Passage dans la création d'une nouvelle instance");
        }

        return instance;
    }

    public static void chargerConfig(FileConfiguration config) {

        LoborConfig instance = LoborConfig.getInstance();

        System.out.println("Passage dans le chargement de la config");

        instance.LoborActif = config.getBoolean("active");

        System.out.println(" Est actif : " + config.getBoolean("active"));

        instance.LoborActifSurTousLesMondes = config.getBoolean("plugin_enabled_worldwide");

        System.out.println("config actif partout : " + config.getBoolean("plugin_enabled_worldwide"));

        instance.typeDeBoutonParDefaut = recupererTypeBoutonFromString(config.getString("default_button_type"));

        ConfigurationSection worldsConfig = config.getConfigurationSection("worlds_configuration");
        if (worldsConfig != null) {
            for (String world : worldsConfig.getKeys(false)) {
                TypesBouton bouton = recupererTypeBoutonFromString(worldsConfig.getString(world));
                instance.ajouterMondeAGerer(world, bouton);

                System.out.println("- Config chargement... : w : " + world + " bt : " + worldsConfig.getString(world));
            }
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
        return this.associationMondeBouton.containsKey(w.getName());
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
        System.out.println("Lobor Config : Ajout du monde " + w + " avec bt : " + b );
//        System.out.println(" Lobor config test de la map -> bt  : " + this.getTypeBoutonAssocie(w));
        System.out.println(" Lobor config test de la map -> bt  : " + LoborConfig.getInstance().getTypeBoutonAssocie(w));
    }

    public TypesBouton getTypeBoutonAssocie(World w) {
        TypesBouton t = null;
        t = this.associationMondeBouton.get(w.getName());
        return t;
    }

    public TypesBouton getTypeBoutonAssocie(String w) {
        TypesBouton t = null;
        t = this.associationMondeBouton.get(w);
        return t;
    }



    public String toString() {
        StringBuilder msgBuilder = new StringBuilder(
                " Config de Lobor : \n" +
                " ----------------- \n" +
                " - Lobor actif : " + this.isLoborActif() + "\n" +
                "- Actif sur tous les mondes : " + LoborConfig.getInstance().isLoborActifSurTousLesMondes() + "\n" +
                //" - Bouton par défaut : " + this.getTypeDeBoutonParDefaut().toString() + "\n" +
                " - Mondes pris en charge : " + "\n");

        for (Map.Entry<String, TypesBouton> e : this.associationMondeBouton.entrySet()) {
            msgBuilder.append("\t -> ").append(e.getKey()).append(" - ").append(e.getValue().toString()).append("\n");
        }

        return msgBuilder.toString();
    }

    private static TypesBouton recupererTypeBoutonFromString(String type) {

        switch (type) {

            case "chevalV1" :
                return TypesBouton.selleChevalV1;

            default:
                return TypesBouton.selleChevalV1;
        }
    }
}
