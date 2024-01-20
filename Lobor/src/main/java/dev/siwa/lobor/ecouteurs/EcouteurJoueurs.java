package dev.siwa.lobor.ecouteurs;

import dev.siwa.lobor.config.LoborConfig;
import dev.siwa.lobor.modele.boutons.IBouton;
import dev.siwa.lobor.modele.boutons.TypesBouton;
import dev.siwa.lobor.modele.montures.MonturesManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.lang.model.element.TypeElement;

public class EcouteurJoueurs implements Listener {

    protected IBouton boutonSelleChevalV1;
    protected MonturesManager monturesManager;

    public EcouteurJoueurs(IBouton boutonManager, MonturesManager monturesManager) {
        this.boutonSelleChevalV1 = boutonManager;
        this.monturesManager = monturesManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        if (LoborConfig.getInstance().isLoborActif()) {

            Player joueur = event.getPlayer();

            if (LoborConfig.getInstance().isLoborActifSurTousLesMondes()) {
                this.fournirBonBouton(LoborConfig.getInstance().getTypeDeBoutonParDefaut(), joueur);
            }

            if (!LoborConfig.getInstance().isLoborActifSurTousLesMondes()) {
                if (LoborConfig.getInstance().isWorldPrisEnCharge(joueur.getWorld())) {
                    this.fournirBonBouton(LoborConfig.getInstance().getTypeBoutonAssocie(joueur.getWorld()), joueur);
                }
            }
        }
    }

    public void fournirBonBouton(TypesBouton type, Player joueur) {
        this.getBonBonton(type).getBouton(joueur);
    }

    public IBouton getBonBonton(TypesBouton type) {

        switch (type) {

            case selleChevalV1 :
                return this.boutonSelleChevalV1;

            default:
                return this.boutonSelleChevalV1;
        }
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {

        Player player = event.getPlayer();
        World world = player.getWorld();

        if (LoborConfig.getInstance().isLoborActif()) {
            if (!LoborConfig.getInstance().isLoborActifSurTousLesMondes()) {
                if (LoborConfig.getInstance().isWorldPrisEnCharge(world)) {
                    this.fournirBonBouton(LoborConfig.getInstance().getTypeBoutonAssocie(world), player);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        World world = player.getWorld();

        if (LoborConfig.getInstance().isLoborActif()) {

            if (!LoborConfig.getInstance().isLoborActifSurTousLesMondes()) {

                this.getBonBonton(LoborConfig.getInstance().getTypeBoutonAssocie(world)).removeBouton(player);

            } else {

                this.getBonBonton(LoborConfig.getInstance().getTypeDeBoutonParDefaut()).removeBouton(player);
            }
        }

        if (monturesManager.possedeCheval(player)) {
            this.monturesManager.supprimerMontureCheval(player);
        }
    }
}
