package daruma.daruma;


import org.bukkit.ChatColor;

import static org.bukkit.Bukkit.getServer;

class Turn {
    void turn(int turn){//ターンの処理
        Daruma.turn=turn;
        getServer().broadcastMessage(ChatColor.WHITE+"ターン数を"+ChatColor.RED+turn+ChatColor.WHITE+"に設定しました");
        System.out.println("ターン数が"+turn+"に設定されました");
    }
}
