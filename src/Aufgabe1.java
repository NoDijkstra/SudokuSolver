/*
    Aufgabe 1) Zweidimensionale Arrays - Spiel Sudoku
*/

import codedraw.CodeDraw;
import codedraw.Palette;
import codedraw.textformat.HorizontalAlign;
import codedraw.textformat.TextFormat;

import java.awt.*;
import java.io.File;
import java.util.Arrays;
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
            while (myFileReader.hasNextLine()) {
                for (int i = 0; i < array.length; i++) {
                    //Iteriere über filename und lösche ASCII ;
                    String[] values = myFileReader.nextLine().trim().split(";");
                    //Speichere String Array in Int Array
                    for (int j = 0; j < values.length; j++) {
                        array[i][j] = Integer.parseInt(values[j]);
                    }
                }//end of for Statement
            }//end of while Statement
            // *********************************************************
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return array;
    }//end of method

    private static boolean solveSudoku(int[][] sudokuField, int idx) {
        if (idx > (sSize * sSize - 1)) {
            return true;
        } else {
            if (sudokuField[idx / sSize][idx % sSize] == 0) {
                for (int num = 1; num <= sSize; num++) {
                    if (!isNumUsedInRow(sudokuField, num, idx / sSize) && !isNumUsedInCol(sudokuField, num, idx % sSize) && !isNumUsedInBox(sudokuField, num, idx / sSize - ((idx / sSize) % subSize), idx % sSize - ((idx % sSize) % subSize))) {
                        sudokuField[idx / sSize][idx % sSize] = num;
                        //Speichert ab, wie oft beim Lösen des Rätsels eine Ziffer auf eine bestimmte Stelle überschrieben wird. 0 = never changed.
                        heatMap[idx / sSize][idx % sSize]++;
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
    }//end of method

    private static boolean isNumUsedInBox(int[][] sudokuField, int num, int row, int col) {
        // TODO: Implementieren Sie hier Ihre Lösung für die Methode
        //Modulo 3 damit wir unsere 9x9 Matrix in 3 Boxen aufteilen können
        int rowBox = row - row % subSize;
        int colBox = col - col % subSize;

        //Iterieren innerhalb dieser Box + 3
        for (int i = rowBox; i < rowBox + subSize; i++) {
            for (int j = colBox; j < colBox + subSize; j++) {
                if (sudokuField[i][j] == num) {
                    return true;
                }
            }
        }//end of for Statement
        return false; //Zeile kann geändert oder entfernt werden.
    }//end of method

    private static boolean isNumUsedInRow(int[][] sudokuField, int num, int row) {
        // TODO: Implementieren Sie hier Ihre Lösung für die Methode
        for (int i = 0; i < sSize; i++) {
            if (sudokuField[row][i] == num) {
                return true;
            }
        }//end of for Statement
        return false; //Zeile kann geändert oder entfernt werden.
    }//end of method

    private static boolean isNumUsedInCol(int[][] sudokuField, int num, int col) {
        // TODO: Implementieren Sie hier Ihre Lösung für die Methode
        for (int i = 0; i < sSize; i++) {
            if (sudokuField[i][col] == num) {
                return true;
            }
        }//end of for Statement
        return false; //Zeile kann geändert oder entfernt werden.
    }//end of method

    private static boolean isValidSudokuSolution(int[][] sudokuField) {
        // TODO: Implementieren Sie hier Ihre Lösung für die Methode
        //Lösung mit Hilfe der Heatmap
        /*
        for (int[] ints : heatMap) {
            for (int num : ints) {
                if (num != 0)
                    return true;
            }
        }//end of for Statement
        return false;
         */
        //Lösung mit Hilfe von HashSet
        /*
        HashSet<String> found = new HashSet();
        for (int row = 0; row < sSize; row++) {
            for (int col = 0; col < sSize; col++) {
                int num = sudokuField[row][col];
                if (num != 0)
                    if (!found.add(num + " in row " + row) || (!found.add(num + " in col " + col) || (!found.add(num + " in block " + row/3 + col/3)))) return false;
            }
        }
        return true;
         */

        //Iteriere einmal über alle Zahlen welche in sudoku existieren dürfen
        for (int num = 1; num <= sSize; num++) {
            //itereriere über Row
            for (int row = 0; row < sSize; row++) {
                //Wir unterteilen unsere 9x9Matrix in eine 3x3 Matrize.
                if (row % subSize == 0) {
                    for (int col = 0; col < sSize; col += subSize) {
                        if (!isNumUsedInBox(sudokuField, num, row, col)) return false;
                    }
                }//end of rowbox for-Statement
                for (int col = 0; col < sSize; col++) {
                    if (!isNumUsedInRow(sudokuField, num, row) && (!isNumUsedInCol(sudokuField, num, col))) return false;
                }
            }
        }//end of for Statement
        return true;
    }//end of method

    private static void drawSudokuField(CodeDraw myDrawObj, int[][] sudokuField) {
        // TODO: Implementieren Sie hier Ihre Lösung für die Methode
        //Array in CodeDraw implementieren
        int SIZE = sSize * cellSize;
        int sideLength = 120;

        //Färben des Canvas
        //ROSA: i+j = ungerade;
        //ORANGE: i+j = gerade
        for (int i = 0; i <= 2; i++) {
            for (int j = 0; j <= 2; j++) {
                int canvasPositionX = 120 * i;
                int canvasPositionY = 120 * j;
                if ((i + j) % 2 == 0) {
                    myDrawObj.setColor(Color.PINK);
                } else {
                    myDrawObj.setColor(Color.ORANGE);
                }
                myDrawObj.fillSquare(canvasPositionX, canvasPositionY, sideLength);
            }
        }//end of for Statement

        //Print numbers inside Canvas
        for (int i = 0; i < sudokuField.length; i++) {
            for (int j = 0; j < sudokuField[i].length; j++) {
                int offsetCanvas = 20;
                int offsetX = offsetCanvas + j * cellSize;
                int offsetY = offsetCanvas + i * cellSize;
                myDrawObj.setColor(Palette.BLACK);
                myDrawObj.drawText(offsetX, offsetY, String.valueOf(sudokuField[i][j]));
            }
        }//end of for Statement

        //Draw Lines inside Canvas
        myDrawObj.setLineWidth(3);
        for (int i = 0; i < SIZE; i++) {
            int offsetLine = i * cellSize;
            myDrawObj.setColor(Palette.BLACK);
            myDrawObj.drawLine(offsetLine, 0, offsetLine, SIZE);
            myDrawObj.drawLine(0, offsetLine, SIZE, offsetLine);

        }//end of for Statement

        //myDrawObj.setCanvasPositionX(1300);
        myDrawObj.show();
    }//end of method

    private static void printArray(int[][] inputArray) {
        for (int y = 0; y < inputArray.length; y++) {
            for (int x = 0; x < inputArray[y].length; x++) {
                System.out.print(inputArray[y][x] + "\t");
            }
            System.out.println();
        }
        System.out.println();
    }//end of method

    public static void main(String[] args) {

        CodeDraw myDrawObj = new CodeDraw(sSize * cellSize, sSize * cellSize);
        TextFormat format = myDrawObj.getTextFormat();
        format.setFontSize(16);
        format.setHorizontalAlign(HorizontalAlign.CENTER);

        String filename = "./src/sudoku4.csv"; //sudoku0.csv - sudoku7.csv available!
        //Title of Canvas
        //Iterate through filename and search for an INTEGER in String
        for (char i : filename.toCharArray()) {
            if (Character.isDigit(i)){
                //cast ASCII Code to Char
                myDrawObj.setTitle("Solved Sudokufile Number: " + (char)i);
            }
        }//end of for Statement

        //myDrawObj.setTitle("Sudoku: " + filename);
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
    }//end of method
}//end of program










