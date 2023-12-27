package dev.siwa.lobor.commandes.debug;

import dev.siwa.lobor.modele.boutons.BoutonSelleChevalV1;
import dev.siwa.lobor.modele.montures.MontureCheval;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandesDebugManager implements CommandExecutor {

    private MontureCheval monture;
    private BoutonSelleChevalV1 selleManagerv1;
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (args.length <= 1) {
            return false;
        }
        //AfficheurDebug.afficherMessage((Player)sender, "Passage dans le debug manager et args.length > 1");
        Player p = (Player) sender;
        this.selleManagerv1 = BoutonSelleChevalV1.newBoutonManagerV1();

        switch (args[1]) {
            case "creerCheval" :
                this.monture = new MontureCheval(p);
                break;

            case "supprimerCheval" :
                if (this.monture != null) {
                    this.monture.supprimer();
                }
                this.monture = null;
                break;

            case "immobiliserCheval" :
                if (this.monture != null) {
                    this.monture.immobiliser();
                }
                break;

            case "rendreChevalMobile" :
                if (this.monture != null) {
                    this.monture.rendreMobile();
                }
                break;

            case "faireMonterCheval" :
                if (this.monture != null) {
                    monture.faireMonterJoueur();
                }
                break;

            case "getBoutonSelle" :
                this.selleManagerv1.getBouton(p);
                break;

            case "removeBoutonSelle" :
                this.selleManagerv1.removeBouton(p);
                break;

            default:
                return false;
        }
        return true;
    }
}
