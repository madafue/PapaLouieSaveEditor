package org.example;

import java.util.Map;
import java.util.Scanner;


public class changeThings {

    public static void changeClickFacebook(Object workingCopy) {
        if (!(workingCopy instanceof Map<?, ?>)) {
            System.out.println("Invalid data structure. Cannot modify.");
            return;
        }

        Map<Object, Object> map = (Map<Object, Object>) workingCopy;

        if (map.containsKey("clickfacebook")) {
            System.out.println("Current value of 'clickfacebook': " + map.get("clickfacebook"));
            System.out.print("Enter a new value for 'clickfacebook' (true/false): ");
            Scanner scanner = new Scanner(System.in);
            String newValue = scanner.nextLine().toLowerCase();

            if (newValue.equals("true") || newValue.equals("false")) {
                map.put("clickfacebook", Boolean.parseBoolean(newValue));
                System.out.println("'clickfacebook' updated to: " + newValue);
            } else {
                System.out.println("Invalid value. Please enter 'true' or 'false'.");
            }
        } else {
            System.out.println("'clickfacebook' not found in the data.");
        }
    }

    public static void changeClickTwitter(Object workingCopy) {
        if (!(workingCopy instanceof Map<?, ?>)) {
            System.out.println("Invalid data structure. Cannot modify.");
            return;
        }

        Map<Object, Object> map = (Map<Object, Object>) workingCopy;

        if (map.containsKey("clicktwitter")) {
            System.out.println("Current value of 'clicktwitter': " + map.get("clicktwitter"));
            System.out.print("Enter a new value for 'clicktwitter' (true/false): ");
            Scanner scanner = new Scanner(System.in);
            String newValue = scanner.nextLine().toLowerCase();

            if (newValue.equals("true") || newValue.equals("false")) {
                map.put("clicktwitter", Boolean.parseBoolean(newValue));
                System.out.println("'clicktwitter' updated to: " + newValue);
            } else {
                System.out.println("Invalid value. Please enter 'true' or 'false'.");
            }
        } else {
            System.out.println("'clicktwitter' not found in the data.");
        }
    }

    public static void changeTips(Object workingCopy) {
        if (!(workingCopy instanceof Map<?, ?>)) {
            System.out.println("Invalid data structure. Cannot modify.");
            return;
        }

        Map<Object, Object> map = (Map<Object, Object>) workingCopy;

        if (map.containsKey("tips")) {
            System.out.println("Current value of 'tips': " + map.get("tips"));
            System.out.print("Enter a new value for 'tips' (at least 3 digits): ");
            Scanner scanner = new Scanner(System.in);
            try {
                int newValue = Integer.parseInt(scanner.nextLine());

                // validate that the number is 4 digits
                if (String.valueOf(newValue).length() >= 3) {
                    map.put("tips", newValue);
                    System.out.println("'tips' updated to: " + newValue);
                } else {
                    System.out.println("Invalid value. Please enter a 4-digit integer.");
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid integer.");
            }
        } else {
            System.out.println("'tips' not found in the data.");
        }
    }

}
