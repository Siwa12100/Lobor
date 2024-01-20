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
        System.out.println("Entree dans le on join de Lobor");
        if (LoborConfig.getInstance().isLoborActif()) {

            System.out.println("Le plugin est bien actif");
            Player joueur = event.getPlayer();
            System.out.println("Le joueur concerné est " + joueur.getName() + ".");

            System.out.println("Lobor actif sur tous les mondes : " + LoborConfig.getInstance().isLoborActifSurTousLesMondes());

            if (LoborConfig.getInstance().isLoborActifSurTousLesMondes()) {
                System.out.println("Lobor est actif sur tous les mondes, le type de bouton par défaut est " + LoborConfig.getInstance().getTypeDeBoutonParDefaut() + ".");
                this.fournirBonBouton(LoborConfig.getInstance().getTypeDeBoutonParDefaut(), joueur);
            }

            if (!LoborConfig.getInstance().isLoborActifSurTousLesMondes()) {

                System.out.println("Lobor n'est pas sur tous les mondes");

                if (LoborConfig.getInstance().isWorldPrisEnCharge(joueur.getWorld())) {

                    System.out.println("le monde " + joueur.getWorld().getName() + " est pris en charge !");
                    this.fournirBonBouton(LoborConfig.getInstance().getTypeBoutonAssocie(joueur.getWorld()), joueur);
                }
            }
        }

        System.out.println("Fin du onJoin");
    }

    public void fournirBonBouton(TypesBouton type, Player joueur) {
        System.out.println("On entre dans le fournir bouton.");
        this.getBonBonton(type).getBouton(joueur);
    }

    public IBouton getBonBonton(TypesBouton type) {

        switch (type) {

            case selleChevalV1 :
                System.out.println("Dans get bon bouton, on retourne un selle de type cheval V1");
                return this.boutonSelleChevalV1;

            default:
                System.out.println("Dans get bon bouton, on retourne un selle par defaut");
                return this.boutonSelleChevalV1;
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        System.out.println("Debut du onQuit");
        if (LoborConfig.getInstance().isLoborActif()) {

            System.out.println("Lobor est bien actif");
            Player joueur = event.getPlayer();
            this.getBonBonton(LoborConfig.getInstance().getTypeBoutonAssocie(joueur.getWorld())).removeBouton(joueur);

            if (monturesManager.possedeCheval(joueur)) {
                this.monturesManager.supprimerMontureCheval(joueur);
            }
        }

        System.out.println("Fin du onQuit");
    }

    @EventHandler
    public void onPlayerChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        World world = player.getWorld();

        System.out.println("Entre dans l'event OnPlayerChangeWorld");

        if (LoborConfig.getInstance().isLoborActif()) {

            System.out.println("Lobor est bien actif");
            if (!LoborConfig.getInstance().isLoborActifSurTousLesMondes()) {

                System.out.println("Lobor n'est pas sur tous les mondes");

                if (LoborConfig.getInstance().isWorldPrisEnCharge(world)) {

                    System.out.println("le monde " + player.getWorld().getName() + " est pris en charge !");
                    this.fournirBonBouton(LoborConfig.getInstance().getTypeBoutonAssocie(world), player);
                }
            }

        }

        System.out.println("Fin du OnPlayerChangeWorld");
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {

        Player player = event.getPlayer();
        World world = player.getWorld();

        System.out.println("Debut du OnPlayerQuit");

        if (LoborConfig.getInstance().isLoborActif()) {

            if (!LoborConfig.getInstance().isLoborActifSurTousLesMondes()) {

                System.out.println("Lobor pas actif partout");

                this.getBonBonton(LoborConfig.getInstance().getTypeBoutonAssocie(world)).removeBouton(player);
            }
        }
    }
}
