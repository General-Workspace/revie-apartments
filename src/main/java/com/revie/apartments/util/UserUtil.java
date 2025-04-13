package com.revie.apartments.util;

public class UserUtil {
    public String capitalizeFirstLetterOfName(String input) {
        String firstLetter = input.substring(0, 1).toUpperCase();
        return firstLetter + input.substring(1);
    }
}
