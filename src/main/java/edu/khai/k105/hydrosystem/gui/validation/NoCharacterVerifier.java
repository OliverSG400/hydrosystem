package edu.khai.k105.hydrosystem.gui.validation;

import javax.swing.*;


public class NoCharacterVerifier extends InputVerifier {

    private static final char DECIMAL_SEPARATOR = '.';
    private static final char MINUS = '-';
    private static final NoCharacterVerifier INSTANCE = new NoCharacterVerifier();

    private NoCharacterVerifier() {
    }

    public static NoCharacterVerifier getInstance() {
        return INSTANCE;
    }

    @Override
    public boolean verify(JComponent input) {
        JTextField field = (JTextField) input;
        for (char character : field.getText().toCharArray()){
            if (!(Character.isDigit(character) || character == DECIMAL_SEPARATOR || character == MINUS)) {
                return false;
            }
        }
        return true;
    }

}
