/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package RecordManagement;

import java.sql.Date;
import java.util.Scanner;

public class InputHelper {
    private static final Scanner scanner = new Scanner(System.in);

    public static int getIntInput(String prompt, boolean allowDefault, int defaultValue) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                if (allowDefault && input.isEmpty()) {
                    return defaultValue;
                }
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer. Please try again.");
            }
        }
    }

    public static float getFloatInput(String prompt, boolean allowDefault, float defaultValue) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                if (allowDefault && input.isEmpty()) {
                    return defaultValue;
                }
                return Float.parseFloat(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid float. Please try again.");
            }
        }
    }

    public static Date getDateInput(String prompt, boolean allowDefault, Date defaultValue) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                if (allowDefault && input.isEmpty()) {
                    return defaultValue;
                }
                return Date.valueOf(input); // Assumes input format is yyyy-mm-dd
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid date format. Please use yyyy-mm-dd.");
            }
        }
    }

    public static String getStringInput(String prompt, boolean allowDefault, String defaultValue) {
        System.out.print(prompt);
        String input = scanner.nextLine();
        return allowDefault && input.isEmpty() ? defaultValue : input;
    }
    
     public static String getStatusInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            String status = scanner.nextLine().trim();

            if (status.equalsIgnoreCase("completed") ||
                status.equalsIgnoreCase("ongoing")) {
                return status;
            } else {
                System.out.println("Invalid status. Please enter one of the following: 'ongoing', 'completed'.");
            }
        }
    }
}
