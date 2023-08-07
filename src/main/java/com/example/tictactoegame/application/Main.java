package com.example.tictactoegame.application;

import com.example.tictactoegame.controllers.ControllerGate;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    public static final ControllerGate CONTROLLER_GATE = new ControllerGate();
    private final MenuScreen menuScreen = new MenuScreen();
    private final GameScreen gameScreen = new GameScreen();
    @Override
    public void start(Stage stage) throws Exception {
        CONTROLLER_GATE.initializeUIController(menuScreen, gameScreen, stage);
        stage.setResizable(false);
        stage.setScene(menuScreen.getRoot());
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}