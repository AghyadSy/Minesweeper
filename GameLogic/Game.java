package GameLogic;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

public class Game implements Serializable {
  public   Cell[][] cells;
    ArrayList<Player> players;
    GameState state;
    ArrayList<Location> mines = new ArrayList<>();
    ArrayList<Location> shields = new ArrayList<>();
   public ArrayList<Location> moves = new ArrayList<>();
    int numberofmines = 0,numberofshiels = 0,roletime=10;
    Thread t1;

    public Game(Integer NumberRows, Integer NumberColmns, Integer NumberOfMines,Integer NumberOfShields,int roletime, ArrayList<Player> players) {
        this.state = GameState.onplay;
        this.players = players;
        players.get(0).setIsHisRole(true);
        numberofmines = NumberOfMines;
        numberofshiels = NumberOfShields;
        this.roletime=roletime;
        cells = new Cell[NumberRows][NumberColmns];
        for (int i = 0; i < NumberRows; i++) {
            for (int j = 0; j < NumberColmns; j++) {
                cells[i][j] = new Cell(CellState.blank, new Location(i, j));
            }
        }

    }

    private void GenerateRandomMines(Location firstopencell) {
        Random rand = new Random();
        Location newlocation;
        for (int i = 0; i < numberofmines; i++) {
            newlocation = new Location(rand.nextInt(cells.length), rand.nextInt(cells[0].length));
            while (mines.contains(newlocation) || (newlocation.equals(firstopencell))) {
                newlocation = new Location(rand.nextInt(cells.length), rand.nextInt(cells[0].length));
            }
            mines.add(newlocation);
            cells[newlocation.row][newlocation.column].SetState(CellState.mine);
        }
    }
    private void GenerateRandomShields(Location firstopencell) {
        Random rand = new Random();
        Location newlocation;
        for (int i = 0; i < numberofshiels; i++) {
            newlocation = new Location(rand.nextInt(cells.length), rand.nextInt(cells[0].length));
            while (mines.contains(newlocation) ||shields.contains(newlocation)|| (newlocation.equals(firstopencell))) {
                newlocation = new Location(rand.nextInt(cells.length), rand.nextInt(cells[0].length));
            }
            shields.add(newlocation);
            cells[newlocation.row][newlocation.column].SetState(CellState.shield);
        }
    }


    public Cell OpenCell(Location cell) {
        if (isfirstmove()) {
            GenerateRandomMines(cell);
            GenerateRandomShields(cell);
        }
        switch (cells[cell.row][cell.column].GetState())
        {
            case blank:
                if ( !IsLastCell()) {
                    int numberofminesaroundcell = CheckAroundCell(cell);
                    if (numberofminesaroundcell == 0) {
                        cells[cell.row][cell.column].SetState(CellState.open);
                        players.get(selectedplayer()).AddScore(10);
                    } else if (numberofminesaroundcell > 0) {
                        cells[cell.row][cell.column].SetState(CellState.number);
                        cells[cell.row][cell.column].SetNumber(numberofminesaroundcell);
                        players.get(selectedplayer()).AddScore(numberofminesaroundcell);
                    }
                    cells[cell.row][cell.column].setplayerEdit(players.get(selectedplayer()));
                }
                else {
                    int numberofminesaroundcell = CheckAroundCell(cell);
                    if (numberofminesaroundcell == 0) {
                        cells[cell.row][cell.column].SetState(CellState.open);
                        players.get(selectedplayer()).AddScore(10);
                    } else if (numberofminesaroundcell > 0) {
                        cells[cell.row][cell.column].SetState(CellState.number);
                        cells[cell.row][cell.column].SetNumber(numberofminesaroundcell);
                        players.get(selectedplayer()).AddScore(numberofminesaroundcell);
                    }
                    players.get(selectedplayer()).AddScore(ScoreAddedISLastCell());
                    cells[cell.row][cell.column].setplayerEdit(players.get(selectedplayer()));
                }
                NextRole();
                break;
            case mine:
                cells[cell.row][cell.column].SetState(CellState.mineopen);
                if (players.get(selectedplayer()).getshieldnumber() == 0)
                    players.get(selectedplayer()).AddScore(-250);
                players.get(selectedplayer()).shieldnumberdecrement();
                cells[cell.row][cell.column].setplayerEdit(players.get(selectedplayer()));
                if ( players.get(selectedplayer()).GetScore() < 0)
                NextRole();
                break;
            case shield:
                if (!IsLastCell()){
                cells[cell.row][cell.column].SetState(CellState.shieldopen);
                players.get(selectedplayer()).shieldnumberincrement();
                cells[cell.row][cell.column].setplayerEdit(players.get(selectedplayer()));
                NextRole();}
                else {
                    cells[cell.row][cell.column].SetState(CellState.shieldopen);
                    players.get(selectedplayer()).shieldnumberincrement();
                    cells[cell.row][cell.column].setplayerEdit(players.get(selectedplayer()));
                    for(int i=0;i<players.size();i++)
                    {
                        players.get(i).AddScore(   players.get(i).getshieldnumber()*50);
                    }
                    NextRole();
                }
                break;
        }
        if (isendgame())
        {
            this.state=GameState.finish;
        }
        moves.add(cell);
        return cells[cell.row][cell.column];
    }

