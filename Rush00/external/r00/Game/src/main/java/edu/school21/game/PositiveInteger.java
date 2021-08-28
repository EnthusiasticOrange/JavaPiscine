package edu.school21.game;

import com.beust.jcommander.IParameterValidator;
import com.beust.jcommander.ParameterException;

public class PositiveInteger implements IParameterValidator {

    @Override
    public void validate(String name, String value) throws ParameterException {
        int n = Integer.valueOf(value);
        if (n < 1) {
            throw new ParameterException("Parameter " + name + " should be positive (found " + value +")");
        }
    }
}