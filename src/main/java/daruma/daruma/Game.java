package daruma.daruma;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;
import static org.bukkit.Bukkit.getServer;

class Game {
    private Daruma plugin;
    public Game(Daruma plg){
        this.plugin=plg;
    }
    void game() {//ゲームの進行の処理
        Timer timer = new Timer(false);
        TimerTask task =new TimerTask() {
            int count = 0;
            int turn = Daruma.turn;
            @Override
            public void run() {
                if(turn==0){
                    for(Player target : Bukkit.getOnlinePlayers()){
                        target.sendTitle(ChatColor.RED+"終了！","",10,15,10);
                        target.teleport(target.getWorld().getSpawnLocation());
                        target.removeMetadata(Events.DATA_KEY,plugin);
                        target.setLevel(0);
                    }
                    Daruma.list.clear();
                    new End(plugin).GameEnd();
                    getServer().broadcastMessage(ChatColor.WHITE + "終了！");
                    Daruma.game=false;
                    Daruma.check=false;
                    timer.cancel();
                }
                if(!Daruma.game){
                    Daruma.check=false;
                    for(Player target : Bukkit.getOnlinePlayers()) {
                        target.removeMetadata(Events.DATA_KEY, plugin);
                    }
                    timer.cancel();
                    Daruma.game=false;
                }
                if (count == 1) {
                    getServer().broadcastMessage("だ");
                } else if (count == 2) {
                    getServer().broadcastMessage("る");
                } else if (count == 3) {
                    getServer().broadcastMessage("ま");
                } else if (count == 4) {
                    getServer().broadcastMessage("さ");
                } else if (count == 5) {
                    getServer().broadcastMessage("ん");
                } else if (count == 6) {
                    getServer().broadcastMessage("が");
                } else if (count == 7) {
                    getServer().broadcastMessage(ChatColor.YELLOW + "こ");
                    for(Player target : Bukkit.getOnlinePlayers())target.playSound(target.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,1,24);
                } else if (count == 8) {
                    getServer().broadcastMessage(ChatColor.YELLOW + "ろ");
                    for(Player target : Bukkit.getOnlinePlayers())target.playSound(target.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,1,24);
                } else if (count == 9) {
                    getServer().broadcastMessage(ChatColor.GOLD + "ん");
                    for(Player target : Bukkit.getOnlinePlayers())target.playSound(target.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,1,24);
                } else if (count == 10) {
                    getServer().broadcastMessage(ChatColor.RED + "だ!");
                    for(Player target : Bukkit.getOnlinePlayers())target.playSound(target.getLocation(),Sound.ENTITY_LIGHTNING_BOLT_IMPACT,1,24);
                }
                count++;
                if(count==11){
                    Daruma.move=true;
                    try {
                        sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    count=0;
                    turn--;
                    Daruma.move=false;
                    getServer().broadcastMessage(ChatColor.WHITE + "残りターン数：" + ChatColor.RED + turn);
                    for(Player target : Bukkit.getOnlinePlayers())target.playSound(target.getLocation(),Sound.BLOCK_NOTE_BLOCK_PLING,1,24);
                }
            }
        };
        timer.schedule(task,0,Daruma.Difficulty);
    }
}