package edu.school21.spring.app.standart;

import edu.school21.spring.preprocessors.PreProcessor;
import edu.school21.spring.preprocessors.PreProcessorToUpperImpl;
import edu.school21.spring.printers.PrinterWithPrefixImpl;
import edu.school21.spring.renderers.Renderer;
import edu.school21.spring.renderers.RendererErrImpl;

public class Main {
    public static void main(String[] args) {
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix("Prefix ");
        printer.print("Hello!") ;
    }
}