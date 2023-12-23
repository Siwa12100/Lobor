package dev.siwa.lobor.commandes.debug;

import org.bukkit.entity.Player;

public class AfficheurDebug {

    public static boolean actif;

    public static void activer(){
        AfficheurDebug.actif = true;
    }

    public static void desactiver() {
        AfficheurDebug.actif = false;
    }

    public static boolean isActif() {
        return AfficheurDebug.actif;
    }

    public static void afficherMessage(Player p, String msg) {
        if (AfficheurDebug.isActif()) {
            String msgFinal = "[Lobor debug] : " + msg;
            p.sendMessage(msgFinal);
        }
    }
}