    public Cell MarkCell(Location cell) {

        switch (cells[cell.row][cell.column].GetState())
        {
            case mine:
                players.get(selectedplayer()).AddScore(5);
                cells[cell.row][cell.column].setplayerEdit(players.get(selectedplayer()));
                cells[cell.row][cell.column].SetState(CellState.mineandmark);
                NextRole();
                break;
            case mineandmark:
                if (cells[cell.row][cell.column].getplayerEdit().getname()==players.get(selectedplayer()).getname()) {
                    cells[cell.row][cell.column].setplayerEdit(players.get(selectedplayer()));
                    cells[cell.row][cell.column].SetState(CellState.mine);
                    NextRole();
                }
                break;
            case blank:
                players.get(selectedplayer()).AddScore(-1);
                cells[cell.row][cell.column].setplayerEdit(players.get(selectedplayer()));
                cells[cell.row][cell.column].SetState(CellState.mark);
                NextRole();
                break;
            case mark:
                if (cells[cell.row][cell.column].GetState() == CellState.mark&&cells[cell.row][cell.column].getplayerEdit().getname()==players.get(selectedplayer()).getname()) {
                    cells[cell.row][cell.column].SetState(CellState.blank);
                    cells[cell.row][cell.column].setplayerEdit(players.get(selectedplayer()));
                    NextRole();
                }
                break;
            case shield:
                cells[cell.row][cell.column].setplayerEdit(players.get(selectedplayer()));
                cells[cell.row][cell.column].SetState(CellState.shielsandmark);
                NextRole();
                break;
            case shielsandmark:
                if (cells[cell.row][cell.column].getplayerEdit().getname()==players.get(selectedplayer()).getname()) {
                    cells[cell.row][cell.column].setplayerEdit(players.get(selectedplayer()));
                    cells[cell.row][cell.column].SetState(CellState.shield);
                    NextRole();
                }
                break;
        }
        if (isendgame())
        {
            this.state=GameState.finish;
        }
        moves.add(cell);
        return cells[cell.row][cell.column];
    }

