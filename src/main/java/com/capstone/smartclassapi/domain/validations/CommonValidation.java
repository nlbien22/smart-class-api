package com.capstone.smartclassapi.domain.validations;

import com.capstone.smartclassapi.domain.exception.ResourceInvalidException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonValidation {

    public static void validatePageAndSize(int page, int size) {
        if (page < 0 || size <= 0) {
            throw new ResourceInvalidException("Page must be greater than or equal to zero, and size must be greater than zero");
        }
    }
    public static String escapeSpecialCharacters(String keyword) {
        String regex = "([%_])";
        String replacement = "\\\\$1";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(keyword);
        return matcher.replaceAll(replacement);
    }
}