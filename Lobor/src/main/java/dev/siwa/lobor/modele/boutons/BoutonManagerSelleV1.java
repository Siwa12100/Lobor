package dev.siwa.lobor.modele.boutons;

import dev.siwa.lobor.modele.invocateurs.IInvocateur;
import dev.siwa.lobor.modele.montures.Monture;
import dev.siwa.lobor.modele.montures.MontureCheval;
import dev.siwa.lobor.modele.montures.MonturesManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BoutonManagerSelleV1 implements IBoutonManager{

    protected static String nomBouton = "monture";
    protected static BoutonManagerSelleV1 instance;
    protected List<Monture> monturesEnCoursUtilisation;

    protected BoutonManagerSelleV1() {
        this.monturesEnCoursUtilisation = new ArrayList<>();
    }

    public static BoutonManagerSelleV1 newBoutonManagerV1() {
        if (BoutonManagerSelleV1.instance == null) {
            BoutonManagerSelleV1.instance = new BoutonManagerSelleV1();
        }
        return BoutonManagerSelleV1.instance;
    }

    public void getBouton(Player joueur) {
        if (isBoutonSelleAbsent(joueur)) {
            joueur.getInventory().addItem(getItem(Material.SADDLE, ChatColor.GOLD + BoutonManagerSelleV1.nomBouton));
        }
    }

    public void removeBouton(Player joueur) {
        ItemStack item = getItem(Material.SADDLE, ChatColor.GOLD + BoutonManagerSelleV1.nomBouton);
        ItemStack[] itemsInInventory = joueur.getInventory().getContents();

        for (int i = 0; i < itemsInInventory.length; i++) {
            ItemStack currentItem = itemsInInventory[i];
            if (currentItem != null && currentItem.isSimilar(item)) {
                joueur.getInventory().setItem(i, null);
                return;
            }
        }
    }

    protected static ItemStack getItem(Material material, String customName) {
        ItemStack it = new ItemStack(material, 1);
        ItemMeta itM = it.getItemMeta();
        if (customName != null) itM.setDisplayName(customName);
        it.setItemMeta(itM);
        return it;
    }

    protected static boolean isBoutonSelleAbsent(Player joueur) {
        ItemStack item = getItem(Material.SADDLE, ChatColor.GOLD + BoutonManagerSelleV1.nomBouton);
        ItemStack[] itemsInInventory = joueur.getInventory().getContents();

        for (ItemStack currentItem : itemsInInventory) {
            if (currentItem != null && currentItem.isSimilar(item)) {
                return false;
            }
        }
        return true;
    }

    public boolean isBouton(ItemStack itemCourant) {
        ItemStack itemSelle = getItem(Material.SADDLE, ChatColor.GOLD + BoutonManagerSelleV1.nomBouton);
        return itemSelle.isSimilar(itemCourant);
    }

    public void interagirAvecBouton(Player joueur, MonturesManager manager, IInvocateur invocateur) {
        if (manager.possedeCheval(joueur)) {
                invocateur.removeCheval(joueur);
        } else {
                invocateur.getCheval(joueur);
                this.monturesEnCoursUtilisation.add(manager.getMonture(joueur));
        }
    }

    public void supprimerPotentielCheval(Player joueur, MonturesManager manager) {
        for (Monture m : this.monturesEnCoursUtilisation) {
            if (m.getProprietaire().equals(joueur)) {
                manager.supprimerMontureCheval(joueur);
            }
        }
    }
}
