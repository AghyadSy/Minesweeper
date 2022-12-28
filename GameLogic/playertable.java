package GameLogic;

import javafx.beans.property.SimpleStringProperty;

public class playertable {
   final String name;
    final String  score;
    final String  ishuman;
    public  playertable(String name,String score,String ishuman)
    {
        this.name=name;
        this.score=score;
        this.ishuman= ishuman;
    }
    public String getname()
    {
        return  this.name;
    }
    public String getscore()
    {
        return  this.score;
    }
    public String getishuman()
    {
        return  this.ishuman;
    }
}
