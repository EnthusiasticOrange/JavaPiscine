package edu.school21.spring.printer;

import edu.school21.spring.renderer.Renderer;

import java.time.LocalDateTime;

public class PrinterWithPrefixImpl implements Printer {
    private Renderer renderer;
    private String prefix;

    public PrinterWithPrefixImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String text) {
        if (prefix != null) {
            renderer.sendToConsole(prefix);
        }
        renderer.sendToConsole(text);
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }
}
