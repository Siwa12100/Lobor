package dev.siwa.lobor.modele.montures;

import dev.siwa.lobor.affichage.AfficheurTchat;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class MonturesManager {
    protected boolean suppressionAutomatiqueMontures;
    protected Map<Player, Monture> couplesMonturesPlayersExistants;

    public MonturesManager(boolean suppAuto) {
        this.suppressionAutomatiqueMontures = suppAuto;
        this.couplesMonturesPlayersExistants = new HashMap<>();
    }

    public boolean isSuppressionAutoMonturesActive() {
        return this.suppressionAutomatiqueMontures;
    }

    public boolean creerMontureCheval(Player p) {
        if (this.couplesMonturesPlayersExistants.containsKey(p)) {
            AfficheurTchat.afficherMessage(p, " vous avez deja une monture.");
            return false;
        } else {
            MontureCheval nvMonture = new MontureCheval(p);
            this.couplesMonturesPlayersExistants.put(p, nvMonture);
            AfficheurTchat.afficherMessage(p, " monture invoquee !");
        }

        return true;
    }

    public void supprimerMontureCheval(Player p) {
        if (!this.couplesMonturesPlayersExistants.containsKey(p)) {
            AfficheurTchat.afficherMessage(p, " vous n'avez pas de monture actuellement.");
        } else {
            this.couplesMonturesPlayersExistants.get(p).supprimer();
            this.couplesMonturesPlayersExistants.remove(p);
            AfficheurTchat.afficherMessage(p, " votre monture a bien disparu.");
        }
    }

//    public void attribuerJoueurMonture(Monture m, Player p) {
//        if (m.getProprietaire() != null) {
//            AfficheurTchat.afficherMessage(p, " cette monture a deja un prioprietaire.");
//        } else {
//            m.setProprietaire(p);
//            this.couplesMonturesPlayersExistants.put(p, m);
//        }
//    }

    public Monture getMonture(Player p) {
        Monture m = null;
        m = this.couplesMonturesPlayersExistants.get(p);
        return m;
    }

    public boolean possedeCheval(Player p) {
        if (this.couplesMonturesPlayersExistants.containsKey(p)) {
            return true;
        }

        return false;
    }

}
