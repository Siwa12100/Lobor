package dev.siwa.lobor.ecouteurs;

import dev.siwa.lobor.modele.boutons.IBouton;
import dev.siwa.lobor.modele.montures.MonturesManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class EcouteurJoueurs implements Listener {

    protected IBouton boutonManager;
    protected MonturesManager monturesManager;
    public EcouteurJoueurs(IBouton boutonManager, MonturesManager monturesManager) {
        this.boutonManager = boutonManager;
        this.monturesManager = monturesManager;
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player joueur = event.getPlayer();
        this.boutonManager.getBouton(joueur);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player joueur = event.getPlayer();
        this.boutonManager.removeBouton(joueur);
        if (monturesManager.possedeCheval(joueur)) {
            this.monturesManager.supprimerMontureCheval(joueur);
        }
    }
}
