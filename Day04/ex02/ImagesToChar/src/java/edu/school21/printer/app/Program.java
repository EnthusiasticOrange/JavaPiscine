package edu.school21.printer.app;

import java.io.IOException;
import edu.school21.printer.logic.*;

public class Program {
    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "true");
        Args programArgs = new Args();

        if (!programArgs.load(args)) {
            return;
        }

        char[][] arr = null;
        char whiteCh = '.';
        char blackCh = 'O';
        try {
            arr = ImageToChar.BmpToCharArray('.', 'O');
        } catch (IOException e) {
            System.err.printf("Failed to read 'image.bmp': %s\n", e.getMessage());
            return;
        } catch (ConvertException e) {
            System.err.printf("Failed to convert 'image.bmp' to array: %s\n", e.getMessage());
            return;
        }

        BmpPrinter printer = new BmpPrinter();

        try {
            printer.setWhiteColor(whiteCh, programArgs.getWhiteColor());
            printer.setBlackColor(blackCh, programArgs.getBlackColor());
        } catch (UnknownColorException e) {
            System.err.printf("Error: %s\n", e.getMessage());
            return;
        }

        printer.print(arr);
    }
}