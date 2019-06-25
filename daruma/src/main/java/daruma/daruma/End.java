package daruma.daruma;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

class End {
    private Daruma plugin;
    public End(Daruma plg_){
        this.plugin = plg_;
    }
    void end(){//ゲームの強制終了ようコマンドの処理
        if(Daruma.game) {
            Daruma.list.clear();
            getServer().broadcastMessage(ChatColor.DARK_RED + "コマンドが実行されました。ゲームを終了します");
            Daruma.game = false;
            for (Player target : Bukkit.getOnlinePlayers()) {
                if(target.hasMetadata(Hantei.DATA_KEY)){
                    target.removeMetadata(Hantei.DATA_KEY,plugin);
                }
                target.teleport(target.getWorld().getSpawnLocation());
                target.setGameMode(GameMode.ADVENTURE);
            }
        }
    }
    void GameEnd(){
        for (Player target : Bukkit.getOnlinePlayers()) {
            target.teleport(target.getWorld().getSpawnLocation());
            target.setGameMode(GameMode.ADVENTURE);
        }
    }
}
