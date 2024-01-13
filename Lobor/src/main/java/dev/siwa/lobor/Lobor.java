package dev.siwa.lobor;

import dev.siwa.lobor.config.LoborConfig;
import dev.siwa.lobor.ecouteurs.EcouteurJoueurs;
import dev.siwa.lobor.ecouteurs.EcouteurPrincipal;
import dev.siwa.lobor.modele.boutons.BoutonSelleChevalV1;
import dev.siwa.lobor.modele.boutons.IBouton;
import dev.siwa.lobor.modele.boutons.TypesBouton;
import dev.siwa.lobor.modele.invocateurs.IInvocateur;
import dev.siwa.lobor.modele.invocateurs.InvocateurClassique;
import dev.siwa.lobor.modele.montures.MonturesManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import dev.siwa.lobor.commandes.CommandesManager;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Lobor extends JavaPlugin {

    public static Lobor instance;
    protected MonturesManager monturesManager;
    protected List<IBouton> lBoutonsManager;
    protected IInvocateur invocateur;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        Lobor.instance = this;
        this.monturesManager = new MonturesManager(false);
        this.lBoutonsManager = this.initialisationListeBoutonsManager();
        this.invocateur = new InvocateurClassique(monturesManager);

        // On initialise la config et on l'affiche :
        //this.loadConfig();
        this.loadConfigFile();
        LoborConfig.chargerConfig(this.config);
        System.out.println(LoborConfig.getInstance());

        Objects.requireNonNull(getCommand("lobor")).setExecutor(CommandesManager.newCommandesManager(true, monturesManager));
        Bukkit.getServer().getPluginManager().registerEvents(new EcouteurPrincipal(monturesManager, this.invocateur, lBoutonsManager), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EcouteurJoueurs(this.lBoutonsManager.get(0), this.monturesManager), this);
    }

    protected List<IBouton> initialisationListeBoutonsManager() {
        List<IBouton> nvListe = new ArrayList<>();
        nvListe.add(BoutonSelleChevalV1.newBoutonManagerV1());
        return nvListe;
    }

    @Override
    public void onDisable() {
        // On s'assure de supprimer toutes les potentielles montures restantes
        this.monturesManager.supprimerToutesMontures();
        this.saveConfigFile();
    }

    private void loadConfigFile() {
        File configFile = new File(getDataFolder(), "config.yml");

        // Vérifie si le fichier de configuration existe, sinon le crée depuis les ressources
        if (!configFile.exists()) {
            getLogger().info("Le fichier de configuration n'existe pas, création depuis les ressources.");
            saveResource("config.yml", false);
        }

        // Charge le fichier de configuration
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    private void saveConfigFile() {
        File configFile = new File(getDataFolder(), "config.yml");

        // Sauvegarde la configuration dans le fichier
        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


//    private void loadConfig() {
//        if (!getDataFolder().exists()) {
//            getDataFolder().mkdirs();
//        }
//
//        File configFile = new File(getDataFolder(), "config.yml");
//
//        if (!configFile.exists()) {
//            saveResource("config.yml", false);
//        }
//
//        config = YamlConfiguration.loadConfiguration(configFile);
//
//        // Ajoutez des valeurs par défaut si elles n'existent pas
//        if (!config.contains("active")) {
//            config.set("active", true);
//        }
//
//        if (!config.contains("default_button_type")) {
//            config.set("default_button_type", "wooden_button");
//        }
//
//        if (!config.contains("plugin_enabled_worldwide")) {
//            config.set("plugin_enabled_worldwide", false);
//        }
//
//        if (!config.contains("worlds_configuration")) {
//            config.createSection("worlds_configuration");
//            config.set("worlds_configuration.world1", "stone_button");
//            config.set("worlds_configuration.world2", "i_button");
//            config.set("worlds_configuration.world3", "gold_button");
//        }
//
//        // Sauvegarde la configuration après ajout des valeurs par défaut
//        saveConfig();
//    }
}
