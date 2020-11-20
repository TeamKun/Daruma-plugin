package daruma.daruma;


import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityInteractEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;

import java.util.*;

import static org.bukkit.Bukkit.getServer;

public class Hantei implements Listener {
    private Daruma plugin;
    public Hantei(Daruma plg){
        this.plugin=plg;
    }
    public final static String DATA_KEY = "DARUMA";
    @EventHandler
    public void GoalEvent(PlayerInteractEvent event) {//ゴールした時
        if (Daruma.check) {
            Player player = event.getPlayer();
            Block block = event.getClickedBlock();
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                if ((block != null ? block.getType() : null) == Material.STONE_BUTTON) {
                    Location location = block.getLocation().clone();
                    location.add(0,-0.1,0);
                    if (player.getGameMode() == GameMode.ADVENTURE && Daruma.list.contains(player.getName()) && location.getBlock().getType().equals(Material.GOLD_BLOCK)) {
                        getServer().broadcastMessage(ChatColor.BLUE + player.getName() + ChatColor.WHITE + "さんがゴールしました！");
                        getServer().broadcastMessage(clearTime(Daruma.time));
                        (player.getWorld()).playSound(player.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 2, 1);
                        Daruma.Goallist.add(player.getName()+"<"+clearTime(Daruma.time)+ChatColor.WHITE+">");
                        player.setGameMode(GameMode.SPECTATOR);
                        Daruma.list.remove(player.getName());
                        if(Daruma.list.isEmpty()){
                            getServer().broadcastMessage("参加者全員がゴールしたため、ゲームを終了します。");
                            for (Player target : Bukkit.getOnlinePlayers()) {
                                target.teleport(target.getWorld().getSpawnLocation());
                                target.setGameMode(GameMode.ADVENTURE);
                            }
                            Daruma.game=false;
                            Daruma.check=false;
                        }
                    }
                }
            }
        }
    }
    @EventHandler
    public void DamegeCancel(EntityDamageEvent event) {//ダメージの無効化
        if (Daruma.check) {
            Entity entity = event.getEntity();
            if (entity.getType() == EntityType.PLAYER) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void HealCancel(EntityRegainHealthEvent event) {//回復の無効化
        if (Daruma.check) {
            Entity entity = event.getEntity();
            if (entity.getType() == EntityType.PLAYER) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void FoodCancel(FoodLevelChangeEvent event) {//空腹度を減らないようにする
        if (Daruma.check) {
            Entity entity = event.getEntity();
            if (entity.getType() == EntityType.PLAYER) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    public void EntityInterect(EntityInteractEvent event){//プレイヤー同士の当たり判定を消す
        if (Daruma.game){
            Entity entity = event.getEntity();
            if (entity.getType() == EntityType.PLAYER) {
                event.setCancelled(true);
            }
        }
    }
    @EventHandler
    void move(PlayerMoveEvent event){//動いたかどうかの判定
        Player player = event.getPlayer();
        if (Daruma.move && Daruma.list.contains(player.getName())){
            player.sendMessage(ChatColor.RED + "動いてしまった！");
            if (player.hasMetadata(DATA_KEY)){
                MetadataValue value = null;
                List<MetadataValue> values = player.getMetadata(DATA_KEY);
                for (MetadataValue v : values) {
                    if (Objects.requireNonNull(v.getOwningPlugin()).getName().equals(plugin.getName())) {
                        value = v;
                        break;
                    }
                }
                if (value == null) {
                    return;
                }
                Location location = (Location)value.value();
                assert location != null;
                player.teleport(location);
            } else {
                player.teleport(Daruma.startpoint);
            }
        }
    }
    @EventHandler
    void save(PlayerMoveEvent event){//セーブポイントの処理
        if (Daruma.game) {
            Player player = event.getPlayer();
            if (player.isOnGround()){
                Location location = event.getTo().clone();
                location.add(0, -0.1, 0);
                if (location.getBlock().getType().equals(Material.EMERALD_BLOCK) && Daruma.list.contains(player.getName())&&player.getLevel()<1) {
                    player.setLevel(5);
                    player.setMetadata(DATA_KEY, new FixedMetadataValue(plugin,player.getLocation().clone()));
                    player.sendMessage(ChatColor.AQUA + "セーブしました！");
                    player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_CHIME, 1, 24);
                    Timer timer = new Timer();
                    TimerTask task = new TimerTask() {
                        int i = 5;
                        @Override
                        public void run() {
                            if(i<1){
                                player.setLevel(0);
                                player.setExp(0);
                                timer.cancel();
                            }
                            player.setLevel(i);
                            i--;
                        }
                    };
                    timer.schedule(task,0,1000);
                }
            }
        }
    }
    private String clearTime(int time){//クリアタイムの計算
        int min,sec;
        min = (time%3600)/60;
        sec = time%60;
        String clearTime;
        clearTime = ChatColor.GREEN+"クリアタイム："+min+"分"+sec+"秒";
        return clearTime;
    }
}