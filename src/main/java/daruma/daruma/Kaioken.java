package daruma.daruma;

import org.bukkit.*;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static org.bukkit.Bukkit.getServer;

class Kaioken implements Listener {
    void kaioken(Player player){//界王拳の処理。ゲームには全く関係ない
        if(!(player.getPlayerListName().contains("[界王拳]"))) {
            getServer().broadcastMessage("<" + player.getName() + ">" + ChatColor.RED + "界王拳!!");
            Location location = player.getLocation();
            player.playSound(location, Sound.ENTITY_GENERIC_EXPLODE,1,1);
            for (int i=1;i<=3;i++) {
                for (float o = 0; o < 360; o = (float) (o + 0.5)) {
                    (player.getWorld()).spawnParticle(Particle.CLOUD, (float) (location.getX() + Math.sin(Math.toRadians(o)) * i), (float) (location.getY()), (float) (location.getZ() + Math.cos(Math.toRadians(o)) * i), 1, 0, 0, 0, 0);
                }
            }
            player.setPlayerListName(player.getName() + ChatColor.RED + "[界王拳]");
            kaioken_count(player);
        }else {
            player.sendMessage("すでに使用しています");
        }
    }
    @EventHandler
    void attack_event(EntityDamageByEntityEvent event){//界王拳状態で殴った時の処理
        if(event.getDamager().getType()== EntityType.PLAYER) {
            Player player = (Player) event.getDamager();
            Entity entity = event.getEntity();
            if (player.getPlayerListName().contains("[界王拳]")) {
                Random random = new Random();
                double damege = event.getDamage();
                damege = damege * 5;
                if(random.nextInt(10)==1){
                    damege = damege * 2;
                    player.sendMessage(ChatColor.AQUA+"クリティカルダメージ！");
                    player.playEffect(entity.getLocation(), Effect.ANVIL_BREAK,true);
                }
                event.setDamage(damege);
                (player.getWorld()).spawnParticle(Particle.EXPLOSION_LARGE, (float) entity.getLocation().getX(), (float) entity.getLocation().getY()+1, (float) entity.getLocation().getZ(), 1, 0, 0, 0);
                player.playSound(entity.getLocation(), Sound.ENTITY_GENERIC_EXPLODE, 1, 1);
                if(!(player.getHealth()<=1)) {
                    player.setHealth(player.getHealth() - 1);
                }
            }
        }
    }
    @EventHandler
    void damege_event(EntityDamageEvent event){//界王拳状態でダメージを受けた時の処理
        if(event.getEntity().getType()== EntityType.PLAYER) {
            Player player = (Player) event.getEntity();
            if (player.getPlayerListName().contains("[界王拳]")) {
                player.playSound(player.getLocation(), Sound.ENTITY_IRON_GOLEM_HURT, 1, 1);
                if (!(event.getDamage() % 2 == 1)) {
                    event.setDamage(event.getDamage() / 2);
                } else if (!(event.getDamage() < 1)) {
                    double damege = event.getDamage() - 1;
                    event.setDamage(damege / 2);
                } else {
                    event.setDamage(0);
                }
            }
        }
    }
    private void kaioken_count(Player player){
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            int i = 0;
            @Override
            public void run() {
                i++;
                if(i>=180||!(player.getPlayerListName().contains("[界王拳]"))) {
                    player.setPlayerListName(player.getName());
                    player.sendMessage("界王拳の効果が終了しました。");
                    timer.cancel();
                }
            }
        };
        timer.schedule(task,0,1000);
    }
}