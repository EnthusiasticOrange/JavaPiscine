package edu.school21.spring.preprocessor;

public class PreProcessorToLowerImpl implements PreProcessor {
    public String translate(String text) {
        return text.toLowerCase();
    }
}
