package daruma.daruma;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getServer;

class Leave {
    private Daruma plugin;
    public Leave(Daruma plg){
        this.plugin=plg;
    }
    void leave(String name,Player player){//途中離脱の処理
        if (Daruma.game) {
            Player target = Bukkit.getPlayerExact(name);
            if (target==null || !(Daruma.list.contains(name))){
                player.sendMessage(ChatColor.DARK_RED+"ターゲット("+name+")が見つかりません。ログインしていないか、既に参加していない状態です");
            }else if(Daruma.list.contains(name)){
                target.setGameMode(GameMode.SPECTATOR);
                Daruma.list.remove(name);
                target.setPlayerListName("["+ChatColor.RED+"観戦中"+ChatColor.WHITE+"]"+ChatColor.RED+target.getName());
                target.sendMessage("ゲームから抜けました");
                player.sendMessage(ChatColor.RED+name+ChatColor.WHITE+"がゲームから抜けました");
                target.removeMetadata(Events.DATA_KEY, plugin);
                if(Daruma.list.isEmpty()){
                    getServer().broadcastMessage("参加者がいなくなったため、ゲームを終了します。");
                    Daruma.game=false;
                    Daruma.check=false;
                    for (Player player1 : Bukkit.getOnlinePlayers()) {
                        player1.setPlayerListName(player1.getPlayerListName());
                        player1.teleport(player1.getWorld().getSpawnLocation());
                        player1.setGameMode(GameMode.ADVENTURE);
                        player1.setPlayerListName(target.getName());
                    }
                }
            }
        }else {
            player.sendMessage("ゲームが始まっていません");
        }
    }
}

