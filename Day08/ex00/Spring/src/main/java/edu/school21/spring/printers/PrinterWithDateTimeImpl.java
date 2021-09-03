package edu.school21.spring.printers;

import edu.school21.spring.renderers.Renderer;

import java.time.LocalDateTime;

public class PrinterWithDateTimeImpl implements Printer {
    private Renderer renderer;

    public PrinterWithDateTimeImpl(Renderer renderer) {
        this.renderer = renderer;
    }

    @Override
    public void print(String text) {
        renderer.sendToConsole(String.format("%s %s", LocalDateTime.now().toString(), text));
    }
}
