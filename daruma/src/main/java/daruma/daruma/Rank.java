package daruma.daruma;

import static org.bukkit.Bukkit.getServer;

class Rank {
    void rank(){//ランキングの表示
        if(!(Daruma.game)) {
            String string;
            if (!(Daruma.Goallist.isEmpty())) {
                for (int i = 1; i <= Daruma.Goallist.size(); i++) {
                    string = i + "位：" + Daruma.Goallist.get(i - 1);
                    getServer().broadcastMessage(string);
                }
            } else {
                getServer().broadcastMessage("ゴールしたプレイヤーはいませんでした。");
            }
        }
    }
}