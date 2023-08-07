package com.example.tictactoegame.application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

import static com.example.tictactoegame.application.Main.CONTROLLER_GATE;

public class MenuScreen {
private final Button playWithPlayerButton = new Button();
private final Button playWithBotButton = new Button();
private final Text text = new Text();
private final HBox boxForButtons = new HBox();
private final BorderPane mainPane = new BorderPane();
private final Scene root = new Scene(mainPane, 300, 200);

    public MenuScreen() {
        createButtons();
        createText();
        createBoxForButtons();
        createMainPane();
    }

    private void createButtons() {
        playWithPlayerButton.setPrefSize(130, 50);
        playWithPlayerButton.setFocusTraversable(false);
        playWithPlayerButton.setText("Player vs Player");
        playWithPlayerButton.setOnAction(CONTROLLER_GATE.sendRequestGetMenuPlayerVsPlayerButtonListenerFromUIController());
        playWithBotButton.setPrefSize(130,50);
        playWithBotButton.setFocusTraversable(false);
        playWithBotButton.setText("Play vs Computer");
        playWithBotButton.setOnAction(CONTROLLER_GATE.sendRequestGetMenuPlayVsComputerButtonListenerFromUIController());
    }

    private void createText() {
        BorderPane.setAlignment(text, Pos.TOP_CENTER);
        BorderPane.setMargin(text, new Insets(50, 0, 0 ,0));
        text.setText("Select Game Mode");
        text.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
    }

    private void createBoxForButtons() {
        boxForButtons.setAlignment(Pos.CENTER);
        boxForButtons.getChildren().add(playWithPlayerButton);
        boxForButtons.getChildren().add(playWithBotButton);
        boxForButtons.setSpacing(10);
    }

    private void createMainPane() {
        mainPane.setCenter(boxForButtons);
        mainPane.setTop(text);
    }

    public Button getPlayWithPlayerButton() {
        return playWithPlayerButton;
    }

    public Button getPlayWithBotButton() {
        return playWithBotButton;
    }

    public Text getText() {
        return text;
    }

    public HBox getBoxForButtons() {
        return boxForButtons;
    }

    public BorderPane getMainPane() {
        return mainPane;
    }

    public Scene getRoot() {
        return root;
    }
}
