package dev.siwa.lobor.ecouteurs;

import dev.siwa.lobor.affichage.AfficheurDebug;
import dev.siwa.lobor.modele.invocateurs.InvocateurClassique;
import dev.siwa.lobor.modele.montures.MonturesManager;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.vehicle.VehicleEnterEvent;

import java.net.http.WebSocket;

public class EcouteurPrincipal implements Listener {

    protected MonturesManager manager;
    protected InvocateurClassique invocateur;

    public EcouteurPrincipal(MonturesManager manager, InvocateurClassique invocateur) {
        this.manager = manager;
        this.invocateur = invocateur;
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




}
