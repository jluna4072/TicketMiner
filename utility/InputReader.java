package utility;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Utility class providing validated console input methods shared across menu classes.
 * Wraps a Scanner and loops on each prompt until the user provides acceptable input.
 *
 * @author Jacob Luna
 * @author Carlos Marquez
 * @author Alan Gutierrez-Zaragoza
 */
public class InputReader {

    private final Scanner in;

    /**
     * Constructs an InputReader backed by the given Scanner.
     *
     * @param in the shared Scanner for console input
     */
    public InputReader(Scanner in) {
        this.in = in;
    }

    /**
     * Repeatedly prompts the user until a non-blank string is entered.
     *
     * @param prompt the message to display before reading input
     * @return the non-blank string entered by the user
     */
    public String readNonBlank(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("This field cannot be blank. Please try again.");
        }
    }

    /**
     * Repeatedly prompts the user until a non-negative integer is entered.
     *
     * @param prompt the message to display before reading input
     * @return the non-negative integer entered by the user
     */
    public int readPositiveInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(in.nextLine().trim());
                if (value >= 0) return value;
                System.out.println("Value cannot be negative. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a whole number.");
            }
        }
    }

    /**
     * Repeatedly prompts the user until a non-negative double is entered.
     *
     * @param prompt the message to display before reading input
     * @return the non-negative double entered by the user
     */
    public double readPositiveDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(in.nextLine().trim());
                if (value >= 0) return value;
                System.out.println("Value cannot be negative. Please try again.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
    }

    /**
     * Repeatedly prompts the user until a valid date in MM/DD/YYYY format is entered.
     *
     * @param prompt the message to display before reading input
     * @return a valid date string in MM/DD/YYYY format
     */
    public String readDate(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine().trim();
            try {
                LocalDate.parse(input, formatter);
                return input;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date. Please enter a valid date in MM/DD/YYYY format.");
            }
        }
    }

    /**
     * Repeatedly prompts the user until a valid time in hh:mm AM/PM format is entered.
     *
     * @param prompt the message to display before reading input
     * @return a valid time string in hh:mm AM/PM format
     */
    public String readTime(String prompt) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        while (true) {
            System.out.print(prompt);
            String input = in.nextLine().trim().toUpperCase();
            try {
                LocalTime.parse(input, formatter);
                return input;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid time. Please enter a valid time in hh:mm AM/PM format (e.g. 07:30 PM).");
            }
        }
    }
}
