package daruma.daruma;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;
import static org.bukkit.Bukkit.getServer;

class Start {//ゲーム開始時の処理。
    public final Daruma plugin;
    public Start(Daruma plg_){
        plugin = plg_;
    }
    void start(Player player) {
        if (!(Daruma.startpoint == null)&&!(Daruma.turn == 0)) {
            Timer timer = new Timer();
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    System.out.println("参加者:");
                    for (Player target : Bukkit.getOnlinePlayers()) {
                        if (target.getGameMode() == GameMode.ADVENTURE) {
                            String name = target.getName();
                            Daruma.list.add(name);
                            System.out.println(name);
                        }
                    }
                    if (Daruma.list.isEmpty()) {
                        player.sendMessage(ChatColor.RED+"参加者がいません。参加の対象はゲームモードがアドベンチャーモードのプレイヤーです。");
                        Daruma.game = false;
                        timer.cancel();
                    } else {
                        Daruma.time = 0;
                        Daruma.Goallist.clear();
                        Daruma.game = true;
                        getServer().broadcastMessage(ChatColor.WHITE + "ターン数は" + ChatColor.RED + Daruma.turn + ChatColor.WHITE + "です");
                        if (Daruma.Difficulty == 1000) {
                            getServer().broadcastMessage("難易度は" + ChatColor.GREEN + "ノーマル" + ChatColor.WHITE + "です");
                        } else {
                            getServer().broadcastMessage("難易度は" + ChatColor.RED + "ハード" + ChatColor.WHITE + "です");
                        }
                        getServer().broadcastMessage(ChatColor.WHITE + "5秒後にゲームを開始します…");
                        Daruma.check = true;
                        count();
                        for (int i = 5; i >= 1; i--) {
                            if (!Daruma.game) {
                                Daruma.check = false;
                                timer.cancel();
                            }
                            for (Player target : Bukkit.getOnlinePlayers()) {
                                target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1, 24);
                                target.sendTitle(ChatColor.RED +""+ i, "", 5, 10, 5);
                                getServer().broadcastMessage(ChatColor.RED + "" + i);
                            }
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        for (Player target : Bukkit.getOnlinePlayers())
                            target.playSound(target.getLocation(), Sound.ENTITY_LIGHTNING_BOLT_IMPACT, 1, 24);
                        new Game(plugin).game();
                        for (Player target : Bukkit.getOnlinePlayers()) {
                            if(target.getGameMode() == GameMode.ADVENTURE) {
                                target.teleport(Daruma.startpoint);
                                target.setHealth(20);
                                target.setFoodLevel(20);
                                target.setLevel(0);
                                target.setExp(0);
                                target.setPlayerListName("[" + ChatColor.GREEN + "参加者" + ChatColor.WHITE + "]" + ChatColor.GREEN + target.getName());
                                target.sendTitle(ChatColor.RED + "だるまさんがころんだ", ChatColor.RED + "スタート！", 10, 15, 10);
                                timer.cancel();
                            }
                        }
                    }
                }
            };
            timer.schedule(task, 0);
        }else {
            player.sendMessage(ChatColor.RED+"以下の問題を解決してください");
            if (Daruma.startpoint == null) {
                player.sendMessage(ChatColor.RED+"・スタート地点がありません");
            }
            if (Daruma.turn == 0) {
                player.sendMessage(ChatColor.RED+"・ターンが０です");
            }
        }
    }
    private void count(){//タイマーの処理。これでクリアタイムを計る
        Timer timer = new Timer();
        TimerTask  task = new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                i++;
                Daruma.time = i;
                if(!Daruma.game)timer.cancel();
            }
        };
        timer.schedule(task,0,1000);
    }
}