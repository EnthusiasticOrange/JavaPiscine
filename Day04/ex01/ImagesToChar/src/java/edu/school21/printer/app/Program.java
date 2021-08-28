package edu.school21.printer.app;

import java.io.IOException;
import edu.school21.printer.logic.*;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: Program --white=CHAR --black=CHAR");
            return;
        }

        char whiteChar = 0;
        char blackChar = 0;

        for (String arg : args) {
            if (arg.startsWith("--white=")) {
                String substr = arg.substring("--white=".length());
                if (substr.length() != 1) {
                    break;
                }
                whiteChar = substr.charAt(0);
            } else if (arg.startsWith("--black=")) {
                String substr = arg.substring("--black=".length());
                if (substr.length() != 1) {
                    break;
                }
                blackChar = substr.charAt(0);
            } else {
                System.out.println("Usage: Program --white=CHAR --black=CHAR");
                return;
            }
        }

        if ((whiteChar == 0) || (blackChar == 0)) {
            System.out.println("Usage: Program --white=CHAR --black=CHAR image.bmp");
            return;
        }

        System.setProperty("java.awt.headless", "true");

        char[][] arr = null;
        try {
            arr = ImageToChar.BmpToCharArray(whiteChar, blackChar);
        } catch (IOException e) {
            System.err.printf("Failed to read 'image.bmp': %s\n", e.getMessage());
            return;
        } catch (ConvertException e) {
            System.err.printf("Failed to convert 'image.bmp' to array: %s\n", e.getMessage());
            return;
        }

        for (int h = 0; h < arr[0].length; ++h) {
            for (int w = 0; w < arr[0].length; ++w) {
                System.out.print(arr[w][h]);
            }
            System.out.println();
        }
    }
}