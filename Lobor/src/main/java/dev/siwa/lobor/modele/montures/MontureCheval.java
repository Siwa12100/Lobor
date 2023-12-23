package dev.siwa.lobor.modele.montures;

import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Horse;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class MontureCheval extends Monture {

    protected static double vitesseMaxChevaux = 0.3375;
    protected Horse cheval;

    public MontureCheval(Player nvProprio) {
        this.proprietaire = nvProprio;
        this.invoquerCheval();
    }

    protected static Horse newCheval(Location position, World monde) {

        Horse nvcheval = (Horse) monde.spawnEntity(position, EntityType.HORSE);
        return nvcheval;
    }

    protected void invoquerCheval() {
        this.cheval = newCheval(this.proprietaire.getLocation(), this.proprietaire.getWorld());
    }

    protected void supprimerCheval() {
        this.cheval.remove();
    }
    @Override
    public void immobiliser() {
        this.cheval.setVelocity(new Vector(0,0,0));
    }

    @Override
    public void rendreMobile() {
        this.setVitesseCheval(MontureCheval.vitesseMaxChevaux);
    }

    public void setVitesseCheval(double nouvelleVitesse) {
        this.cheval.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(nouvelleVitesse);
    }

    @Override
    public void faireMonterJoueur() {

    }
}
