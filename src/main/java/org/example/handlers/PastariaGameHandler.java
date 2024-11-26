package org.example.handlers;

import java.util.Map;
import java.util.Scanner;

public class PastariaGameHandler implements GameHandler {
    private final Map<Object, Object> gameData;

    public PastariaGameHandler(Map<Object, Object> gameData) {
        this.gameData = gameData;
    }

    @Override
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Change Element Menu (Papa's Pastaria):");
            System.out.println("1. Option 1");
            System.out.println("2. Option 2");
            System.out.println("3. Go Back");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    break;
                case "2":
                    break;
                case "3":
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
