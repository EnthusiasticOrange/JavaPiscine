package edu.school21.spring.printer;

import edu.school21.spring.renderer.Renderer;

import java.time.LocalDateTime;

public class PrinterWithPrefixImpl implements Printer {
    private Renderer renderer;
    private String prefix;

    public PrinterWithPrefixImpl(Renderer renderer, String prefix) {
        this.renderer = renderer;
        this.prefix = prefix;
    }

    public PrinterWithPrefixImpl(Renderer renderer) {
        this(renderer, "");
    }

    @Override
    public void print(String text) {
        renderer.sendToConsole(prefix);
        renderer.sendToConsole(text);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
