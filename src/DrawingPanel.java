import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DrawingPanel extends JPanel {

    private GameOfLife game;

    /**
     * Initializes with the <code>GameOfLife</code> instance to later draw its grid.
     * @param game <code>GameOfLife</code> instance
     */
    public DrawingPanel(GameOfLife game) {
        super();
        this.game = game;
    }

    /**
     * Special method automatically called to draw. Draws the game grid.
     * @param g the <code>Graphics</code> context in which to paint
     */
    public void paint(Graphics g) {
        super.paint(g);
        this.game.drawGrid(g);
    }
}
