package utility;

import java.util.regex.Pattern;
import java.util.Scanner;

public class ValidationUI {

    private static final Scanner scanner = new Scanner(System.in);

    // Regular expression patterns for validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
    
    // Phone number should start with "01" and be 10 or 11 characters long
    private static final Pattern PHONE_PATTERN = Pattern.compile(
            "^01[0-9]{8,9}$");
    
    private static final Pattern ID_PATTERN = Pattern.compile(
            "^[A-Za-z0-9_-]{5,20}$");

    // Validate if a string is not null and not empty
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }

    // Validate if an email address is in the correct format
    public static boolean isValidEmail(String email) {
        return isNotEmpty(email) && EMAIL_PATTERN.matcher(email).matches();
    }

    // Validate if a phone number is in the correct format
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return isNotEmpty(phoneNumber) && PHONE_PATTERN.matcher(phoneNumber).matches();
    }

    // Validate if a string matches a specific pattern
    public static boolean matchesPattern(String value, String pattern) {
        return isNotEmpty(value) && Pattern.compile(pattern).matcher(value).matches();
    }

    public static boolean retryOrExit() {
        System.out.print("Do you want to try again? (y/n): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        return choice.equals("y");
    }
    
}
