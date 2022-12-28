package GameLogic;

import java.io.*;

public class IOManagement {
    Game game;
    public IOManagement(Game game)
    {
        this.game=game;
    }
    public void Save(String path)
    {
      try {
          FileOutputStream out=new FileOutputStream(path);
          ObjectOutputStream obj=new ObjectOutputStream(out);
          obj.writeObject(game);
          obj.close();
          out.close();
          System.out.println("Save is done");
      }
      catch (IOException ex){ex.printStackTrace();}
    }
    public  Game Load(String path)
    {
        Game game=null;
        try {
            FileInputStream in=new FileInputStream(path);
            ObjectInputStream obj=new ObjectInputStream(in);
            game=(Game)obj.readObject();
            obj.close();
            in.close();
        }
        catch (Exception x){System.out.println("Can't find the file");return null;}
        System.out.println("Load is done");
        return game;
    }
}
