package com.example.tictactoegame.controllers;

import com.example.tictactoegame.application.GameButton;
import com.example.tictactoegame.computer.Computer;

import java.util.Arrays;

import static com.example.tictactoegame.application.Main.CONTROLLER_GATE;

public class GameController {
    public static final int CROSS = 1;
    public static final int ZERO = 2;
    private final Integer[] cells = new Integer[9];
    private final Computer computer = new Computer(cells);
    private boolean isComputer;
    private int whoseMove = CROSS;
    private int moveAmount;
    GameController() {
    }
    Integer[] getCells() {
        return cells;
    }
    int getWhoseMove() {
        return whoseMove;
    }
    int getMoveAmount() {
        return moveAmount;
    }
    boolean getIsComputer() {
        return isComputer;
    }
    void setIsComputer() {
        isComputer = true;
    }
    void setMoveAmount(int moveAmount) {
        this.moveAmount = moveAmount;
    }
    void computerHandle() {
        computer.handle();
    }
    void makeMove(GameButton button) {
        cells[button.getNumber()] = whoseMove;
        moveAmount++;
        if (moveAmount >= 5) {
            int winner = checkWinner();
            if (winner != 0) {
                CONTROLLER_GATE.sendRequestWinToUIController(winner);
                return;
            }
            if (moveAmount == 9) {
                CONTROLLER_GATE.sendRequestWinToUIController(0);
                return;
            }
        }
        changeWhoseMove();
        if (isComputer && whoseMove == ZERO) computer.handle();
    }
    void restartGame() {
        if (isComputer) computer.clearAllData();
        isComputer = false;
        whoseMove = CROSS;
        moveAmount = 0;
        Arrays.fill(cells, null);
    }
    private void changeWhoseMove() {
        whoseMove = whoseMove == CROSS ? ZERO : CROSS;
    }
    private int checkWinner() {
        if (cells[4] != null) {
            if (cells[4].equals(cells[0]) && cells[0].equals(cells[8])) return whoseMove;
            if (cells[4].equals(cells[2]) && cells[2].equals(cells[6])) return whoseMove;
            if (cells[4].equals(cells[1]) && cells[1].equals(cells[7])) return whoseMove;
            if (cells[4].equals(cells[3]) && cells[3].equals(cells[5])) return whoseMove;
        }
        if (cells[0] != null) {
            if (cells[0].equals(cells[1]) && cells[1].equals(cells[2])) return whoseMove;
            if (cells[0].equals(cells[3]) && cells[3].equals(cells[6])) return whoseMove;
        }
        if (cells [8] != null) {
            if (cells[8].equals(cells[7]) && cells[7].equals(cells[6])) return whoseMove;
            if (cells[8].equals(cells[5]) && cells[5].equals(cells[2])) return whoseMove;
        }
        return 0;
    }
}
