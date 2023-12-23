package dev.siwa.lobor.affichage;

import org.bukkit.entity.Player;

public class AfficheurTchat {

    protected static String enTeteMsg = "[Lobor info] : ";
    public static void afficherMessage(Player p, String msg) {
        p.sendMessage(AfficheurTchat.enTeteMsg + msg);
    }
}
