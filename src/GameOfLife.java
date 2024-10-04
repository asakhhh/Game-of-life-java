import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameOfLife extends JFrame {
    private DrawingPanel drawingPanel;
    private boolean running = false;
    private Grid grid;
    private int frameCount = 0;
    private double generationsPerSec, nextGenerationFrame;

    public boolean isRunning() {
        return running;
    }

    public void pause() {
        running = false;
    }

    public void resume() {
        running = true;
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GameOfLife frame = new GameOfLife();
                    frame.setVisible(true);
                    frame.addContents();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public GameOfLife() {
        super("Game of Life");

        grid = new Grid();
        this.frameCount = 0;
        this.generationsPerSec = 3;
        this.nextGenerationFrame = 50.0 / this.generationsPerSec;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds(300, 100, screen.width - 600, screen.height - 150);
        drawingPanel = new DrawingPanel(this);
        drawingPanel.setLayout(null);
        setContentPane(drawingPanel);

        Animator animator = new Animator(this);
        getContentPane().addMouseListener(animator);
        Timer timer = new Timer(20, animator);
        timer.start();
    }

    public void addContents() {
        JLabel text = new JLabel("Welcome to a simple simulation of Conway's Game of Life!");
        JLabel text2 = new JLabel("The grid size is 50x50. " +
                "While the game is not running, the grid can be edited manually by clicking the cells.");
        text.setFont(new Font("Serif", Font.PLAIN, 18));
        text.setBounds(30, 20, 2000, 30);
        this.add(text);
        text2.setFont(new Font("Serif", Font.PLAIN, 18));
        text2.setBounds(30, 50, 2000, 30);
        this.add(text2);

        JLabel pauseButtonText = new JLabel("Start/Pause/Resume the game");
        pauseButtonText.setFont(new Font("Serif", Font.PLAIN, 18));
        pauseButtonText.setBounds(90, 150, 300, 40);
        this.add(pauseButtonText);

        JButton pauseButton = new JButton("Start");
        pauseButton.setBounds(365, 150, 115, 40);
        this.add(pauseButton);
        pauseButton.setBackground(Color.green);
        pauseButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                running = !running;
                pauseButton.setText(isRunning() ? "Pause" : "Resume");
            }
        });

        JLabel clearButtonText = new JLabel("Clear the grid");
        clearButtonText.setFont(new Font("Serif", Font.PLAIN, 18));
        clearButtonText.setBounds(220, 200, 300, 40);
        this.add(clearButtonText);

        JButton clearButton = new JButton("Clear");
        clearButton.setBounds(365, 200, 115, 40);
        this.add(clearButton);
        clearButton.setBackground(Color.green);
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                running = false;
                grid.clear();
                pauseButton.setText("Start");
            }
        });

        JLabel randomButtonText = new JLabel("Random fill");
        randomButtonText.setFont(new Font("Serif", Font.PLAIN, 18));
        randomButtonText.setBounds(240, 250, 300, 40);
        this.add(randomButtonText);

        JButton randomButton = new JButton("Set Random");
        randomButton.setBounds(365, 250, 115, 40);
        this.add(randomButton);
        randomButton.setBackground(Color.green);
        randomButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                running = false;
                grid.fillRandom();
                pauseButton.setText("Start");
            }
        });

        JLabel gosperButtonText = new JLabel("Gosper's glider gun");
        gosperButtonText.setFont(new Font("Serif", Font.PLAIN, 18));
        gosperButtonText.setBounds(175, 300, 300, 40);
        this.add(gosperButtonText);

        JButton gosperButton = new JButton("Gosper gun");
        gosperButton.setBounds(365, 300, 115, 40);
        this.add(gosperButton);
        gosperButton.setBackground(Color.green);
        gosperButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                running = false;
                grid.fillGosper();
                pauseButton.setText("Start");
            }
        });

        JLabel switchGenPerSecText = new JLabel("Choose animation speed:");
        switchGenPerSecText.setFont(new Font("Serif", Font.PLAIN, 18));
        switchGenPerSecText.setBounds(160, 370, 300, 40);
        this.add(switchGenPerSecText);
        JLabel switchGenPerSecText2 = new JLabel("( generations per second )");
        switchGenPerSecText2.setFont(new Font("Serif", Font.PLAIN, 18));
        switchGenPerSecText2.setBounds(160, 390, 300, 40);
        this.add(switchGenPerSecText2);

        JButton[] options = new JButton[5]; // 0.5, 1, 3, 10, 30
        Double[] val = {0.5, 1.0, 3.0, 10.0, 30.0};
        for (int i = 0; i < 5; i++) {
            JButton button = new JButton(val[i].toString());
            button.setBackground(Color.cyan);
            if (i == 2) {
                button.setBackground(Color.blue);
            }
            button.setBounds(39 + 90 * i, 430, 80, 40);
            double newGenerationsPerSec = val[i];
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    frameCount = 0;
                    generationsPerSec = newGenerationsPerSec;
                    nextGenerationFrame = 50.0 / generationsPerSec;
                    for (int i = 0; i < 5; i++) {
                        options[i].setBackground(Color.cyan);
                    }
                    button.setBackground(Color.blue);
                }
            });
            this.add(button);
            options[i] = button;
        }
    }

    public void drawGrid(Graphics g) {
        this.grid.draw(g);
    }

    public void evolve() {
        incrementFrameCount();
        if (frameCount == (int)nextGenerationFrame) {
            nextGenerationFrame = nextGenerationFrame + 50.0 / generationsPerSec;
            if (nextGenerationFrame > 1001) {
                nextGenerationFrame -= 1000;
            }
            if (running) {
                grid.evolve();
            }
        }
    }

    public void incrementFrameCount() {
        frameCount = frameCount % 1000 + 1;
    }

    public int getFrameCount() {
        return frameCount;
    }

    public double getGenerationsPerSec() {
        return generationsPerSec;
    }

    public double getNextGenerationFrame() {
        return nextGenerationFrame;
    }

    public void updateCell(int mouseY, int mouseX) {
        if (!running) {
            grid.updateMouse(mouseY, mouseX);
        }
    }
}
