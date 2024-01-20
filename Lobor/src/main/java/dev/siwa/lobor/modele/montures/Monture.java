package dev.siwa.lobor.modele.montures;

import org.bukkit.entity.Player;

public abstract class Monture {

    protected Player proprietaire;
    protected boolean mobile;

    protected Monture() {
        this.proprietaire = null;
    }

    protected Monture(Player p) {
        this.proprietaire = p;
    }

    public abstract void immobiliser();
    public abstract void supprimer();
    public abstract void rendreMobile();
    public abstract void faireMonterJoueur();

    public boolean isMobile() {
        return this.mobile;
    }

    public void setProprietaire(Player p) {
        this.proprietaire = p;
    }

    public Player getProprietaire() {
        return this.proprietaire;
    }
}