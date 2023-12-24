package dev.siwa.lobor.modele.boutons;

import dev.siwa.lobor.modele.invocateurs.IInvocateur;
import dev.siwa.lobor.modele.montures.MonturesManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface IBoutonManager {
    void getBouton(Player joueur);
    void removeBouton(Player joueur);
    void interagirAvecBouton(Player joueur, MonturesManager manager, IInvocateur invocateur);
    boolean isBouton(ItemStack itemCourant);
}
