package dev.siwa.lobor.modele.boutons;

import dev.siwa.lobor.affichage.AfficheurDebug;
import dev.siwa.lobor.modele.invocateurs.IInvocateur;
import dev.siwa.lobor.modele.montures.MonturesManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Objects;

public class BoutonSelle {

    protected static String nomBouton = "monture";

    public static void getBoutonSelle(Player joueur) {
        if (isBoutonSelleAbsent(joueur)) {
            joueur.getInventory().addItem(getItem(Material.SADDLE, ChatColor.GOLD + BoutonSelle.nomBouton));
            AfficheurDebug.afficherMessage(joueur, " Item Selle bien get dans le if ...");
        }
    }

    public static void removeBoutonSelle(Player joueur) {
        ItemStack item = getItem(Material.SADDLE, ChatColor.GOLD + BoutonSelle.nomBouton);
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

    public static boolean isBoutonSelleAbsent(Player joueur) {

        ItemStack item = getItem(Material.SADDLE, ChatColor.GOLD + BoutonSelle.nomBouton);
        ItemStack[] itemsInInventory = joueur.getInventory().getContents();

        for (ItemStack currentItem : itemsInInventory) {
            if (currentItem != null && currentItem.isSimilar(item)) {
                // Le joueur possède déjà cet item
                return false;
            }
        }

        // Le joueur ne possède pas cet item
        return true;
    }

    public static boolean isBontonSelle(ItemStack itemAtester) {
        ItemStack itemSelle = getItem(Material.SADDLE, ChatColor.GOLD + BoutonSelle.nomBouton);
        AfficheurDebug.afficherMessage("Passage dans la verif de si l'item est une selleBouton");
        return itemSelle.isSimilar(itemAtester);
    }

    public static void interagirAvecBouton(Player joueur, MonturesManager manager, IInvocateur invocateur) {

        if (manager.possedeCheval(joueur)) {
                invocateur.removeCheval(joueur);
                AfficheurDebug.afficherMessage(joueur, "le mec avait bien un cheval, on lui supprime");
            } else {
                invocateur.getCheval(joueur);
                AfficheurDebug.afficherMessage(joueur, "le mec avait pas de cheval, on lui en donne un...");
            }

    }
}
