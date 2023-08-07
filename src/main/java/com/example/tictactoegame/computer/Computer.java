package com.example.tictactoegame.computer;

import com.example.tictactoegame.application.Orientation;

import java.util.*;

import static com.example.tictactoegame.application.GameScreen.CELLS_AMOUNT;
import static com.example.tictactoegame.application.GameScreen.SIDE;
import static com.example.tictactoegame.application.Main.CONTROLLER_GATE;
import static com.example.tictactoegame.controllers.GameController.CROSS;
import static com.example.tictactoegame.controllers.GameController.ZERO;

public class Computer {
    private final static int ATTACK_POSSIBILITIES_MODE = 3;
    private final Integer[] cells;
    private final ArrayList<Integer> emptyCells = new ArrayList<>(9);
    private final Integer[][] enemyMoves = new Integer[SIDE][SIDE];
    private final Integer[][] thisMoves = new Integer[SIDE][SIDE];
    private ArrayList<Integer> availableMoves = new ArrayList<>(CELLS_AMOUNT);
    private final Random moveGenerator = new Random();
    private boolean canWin, crossCanWin, canMakeAttack;
    public Computer(Integer[] cells) {
        this.cells = cells;
        fillEmptyCells();
    }

    private void fillEmptyCells() {
        emptyCells.clear();
        for (int i = 0; i < CELLS_AMOUNT; i++)
            emptyCells.add(i);
    }

    private int[] transformOneDimensionalIndexToTwoDimensionalIndex(int index) {
        int[] twoDimensionalIndex = new int[2];
        twoDimensionalIndex[0] = index / 3;
        twoDimensionalIndex[1] = index - 3 * (index / 3);
        return twoDimensionalIndex;
    }

    private int transformTwoDimensionalIndexToOneDimensionalIndex(int[] index) {
        int oneDimensionalIndex = index[0] * 3;
        oneDimensionalIndex += index[1];
        return oneDimensionalIndex;
    }

    private void scanNewEnemyMoves() {
        Iterator<Integer> it = emptyCells.iterator();
        while (it.hasNext()) {
            int i = it.next();
            if (cells[i] != null && cells[i] == CROSS) {
                int[] index = transformOneDimensionalIndexToTwoDimensionalIndex(i);
                enemyMoves[index[0]][index[1]] = CROSS;
                it.remove();
                return;
            }
        }
    }

    private int getLogicSumOfRangeOfElements(Integer[] array) {
        int c0 = array[0] == null ? 0 : 1;
        int c1 = array[1] == null ? 0 : 2;
        int c2 = array[2] == null ? 0 : 4;
        return c0+c1+c2;
    }

    private Integer[] createVerticalArray(int mode, int column) {
        Integer[][] checkingCells = mode == CROSS ? enemyMoves : thisMoves;
        Integer[] verticalArray = new Integer[3];
        for (int i = 0; i < verticalArray.length; i++)
            verticalArray[i] = checkingCells[i][column];
        return verticalArray;
    }

    private Integer[] createDiagonalArray(int mode, Orientation orientation) {
        Integer[][] checkingCells = mode == CROSS ? enemyMoves : thisMoves;
        Integer[] diagonalArray = new Integer[3];
        diagonalArray[0] = orientation == Orientation.RIGHT_DIRECTIONAL ? checkingCells[0][0] : checkingCells[0][2];
        diagonalArray[1] = checkingCells[1][1];
        diagonalArray[2] = orientation == Orientation.RIGHT_DIRECTIONAL ? checkingCells[2][2] : checkingCells[2][0];
        return diagonalArray;
    }

