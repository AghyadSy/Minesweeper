package GameLogic;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;

public class drawasync implements Runnable {
    Game game;
    GridPane gridPane;
    int couter=0;
public drawasync(Game game,GridPane gridPane)
{
    this.game=game;
    this.gridPane=gridPane;
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

    @Override
    public void run() {

        for ( int i = 0; i < game.moves.size(); i++) {

            try {
                Thread.sleep(1500);
            } catch (InterruptedException ex) {
            }
            Platform.runLater(new Runnable() {
                @Override
                public void run() {

                    try {
                        ((Button) getNodeByRowColumnIndex(game.moves.get(couter).row, game.moves.get(couter).column, gridPane)).setBackground(new Background(new BackgroundFill(game.cells[game.moves.get(couter).row][game.moves.get(couter).column].getplayerEdit().getcolor(), CornerRadii.EMPTY, Insets.EMPTY)));
                    } catch (NullPointerException ex) {
                    }
                    ((Button) getNodeByRowColumnIndex(game.moves.get(couter).row, game.moves.get(couter).column, gridPane)).setText(checkcellstate(game.cells[game.moves.get(couter).row][game.moves.get(couter).column]));
                    couter++;
                }
            });

        }







    }
}
