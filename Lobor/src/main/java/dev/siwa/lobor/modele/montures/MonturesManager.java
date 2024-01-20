package dev.siwa.lobor.modele.montures;

import dev.siwa.lobor.affichage.AfficheurTchat;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
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
            return false;

        } else {
            MontureCheval nvMonture = new MontureCheval(p);
            this.couplesMonturesPlayersExistants.put(p, nvMonture);
        }

        return true;
    }

    public void supprimerMontureCheval(Player p) {

        if (this.couplesMonturesPlayersExistants.containsKey(p)) {

            this.couplesMonturesPlayersExistants.get(p).supprimer();
            this.couplesMonturesPlayersExistants.remove(p);
        }
    }

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

    public void supprimerToutesMontures() {

        List<Monture> toutesLesMontures = couplesMonturesPlayersExistants.values().stream().toList();

        for (Monture m : toutesLesMontures) {
            m.supprimer();
            this.couplesMonturesPlayersExistants.remove(m.getProprietaire());
        }
    }
}
