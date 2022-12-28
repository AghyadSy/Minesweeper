package sample;
import GameLogic.*;
import GameLogic.Cell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Controller {

    @FXML
    TextField Nrowstxt;

    @FXML
    TextField NcOLtxt;

    @FXML
    TextField Nminestxt;
    @FXML
    TextField playerNametxt;

    @FXML
    CheckBox ish;
    @FXML
    ColorPicker CP;

    @FXML
    CheckBox isuseshield;

    @FXML
    TextField shieldnumber;

    @FXML
    TextField shieldnumberingame;
    @FXML
    TextField roletime;

    ArrayList<Player> Players = new ArrayList<>();
    GridPane grid;
    Game game1;
    Thread t1;
    RolesManagement RM;
    IOManagement ioManagement;
    Label l = new Label();
    Button savebtn = new Button();
    Button qicksavebtn = new Button();

    public void bestscorepress(javafx.event.ActionEvent actionEvent) {
        ScoreManagement sm=new ScoreManagement(game1,Main.windows );
sm.showbestscore();

    }

    public void browsgamebtn(javafx.event.ActionEvent actionEvent) {


            ioManagement = new IOManagement(game1);
            File file ;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            file= fileChooser.showOpenDialog(Main.windows);
            System.out.println(file.getPath());
            game1 = ioManagement.Load(file.getPath());
            if (game1.getState()==GameState.finish) {
                grid = new GridPane();
                grid.setPadding(new Insets(10, 10, 10, 10));
                for (int i = 0; i < game1.cells.length; i++) {
                    for (int j = 0; j < game1.cells[0].length; j++) {
                        Button button = new Button();
                        button.setFont(new Font(14));
                        button.setMinSize(32, 32);
                        button.setText("   ");
                        GridPane.setConstraints(button, i, j);
                        grid.getChildren().add(button);
                    }
                }
                Scene scene = new Scene(grid, 800, 600);
                Main.windows.setScene(scene);
                Main.windows.show();
                drawasync drawdrawasync=new drawasync(game1,grid );
                Thread tt=new Thread(drawdrawasync);
                tt.start();
            }
            else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Warning");
                alert.setHeaderText("File error");
                alert.setContentText("the open game is not finish");
                alert.show();
            }
    }

    public void qickloadbtnpress(javafx.event.ActionEvent actionEvent)
    {
        ioManagement = new IOManagement(game1);
        game1 = ioManagement.Load("save");
        l.setText(game1.GetPlayerDetils());
        l.setBackground((new Background(new BackgroundFill(game1.GetPlayerColor(), CornerRadii.EMPTY, Insets.EMPTY))));
        savebtn.setText("Save");
        savebtn.setOnAction(e -> {
            ioManagement = new IOManagement(game1);
            File file2 ;
            FileChooser fileChooser2 = new FileChooser();
            fileChooser2.setTitle("Save file");
            file2= fileChooser2.showSaveDialog(Main.windows);
            System.out.println(file2.getPath());
            ioManagement.Save(file2.getPath());
        });

        qicksavebtn.setText("Qick save");
        qicksavebtn.setOnAction(e -> {
            ioManagement = new IOManagement(game1);
            ioManagement.Save("save");
        });
        grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        for (int i = 0; i < game1.cells.length; i++) {
            for (int j = 0; j < game1.cells[0].length; j++) {
                Button button = new Button();
                button.setFont(new Font(14));
                button.setMinSize(32, 32);
                button.setText("   ");
                button.setOnMousePressed(e ->
                {
                    if (e.isPrimaryButtonDown()) {
                        game1.OpenCell(new Location(GridPane.getRowIndex((Button) e.getSource()), GridPane.getColumnIndex((Button) e.getSource())));
                        draw();
                        l.setText(game1.GetPlayerDetils());
                        l.setBackground((new Background(new BackgroundFill(game1.GetPlayerColor(), CornerRadii.EMPTY, Insets.EMPTY))));
                        t1.interrupt();
                        RM = new RolesManagement(game1, l);
                        t1 = new Thread(RM);
                        t1.start();
                    }
                    if (e.isSecondaryButtonDown()) {
                        game1.MarkCell(new Location(GridPane.getRowIndex((Button) e.getSource()), GridPane.getColumnIndex((Button) e.getSource())));
                        draw();
                        l.setText(game1.GetPlayerDetils());
                        l.setBackground((new Background(new BackgroundFill(game1.GetPlayerColor(), CornerRadii.EMPTY, Insets.EMPTY))));
                        t1.interrupt();
                        RM = new RolesManagement(game1, l);
                        t1 = new Thread(RM);
                        t1.start();
                    }
                });
                GridPane.setConstraints(button, i, j);
                grid.getChildren().add(button);
                if (i == game1.cells.length - 1 && j == game1.cells[0].length - 1) {
                    GridPane.setConstraints(l, i + 2, 0);
                    grid.getChildren().add(l);

                    GridPane.setConstraints(qicksavebtn, i + 4, 0);
                    grid.getChildren().add(qicksavebtn);

                    GridPane.setConstraints(savebtn, i + 8, 0);
                    grid.getChildren().add(savebtn);
                }
            }

        }
        Scene scene = new Scene(grid, 800, 600);
        Main.windows.setScene(scene);
        Main.windows.show();
        RM = new RolesManagement(game1, l);
        draw();
        t1 = new Thread(RM);
        t1.start();
    }

    public void loadbtnpress(javafx.event.ActionEvent actionEvent)
    {

        try {
            ioManagement = new IOManagement(game1);
            File file ;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Open file");
            file= fileChooser.showOpenDialog(Main.windows);
            System.out.println(file.getPath());
            game1 = ioManagement.Load(file.getPath());
            l.setText(game1.GetPlayerDetils());
            l.setBackground((new Background(new BackgroundFill(game1.GetPlayerColor(), CornerRadii.EMPTY, Insets.EMPTY))));
            savebtn.setText("Save");
            savebtn.setOnAction(e -> {
                ioManagement = new IOManagement(game1);
                File file2 ;
                FileChooser fileChooser2 = new FileChooser();
                fileChooser2.setTitle("Save file");
                file2= fileChooser2.showSaveDialog(Main.windows);
                System.out.println(file2.getPath());
                ioManagement.Save(file2.getPath());
            });

            qicksavebtn.setText("Qick save");
            qicksavebtn.setOnAction(e -> {
                ioManagement = new IOManagement(game1);
                ioManagement.Save("save");
            });
            grid = new GridPane();
            grid.setPadding(new Insets(10, 10, 10, 10));
            for (int i = 0; i < game1.cells.length; i++) {
                for (int j = 0; j < game1.cells[0].length; j++) {
                    Button button = new Button();
                    button.setFont(new Font(14));
                    button.setMinSize(32, 32);
                    button.setText("   ");
                    button.setOnMousePressed(e ->
                    {
                        if (e.isPrimaryButtonDown()) {
                            game1.OpenCell(new Location(GridPane.getRowIndex((Button) e.getSource()), GridPane.getColumnIndex((Button) e.getSource())));
                            draw();
                            l.setText(game1.GetPlayerDetils());
                            l.setBackground((new Background(new BackgroundFill(game1.GetPlayerColor(), CornerRadii.EMPTY, Insets.EMPTY))));
                            t1.interrupt();
                            RM = new RolesManagement(game1, l);
                            t1 = new Thread(RM);
                            t1.start();
                        }
                        if (e.isSecondaryButtonDown()) {
                            game1.MarkCell(new Location(GridPane.getRowIndex((Button) e.getSource()), GridPane.getColumnIndex((Button) e.getSource())));
                            draw();
                            l.setText(game1.GetPlayerDetils());
                            l.setBackground((new Background(new BackgroundFill(game1.GetPlayerColor(), CornerRadii.EMPTY, Insets.EMPTY))));
                            t1.interrupt();
                            RM = new RolesManagement(game1, l);
                            t1 = new Thread(RM);
                            t1.start();
                        }
                    });
                    GridPane.setConstraints(button, i, j);
                    grid.getChildren().add(button);
                    if (i == game1.cells.length - 1 && j == game1.cells[0].length - 1) {
                        GridPane.setConstraints(l, i + 2, 0);
                        grid.getChildren().add(l);

                        GridPane.setConstraints(qicksavebtn, i + 4, 0);
                        grid.getChildren().add(qicksavebtn);

                        GridPane.setConstraints(savebtn, i + 8, 0);
                        grid.getChildren().add(savebtn);
                    }
                }

            }
            Scene scene = new Scene(grid, 800, 600);
            Main.windows.setScene(scene);
            Main.windows.show();
            RM = new RolesManagement(game1, l);
            draw();
            t1 = new Thread(RM);
            t1.start();
        }
        catch (Exception ex){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Warning");
            alert.setHeaderText("File error");
            alert.setContentText("the open file is not true");
            alert.show();
        }

    }

    public void pressnewgamebtn(javafx.event.ActionEvent actionEvent) {

        game1 = new Game(Integer.parseInt(Nrowstxt.getText()), Integer.parseInt(NcOLtxt.getText()), Integer.parseInt(Nminestxt.getText()), Integer.parseInt(shieldnumberingame.getText()), Integer.parseInt(
                roletime.getText()), Players);
        l.setText(game1.GetPlayerDetils());
        l.setBackground((new Background(new BackgroundFill(game1.GetPlayerColor(), CornerRadii.EMPTY, Insets.EMPTY))));
        savebtn.setText("Save");
        savebtn.setOnAction(e -> {
             ioManagement = new IOManagement(game1);
            File file ;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save file");
            file= fileChooser.showSaveDialog(Main.windows);
            System.out.println(file.getPath());
            ioManagement.Save(file.getPath());
        });

        qicksavebtn.setText("Qick save");
        qicksavebtn.setOnAction(e -> {
            ioManagement = new IOManagement(game1);
            ioManagement.Save("save");
        });


        grid = new GridPane();
        grid.setPadding(new Insets(10, 10, 10, 10));
        for (int i = 0; i < Integer.parseInt(Nrowstxt.getText()); i++) {
            for (int j = 0; j < Integer.parseInt(NcOLtxt.getText()); j++) {
                Button button = new Button();
                button.setFont(new Font(14));
                button.setMinSize(32, 32);
                button.setText("   ");
                button.setOnMousePressed(e ->
                {
                    if (e.isPrimaryButtonDown()) {
                        game1.OpenCell(new Location(GridPane.getRowIndex((Button) e.getSource()), GridPane.getColumnIndex((Button) e.getSource())));
                        draw();
                        l.setText(game1.GetPlayerDetils());
                        l.setBackground((new Background(new BackgroundFill(game1.GetPlayerColor(), CornerRadii.EMPTY, Insets.EMPTY))));
                        t1.interrupt();
                        RM = new RolesManagement(game1, l);
                        t1 = new Thread(RM);
                        t1.start();
                    }
                    if (e.isSecondaryButtonDown()) {
                        game1.MarkCell(new Location(GridPane.getRowIndex((Button) e.getSource()), GridPane.getColumnIndex((Button) e.getSource())));
                        draw();
                        l.setText(game1.GetPlayerDetils());
                        l.setBackground((new Background(new BackgroundFill(game1.GetPlayerColor(), CornerRadii.EMPTY, Insets.EMPTY))));
                        t1.interrupt();
                        RM = new RolesManagement(game1, l);
                        t1 = new Thread(RM);
                        t1.start();
                    }
                });
                GridPane.setConstraints(button, i, j);
                grid.getChildren().add(button);
                if (i == Integer.parseInt(Nrowstxt.getText()) - 1 && j == Integer.parseInt(NcOLtxt.getText()) - 1) {
                    GridPane.setConstraints(l, i + 2, 0);
                    grid.getChildren().add(l);

                    GridPane.setConstraints(qicksavebtn, i + 4, 0);
                    grid.getChildren().add(qicksavebtn);

                    GridPane.setConstraints(savebtn, i + 8, 0);
                    grid.getChildren().add(savebtn);
                }
            }

        }
        Scene scene = new Scene(grid, 800, 600);
        Main.windows.setScene(scene);
        Main.windows.show();
        RM = new RolesManagement(game1, l);
        t1 = new Thread(RM);
        t1.start();


    }

    public void pressnaddbtn(javafx.event.ActionEvent actionEvent) {
        Players.add(new Player(playerNametxt.getText(), CP.getValue(), ish.isSelected(), isuseshield.isSelected(), Integer.parseInt(shieldnumber.getText())));
    }

    public String checkcellstate(Cell c) {
        if (c.GetState() == CellState.number)
            return c.GetNumber().toString();
        if (c.GetState() == CellState.mine)
            return " ";
        if (c.GetState() == CellState.mineopen)
            return "¤";
        if (c.GetState() == CellState.mineandmark)
            return "⁋";
        if (c.GetState() == CellState.shield)
            return "";
        if (c.GetState() == CellState.shieldopen)
            return "♠";
        if (c.GetState() == CellState.shielsandmark)
            return "⁋";
        if (c.GetState() == CellState.mark)
            return "⁋";
        if (c.GetState() == CellState.blank)
            return "";
        if (c.GetState() == CellState.open)
            return "";
        else return "";
    }

    public void checkshields() {
        if (isuseshield.isSelected() == false) {
            shieldnumber.setText("0");
            shieldnumber.setDisable(true);
        } else {
            shieldnumber.setDisable(false);
        }
    }

    public void draw() {
        if (game1.getState()==GameState.onplay) {
            for (int i = 0; i < game1.cells.length; i++) {
                for (int j = 0; j < game1.cells[0].length; j++) {
                    try {
                        ((Button) getNodeByRowColumnIndex(i, j, grid)).setBackground(new Background(new BackgroundFill(game1.cells[i][j].getplayerEdit().getcolor(), CornerRadii.EMPTY, Insets.EMPTY)));
                    } catch (NullPointerException ex) {
                    }
                    ((Button) getNodeByRowColumnIndex(i, j, grid)).setText(checkcellstate(game1.cells[i][j]));
                }
            }
        }
        else {
            for (int i = 0; i < game1.cells.length; i++) {
                for (int j = 0; j < game1.cells[0].length; j++) {
                    try {
                        ((Button) getNodeByRowColumnIndex(i, j, grid)).setBackground(new Background(new BackgroundFill(game1.cells[i][j].getplayerEdit().getcolor(), CornerRadii.EMPTY, Insets.EMPTY)));
                    } catch (NullPointerException ex) {
                    }
                    ((Button) getNodeByRowColumnIndex(i, j, grid)).setText(checkcellstate(game1.cells[i][j]));
                }
            }
            ioManagement = new IOManagement(game1);
            Date date = new Date();
            String strDateFormat = "hh-mm-ss";
            DateFormat dateFormat = new SimpleDateFormat(strDateFormat);
            String formattedDate= dateFormat.format(date);
            ioManagement.Save("Display game "+formattedDate);
        ScoreManagement sm=new ScoreManagement(game1,Main.windows );
        sm.showgamescore();
        sm.afterfinishgame();
        }
        }




    public Node getNodeByRowColumnIndex (final int row, final int column, GridPane gridPane) {
        Node result = null;
        ObservableList<Node> childrens = gridPane.getChildren();
        for (Node node : childrens) {
            if(gridPane.getRowIndex(node) == row && gridPane.getColumnIndex(node) == column) {
                result = node;
                break;
            }
        }
        return result;
    }
}