    private void checkRangeForMoves(int mode, Integer[] range, int position, Orientation orientation) {
        Integer[][] checkingCells = mode == CROSS ? thisMoves : enemyMoves;
        int sum = getLogicSumOfRangeOfElements(range);
        int[] c0Position;
        int[] c1Position;
        int[] c2Position;
        if (orientation == Orientation.HORIZONTAL || orientation == Orientation.VERTICAL) {
            c0Position = orientation == Orientation.HORIZONTAL ? new int[]{position, 0} : new int[]{0, position};
            c1Position = orientation == Orientation.HORIZONTAL ? new int[]{position, 1} : new int[]{1, position};
            c2Position = orientation == Orientation.HORIZONTAL ? new int[]{position, 2} : new int[]{2, position};
        }
        else {
            c0Position = orientation == Orientation.RIGHT_DIRECTIONAL ? new int[] {0, 0} : new int[] {0, 2};
            c1Position = new int[] {1, 1};
            c2Position = orientation == Orientation.RIGHT_DIRECTIONAL ? new int[] {0, 2} : new int[] {2, 0};
        }
        boolean c0IsNull = checkingCells[c0Position[0]][c0Position[1]] == null;
        boolean c1IsNull = checkingCells[c1Position[0]][c1Position[1]] == null;
        boolean c2IsNull = checkingCells[c2Position[0]][c2Position[1]] == null;
        if (sum == 6 && c0IsNull) {
            availableMoves.add(transformTwoDimensionalIndexToOneDimensionalIndex(c0Position));
            if (mode == ZERO) canWin = true;
            else if (mode == CROSS) crossCanWin = true;
        } else if (sum == 5 && c1IsNull) {
            availableMoves.add(transformTwoDimensionalIndexToOneDimensionalIndex(c1Position));
            if (mode == ZERO) canWin = true;
            else if (mode == CROSS) crossCanWin = true;
        } else if (sum == 3 && c2IsNull) {
            availableMoves.add(transformTwoDimensionalIndexToOneDimensionalIndex(c2Position));
            if (mode == ZERO) canWin = true;
            else if (mode == CROSS) crossCanWin = true;
        } else if (mode == ATTACK_POSSIBILITIES_MODE) {
            if (sum == 1 && c1IsNull && c2IsNull) {
                availableMoves.add(transformTwoDimensionalIndexToOneDimensionalIndex(c1Position));
                availableMoves.add(transformTwoDimensionalIndexToOneDimensionalIndex(c2Position));
                canMakeAttack = true;
            } if (sum == 2 && c0IsNull && c2IsNull) {
                availableMoves.add(transformTwoDimensionalIndexToOneDimensionalIndex(c0Position));
                availableMoves.add(transformTwoDimensionalIndexToOneDimensionalIndex(c2Position));
                canMakeAttack = true;
            } if (sum == 4 && c0IsNull && c1IsNull) {
                availableMoves.add(transformTwoDimensionalIndexToOneDimensionalIndex(c0Position));
                availableMoves.add(transformTwoDimensionalIndexToOneDimensionalIndex(c1Position));
                canMakeAttack = true;
            }
        }
    }

    public void handle() {
        int moveAmount = setMoveAmountAndClearSomeData();
        if (moveAmount >= 3) {
            for (int i = 0; i < thisMoves.length; i++) {
                checkStraightRanges(ZERO, thisMoves[i], i);
            }
            checkDiagonalsRanges(ZERO);
            // Сука какая же эта хуйня я половину не понимаю а другую без пол литра не разберешь сука
            if (!canWin) {
                for (int i = 0; i < enemyMoves.length; i++) {
                    checkStraightRanges(CROSS, enemyMoves[i], i);
                }
                checkDiagonalsRanges(CROSS);
            }
            if (!canWin && !crossCanWin) {
                for (int i = 0; i < thisMoves.length; i++) {
                    checkStraightRanges(ATTACK_POSSIBILITIES_MODE, thisMoves[i], i);
                }
                checkDiagonalsRanges(ATTACK_POSSIBILITIES_MODE);
            }
        }
        if (!canWin && !crossCanWin && !canMakeAttack) availableMoves = new ArrayList<>(emptyCells);
        makeMove();
    }

    public void clearAllData() {
        fillEmptyCells();
        clearTwoDimensionalArray(thisMoves);
        clearTwoDimensionalArray(enemyMoves);
        availableMoves.clear();
        canWin = false;
        crossCanWin = false;
        canMakeAttack = false;
    }

    private int setMoveAmountAndClearSomeData() {
        canWin = false;
        crossCanWin = false;
        canMakeAttack = false;
        availableMoves.clear();
        scanNewEnemyMoves();
        return CONTROLLER_GATE.sendRequestGetMoveAmountFromGameController();
    }

    private void checkStraightRanges(int mode, Integer[] horizontalRange, int position) {
        Integer[] verticalArray = createVerticalArray(mode, position);
        checkRangeForMoves(mode, horizontalRange, position, Orientation.HORIZONTAL);
        checkRangeForMoves(mode, verticalArray, position, Orientation.VERTICAL);
    }

    private void checkDiagonalsRanges(int mode) {
        Integer[] rightDirectionalDiagonalRange = createDiagonalArray(mode, Orientation.RIGHT_DIRECTIONAL);
        Integer[] leftDirectionalDiagonalRange = createDiagonalArray(mode, Orientation.LEFT_DIRECTIONAL);
        checkRangeForMoves(mode, rightDirectionalDiagonalRange, 0, Orientation.RIGHT_DIRECTIONAL);
        checkRangeForMoves(mode, leftDirectionalDiagonalRange, 0, Orientation.LEFT_DIRECTIONAL);
    }

    private void makeMove() {
        int moveIndex = availableMoves.get(moveGenerator.nextInt(availableMoves.size()));
        emptyCells.remove((Object) moveIndex);
        cells[moveIndex] = ZERO;
        int[] twoDimensionalMoveIndex = transformOneDimensionalIndexToTwoDimensionalIndex(moveIndex);
        thisMoves[twoDimensionalMoveIndex[0]][twoDimensionalMoveIndex[1]] = ZERO;
        CONTROLLER_GATE.makeMoveForComputer(moveIndex);
    }

    private void clearTwoDimensionalArray(Integer[][] array) {
        for (Integer[] a : array)
            Arrays.fill(a, null);
    }
}