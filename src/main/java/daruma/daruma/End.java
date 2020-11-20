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
    void end(){//ゲームの強制終了用コマンドの処理
        if(Daruma.game) {
            Daruma.list.clear();
            getServer().broadcastMessage(ChatColor.DARK_RED + "コマンドが実行されました。ゲームを終了します");
            Daruma.game = false;
            for (Player target : Bukkit.getOnlinePlayers()) {
                if (target.getGameMode() == GameMode.ADVENTURE || target.getGameMode() == GameMode.SPECTATOR) {
                    if (target.hasMetadata(Events.DATA_KEY)) {
                        target.removeMetadata(Events.DATA_KEY, plugin);
                    }
                    target.teleport(target.getWorld().getSpawnLocation());
                    target.setGameMode(GameMode.ADVENTURE);
                    target.setLevel(0);
                    target.setPlayerListName(target.getName());
                }
            }
        }
    }
    void GameEnd(){
        for (Player target : Bukkit.getOnlinePlayers()) {
            if (target.getGameMode() == GameMode.ADVENTURE || target.getGameMode() == GameMode.SPECTATOR) {
                target.teleport(target.getWorld().getSpawnLocation());
                target.setGameMode(GameMode.ADVENTURE);
                target.setPlayerListName(target.getName());
            }
        }
    }
}
