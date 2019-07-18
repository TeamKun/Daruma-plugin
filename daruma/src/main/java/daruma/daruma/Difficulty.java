package daruma.daruma;

import org.bukkit.ChatColor;

import static org.bukkit.Bukkit.getServer;

class Difficulty {
    void difficulty(String difficulty){
        if(difficulty.equals("normal")){
            getServer().broadcastMessage("難易度が"+ChatColor.GREEN+"ノーマル"+ChatColor.WHITE+"に設定されました。");
            Daruma.Difficulty=1000;
        }else if(difficulty.equals("hard")){
            getServer().broadcastMessage("難易度が"+ChatColor.RED+"ハード"+ChatColor.WHITE+"に設定されました。");
            Daruma.Difficulty=500;
        }
    }
}