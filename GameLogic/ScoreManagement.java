package GameLogic;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Main;

import java.io.*;
import java.util.ArrayList;

public class ScoreManagement {
    Game game;
    GridPane gridPane;
    Stage stage;
    bestscore bestscore;
    public ScoreManagement(Game game,Stage stage)
    {
        this.game=game;
        this.stage=stage;
    }
    public  void showgamescore()
    {
/*
        TableColumn<playertable,String> nameC=new TableColumn<>("Player name");
       // nameC.setCellValueFactory( p -> p.getValue().name);
        nameC.setCellValueFactory(new PropertyValueFactory<playertable,String>("name"));
        nameC.setMinWidth(200);


        TableColumn<playertable,String> scoreC=new TableColumn<>("Player score");
        scoreC.setCellValueFactory(new PropertyValueFactory<>("score"));
        scoreC.setMinWidth(200);


        TableColumn ishumanC=new TableColumn("Player is human");
        ishumanC.setCellValueFactory(new PropertyValueFactory<>("ishuman"));
        ishumanC.setMinWidth(200);

        ObservableList<playertable> data =
                FXCollections.observableArrayList(
                        new playertable("Jacob", "Smith", "jacob.smith@example.com"),
                        new playertable("Isabella", "Johnson", "isabella.johnson@example.com"),
                        new playertable("Ethan", "Williams", "ethan.williams@example.com"),
                        new playertable("Emma", "Jones", "emma.jones@example.com"),
                        new playertable("Michael", "Brown", "michael.brown@example.com")
                );

        TableView<playertable> tt=new TableView<>();

        tt.setEditable(true);
       // tt.getItems().addAll(data);
        tt.setItems(data);
        tt.getColumns().addAll(nameC,scoreC,ishumanC);

        System.out.println( tt.getItems().get(2).ishuman);
       // tt.getItems().addAll(getdata());





/*
        TableView<Player> table = new TableView();

        ObservableList data = FXCollections.observableArrayList(

    );
        data.addAll(game.players);

        TableColumn columnOne = new TableColumn("Player name");
        TableColumn columnTwo = new TableColumn("Score");

        table.getColumns().addAll(columnOne, columnTwo);

        columnOne.setCellValueFactory(
                new PropertyValueFactory<Player,String>("name")
        );
        columnTwo.setCellValueFactory(
                new PropertyValueFactory<Player,Integer>("score")
        );

        table.getItems().add(data);
        */
        gridPane=new GridPane();

        Label l1=new Label("Player name");
        Label l2=new Label("Player score");
        Label l3=new Label("Player is human");
        l1.setFont(new Font(24));
        GridPane.setMargin( l1,new Insets(30, 30, 30, 30));
        l2.setFont(new Font(24));
        GridPane.setMargin( l2,new Insets(30, 30, 30, 30));
        l3.setFont(new Font(24));
        GridPane.setMargin( l3,new Insets(30, 30, 30, 30));
        GridPane.setConstraints(l1, 0, 0);
        gridPane.getChildren().add(l1);
        GridPane.setConstraints(l2, 1, 0);
        gridPane.getChildren().add(l2);
        GridPane.setConstraints(l3, 2, 0);
        gridPane.getChildren().add(l3);
        for(int i=0;i<game.players.size();i++)
        {
            Label ll1=new Label(game.players.get(i).name);
            ll1.setBackground((new Background(new BackgroundFill(game.players.get(i).getcolor(), CornerRadii.EMPTY, Insets.EMPTY))));
            GridPane.setMargin( ll1,new Insets(30, 30, 30, 30));
            ll1.setFont(new Font(24));
            Label ll2=new Label(String.valueOf( game.players.get(i).score));
            ll2.setBackground((new Background(new BackgroundFill(game.players.get(i).getcolor(), CornerRadii.EMPTY, Insets.EMPTY))));
            GridPane.setMargin( ll2,new Insets(30, 30, 30, 30));
            ll2.setFont(new Font(24));
            Label ll3=new Label(String.valueOf(game.players.get(i).getishuman()));
            ll3.setFont(new Font(24));
            ll3.setBackground((new Background(new BackgroundFill(game.players.get(i).getcolor(), CornerRadii.EMPTY, Insets.EMPTY))));
            GridPane.setMargin( ll3,new Insets(30, 30, 30, 30));
            GridPane.setConstraints(ll1, 0, i+1);
            gridPane.getChildren().add(ll1);
            GridPane.setConstraints(ll2, 1, i+1);
            gridPane.getChildren().add(ll2);
            GridPane.setConstraints(ll3, 2, i+1);
            gridPane.getChildren().add(ll3);
        }


        Scene scene = new Scene(gridPane, 800, 600);
        Main.windows.setScene(scene);
        Main.windows.show();

    }

