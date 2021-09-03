package edu.school21.spring.preprocessors;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String translate(String text) {
        return text.toUpperCase();
    }
}
