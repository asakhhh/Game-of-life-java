import java.awt.*;
import java.io.File;
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

    public void updateMouse(int mouseY, int mouseX) {
        mouseY -= drawY;
        mouseX -= drawX;
        if (mouseY < 0 || mouseX < 0 || mouseY % (cellSize + 1) == 0 || mouseX % (cellSize + 1) == 0) {
            return;
        }
        int cellY = mouseY / (cellSize + 1);
        int cellX = mouseX / (cellSize + 1);
        update(cellY, cellX);
    }

    public void update(int y, int x) {
        grid.get(y).set(x, !isAlive(y, x));
    }

    public void fillGosper() {
        clear();
        update(1, 24);
        update(1, 25);
        update(2, 24);
        update(2, 25);
        update(2, 26);
        update(3, 10);
        update(3, 26);
        update(3, 27);
        update(3, 29);
        update(4, 8);
        update(4, 10);
        update(4, 15);
        update(4, 16);
        update(4, 17);
        update(4, 26);
        update(4, 29);
        update(4, 35);
        update(4, 36);
        update(5, 7);
        update(5, 9);
        update(5, 26);
        update(5, 27);
        update(5, 29);
        update(5, 35);
        update(5, 36);
        update(6, 2);
        update(6, 6);
        update(6, 9);
        update(6, 17);
        update(6, 20);
        update(6, 21);
        update(6, 24);
        update(6, 25);
        update(6, 26);
        update(7, 1);
        update(7, 2);
        update(7, 7);
        update(7, 9);
        update(7, 17);
        update(7, 21);
        update(7, 24);
        update(7, 25);
        update(8, 8);
        update(8, 10);
        update(8, 17);
        update(8, 20);
        update(9, 10);
        update(9, 19);
        update(9, 20);
    }
}
