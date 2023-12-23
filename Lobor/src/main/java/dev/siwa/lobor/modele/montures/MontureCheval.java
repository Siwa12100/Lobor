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
        this.proprietaire = nvProprio;
        this.invoquerCheval();
        preparerCheval();
    }

    protected static Horse newCheval(Location position, World monde) {

        Horse nvcheval = (Horse) monde.spawnEntity(position, EntityType.HORSE);
        return nvcheval;
    }

    protected void invoquerCheval() {
        this.cheval = newCheval(this.proprietaire.getLocation(), this.proprietaire.getWorld());
    }

    public void supprimerCheval() {
        this.cheval.remove();
    }
    @Override
    public void immobiliser() {

        this.cheval.addPotionEffect(new PotionEffect(PotionEffectType.SLOW, Integer.MAX_VALUE, 255, false, false));
    }

    @Override
    public void rendreMobile() {

        PotionEffectType effetLenteur = PotionEffectType.SLOW;
        if (this.cheval.hasPotionEffect(effetLenteur)) {
            this.cheval.removePotionEffect(effetLenteur);
        }
    }

    public void setVitesseCheval(double nouvelleVitesse) {
        this.cheval.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(nouvelleVitesse);
    }

    protected void preparerCheval() {

        // si le cheval est encore sauvage, on le dresse
        if (!this.cheval.isTamed()) {
            this.cheval.setTamed(true);
        }
        // Si le chemin n'a pas de selle, on lui en met une
        if (this.cheval.getInventory().isEmpty()) {
            this.cheval.getInventory().setSaddle(new ItemStack(Material.SADDLE));
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
