import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class TicTacToeGUI extends JFrame implements ActionListener {

    JButton[][] buttons = new JButton[3][3];
    boolean playerX = true;
    boolean vsSystem;
    Random rand = new Random();

    public TicTacToeGUI() {
        // Ask mode
        int choice = JOptionPane.showOptionDialog(
                null,
                "Choose Game Mode",
                "Tic Tac Toe",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new String[]{"Play with System", "Play with Player"},
                null
        );

        vsSystem = (choice == 0);

        setTitle("Tic Tac Toe");
        setSize(400, 400);
        setLayout(new GridLayout(3, 3));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create buttons
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j] = new JButton("");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 60));
                buttons[i][j].addActionListener(this);
                add(buttons[i][j]);
            }
        }

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton btn = (JButton) e.getSource();

        if (!btn.getText().equals(""))
            return;

        btn.setText("X");

        if (checkWin("X")) {
            endGame("Player X wins!");
            return;
        }

        if (isBoardFull()) {
            endGame("It's a Draw!");
            return;
        }

        if (vsSystem) {
            systemMove();
        } else {
            playerX = !playerX;
        }
    }

    // System move (random empty cell)
    void systemMove() {
        while (true) {
            int r = rand.nextInt(3);
            int c = rand.nextInt(3);

            if (buttons[r][c].getText().equals("")) {
                buttons[r][c].setText("O");
                break;
            }
        }

        if (checkWin("O")) {
            endGame("System wins!");
        } else if (isBoardFull()) {
            endGame("It's a Draw!");
        }
    }

    // Check win
    boolean checkWin(String s) {
        for (int i = 0; i < 3; i++) {
            if (buttons[i][0].getText().equals(s) &&
                buttons[i][1].getText().equals(s) &&
                buttons[i][2].getText().equals(s))
                return true;

            if (buttons[0][i].getText().equals(s) &&
                buttons[1][i].getText().equals(s) &&
                buttons[2][i].getText().equals(s))
                return true;
        }

        if (buttons[0][0].getText().equals(s) &&
            buttons[1][1].getText().equals(s) &&
            buttons[2][2].getText().equals(s))
            return true;

        if (buttons[0][2].getText().equals(s) &&
            buttons[1][1].getText().equals(s) &&
            buttons[2][0].getText().equals(s))
            return true;

        return false;
    }

    boolean isBoardFull() {
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (buttons[i][j].getText().equals(""))
                    return false;
        return true;
    }

    void endGame(String message) {
        JOptionPane.showMessageDialog(this, message);
        System.exit(0);
    }

    public static void main(String[] args) {
        new TicTacToeGUI();
    }
}
