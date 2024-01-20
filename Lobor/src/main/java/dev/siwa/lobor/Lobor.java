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

public class Lobor extends JavaPlugin {

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
        this.monturesManager.supprimerToutesMontures();
        this.saveConfigFile();
    }

    private void loadConfigFile() {
        File configFile = new File(getDataFolder(), "config.yml");

        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }

        config = YamlConfiguration.loadConfiguration(configFile);
    }

    private void saveConfigFile() {
        File configFile = new File(getDataFolder(), "config.yml");

        try {
            config.save(configFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}