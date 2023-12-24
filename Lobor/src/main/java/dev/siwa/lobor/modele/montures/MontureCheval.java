package dev.siwa.lobor.modele.montures;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.Vector;

public class MontureCheval extends Monture {

    protected static double vitesseMaxChevaux = 0.3375;
    protected Horse cheval;

    public MontureCheval(Player nvProprio) {
        super(nvProprio);
        this.invoquerCheval();
        preparerCheval();
    }

    protected static Horse newCheval(Location position, World monde) {
        return (Horse) monde.spawnEntity(position, EntityType.HORSE);
    }

    protected void invoquerCheval() {
        this.cheval = newCheval(this.proprietaire.getLocation(), this.proprietaire.getWorld());
    }

    public void supprimer() {
        this.cheval.remove();
    }

    @Override
    public void immobiliser() {
        this.cheval.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, false, false));
        this.mobile = false;
    }

    @Override
    public void rendreMobile() {
        PotionEffectType effetLenteur = PotionEffectType.SLOW;

        if (this.cheval.hasPotionEffect(effetLenteur)) {
            this.cheval.removePotionEffect(effetLenteur);
        }
        this.mobile = true;
    }

    public void setVitesseCheval(double nouvelleVitesse) {
        this.cheval.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(nouvelleVitesse);
    }

    protected void preparerCheval() {
        if (!this.cheval.isTamed()) {
            this.cheval.setTamed(true);
        }

        if (this.cheval.getInventory().isEmpty()) {
            this.cheval.getInventory().setSaddle(new ItemStack(Material.SADDLE));
        }

        // Me jugez pas svp ^^
        if (this.proprietaire.getPlayerProfile().getName().equals("_Siwa_")) {
            this.cheval.getInventory().setArmor(new ItemStack(Material.DIAMOND_HORSE_ARMOR));
        }

        this.setVitesseCheval(MontureCheval.vitesseMaxChevaux);
    }

    @Override
    public void faireMonterJoueur() {
        if (!this.proprietaire.isInsideVehicle()) {
            this.cheval.addPassenger(this.proprietaire);
        }
    }
}
