package GameLogic;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.text.Font;

public class RolesManagement implements Runnable {
    Game game;
   private int roletime=10;
   private int time =0;
   Label l;
    public RolesManagement(Game game,Label l)
    {
        this.game=game;
        this.roletime=game.roletime;
        this.l=l;
        l.setFont(new Font(18));
    }



    @Override
    public void run() {
        for(int i=0;i<this.roletime;i++)
        {
            if (Thread.interrupted())
                return;
            try {
                Thread.sleep(1000);
            }
            catch (InterruptedException x){return;}
            time++;
           // System.out.println(time);
            Platform.runLater( new Runnable() {
                @Override public void run() {
                    l.setText(game.GetPlayerDetils()+" Time : "+time);
                    l.setBackground((new Background(new BackgroundFill(game.GetPlayerColor(), CornerRadii.EMPTY, Insets.EMPTY))));
                }
            });

        }
        game.NextRole();
        this.time=0;
        run();
    }
}
