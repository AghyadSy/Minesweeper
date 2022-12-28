package GameLogic;
import javafx.scene.paint.Color;

import java.io.Serializable;

public class Player implements Serializable , Comparable<Player> {
     String name;
     int score;
     boolean IsHisRole;
  //  private Color color;
    private double Red;
    private double Green;
    private double Blue;
    private int move;
    private boolean Ishuman;
    private boolean isuseshield;
    private int shieldnumber;

    public Player(String name, Color color, boolean ishuman, boolean isuseshield, int shieldnumber) {
        this.name = name;
        this.score = 0;
        this.IsHisRole = false;
        this.move = 0;
        this.Red = color.getRed();
        this.Green = color.getGreen();
        this.Blue = color.getBlue();
        this.Ishuman = ishuman;
        this.isuseshield = isuseshield;
        this.shieldnumber = shieldnumber;

    }

    public void AddScore(int score) {
        this.score += score;
    }

    public int GetScore() {
        return this.score;
    }

    public boolean GetIsHisRole() {
        return this.IsHisRole;
    }

    public void setIsHisRole(boolean isHisRole) {
        this.IsHisRole = isHisRole;
    }

    public void setcolor(Color color) {
        this.Red = color.getRed();
        this.Green = color.getGreen();
        this.Blue = color.getBlue();
    }

    public Color getcolor() {
        return new Color(this.Red,this.Green ,this.Blue ,1 );
    }

    public void moveincrement() {
        this.move++;
    }

    public int getmove() {
        return this.move;
    }

    public boolean getishuman() {
        return this.Ishuman;
    }

    public String getname() {
        return this.name;
    }

    public String toString() {
        return "Player : " + this.name + " Score : " + this.score + " Shields : " + this.shieldnumber;
    }


    public int getshieldnumber() {
        return this.shieldnumber;
    }

    public void shieldnumberincrement() {
        if (this.isuseshield) this.shieldnumber++;
    }

    public void shieldnumberdecrement() {
        if (this.isuseshield && this.shieldnumber != 0) this.shieldnumber--;
    }


    @Override
    public int compareTo(Player o) {
        if (this.score<o.score)return  1;
        if (this.score>o.score)return  -1;
        return 0;
    }
}
