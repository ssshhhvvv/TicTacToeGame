package com.example.tictactoegame.application;

import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameScreen {
    public static final int SIDE = 3;
    public static final int CELLS_AMOUNT = 9;
    private final GameButton[] buttons = new GameButton[CELLS_AMOUNT];
    private final StackPane[] cells = new StackPane[CELLS_AMOUNT];
    private final HBox[] rows = new HBox[SIDE];
    private final VBox grid = new VBox();
    private final Alert endWindow = new Alert(Alert.AlertType.CONFIRMATION);
    private final Scene root = new Scene(grid, 450, 450);

    public GameScreen() {
        createButtons();
        createCells();
        createRows();
        createGrid();
        createEndWindow();
    }

    public GameButton[] getButtons() {
        return buttons;
    }

    public StackPane[] getCells() {
        return cells;
    }

    public HBox[] getRows() {
        return rows;
    }

    public VBox getGrid() {
        return grid;
    }

    public Alert getEndWindow() {
        return endWindow;
    }

    public Scene getRoot() {
        return root;
    }
    private void createEndWindow() {
        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("No");

        endWindow.getButtonTypes().setAll(yes, no);
    }
    private void createGrid() {
        for (int i = 0; i < SIDE; i++)
            grid.getChildren().add(rows[i]);
    }
    private void createRows() {
        for(int i = 0; i < SIDE; i++) {
            rows[i] = new HBox();
            for (int j = i * 3; j < i * 3 + 3; j++) {
                rows[i].getChildren().add(cells[j]);
            }
        }
    }
    private void createCells() {
        for (int i = 0; i < CELLS_AMOUNT; i++) {
            cells[i] = new StackPane();
            cells[i].getChildren().add(buttons[i].getButton());
            cells[i].getChildren().add(buttons[i].getText());
        }
    }
    private void createButtons() {
        for(int i = 0; i < CELLS_AMOUNT; i++)
            buttons[i] = new GameButton(i);
    }
}
