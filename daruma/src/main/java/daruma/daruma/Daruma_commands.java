package daruma.daruma;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            case "kaioken":
                new Kaioken().kaioken((Player)sender);
                break;
            case "start":
                new Start(plugin).start((Player)sender);
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
                sender.sendMessage("/daruma <setstartpoint|turn|start|end|leave|join|rank|difficulty>");
        }
        else if(args.length==2) {
            try {
                switch (args[0].toLowerCase()) {
                    case "turn":
                        new Turn().turn(Integer.parseInt(args[1]));
                        break;
                    case "watching":
                        new Watching().watching(args[1],(Player)sender);
                        break;
                    case "join":
                        new Join().join(args[1],(Player)sender);
                        break;
                    case "leave":
                        new Leave(plugin).leave(args[1],(Player)sender);
                        break;
                    case "difficulty":
                        if (!(Daruma.game)) {
                            if (!(args[1].equals("normal")) && !(args[1].equals("hard"))) {
                                sender.sendMessage(ChatColor.RED + "<normal>か<hard>のどちらかで指定してください");
                            } else {
                                new Difficulty().difficulty(args[1]);
                            }
                        }else{
                            sender.sendMessage(ChatColor.RED+"ゲーム中です");
                        }
                        break;
                    default:
                        sender.sendMessage("/daruma <setstartpoint|turn|start|end|leave|join|rank|difficulty>");
                }
            }catch(Exception e){
                e.printStackTrace();
                sender.sendMessage(ChatColor.RED + "エラー");
            }
        }
        return true;
    }
}