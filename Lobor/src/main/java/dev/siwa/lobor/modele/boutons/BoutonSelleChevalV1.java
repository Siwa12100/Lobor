package dev.siwa.lobor.modele.boutons;

import dev.siwa.lobor.modele.invocateurs.IInvocateur;
import dev.siwa.lobor.modele.montures.Monture;
import dev.siwa.lobor.modele.montures.MonturesManager;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class BoutonSelleChevalV1 implements IBouton {

    protected static String nomBouton = "monture";
    protected static BoutonSelleChevalV1 instance;
    protected List<Monture> monturesEnCoursUtilisation;

    protected final TypesBouton typeBouton;

    protected BoutonSelleChevalV1() {
        this.monturesEnCoursUtilisation = new ArrayList<>();
        this.typeBouton = TypesBouton.selleChevalV1;
    }

    public static BoutonSelleChevalV1 newBoutonManagerV1() {
        if (BoutonSelleChevalV1.instance == null) {
            BoutonSelleChevalV1.instance = new BoutonSelleChevalV1();
        }
        return BoutonSelleChevalV1.instance;
    }

    public void getBouton(Player joueur) {
        if (isBoutonSelleAbsent(joueur)) {
            joueur.getInventory().addItem(getItem(Material.SADDLE, ChatColor.GOLD + BoutonSelleChevalV1.nomBouton));
        }

        System.out.println("On donne une selle cheval V1 a " + joueur.getName());
    }

    public void removeBouton(Player joueur) {

        System.out.println("Passage dans le removeBouton pour le joueur " + joueur.getName());
        ItemStack item = getItem(Material.SADDLE, ChatColor.GOLD + BoutonSelleChevalV1.nomBouton);
        ItemStack[] itemsInInventory = joueur.getInventory().getContents();

        for (int i = 0; i < itemsInInventory.length; i++) {
            ItemStack currentItem = itemsInInventory[i];
            if (currentItem != null && currentItem.isSimilar(item)) {
                joueur.getInventory().setItem(i, null);

                System.out.println("La selle de type cheval V1 a bien ete supprimee au joueur " + joueur.getName());
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
        ItemStack item = getItem(Material.SADDLE, ChatColor.GOLD + BoutonSelleChevalV1.nomBouton);
        ItemStack[] itemsInInventory = joueur.getInventory().getContents();

        for (ItemStack currentItem : itemsInInventory) {
            if (currentItem != null && currentItem.isSimilar(item)) {
                return false;
            }
        }
        return true;
    }

    public boolean isBouton(ItemStack itemCourant) {
        ItemStack itemSelle = getItem(Material.SADDLE, ChatColor.GOLD + BoutonSelleChevalV1.nomBouton);
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

    public TypesBouton getTypeBouton() {
        return  this.typeBouton;
    }
}
