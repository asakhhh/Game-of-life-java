import java.awt.*;
import java.util.ArrayList;
import java.util.Random;

public class Grid {
    private ArrayList<ArrayList<Boolean>> grid;
    private final int size, cellSize;
    private int drawX, drawY;

    public Grid() {
        size = 50;
        cellSize = 14;
        drawX = 500;
        drawY = 120;
        clear();
    }

    public void clear() {
        grid = new ArrayList<>();
        for (int y = 0; y < size; y++) {
            ArrayList<Boolean> row = new ArrayList<>();
            for (int x = 0; x < size; x++) {
                row.add(false);
            }
            grid.add(row);
        }
    }

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

    private boolean isAlive(int y, int x) {
        return grid.get(y).get(x);
    }

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

    public void fillRandom() {
        Random random = new Random();
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                grid.get(y).set(x, random.nextInt(10) < 4); // ~40% population
            }
        }
    }

    public void update(int mouseY, int mouseX) {
        mouseY -= drawY;
        mouseX -= drawX;
        if (mouseY < 0 || mouseX < 0 || mouseY % (cellSize + 1) == 0 || mouseX % (cellSize + 1) == 0) {
            return;
        }
        int cellY = mouseY / (cellSize + 1);
        int cellX = mouseX / (cellSize + 1);
        grid.get(cellY).set(cellX, !isAlive(cellY, cellX));
    }

    public void fillGosper() {

    }
}
