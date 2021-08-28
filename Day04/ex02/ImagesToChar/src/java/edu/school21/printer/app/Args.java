package edu.school21.printer.app;

import com.beust.jcommander.*;
import java.awt.Color;

@Parameters(separators = "=")
public class Args {
    @Parameter(
            names = "--white",
            description = "Color to replace WHITE in BMP image",
            required = true
    )
    private String white;

    @Parameter(
            names = "--black",
            description = "Color to replace WHITE in BMP image",
            required = true
    )
    private String black;

    public Args() {
        this.white = "WHITE";
        this.black = "BLACK";
    }

    public boolean load(String[] args) {
        try {
            JCommander.newBuilder()
                    .addObject(this)
                    .build()
                    .parse(args);
        } catch (ParameterException e) {
            System.err.printf("%s\n", e.getMessage());
            return false;
        }

        return true;
    }

    public String getWhiteColor() {
        return this.white;
    }

    public String getBlackColor() {
        return this.black;
    }
}