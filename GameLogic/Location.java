package GameLogic;

import java.io.Serializable;

public class Location implements Serializable {
    public int column;
    public int row;
    public Location (int row,int column)
    {
        this.column=column;
        this.row=row;
    }
    public boolean equals(Object object2) {
        if(this.column ==((Location) object2).column&&this.row ==((Location) object2).row
        ) {
            return true;
        }
        else return false;
    }
}