    public void showbestscore()
    {
        try {
            bestscore=Load("bs");
        }
        catch (Exception ex){}
        gridPane=new GridPane();

        Label l1=new Label("Player name");
        Label l2=new Label("Player score");
        Label l3=new Label("Player is human");
        l1.setFont(new Font(24));
        GridPane.setMargin( l1,new Insets(30, 30, 30, 30));
        l2.setFont(new Font(24));
        GridPane.setMargin( l2,new Insets(30, 30, 30, 30));
        l3.setFont(new Font(24));
        GridPane.setMargin( l3,new Insets(30, 30, 30, 30));
        GridPane.setConstraints(l1, 0, 0);
        gridPane.getChildren().add(l1);
        GridPane.setConstraints(l2, 1, 0);
        gridPane.getChildren().add(l2);
        GridPane.setConstraints(l3, 2, 0);
        gridPane.getChildren().add(l3);
        for(int i=0;i<bestscore.bestplayers.size();i++)
        {
            Label ll1=new Label(bestscore.bestplayers.get(i).name);
            ll1.setBackground((new Background(new BackgroundFill(bestscore.bestplayers.get(i).getcolor(), CornerRadii.EMPTY, Insets.EMPTY))));
            GridPane.setMargin( ll1,new Insets(30, 30, 30, 30));
            ll1.setFont(new Font(24));
            Label ll2=new Label(String.valueOf( bestscore.bestplayers.get(i).score));
            ll2.setBackground((new Background(new BackgroundFill(bestscore.bestplayers.get(i).getcolor(), CornerRadii.EMPTY, Insets.EMPTY))));
            GridPane.setMargin( ll2,new Insets(30, 30, 30, 30));
            ll2.setFont(new Font(24));
            Label ll3=new Label(String.valueOf(bestscore.bestplayers.get(i).getishuman()));
            ll3.setFont(new Font(24));
            ll3.setBackground((new Background(new BackgroundFill(bestscore.bestplayers.get(i).getcolor(), CornerRadii.EMPTY, Insets.EMPTY))));
            GridPane.setMargin( ll3,new Insets(30, 30, 30, 30));
            GridPane.setConstraints(ll1, 0, i+1);
            gridPane.getChildren().add(ll1);
            GridPane.setConstraints(ll2, 1, i+1);
            gridPane.getChildren().add(ll2);
            GridPane.setConstraints(ll3, 2, i+1);
            gridPane.getChildren().add(ll3);
        }


        Scene scene = new Scene(gridPane, 800, 600);
        Main.windows.setScene(scene);
        Main.windows.show();


    }
    public  void afterfinishgame()
    {
        try {
            bestscore=Load("bs");
            bestscore.checknewplayerifisbest(game.players);
            Save("bs");

        }
        catch (Exception ex){bestscore=new bestscore(); bestscore.checknewplayerifisbest(game.players);
            Save("bs");}

    }
    public void Save(String path)
    {
        try {
            FileOutputStream out=new FileOutputStream(path);
            ObjectOutputStream obj=new ObjectOutputStream(out);
            obj.writeObject(bestscore);
            obj.close();
            out.close();
            System.out.println("Save is done");
        }
        catch (IOException ex){ex.printStackTrace();}
    }
    public  bestscore Load(String path)
    {
        bestscore game=null;
        try {
            FileInputStream in=new FileInputStream(path);
            ObjectInputStream obj=new ObjectInputStream(in);
            game=(bestscore) obj.readObject();
            obj.close();
            in.close();
        }
        catch (Exception x){System.out.println("Can't find the file");return null;}
        System.out.println("Load is done");
        return game;
    }




}
