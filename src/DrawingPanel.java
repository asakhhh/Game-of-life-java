import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawingPanel extends JPanel {

    private GameOfLife game;

    public DrawingPanel(GameOfLife game) {
        super();
        this.game = game;
    }

    public void paint(Graphics g) {
        super.paint(g);
        this.game.drawGrid(g);
    }
}
