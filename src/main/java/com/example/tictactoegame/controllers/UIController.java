package com.example.tictactoegame.controllers;

import com.example.tictactoegame.application.GameButton;
import com.example.tictactoegame.application.GameScreen;
import com.example.tictactoegame.application.MenuScreen;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.util.Optional;

import static com.example.tictactoegame.application.Main.CONTROLLER_GATE;
import static com.example.tictactoegame.controllers.GameController.CROSS;

public class UIController {
    private GameScreen gameScreen;
    private MenuScreen menuScreen;
    private Stage stage;
    UIController() {

    }

    void initialize(MenuScreen menuScreen, GameScreen gameScreen, Stage stage) {
        this.menuScreen = menuScreen;
        this.gameScreen = gameScreen;
        this.stage = stage;
    }

    void makeMove(GameButton button) {
        button.getButton().setOnAction(null);
        button.setSign(CONTROLLER_GATE.getWhoseMoveFromGameController());
    }

    void win(int winner) {
        createAndShowEndWindow(winner);
        restartGame();
    }

    GameButton getButtonByNumber(int number) {
        for (GameButton button : gameScreen.getButtons())
            if (button.getNumber() == number) return button;
        return null;
    }

    private void createAndShowEndWindow(int winner) {
        if (winner == 0) {
            gameScreen.getEndWindow().setTitle("Draw");
            gameScreen.getEndWindow().setHeaderText("Nobody won");
        }
        else {
            String name = winner == CROSS ? "Crosses" : "Zeroes";
            gameScreen.getEndWindow().setTitle("Win!");
            gameScreen.getEndWindow().setHeaderText(name + " won");
        }
        gameScreen.getEndWindow().setContentText("Start Again?");
        Optional<ButtonType> result = gameScreen.getEndWindow().showAndWait();
        if (result.get() == gameScreen.getEndWindow().getButtonTypes().get(1))
            Platform.exit();
    }

    private void changeScene(Scene sceneForChange) {
        stage.close();
        stage.setScene(sceneForChange);
        stage.show();
    }

    private void restartGame() {
        resetGameButtons();
        CONTROLLER_GATE.sendRequestRestartGameToGameController();
        changeScene(menuScreen.getRoot());
    }

    private void resetGameButtons() {
        for(GameButton gameButton : gameScreen.getButtons()) {
            gameButton.resetSign();
            gameButton.getButton().setOnAction(new GameButtonListener(gameButton));
        }
    }

    class MenuPlayerVsPlayerButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            changeScene(gameScreen.getRoot());
        }
    }
    class MenuPlayVsComputerButtonListener implements EventHandler<ActionEvent> {
        @Override
        public void handle(ActionEvent event) {
            CONTROLLER_GATE.sendRequestSetIsComputerToGameController();
            changeScene(gameScreen.getRoot());
        }
    }

    static class GameButtonListener implements EventHandler<ActionEvent> {
        private final GameButton button;

        GameButtonListener(GameButton button) {
            this.button = button;
        }

        @Override
        public void handle(ActionEvent event) {
            button.getButton().setOnAction(null);
            button.setSign(CONTROLLER_GATE.getWhoseMoveFromGameController());
            CONTROLLER_GATE.sendRequestMakeMoveToGameController(button);
        }
    }
}

