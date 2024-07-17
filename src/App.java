import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth =600;
        int boardHeight = boardWidth;

        JFrame frame = new JFrame("Snake With Isura");
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        SnakeGame snakeGame = new SnakeGame(boardHeight, boardWidth);
        frame.add(snakeGame);
        frame.pack();
        snakeGame.requestFocus();
    }
}
