package edu.school21.spring.preprocessor;

import java.util.Locale;

public class PreProcessorToUpperImpl implements PreProcessor {
    @Override
    public String translate(String text) {
        return text.toUpperCase();
    }
}
