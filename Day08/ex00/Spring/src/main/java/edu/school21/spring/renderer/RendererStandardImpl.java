package edu.school21.spring.renderer;

import edu.school21.spring.preprocessor.PreProcessor;

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
