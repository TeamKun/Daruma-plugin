package daruma.daruma;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

class Leave {
    private Daruma plugin;
    public Leave(Daruma plg){
        this.plugin=plg;
    }
    void leave(String name,Player player){//途中離脱の処理
        Player target = Bukkit.getPlayerExact(name);
        if (target==null || !(Daruma.list.contains(name))){
            player.sendMessage(ChatColor.DARK_RED+"ターゲットが見つかりません。ログインしていないか、すでに参加していない状態です");
        }else if(Daruma.list.contains(name)){
            target.setGameMode(GameMode.SPECTATOR);
            Daruma.list.remove(name);
            target.sendMessage("ゲームから抜けました");
            player.sendMessage(ChatColor.RED+name+ChatColor.WHITE+"がゲームから抜けました");
            target.removeMetadata(Hantei.DATA_KEY, plugin);
        }
    }
}

