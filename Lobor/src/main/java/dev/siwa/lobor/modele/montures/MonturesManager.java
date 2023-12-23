package dev.siwa.lobor.modele.montures;

import dev.siwa.lobor.affichage.AfficheurTchat;
import org.bukkit.entity.Player;

import java.util.Map;

public class MonturesManager {
    protected boolean suppressionAutomatiqueMontures;
    protected Map<Player, Monture> monturesExistantes;

    public MonturesManager(boolean suppAuto) {
        this.suppressionAutomatiqueMontures = suppAuto;
    }

    public boolean isSuppressionAutoMonturesActive() {
        return this.suppressionAutomatiqueMontures;
    }

    public void creerMontureCheval(Player p) {
        if (this.monturesExistantes.containsKey(p)) {
            AfficheurTchat.afficherMessage(p, " vous avez deja une monture.");
        } else {
            MontureCheval nvMonture = new MontureCheval(p);
            this.monturesExistantes.put(p, nvMonture);
            AfficheurTchat.afficherMessage(p, " monture invoquee !");
        }
    }

    public void supprimerMontureCheval(Player p) {
        if (!this.monturesExistantes.containsKey(p)) {
            AfficheurTchat.afficherMessage(p, " vous n'avez pas de monture actuellement.");
        } else {
            this.monturesExistantes.get(p).supprimer();
            this.monturesExistantes.remove(p);
            AfficheurTchat.afficherMessage(p, " votre monture a bien disparu.");
        }
    }

    public void attribuerJoueurMonture(Monture m, Player p) {
        if (m.getProprietaire() != null) {
            // A finir...
        }
    }

}
