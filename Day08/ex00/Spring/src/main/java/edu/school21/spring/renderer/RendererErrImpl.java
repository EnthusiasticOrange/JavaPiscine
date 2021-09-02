package edu.school21.spring.renderer;

import edu.school21.spring.preprocessor.PreProcessor;

public class RendererErrImpl implements Renderer {
    PreProcessor processor;

    public RendererErrImpl(PreProcessor processor) {
        this.processor = processor;
    }

    public void sendToConsole(String text) {
        System.err.print(processor.translate(text));
    }
}
