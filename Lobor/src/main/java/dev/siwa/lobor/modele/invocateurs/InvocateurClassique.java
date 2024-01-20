package dev.siwa.lobor.modele.invocateurs;

import dev.siwa.lobor.modele.montures.MonturesManager;
import org.bukkit.entity.Player;

public class InvocateurClassique implements IInvocateur {

    protected MonturesManager manager;
    public InvocateurClassique(MonturesManager manager) {
        this.manager = manager;
    }

    @Override
    public void getCheval(Player p) {

        if (p != null) {
            boolean verif =  this.manager.creerMontureCheval(p);
            if (verif) {
                this.manager.getMonture(p).faireMonterJoueur();
            }
        }
    }

    @Override
    public void removeCheval(Player p) {
        if (p != null) {
            this.manager.supprimerMontureCheval(p);
        }
    }
}
