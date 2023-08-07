package com.example.tictactoegame.application;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

import static com.example.tictactoegame.application.Main.CONTROLLER_GATE;
import static com.example.tictactoegame.controllers.GameController.CROSS;

public class GameButton {
    private static final String SIGN_CROSS = "X";
    private static final String SIGN_ZERO = "0";
    private final int number;
    private final Button button = new Button();

    private final Text text = new Text();
    public GameButton(int number) {
        this.number = number;
        button.setMinSize(150,150);
        button.setFocusTraversable(false);
        button.setOnAction(CONTROLLER_GATE.sendRequestGetGameButtonListenerFromUIController(this));
        text.setStyle("-fx-font-size: 30");
        resetSign();
    }
    public Button getButton() {
        return button;
    }

    public Text getText() {return text;}
    public int getNumber() {
        return number;
    }

    public void setSign(int whoseMove) {
        text.setText(whoseMove == CROSS ? SIGN_CROSS : SIGN_ZERO);
    }

    public void resetSign() {
        text.setText(null);
    }
}
