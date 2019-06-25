package daruma.daruma;


import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

class Setstartpoint {
    void setstartpoint(Player player){//スタート地点の処理
        Location location = player.getLocation().clone();
        location.setX(Math.floor(location.getX())+0.5);
        location.setY(Math.floor(location.getY()));
        location.setZ(Math.floor(location.getZ())+0.5);
        Daruma.startpoint=location;
        player.sendMessage(ChatColor.WHITE+"スタート地点が"+ChatColor.GREEN+Daruma.startpoint.getX()+ChatColor.WHITE+"、"+ChatColor.GREEN+Daruma.startpoint.getY()+ChatColor.WHITE+"、"+ChatColor.GREEN+Daruma.startpoint.getZ()+ChatColor.WHITE+"に設定されました");
        System.out.println("スタート地点が"+Daruma.startpoint.getX()+"、"+Daruma.startpoint.getY()+"、"+Daruma.startpoint.getZ()+"に設定されました");
    }
}
