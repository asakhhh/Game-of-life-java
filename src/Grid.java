import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Grid {
    private ArrayList<ArrayList<Boolean>> grid;
    private final int size, cellSize;
    private int drawX, drawY;

    /**
     * Initializes the grid and sets default values. <br/>
     * <p>
     * size - size of the grid <br/>
     * cellSize - size of one cell in pixels <br/>
     * drawX, drawY - drawing coordinates of the top-left corner
     */
    public Grid() {
        size = 50;
        cellSize = 14;
        drawX = 500;
        drawY = 120;
        clear(); // is used to initialize empty grid
    }

    /**
     * Initializes the grid as a new 2D ArrayList and sets all cells as dead - False
     */
    public void clear() { // initializes the grid and clears
        grid = new ArrayList<>();
        for (int y = 0; y < size; y++) {
            ArrayList<Boolean> row = new ArrayList<>();
            for (int x = 0; x < size; x++) {
                row.add(false);
            }
            grid.add(row);
        }
    }

    /**
     * Method used to draw the grid. Is called by DrawingPanel.
     * @param g Graphics object used to draw - is passed from DrawingPanel's paint method
     */
    public void draw(Graphics g) {
        for (int y = 0; y <= size; y++) {
            g.drawLine(drawX, drawY + (cellSize + 1) * y, drawX + size * (cellSize + 1), drawY + (cellSize + 1) * y);
        }
        for (int x = 0; x <= size; x++) {
            g.drawLine(drawX + (cellSize + 1) * x, drawY, drawX + (cellSize + 1) * x, drawY + (cellSize + 1) * size);
        }
        g.setColor(Color.green);
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                if (isAlive(y, x)) {
                    g.fillRect(drawX + 1 + (cellSize + 1) * x, drawY + 1 + (cellSize + 1) * y, cellSize, cellSize);
                }
            }
        }
        g.setColor(Color.black);
    }

    /**
     * Private helper method that counts the number of live cells around a cell.
     * @param y y-coordinate of the cell
     * @param x x-coordinate of the cell
     * @return the number of live cells around a cell, excluding the cell itself
     */
    private int countAdjacentAlive(int y, int x) {
        int result = 0;
        for (int ny = y - 1; ny <= y + 1; ny++) {
            for (int nx = x - 1; nx <= x + 1; nx++) {
                if (ny >= 0 && ny < size && nx >= 0 && nx < size && isAlive(ny, nx)) {
                    result++;
                }
            }
        }
        if (isAlive(y, x)) {
            result--;
        }
        return result;
    }

    /**
     * Private helper method used to check whether a cell is alive with given coordinates
     * @param y y-coordinate of the cell
     * @param x x-coordinate of the cell
     * @return true if the cell is alive, false otherwise
     */
    private boolean isAlive(int y, int x) {
        return grid.get(y).get(x);
    }

    /**
     * Computes and changes the grid to its next generation. <br/>
     * Rules:<br/>
     * <ul>
     *     <li>Live cell remains live if it has 2 or 3 neighbours, dies otherwise;</li>
     *     <li>Dead cell becomes alive if it has exactly 3 neighbours, remains dead otherwise.</li>
     * </ul>
     */
    public void evolve() {
        ArrayList<ArrayList<Boolean>> newGrid = new ArrayList<>();
        for (int y = 0; y < size; y++) {
            ArrayList<Boolean> newRow = new ArrayList<>();
            for (int x = 0; x < size; x++) {
                int adjAlive = countAdjacentAlive(y, x);
                if (isAlive(y, x) && (adjAlive < 2 || adjAlive > 3)) {
                    newRow.add(false);
                } else if (!isAlive(y, x) && adjAlive == 3) {
                    newRow.add(true);
                } else {
                    newRow.add(isAlive(y, x));
                }
            }
            newGrid.add(newRow);
        }
        grid = newGrid;
    }

    /**
     * Fills the grid randomly. Aims to make 30% of the grid live.
     */
    public void fillRandom() {
        Random random = new Random();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                grid.get(y).set(x, random.nextInt(10) < 3); // ~30% population
            }
        }
    }

    /**
     * Tries to update a cell based on a mouse click. If press and release happened in the same valid cell,
     * the cell is toggled. Nothing happens otherwise.
     * @param mouseDownY y-coordinate of the mouse press
     * @param mouseDownX x-coordinate of the mouse press
     * @param mouseUpY y-coordinate of the mouse release
     * @param mouseUpX x-coordinate of the mouse release
     */
    public void updateMouse(int mouseDownY, int mouseDownX, int mouseUpY, int mouseUpX) {
        mouseDownY -= drawY;
        mouseDownX -= drawX;
        mouseUpY -= drawY;
        mouseUpX -= drawX;
        if (mouseDownY < 0 || mouseUpY < 0 || mouseDownX < 0 || mouseUpX < 0 ||
                mouseDownY % (cellSize + 1) == 0 || mouseDownX % (cellSize + 1) == 0 ||
                mouseUpY % (cellSize + 1) == 0 || mouseUpX % (cellSize + 1) == 0) {
            return;
        }
        int cellDownY = mouseDownY / (cellSize + 1);
        int cellDownX = mouseDownX / (cellSize + 1);
        int cellUpY = mouseUpY / (cellSize + 1);
        int cellUpX = mouseUpX / (cellSize + 1);
        if (cellDownY == cellUpY && cellDownX == cellUpX) {
            update(cellDownY, cellDownX);
        }
    }

    /**
     * Updates a cell based on its coordinates.
     * @param y y-coordinate of the cell
     * @param x x-coordinate of the cell
     */
    public void update(int y, int x) {
        grid.get(y).set(x, !isAlive(y, x));
    }

    /**
     * Initializes Gosper's Glider gun on the grid. It is an infinite cycle.
     */
    public void fillGosper() {
        clear();              update(1, 24);  update(1, 25);  update(2, 24);  update(2, 25);
        update(2, 26);  update(3, 10);  update(3, 26);  update(3, 27);  update(3, 29);
        update(4, 8);   update(4, 10);  update(4, 15);  update(4, 16);  update(4, 17);
        update(4, 26);  update(4, 29);  update(4, 35);  update(4, 36);  update(5, 7);
        update(5, 9);   update(5, 26);  update(5, 27);  update(5, 29);  update(5, 35);
        update(5, 36);  update(6, 2);   update(6, 6);   update(6, 9);   update(6, 17);
        update(6, 20);  update(6, 21);  update(6, 24);  update(6, 25);  update(6, 26);
        update(7, 1);   update(7, 2);   update(7, 7);   update(7, 9);   update(7, 17);
        update(7, 21);  update(7, 24);  update(7, 25);  update(8, 8);   update(8, 10);
        update(8, 17);  update(8, 20);  update(9, 10);  update(9, 19);  update(9, 20);
    }
}
