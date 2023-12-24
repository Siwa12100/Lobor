package dev.siwa.lobor.ecouteurs;

import dev.siwa.lobor.affichage.AfficheurDebug;
import dev.siwa.lobor.modele.boutons.BoutonSelle;
import dev.siwa.lobor.modele.invocateurs.InvocateurClassique;
import dev.siwa.lobor.modele.montures.MonturesManager;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.vehicle.VehicleEnterEvent;
import org.bukkit.inventory.ItemStack;

import java.net.http.WebSocket;
import java.sql.Timestamp;
import java.time.Instant;

public class EcouteurPrincipal implements Listener {

    protected MonturesManager manager;
    protected InvocateurClassique invocateur;

    protected Timestamp dernierClickDroit;

    public EcouteurPrincipal(MonturesManager manager, InvocateurClassique invocateur) {
        this.manager = manager;
        this.invocateur = invocateur;
        this.dernierClickDroit = null;
    }

    @EventHandler
    public void onMount(VehicleEnterEvent event) {
        if (event.getEntered() instanceof Player && event.getVehicle() instanceof Horse) {
            Player joueur = (Player) event.getEntered();
            if (manager.possedeCheval(joueur)) {
                manager.getMonture(joueur).rendreMobile();
                AfficheurDebug.afficherMessage(joueur, " vous gripper sur votre monture, levee du frein a main !");
            }

        }
    }

    @EventHandler
    public void onDismount(org.bukkit.event.vehicle.VehicleExitEvent event) {
        if (event.getVehicle() instanceof Horse && event.getExited() instanceof Player) {
            Player joueur = (Player) event.getExited();
            if (manager.possedeCheval(joueur)) {
                manager.getMonture(joueur).immobiliser();
                AfficheurDebug.afficherMessage(joueur, "Vous descendez de la monture, frein a main active !");
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        AfficheurDebug.afficherMessage("entree dans un OnInteract action : " + event.getAction());
        // On commence par regarder si le gars fait bien un click droit
        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Player joueur = event.getPlayer();
            AfficheurDebug.afficherMessage(joueur, "Il s'agit bien d'un click droit : " + event.getAction() );



            AfficheurDebug.afficherMessage("Nouveau dernierClick droit : " + dernierClickDroit);

            ItemStack itemCourant = event.getItem();

            // Ensuite on regarde si l'item courant est bien une selle custom souhaitée
            if (BoutonSelle.isBontonSelle(itemCourant)) {
                AfficheurDebug.afficherMessage("Le mec a bien une selle custom dans l'inventaire");
                // Si oui, on interagie avec le bouton

                int differenceNecessaire = 1;
                Timestamp nouveauClickDroit = Timestamp.from(Instant.now());
                if (this.dernierClickDroit != null) {
                    int differenceSecondes = nouveauClickDroit.getSeconds() - this.dernierClickDroit.getSeconds();
                    if (differenceSecondes < differenceNecessaire) {
                        AfficheurDebug.afficherMessage("le difference de scondes trop faible " + differenceSecondes);
                        this.dernierClickDroit = nouveauClickDroit;
                        return;
                    }
                }

                this.dernierClickDroit = nouveauClickDroit;

                BoutonSelle.interagirAvecBouton(joueur, this.manager, this.invocateur);
            }
        }
    }




}
