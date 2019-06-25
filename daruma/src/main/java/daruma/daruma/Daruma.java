package daruma.daruma;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Daruma extends JavaPlugin{//メインクラス
    static int turn = 0;
    static int time = 0;
    static Location startpoint = null;
    static boolean move = false;
    static boolean check = false;
    static boolean game = false;
    static List<String> list = new ArrayList<>();
    static List<String> Goallist = new ArrayList<>();
    @Override
    public void onEnable() {
        Objects.requireNonNull(this.getCommand("Daruma")).setExecutor(new Daruma_commands(this));
        getServer().getPluginManager().registerEvents(new Hantei(this),this);
        getLogger().info("だるま起動しました");
        // Plugin startup logic
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
