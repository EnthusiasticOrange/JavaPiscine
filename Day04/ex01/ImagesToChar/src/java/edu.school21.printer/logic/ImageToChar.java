package edu.school21.printer.logic;

import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Color;

public class ImageToChar {
    public static char[][] BmpToCharArray(char whiteChar, char blackChar)
            throws IOException, ConvertException {
        BufferedImage image = ImageIO.read(getClass().getResourceAsStream("image.bmp"););

        char[][] arr = new char[image.getWidth()][image.getHeight()];

        for (int w = 0; w < image.getWidth(); ++w) {
            for (int h = 0; h < image.getHeight(); ++h) {
                int color = image.getRGB(w, h);
                if (color == Color.WHITE.getRGB()) {
                    arr[w][h] = whiteChar;
                } else if (color == Color.BLACK.getRGB()){
                    arr[w][h] = blackChar;
                } else {
                    throw new ConvertException("Only WHITE and BLACK images are supported");
                }
            }
        }
        return arr;
    }
}