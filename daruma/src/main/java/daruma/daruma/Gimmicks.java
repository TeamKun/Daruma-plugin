package daruma.daruma;

import daruma.daruma.Daruma;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.*;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Objects;

import static daruma.daruma.Events.DATA_KEY;

public class Gimmicks implements Listener {//ギミックの処理。イベントの処理同様ゲーム以外では発動しない
    private Daruma plugin;
    public Gimmicks(Daruma plg){
        this.plugin=plg;
    }
    @EventHandler
    void jumpPad(PlayerMoveEvent event){//ジャンプパッドを踏んだ時の処理
        if((Daruma.game)){
            Player player = event.getPlayer();
            if (player.isOnGround()){
                Location location = event.getTo().clone();
                location.add(0, -0.1, 0);
                if (location.getBlock().getType().equals(Material.REDSTONE_BLOCK) && Daruma.list.contains(player.getName())) {
                    player.sendMessage(ChatColor.AQUA+"ジャンプパッドに乗った！");
                    for(float o=0;o<360;o=(float)(o+0.5)){
                        (player.getWorld()).spawnParticle(Particle.CLOUD,(float) (location.getX()+Math.sin(Math.toRadians(o))*1), (float) (location.getY()), (float) (location.getZ()+Math.cos(Math.toRadians(o))*1), 1, 0, 0, 0, 0);
                    }
                    player.playSound(player.getLocation(), Sound.ITEM_FIRECHARGE_USE, 1, 1);
                    player.setVelocity(new Vector(0,1.75,0));
                }
            }
        }
    }
    @EventHandler
    void dontstep(PlayerMoveEvent event){//ダメージ床を踏んだ時の処理
        if((Daruma.game)){
            Player player = event.getPlayer();
            if (player.isOnGround()){
                Location location = event.getTo().clone();
                location.add(0, -0.1, 0);
                if (location.getBlock().getType().equals(Material.NETHER_WART_BLOCK) && Daruma.list.contains(player.getName())) {
                    (player.getWorld()).spawnParticle(Particle.FLAME, location.getX(), location.getY(), location.getZ(), 30, 0.35, 0.35, 0.35);
                    if (player.hasMetadata(DATA_KEY)) {
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
                        Location location1 = (Location) value.value();
                        assert location1 != null;
                        player.teleport(location1);
                    } else {
                        player.teleport(Daruma.startpoint);
                    }
                    player.playSound(player.getLocation(), Sound.ENTITY_GHAST_HURT, 1, 1);
                }
            }
        }
    }
    @EventHandler
    void dashPad(PlayerMoveEvent event){//ダッシュパッドを踏んだ時の処理
        if((Daruma.game)){
            Player player = event.getPlayer();
            if (player.isOnGround()){
                Location location = event.getTo().clone();
                location.add(0, -0.1, 0);
                if (location.getBlock().getType().equals(Material.DIAMOND_BLOCK) && Daruma.list.contains(player.getName())&&!(player.hasPotionEffect(PotionEffectType.SPEED))) {
                    player.sendMessage(ChatColor.AQUA+"ダッシュパッドに乗った！");
                    player.playSound(player.getLocation(),Sound.BLOCK_BEACON_AMBIENT,1,1);
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED,30,3),true);
                }
            }
        }
    }
    @EventHandler
    void slowPad(PlayerMoveEvent event){//スロウパッドを踏んだ時の処理
        if((Daruma.game)){
            Player player = event.getPlayer();
            if (player.isOnGround()){
                Location location = event.getTo().clone();
                location.add(0, -0.1, 0);
                if (location.getBlock().getType().equals(Material.LAPIS_BLOCK) && Daruma.list.contains(player.getName())&&!(player.hasPotionEffect(PotionEffectType.SLOW))) {
                    player.playSound(player.getLocation(),Sound.ENTITY_SLIME_JUMP,1,1);
                    player.sendMessage(ChatColor.RED+"スロウパッドに乗った！");
                    player.addPotionEffect(new PotionEffect(PotionEffectType.SLOW,80,3),true);
                }
            }
        }
    }
    @EventHandler
    void blindPad(PlayerMoveEvent event){//ブラインドパッドを踏んだ時の処理
        if((Daruma.game)){
            Player player = event.getPlayer();
            if (player.isOnGround()){
                Location location = event.getTo().clone();
                location.add(0, -0.1, 0);
                if (location.getBlock().getType().equals(Material.COAL_BLOCK) && Daruma.list.contains(player.getName())&&!(player.hasPotionEffect(PotionEffectType.BLINDNESS))) {
                    player.playSound(player.getLocation(),Sound.ENTITY_SLIME_JUMP,1,1);
                    player.sendMessage(ChatColor.RED+"ブラインドパッドに乗った！");
                    player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS,80,255),true);
                }
            }
        }
    }
}
