/*
    Aufgabe 1) Zweidimensionale Arrays - Spiel Sudoku
*/

import codedraw.CodeDraw;
import codedraw.Palette;
import codedraw.textformat.HorizontalAlign;
import codedraw.textformat.TextFormat;

import java.awt.*;
import java.io.File;
import java.util.Scanner;

public class Aufgabe1 {

    private static final int sSize = 9; //sudoku field size ==> 9x9
    private static final int subSize = 3; //sudoku subfield size ==> 3x3
    private static final int cellSize = 40; //sudoku cell size in pixel
    private static final int[][] heatMap = new int[sSize][sSize]; // array for generating the heatmap


    private static int[][] readArrayFromFile(String filename) {
        //9x9 Matrix in die von .CSV überschrieben werden soll.
        int[][] array = new int[sSize][sSize];
        try {
            Scanner myFileReader = new Scanner(new File(filename));
            // TODO: Implementieren Sie hier Ihre Lösung für die Methode
            // *********************************************************
            while (myFileReader.hasNextLine()){
                for (int i = 0; i < array.length; i++) {
                    //Iteriere über filename und lösche ASCII ;
                    String[] values = myFileReader.nextLine().trim().split(";");
                    //Speichere String Array in Int Array
                    for (int j = 0; j < values.length; j++) {
                        array[i][j] = Integer.parseInt(values[j]);
                    }
                }
            }
            // *********************************************************
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return array;
    }

    private static boolean solveSudoku(int[][] sudokuField, int idx) {
        if (idx > (sSize * sSize - 1)) {
            return true;
        } else {
            if (sudokuField[idx / sSize][idx % sSize] == 0) {
                for (int num = 1; num <= sSize; num++) {
                    if (!isNumUsedInRow(sudokuField, num, idx / sSize) && !isNumUsedInCol(sudokuField, num, idx % sSize) && !isNumUsedInBox(sudokuField, num, idx / sSize - ((idx / sSize) % subSize), idx % sSize - ((idx % sSize) % subSize))) {
                        sudokuField[idx / sSize][idx % sSize] = num;
                        if (solveSudoku(sudokuField, idx + 1)) {
                            return true;
                        } else {
                            sudokuField[idx / sSize][idx % sSize] = 0;
                        }
                    }
                }
            } else {
                return solveSudoku(sudokuField, idx + 1);
            }
        }
        return false;
    }

    private static boolean isNumUsedInBox(int[][] sudokuField, int num, int row, int col) {
        // TODO: Implementieren Sie hier Ihre Lösung für die Methode
        //Modulo 3 damit wir unsere 9x9 Matrix in 3 Boxen aufteilen können
        int rowBox = row - row % subSize;
        int colBox = col - col % subSize;

        //Iterieren innerhalb dieser Box + 3
        for (int i = rowBox; i < rowBox + subSize; i++) {
            for (int j = colBox; j < colBox + subSize; j++) {
                if (sudokuField[i][j] == num){
                    return true;
                }
            }
        }
        return false; //Zeile kann geändert oder entfernt werden.
    }

    private static boolean isNumUsedInRow(int[][] sudokuField, int num, int row) {
        // TODO: Implementieren Sie hier Ihre Lösung für die Methode
        for (int i = 0; i < sSize; i++) {
            if (sudokuField[row][i] == num){
                return true;
            }
        }
        return false; //Zeile kann geändert oder entfernt werden.
    }

    private static boolean isNumUsedInCol(int[][] sudokuField, int num, int col) {
        // TODO: Implementieren Sie hier Ihre Lösung für die Methode
        for (int i = 0; i < sSize; i++) {
            if (sudokuField[i][col] == num){
                    return true;
            }
        }
        return false; //Zeile kann geändert oder entfernt werden.
    }

    private static boolean isValidSudokuSolution(int[][] sudokuField) {
        // TODO: Implementieren Sie hier Ihre Lösung für die Methode
        int num = 0;
        int row = 0;
        int col = 0;
        return !isNumUsedInBox(sudokuField, num, row, col) && !isNumUsedInRow(sudokuField, num, row) && !isNumUsedInCol(sudokuField, num, col);
    }

    private static void drawSudokuField(CodeDraw myDrawObj, int[][] sudokuField) {
        // TODO: Implementieren Sie hier Ihre Lösung für die Methode
        int SIZE = sSize * cellSize;
        int row = 3;
        int col = 3;
        int rowBox = row - row % 3;
        int colBox = col - col % 3;
        for (int i = rowBox; i < rowBox + subSize; i++) {
            for (int j = colBox; j < colBox + subSize; j++) {
                if (rowBox % 3 == 0){
                    myDrawObj.fillSquare(cellSize, cellSize, cellSize);
                    myDrawObj.setColor(Palette.ORANGE);
                } else {
                    myDrawObj.setColor(Palette.PINK);
                }

            }
        }
        myDrawObj.setLineWidth(3);
        for (int i = 0; i < SIZE; i++) {
            int offset = i * cellSize;
            myDrawObj.drawLine(offset, 0, offset, SIZE);
            for (int j = 0; j < SIZE; j++) {
                myDrawObj.drawLine(0, offset, SIZE, offset);
            }
        }
        myDrawObj.setCanvasPositionX(1300);
        myDrawObj.show();
    }

    private static void printArray(int[][] inputArray) {
        for (int y = 0; y < inputArray.length; y++) {
            for (int x = 0; x < inputArray[y].length; x++) {
                System.out.print(inputArray[y][x] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void main(String[] args) {

        CodeDraw myDrawObj = new CodeDraw(sSize * cellSize, sSize * cellSize);
        TextFormat format = myDrawObj.getTextFormat();
        format.setFontSize(16);
        format.setHorizontalAlign(HorizontalAlign.CENTER);

        String filename = "./src/sudoku2.csv"; //sudoku0.csv - sudoku7.csv available!
        System.out.println("Reading " + filename + " ...");
        int[][] sudokuField = readArrayFromFile(filename);
        printArray(sudokuField);

        System.out.println("Solving Sudoku ...");
        solveSudoku(sudokuField, 0);
        printArray(sudokuField);
        System.out.println("Valid solution: " + isValidSudokuSolution(sudokuField));
        System.out.println();

        drawSudokuField(myDrawObj, sudokuField);

        System.out.println("Results of the heatMap:");
        printArray(heatMap);


    }
}










