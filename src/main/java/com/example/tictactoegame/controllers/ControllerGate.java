package com.example.tictactoegame.controllers;

import com.example.tictactoegame.application.GameButton;
import com.example.tictactoegame.application.GameScreen;
import com.example.tictactoegame.application.MenuScreen;
import javafx.stage.Stage;

public class ControllerGate {
    private final UIController uiController = new UIController();
    private final GameController gameController = new GameController();
    public void initializeUIController(MenuScreen menuScreen, GameScreen gameScreen, Stage stage) {
        uiController.initialize(menuScreen, gameScreen, stage);
    }

    public UIController.MenuPlayerVsPlayerButtonListener sendRequestGetMenuPlayerVsPlayerButtonListenerFromUIController() {
        return uiController.new MenuPlayerVsPlayerButtonListener();
    }

    public UIController.MenuPlayVsComputerButtonListener sendRequestGetMenuPlayVsComputerButtonListenerFromUIController() {
        return uiController.new MenuPlayVsComputerButtonListener();
    }

    public UIController.GameButtonListener sendRequestGetGameButtonListenerFromUIController(GameButton button) {
        return new UIController.GameButtonListener(button);
    }
    public int sendRequestGetMoveAmountFromGameController() {return gameController.getMoveAmount();}
    public void sendRequestSetIsComputerToGameController() {
        gameController.setIsComputer();
    }

    public void makeMoveForComputer(int index) {
        GameButton button = uiController.getButtonByNumber(index);
        uiController.makeMove(button);
        gameController.makeMove(button);
    }

    void sendRequestMakeMoveToGameController(GameButton button) {
        gameController.makeMove(button);
    }

    int getWhoseMoveFromGameController() {
        return gameController.getWhoseMove();
    }

    void sendRequestWinToUIController(int winner) {
            uiController.win(winner);
    }


    void sendRequestRestartGameToGameController() {
        gameController.restartGame();
    }
}
