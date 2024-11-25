package org.example;

import java.io.*;
import java.nio.file.*;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.DataFormatException;



import static org.example.changeThings.*;
import static org.example.compressionTools.*;

public class FlashSaveFileProcessor {

    public static boolean isMap = false;
    public static byte[] fileData;
    public static byte[] decompressedData;
    public static Object sourceObject;
    public static Object temporarySourceObject;


    public static void main(String[] args) {
        File selectedFile = PapaFileChooser.promptForFile();
        if (selectedFile == null) {
            System.out.println("No file selected. Exiting...");
            System.exit(0);
        }
            try {
                fileData = Files.readAllBytes(selectedFile.toPath());
                decompressedData = decompressZlib(fileData);
                sourceObject = deserializeAmf(decompressedData);
                temporarySourceObject = cloneObject(sourceObject); // Create a working copy

                if (sourceObject instanceof Map<?, ?>) {
                    isMap = true;
                } else {
                    System.out.println("Not a valid map structure. Exiting...");
                    System.exit(0);
                }

                printMenu();

            } catch (IOException | DataFormatException | ClassNotFoundException e) {
                System.err.println("Error processing file: " + e.getMessage());
                e.printStackTrace();
            }
        }




    public static void printData(Object data) {
        if (data instanceof Map<?, ?> map) {
            System.out.println("Current Data:");
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } else {
            System.out.println("Unsupported data format.");
        }
    }

    public static void printMenu() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Main Menu:");
            System.out.println("1. Print Data");
            System.out.println("2. Change Element");
            System.out.println("3. Save Changes");
            System.out.println("4. Exit");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    printData(temporarySourceObject);
                    break;
                case "2":
                    changeElementMenu();
                    break;
                case "3":
                    saveChanges();
                    break;
                case "4":
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }


    public static void changeElementMenu() {
        if (!isMap) {
            System.out.println("No map data found. Cannot change elements.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Change Element Menu:");
            System.out.println("1. Clicked Facebook Button");
            System.out.println("2. Clicked Twitter Button");
            System.out.println("3. Tips");
            System.out.println("4. Go Back");
            System.out.print("Select an option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    changeClickFacebook(temporarySourceObject);
                    break;
                case "2":
                    changeClickTwitter(temporarySourceObject);
                    break;
                case "3":
                    changeTips(temporarySourceObject);
                case "4":
                    return; // Go back to the main menu
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
    }

    public static void saveChanges() {
        if (!(temporarySourceObject instanceof Map<?, ?>)) {
            System.out.println("No valid data to save.");
            return;
        }

        try {
            byte[] updatedData = serializeAmf(temporarySourceObject);
            byte[] compressedData = compressZlib(updatedData);

            Files.write(Paths.get("updated_backup.papa"), compressedData);
            System.out.println("Changes saved to 'updated_backup.papa'.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Object cloneObject(Object original) {
        try {
            byte[] serializedData = serializeAmf(original);
            return deserializeAmf(serializedData);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Failed to clone object", e);
        }
    }




}
