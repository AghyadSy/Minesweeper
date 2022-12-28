package GameLogic;

import java.io.Serializable;

public class Cell implements Serializable {
    private CellState state;
    private Location location;
    private Integer number;
    private Player playerEdit;
    public Cell(CellState cellState,Location location,Integer number)
    {
        this.state=cellState;
        this.location=location;
        this.number=number;
    }
    public Cell(CellState cellState,Location location)
    {
        this.state=cellState;
        this.location=location;
        this.number=null;
    }
   public CellState GetState()
   {
       return  this.state;
   }
    public Location GetLocation()
    {
        return  this.location;
    }
    public Integer GetNumber()
    {
        return  this.number;
    }
    public void SetState(CellState state)
    {
        this.state=state;
    }
    public void SetLocation(Location location)
    {
        this.location=location;
    }
    public void SetNumber(Integer number)
    {
        this.number=number;
    }
    public  void setplayerEdit(Player player){this.playerEdit=player;}
    public Player getplayerEdit()
    {
        return this.playerEdit;
    }
}
