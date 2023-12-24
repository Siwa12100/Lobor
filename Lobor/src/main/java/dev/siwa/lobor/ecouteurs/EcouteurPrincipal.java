package dev.siwa.lobor.ecouteurs;

import dev.siwa.lobor.affichage.AfficheurDebug;
import dev.siwa.lobor.modele.boutons.BoutonManagerSelleV1;
import dev.siwa.lobor.modele.boutons.IBoutonManager;
import dev.siwa.lobor.modele.invocateurs.InvocateurClassique;
import dev.siwa.lobor.modele.montures.MonturesManager;
import net.kyori.adventure.title.Title;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class EcouteurPrincipal implements Listener {

    protected static int delaisEntreClicksObligatoire = 1;
    protected MonturesManager manager;
    protected InvocateurClassique invocateur;

    protected List<IBoutonManager> lBoutonsManagers;

    protected Timestamp dernierClickDroit;

    public EcouteurPrincipal(MonturesManager manager, InvocateurClassique invocateur, List<IBoutonManager> lBoutonsManagers) {
        this.manager = manager;
        this.invocateur = invocateur;
        this.dernierClickDroit = null;
        this.lBoutonsManagers = lBoutonsManagers;
    }

    @EventHandler
    public void onMount(VehicleEnterEvent event) {
        if (event.getEntered() instanceof Player && event.getVehicle() instanceof Horse) {
            Player joueur = (Player) event.getEntered();
            if (manager.possedeCheval(joueur)) {
                manager.getMonture(joueur).rendreMobile();
                //AfficheurDebug.afficherMessage(joueur, " vous gripper sur votre monture, levee du frein a main !");
            }

        }
    }

    @EventHandler
    public void onDismount(org.bukkit.event.vehicle.VehicleExitEvent event) {
        if (event.getVehicle() instanceof Horse && event.getExited() instanceof Player) {
            Player joueur = (Player) event.getExited();
            if (manager.possedeCheval(joueur)) {
                manager.getMonture(joueur).immobiliser();
                //AfficheurDebug.afficherMessage(joueur, "Vous descendez de la monture, frein a main active !");
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        //AfficheurDebug.afficherMessage("Entree dans OnInteract, action : " + event.getAction());
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player joueur = event.getPlayer();
            ItemStack itemCourant = event.getItem();

            for (IBoutonManager bouton : this.lBoutonsManagers) {
                if (bouton.isBouton(itemCourant)) {

                    if (isDelaisSuffisant()) {
                        bouton.interagirAvecBouton(joueur, this.manager, this.invocateur);
                    }
                    return;
                }
            }
        }
    }

    protected boolean isDelaisSuffisant() {
        Timestamp tempsActuel = Timestamp.from(Instant.now());

        if (this.dernierClickDroit == null) {
            this.dernierClickDroit = tempsActuel;
            return true;
        }
        int tempsEntreClicksDroits = this.differenceSecondes(this.dernierClickDroit, tempsActuel);

//        AfficheurDebug.afficherMessage("Dernier click il y a : " + this.dernierClickDroit.getSeconds());
//        AfficheurDebug.afficherMessage("Nouveau click il y a : " + tempsActuel.getSeconds());
//        AfficheurDebug.afficherMessage("Intervalle en secondes " + tempsEntreClicksDroits);

        if (tempsEntreClicksDroits < EcouteurPrincipal.delaisEntreClicksObligatoire) {
            // On envoie potentiellement un msg à l'utilisateur...
            //...
            // On update la date du dernier click droit :
            this.dernierClickDroit = dernierClickDroit;
            return false;
        }
        this.dernierClickDroit = tempsActuel;
        return true;
    }

    protected int differenceSecondes(Timestamp ancienTemps, Timestamp nouveauTemps) {
        long difference = nouveauTemps.getTime() - ancienTemps.getTime();
        return (int) (difference / 1000);
    }

    @EventHandler
    public void onVehiculeExit(VehicleExitEvent event) {
        if (event.getExited() instanceof Player) {
            Player joueur = (Player) event.getExited();
            for (IBoutonManager b : this.lBoutonsManagers) {
                if (b instanceof BoutonManagerSelleV1) {
                    ((BoutonManagerSelleV1) b).supprimerPotentielCheval(joueur, this.manager);
                }
            }
        }
    }
}
