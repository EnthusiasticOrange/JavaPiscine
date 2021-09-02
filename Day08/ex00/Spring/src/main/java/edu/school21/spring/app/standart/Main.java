package edu.school21.spring.app.standart;

import edu.school21.spring.preprocessor.PreProcessor;
import edu.school21.spring.preprocessor.PreProcessorToUpperImpl;
import edu.school21.spring.printer.PrinterWithPrefixImpl;
import edu.school21.spring.renderer.Renderer;
import edu.school21.spring.renderer.RendererErrImpl;

public class Main {
    public static void main(String[] args) {
        PreProcessor preProcessor = new PreProcessorToUpperImpl();
        Renderer renderer = new RendererErrImpl(preProcessor);
        PrinterWithPrefixImpl printer = new PrinterWithPrefixImpl(renderer);
        printer.setPrefix("Prefix ");
        printer.print("Hello!") ;
    }
}