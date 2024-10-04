import java.awt.event.*;

public class Animator implements ActionListener, MouseListener {
    private GameOfLife game;
    private int clickX, clickY;

    public Animator(GameOfLife game) {
        this.game = game;
    }

    public void actionPerformed(ActionEvent event) {
        this.game.evolve();
        this.game.repaint();
    }

    public void mouseClicked(MouseEvent event) {
        game.updateCell(event.getY(), event.getX());
    }

    public void mousePressed(MouseEvent event) {
    }

    public void mouseReleased(MouseEvent event) {
    }

    public void mouseEntered(MouseEvent event) {
    }

    public void mouseExited(MouseEvent event) {

    }
}
