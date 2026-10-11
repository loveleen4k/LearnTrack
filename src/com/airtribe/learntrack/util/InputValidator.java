package com.airtribe.learntrack.util;

import com.airtribe.learntrack.exception.InvalidInputException;

public class InputValidator {

    private InputValidator() {
    }

    public static int parseInt(String text, String fieldName) {
        try {
            return Integer.parseInt(text.trim());
        } catch (NumberFormatException e) {
            throw new InvalidInputException(fieldName + " must be a whole number.");
        }
    }

    public static String requireNonBlank(String text, String fieldName) {
        if (isBlank(text)) {
            throw new InvalidInputException(fieldName + " cannot be empty.");
        }
        return text.trim();
    }

    public static String requireValidEmail(String email) {
        String trimmed = requireNonBlank(email, "Email");
        if (!trimmed.contains("@") || !trimmed.contains(".")) {
            throw new InvalidInputException("Email is not valid.");
        }
        return trimmed;
    }

    public static int requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new InvalidInputException(fieldName + " must be greater than 0.");
        }
        return value;
    }

    public static boolean isBlank(String text) {
        return text == null || text.trim().isEmpty();
    }
}
