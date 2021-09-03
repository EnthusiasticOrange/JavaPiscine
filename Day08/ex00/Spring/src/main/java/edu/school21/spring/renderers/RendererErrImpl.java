package edu.school21.spring.renderers;

import edu.school21.spring.preprocessors.PreProcessor;

public class RendererErrImpl implements Renderer {
    PreProcessor processor;

    public RendererErrImpl(PreProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void sendToConsole(String text) {
        System.err.print(processor.translate(text));
    }
}
