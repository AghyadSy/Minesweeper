package GameLogic;

import java.io.Serializable;
import java.util.ArrayList;

public class bestscore implements Serializable {
    ArrayList <Player> bestplayers=new ArrayList<>();

    public void checknewplayerifisbest(ArrayList<Player> players)
    {
        for(int i=0;i<players.size();i++)
        {
                bestplayers.add((players.get(i)));
                bestplayers.sort(Player::compareTo);
        }
    }
}
