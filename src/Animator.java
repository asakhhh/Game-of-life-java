import java.awt.event.*;

public class Animator implements ActionListener, MouseListener {
    private GameOfLife game;
    private int clickX, clickY;

    /**
     * Initializes with the game frame to animate with it.
     * @param game <code>GameOfLife</code> instance
     */
    public Animator(GameOfLife game) {
        this.game = game;
    }

    /**
     * <code>ActionListener/<code> interface method - is activated by timer every 20 ms.
     * @param event the event to be processed
     */
    public void actionPerformed(ActionEvent event) {
        this.game.evolve();
        this.game.repaint();
    }

    /**
     * <code>MouseListener</code> interface method. Is not used
     * @param event mouse action
     */
    public void mouseClicked(MouseEvent event) {
    }

    /**
     * <code>MouseListener</code> interface method. Saves the press coordinates to use later on release.
     * @param event mouse action
     */
    public void mousePressed(MouseEvent event) {
        clickX = event.getX();
        clickY = event.getY();
    }

    /**
     * <code>MouseListener</code> interface method. Tries to update cell on mouse release.
     * @param event mouse action
     */
    public void mouseReleased(MouseEvent event) {
        this.game.updateCell(clickY, clickX, event.getY(), event.getX());
    }

    /**
     * <code>MouseListener</code> interface method. Is not used
     * @param event mouse action
     */
    public void mouseEntered(MouseEvent event) {
    }

    /**
     * <code>MouseListener</code> interface method. Is not used
     * @param event mouse action
     */
    public void mouseExited(MouseEvent event) {

    }
}
