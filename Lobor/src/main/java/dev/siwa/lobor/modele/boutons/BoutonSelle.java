package dev.siwa.lobor.modele.boutons;

import dev.siwa.lobor.affichage.AfficheurDebug;
import org.bukkit.ChatColor;
import org.bukkit.Material;
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
        if (!isBoutonSelleAbsent(joueur)) {
            for (ItemStack item : joueur.getInventory().getContents()) {
                if (item != null && item.getType() == Material.SADDLE) {

                    ItemMeta donneesItem = item.getItemMeta();
                    if (donneesItem != null && donneesItem.hasDisplayName()
                            && Objects.equals(donneesItem.displayName(), BoutonSelle.nomBouton)) {
                        joueur.getInventory().remove(item);

                        AfficheurDebug.afficherMessage(joueur, "Passage apres le remove...");
                    }
                }
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

        for (ItemStack item : joueur.getInventory().getContents()) {
            if (item != null && item.getType() == Material.SADDLE) {

                ItemMeta donneesItem = item.getItemMeta();
                if (donneesItem != null && donneesItem.hasDisplayName()
                        && Objects.requireNonNull(donneesItem.displayName()).toString().equals(BoutonSelle.nomBouton)) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isBoutonSelleAbsentVerif(Player p) {
        return !BoutonSelle.isBoutonSelleAbsent(p);
    }

}
