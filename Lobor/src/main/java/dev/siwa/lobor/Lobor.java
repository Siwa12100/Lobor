package dev.siwa.lobor;

import dev.siwa.lobor.ecouteurs.EcouteurPrincipal;
import dev.siwa.lobor.modele.boutons.BoutonManagerSelleV1;
import dev.siwa.lobor.modele.boutons.IBoutonManager;
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

    @Override
    public void onEnable() {
        // Plugin startup logic
        //getCommand("lobor").setExecutor(new CommandesManager(true));
        MonturesManager monturesManager = new MonturesManager(false);
        InvocateurClassique invocateurClassique = new InvocateurClassique(monturesManager);

        Objects.requireNonNull(getCommand("lobor")).setExecutor(CommandesManager.newCommandesManager(true, monturesManager));

        List<IBoutonManager> lBoutonsManager = new ArrayList<>();
        lBoutonsManager.add(BoutonManagerSelleV1.newBoutonManagerV1());

        Bukkit.getServer().getPluginManager().registerEvents(new EcouteurPrincipal(monturesManager, invocateurClassique, lBoutonsManager), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
