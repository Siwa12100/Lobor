package dev.siwa.lobor;

import dev.siwa.lobor.ecouteurs.EcouteurJoueurs;
import dev.siwa.lobor.ecouteurs.EcouteurPrincipal;
import dev.siwa.lobor.modele.boutons.BoutonManagerSelleV1;
import dev.siwa.lobor.modele.boutons.IBoutonManager;
import dev.siwa.lobor.modele.invocateurs.IInvocateur;
import dev.siwa.lobor.modele.invocateurs.InvocateurClassique;
import dev.siwa.lobor.modele.montures.MonturesManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import dev.siwa.lobor.commandes.CommandesManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import dev.siwa.lobor.ecouteurs.EcouteurPrincipal;
import org.w3c.dom.ls.LSInput;

public final class Lobor extends JavaPlugin {

    public static Lobor instance;
    protected MonturesManager monturesManager;
    protected List<IBoutonManager> lBoutonsManager;
    protected IInvocateur invocateur;

    @Override
    public void onEnable() {
        Lobor.instance = this;
        this.monturesManager = new MonturesManager(false);
        this.lBoutonsManager = this.initialisationListeBoutonsManager();
        this.invocateur = new InvocateurClassique(monturesManager);

        Objects.requireNonNull(getCommand("lobor")).setExecutor(CommandesManager.newCommandesManager(true, monturesManager));
        Bukkit.getServer().getPluginManager().registerEvents(new EcouteurPrincipal(monturesManager, this.invocateur, lBoutonsManager), this);
        Bukkit.getServer().getPluginManager().registerEvents(new EcouteurJoueurs(this.lBoutonsManager.get(0), this.monturesManager), this);
    }

    protected List<IBoutonManager> initialisationListeBoutonsManager() {
        List<IBoutonManager> nvListe = new ArrayList<>();
        nvListe.add(BoutonManagerSelleV1.newBoutonManagerV1());
        return nvListe;
    }

    @Override
    public void onDisable() {
        // On s'assure de supprimer toutes les potentielles montures restantes
        this.monturesManager.supprimerToutesMontures();
    }
}
