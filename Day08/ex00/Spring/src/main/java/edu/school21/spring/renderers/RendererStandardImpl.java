package edu.school21.spring.renderers;

import edu.school21.spring.preprocessors.PreProcessor;

public class RendererStandardImpl implements Renderer {
    PreProcessor processor;

    public RendererStandardImpl(PreProcessor processor) {
        this.processor = processor;
    }

    @Override
    public void sendToConsole(String text) {
        System.out.print(processor.translate(text));
    }
}
