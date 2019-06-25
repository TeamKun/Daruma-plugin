package daruma.daruma;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

class Join {
    void join(String name,Player player){//途中参加の処理
        Player target = Bukkit.getPlayerExact(name);
        if (target==null || Daruma.list.contains(name)) {
            player.sendMessage(ChatColor.DARK_RED+"ターゲットが見つかりません。ログインしていないか、すでに参加しています");
        }else if(!(Daruma.list.contains(name))){
            target.setGameMode(GameMode.ADVENTURE);
            target.teleport(Daruma.startpoint);
            Daruma.list.add(name);
            target.setHealth(20);
            target.setFoodLevel(20);
            target.sendMessage("ゲームに参加しました");
            player.sendMessage(ChatColor.AQUA+name+ChatColor.WHITE+"がゲームに参加しました");
        }
    }
}
