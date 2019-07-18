package daruma.daruma;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

class Watching {
    void watching(String name, Player player){//観戦用の処理。Leaveクラスと別にしているのはLeaveクラスではゲームに参加している人しか観戦状態にできないため
        if (Daruma.game) {
            Player target = Bukkit.getPlayerExact(name);
            if (target==null || (Daruma.list.contains(name))||name.contains("[観戦中]")){
                player.sendMessage(ChatColor.DARK_RED+"ターゲット("+name+")が見つかりません。ログインしていないか、既に観戦状態、あるいはゲームに参加しています。");
            }else if(!(Daruma.list.contains(name))){
                target.setGameMode(GameMode.SPECTATOR);
                target.setPlayerListName("["+ChatColor.RED+"観戦中"+ChatColor.WHITE+"]"+ChatColor.RED+target.getName());
                target.sendMessage("観戦状態になりました");
                target.teleport(Daruma.startpoint);
                player.sendMessage(ChatColor.RED+name+ChatColor.WHITE+"が観戦状態になりました");
            }
        }else {
            player.sendMessage("ゲームが始まっていません");
        }
    }
}