    private int CheckAroundCell(Location location) {
        int minesnumber = 0;
        try {

            if (cells[location.row - 1][location.column - 1].GetState() == CellState.mine || cells[location.row - 1][location.column - 1].GetState() == CellState.mineandmark|| cells[location.row - 1][location.column - 1].GetState() == CellState.mineopen)
                minesnumber++;
        } catch (Exception ex) {
        }
        try {
            if (cells[location.row][location.column - 1].GetState() == CellState.mine || cells[location.row][location.column - 1].GetState() == CellState.mineandmark|| cells[location.row][location.column - 1].GetState() == CellState.mineopen)
                minesnumber++;
        } catch (Exception ex) {
        }
        try {
            if (cells[location.row + 1][location.column - 1].GetState() == CellState.mine || cells[location.row + 1][location.column - 1].GetState() == CellState.mineandmark|| cells[location.row + 1][location.column - 1].GetState() == CellState.mineopen)
                minesnumber++;
        } catch (Exception ex) {
        }
        try {
            if (cells[location.row - 1][location.column].GetState() == CellState.mine || cells[location.row - 1][location.column].GetState() == CellState.mineandmark|| cells[location.row - 1][location.column].GetState() == CellState.mineopen)
                minesnumber++;
        } catch (Exception ex) {
        }
        try {
            if (cells[location.row + 1][location.column].GetState() == CellState.mine || cells[location.row + 1][location.column].GetState() == CellState.mineandmark|| cells[location.row + 1][location.column].GetState() == CellState.mineopen)
                minesnumber++;
        } catch (Exception ex) {
        }
        try {
            if (cells[location.row - 1][location.column + 1].GetState() == CellState.mine || cells[location.row - 1][location.column + 1].GetState() == CellState.mineandmark|| cells[location.row - 1][location.column + 1].GetState() == CellState.mineopen)
                minesnumber++;
        } catch (Exception ex) {
        }
        try {
            if (cells[location.row][location.column + 1].GetState() == CellState.mine || cells[location.row][location.column + 1].GetState() == CellState.mineandmark|| cells[location.row][location.column + 1].GetState() == CellState.mineopen)
                minesnumber++;
        } catch (Exception ex) {
        }
        try {
            if (cells[location.row + 1][location.column + 1].GetState() == CellState.mine || cells[location.row + 1][location.column + 1].GetState() == CellState.mineandmark|| cells[location.row + 1][location.column + 1].GetState() == CellState.mineopen)
                minesnumber++;
        } catch (Exception ex) {
        } finally {
            return minesnumber;
        }


    }

    private boolean IsLastCell() {
        int cellsok = 0;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j].GetState() == CellState.blank)
                    cellsok++;
            }
        }
        return cellsok == 1;
    }
    private boolean isendgame()
    {
        int cellsok = 0;
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j].GetState() == CellState.blank)
                    cellsok++;
            }
        }
        return cellsok == 0;
    }

    private int ScoreAddedISLastCell() {
        int number = 0;

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j].GetState() == CellState.mine)
                    number++;
            }
        }
        return number * 100;
    }

    public void NextRole() {
int a=selectedplayer();
        players.get(a).moveincrement();
        players.get(a).setIsHisRole(false);
        try {
            players.get(a+1).setIsHisRole(true);
            if ( players.get(a+1).getishuman()){}
                else OpenCell(randomcellcomputer());

        }
        catch (Exception ex){  players.get(0).setIsHisRole(true);
            if ( players.get(0).getishuman()){}
            else OpenCell(randomcellcomputer());
        }


    }
    public  int selectedplayer() {
        int a=0;
        for (int i = 0; i < players.size(); i++)
            if (players.get(i).GetIsHisRole()==true)
                a=i;
            return a;

    }
    private boolean isfirstmove()
    {
        for (int i = 0; i < players.size(); i++)
            if (players.get(i).getmove()!=0)
                return false;
            return true;
    }
    public String GetPlayerDetils()
    {
       return players.get(selectedplayer()).toString();
    }
    public Color GetPlayerColor()
    {
        return players.get(selectedplayer()).getcolor();
    }
    private Location randomcellcomputer()
    {
        Location a=new Location(0,0 );
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (cells[i][j].GetState() == CellState.blank)
                    a=new Location(i,j );
            }
        }
        return a;


    }
    public GameState getState()
    {
        return  this.state;
    }

}
