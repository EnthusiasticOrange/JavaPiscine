package edu.school21.printer.app;

import com.diogonunes.jcolor.Attribute;
import com.diogonunes.jcolor.AnsiFormat;

public class BmpPrinter {
    Character whiteChar;
    Character blackChar;
    Attribute whiteAttribute;
    Attribute blackAttribute;

    public BmpPrinter() {
        this.whiteChar = '.';
        this.blackChar = 'O';
        this.whiteAttribute = Attribute.WHITE_BACK();
        this.blackAttribute = Attribute.BLACK_BACK();
    }

    public void setWhiteColor(char ch, String color) {
        this.whiteAttribute = stringToColor(color);
        this.whiteChar = ch;
    }

    public void setBlackColor(char ch, String color) {
        this.blackAttribute = stringToColor(color);
        this.blackChar = ch;
    }

    public void print(char[][] array) {
        AnsiFormat fWhite = new AnsiFormat(Attribute.WHITE_TEXT(), this.whiteAttribute);
        AnsiFormat fBlack = new AnsiFormat(Attribute.WHITE_TEXT(), this.blackAttribute);

        AnsiFormat fCur = null;
        for (int h = 0; h < array[0].length; ++h) {
            for (int w = 0; w < array[0].length; ++w) {
                if (array[w][h] == this.whiteChar) {
                    fCur = fWhite;
                } else if (array[w][h] == this.blackChar) {
                    fCur = fBlack;
                }
                System.out.print(fCur.format(" "));
            }
            System.out.println();
        }
    }

    private static Attribute stringToColor(String color) {
        color = color.toUpperCase();
        switch (color) {
            case "BLACK":
                return Attribute.BLACK_BACK();
            case "BLUE":
                return Attribute.BLUE_BACK();
            case "CYAN":
                return Attribute.CYAN_BACK();
            case "GREEN":
                return Attribute.GREEN_BACK();
            case "MAGENTA":
                return Attribute.MAGENTA_BACK();
            case "RED":
                return Attribute.RED_BACK();
            case "WHITE":
                return Attribute.WHITE_BACK();
            case "YELLOW":
                return Attribute.YELLOW_BACK();
            default:
                throw new UnknownColorException("Unkown color " + color);
        }
    }
}