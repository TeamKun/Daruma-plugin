package daruma.daruma;

import org.bukkit.ChatColor;

import static org.bukkit.Bukkit.getServer;

class Rank {
    void rank(){//ランキングの表示の処理
        if(!(Daruma.game)) {
            String string;
            int p=5;
            if(p>Daruma.Goallist.size()){
                p=Daruma.Goallist.size();
            }
            getServer().broadcastMessage(ChatColor.YELLOW+"ランキング");
            if (!(Daruma.Goallist.isEmpty())) {//ランキングは5位まで表示する。
                for (int i = 1; i <= p; i++) {
                    string = i + "位：" + Daruma.Goallist.get(i - 1);
                    getServer().broadcastMessage(string);
                }
            } else {
                getServer().broadcastMessage("ゴールしたプレイヤーはいませんでした。");
            }
        }
    }
}