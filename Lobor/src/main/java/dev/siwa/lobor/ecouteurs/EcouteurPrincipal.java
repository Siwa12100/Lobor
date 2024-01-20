package dev.siwa.lobor.ecouteurs;

import dev.siwa.lobor.modele.boutons.BoutonSelleChevalV1;
import dev.siwa.lobor.modele.boutons.IBouton;
import dev.siwa.lobor.modele.boutons.TypesBouton;
import dev.siwa.lobor.modele.invocateurs.IInvocateur;
import dev.siwa.lobor.modele.montures.MonturesManager;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.event.vehicle.VehicleExitEvent;
import org.bukkit.inventory.ItemStack;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

public class EcouteurPrincipal implements Listener {

    protected static int delaisEntreClicksObligatoire = 1;
    protected MonturesManager manager;
    protected IInvocateur invocateur;
    protected List<IBouton> lBoutonsManagers;
    protected Timestamp dernierClickDroit;

    public EcouteurPrincipal(MonturesManager manager, IInvocateur invocateur, List<IBouton> lBoutonsManagers) {
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
            }
        }
    }

    @EventHandler
    public void onDismount(org.bukkit.event.vehicle.VehicleExitEvent event) {

        if (event.getVehicle() instanceof Horse && event.getExited() instanceof Player) {
            Player joueur = (Player) event.getExited();

            if (manager.possedeCheval(joueur)) {
                manager.getMonture(joueur).immobiliser();
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player joueur = event.getPlayer();
            ItemStack itemCourant = event.getItem();

            for (IBouton bouton : this.lBoutonsManagers) {
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

        if (tempsEntreClicksDroits < EcouteurPrincipal.delaisEntreClicksObligatoire) {
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

            for (IBouton b : this.lBoutonsManagers) {
                if (b.getTypeBouton() == TypesBouton.selleChevalV1) {
                    ((BoutonSelleChevalV1) b).supprimerPotentielCheval(joueur, this.manager);
                }
            }
        }
    }
}
