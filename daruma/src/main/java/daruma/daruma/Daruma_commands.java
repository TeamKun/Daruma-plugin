package daruma.daruma;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Thread.sleep;
import static org.bukkit.Bukkit.getServer;


public class Daruma_commands implements CommandExecutor {
    public final Daruma plugin;
    public Daruma_commands(Daruma plg_){
        plugin = plg_;
    }
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {//コマンド。
        if(!(sender instanceof Player)) {
            return false;
        }
        if (!sender.isOp()){
            return false;
        }
        if(args.length==1) switch (args[0].toLowerCase()) {
            case "start":
                if (Daruma.startpoint == null) {
                    sender.sendMessage("スタート地点がありません");
                    return false;
                }
                if (Daruma.turn == 0) {
                    sender.sendMessage("ターンが０です");
                    return false;
                }
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
                                target.teleport(Daruma.startpoint);
                                target.setHealth(20);
                                target.setFoodLevel(20);
                                target.setLevel(0);
                                target.setExp(0);
                            }
                        }
                        if(Daruma.list.isEmpty()){
                            sender.sendMessage("参加者がいません");
                            Daruma.game=false;
                            timer.cancel();
                        }
                        Daruma.time=0;
                        Daruma.Goallist.clear();
                        Daruma.game = true;
                        getServer().broadcastMessage(ChatColor.WHITE + "ターン数は" + ChatColor.RED + Daruma.turn + ChatColor.WHITE + "です");
                        getServer().broadcastMessage(ChatColor.WHITE + "5秒後にゲームを開始します…");
                        Daruma.check = true;
                        count();
                        for (int i = 5; i >= 1; i--) {
                            if (!Daruma.game) {
                                Daruma.check = false;
                                timer.cancel();
                            }
                            for(Player target : Bukkit.getOnlinePlayers())target.playSound(target.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING,1,24);
                            getServer().broadcastMessage(ChatColor.RED + "" + i);
                            try {
                                sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        for(Player target : Bukkit.getOnlinePlayers())target.playSound(target.getLocation(),Sound.ENTITY_LIGHTNING_BOLT_IMPACT,1,24);
                        new Game(plugin).game();
                        for(Player target : Bukkit.getOnlinePlayers())target.sendTitle(ChatColor.RED+"だるまさんがころんだ",ChatColor.RED+"スタート！",10,15,10);
                        timer.cancel();
                    }
                };
                timer.schedule(task, 0);
                break;
            case "setstartpoint":
                new Setstartpoint().setstartpoint((Player) sender);
                break;
            case "end":
                new End(plugin).end();
                break;
            case "rank":
                if(!(Daruma.game)) {
                    new Rank().rank();
                }else{
                    sender.sendMessage(ChatColor.RED+"ゲーム中です");
                }
                break;
            default:
                sender.sendMessage("/daruma <setstartpoint|turn|start|end|leave|join|rank>");
        }
        else if(args.length==2) {
            try {
                switch (args[0].toLowerCase()) {
                    case "turn":
                        new Turn().turn(Integer.parseInt(args[1]));
                        break;
                    case "join":
                        new Join().join(args[1],(Player)sender);
                        break;
                    case "leave":
                        new Leave(plugin).leave(args[1],(Player)sender);
                        break;
                    default:
                        sender.sendMessage("/daruma <setstartpoint|turn|start|end|leave|join|rank>");
                }
            }catch(Exception e){
                e.printStackTrace();
                sender.sendMessage(ChatColor.RED + "エラー");
            }
        }
        return true;
    }
    private void count(){
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