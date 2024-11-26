package org.example;

import org.example.handlers.BakeriaGameHandler;
import org.example.handlers.GameHandler;
import org.example.handlers.*;

import java.io.*;
import java.nio.file.*;
import java.util.Map;
import java.util.Scanner;
import java.util.zip.DataFormatException;

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
        Map<Object, Object> map = (Map<Object, Object>) temporarySourceObject;
        String gameSKU = map.get("gameSKU").toString();
        GameHandler gameHandler = getGameHandler(gameSKU, map);

        if (gameHandler == null) {
            System.out.println("Unsupported game SKU: " + gameSKU);
            return;
        }

        while (true) {
            System.out.println("Main Menu " + gameSKU);
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
                    gameHandler.displayMenu();
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

    private static GameHandler getGameHandler(String gameSKU, Map<Object, Object> map) {
        switch (gameSKU) {
            case "papasscooperiahd":
                return new ScooperiaGameHandler(map);
            case "papasbakeria":
                return new BakeriaGameHandler(map);
            case "papascheeseria":
                return new CheeseriaGameHandler(map);
            case "papascupcakeria":
                return new CupcakeriaGameHandler(map);
            case "papasdonuteria":
                return new DonuteriaGameHandler(map);
            case "papasfreezeria_":
                return new FreezeriaGameHandler(map);
            case "papashotdoggeria":
                return new HotDoggeriaGameHandler(map);
            case "papaspancakeria_":
                return new PancakeriaGameHandler(map);
            case "papaspastaria":
                return new PastariaGameHandler(map);
            case "papassushiria":
                return new SushiriaGameHandler(map);
            case "papastaqueria_":
                return new TacoMiaGameHandler(map);
            case "papaswingeria_":
                return new WingeriaGameHandler(map);

            default:
                return null;
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
