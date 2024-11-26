package org.example.handlers;

import org.example.changeThings;

import java.util.Map;
import java.util.Scanner;

public class ScooperiaGameHandler implements GameHandler {
    private final Map<Object, Object> gameData;

    public ScooperiaGameHandler(Map<Object, Object> gameData) {
        this.gameData = gameData;
    }

    @Override
    public void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Change Element Menu (Papa's Scooperia):");
            System.out.println("1. Clicked Facebook Button");
            System.out.println("2. Clicked Twitter Button");
            System.out.println("3. Tips");
            System.out.println("4. Go Back");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    changeThings.changeClickFacebook(gameData);
                    break;
                case "2":
                    changeThings.changeClickTwitter(gameData);
                    break;
                case "3":
                    changeThings.changeTips(gameData);
                    break;
                case "4":
                    return; // Go back to the main menu
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }
}
