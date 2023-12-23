package dev.siwa.lobor.commandes.debug;

import org.bukkit.entity.Player;

public class afficheurDebug {

    public static boolean actif;

    public static void activer(){
        afficheurDebug.actif = true;
    }

    public static void desactiver() {
        afficheurDebug.actif = false;
    }

    public static boolean isActif() {
        return afficheurDebug.actif;
    }

    public static void afficherMessage(Player p, String msg) {
        if (afficheurDebug.isActif()) {
            String msgFinal = "[Lobor debug] : " + msg;
            p.sendMessage(msgFinal);
        }
    }
}
