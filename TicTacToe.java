import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Optional;
import java.util.Random;

public class TicTacToeFX extends Application {

    Button[][] buttons = new Button[3][3];
    boolean playerX = true;
    boolean vsSystem = false;
    Random rand = new Random();

    @Override
    public void start(Stage stage) {

        // -------- Game Mode Dialog --------
        ChoiceDialog<String> dialog =
                new ChoiceDialog<>("Play with System", "Play with System", "Play with Player");
        dialog.setTitle("Tic Tac Toe");
        dialog.setHeaderText("Choose Game Mode");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get().equals("Play with System")) {
            vsSystem = true;
        }

        // -------- Grid --------
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setStyle("-fx-background-color: linear-gradient(to bottom, #232526, #414345);");

        DropShadow shadow = new DropShadow(15, Color.BLACK);

        // -------- Buttons --------
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Button btn = new Button("");
                btn.setFont(Font.font("Verdana", 40));
                btn.setPrefSize(120, 120);
                btn.setEffect(shadow);

                btn.setStyle(
                        "-fx-background-color: linear-gradient(#ffffff, #dddddd);" +
                        "-fx-border-color: #000000; -fx-border-width: 2;"
                );

                int r = i, c = j;
                btn.setOnAction(e -> handleMove(btn, r, c));

                buttons[i][j] = btn;
                grid.add(btn, j, i);
            }
        }

        Scene scene = new Scene(grid, 420, 420);
        stage.setTitle("Tic Tac Toe - JavaFX");
        stage.setScene(scene);
        stage.show();
    }

    // -------- Handle Move --------
    void handleMove(Button btn, int r, int c) {

        if (!btn.getText().isEmpty()) return;

        String mark = playerX ? "X" : "O";
        btn.setText(mark);
        btn.setTextFill(playerX ? Color.BLUE : Color.RED);

        if (checkWin(mark)) {
            endGame("Player " + mark + " wins!");
            return;
        }

        if (isBoardFull()) {
            endGame("It's a Draw!");
            return;
        }

        playerX = !playerX;

        if (vsSystem && !playerX) {
            systemMove();
            playerX = true;
        }
    }

    // -------- System Move --------
    void systemMove() {
        while (true) {
            int r = rand.nextInt(3);
            int c = rand.nextInt(3);

            if (buttons[r][c].getText().isEmpty()) {
                buttons[r][c].setText("O");
                buttons[r][c].setTextFill(Color.RED);
                break;
            }
        }

        if (checkWin("O")) {
            endGame("System wins!");
        } else if (isBoardFull()) {
            endGame("It's a Draw!");
        }
    }

    // -------- Check Win --------
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

        return (buttons[0][0].getText().equals(s) &&
                buttons[1][1].getText().equals(s) &&
                buttons[2][2].getText().equals(s)) ||
               (buttons[0][2].getText().equals(s) &&
                buttons[1][1].getText().equals(s) &&
                buttons[2][0].getText().equals(s));
    }

    boolean isBoardFull() {
        for (Button[] row : buttons)
            for (Button b : row)
                if (b.getText().isEmpty())
                    return false;
        return true;
    }

    void endGame(String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
