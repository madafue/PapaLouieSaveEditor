package org.madafue;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.madafue.handlers.*;

import java.io.File;
import java.nio.file.Files;
import java.util.Map;
import java.util.Objects;

import static org.madafue.compressionTools.*;

public class JavaFXApp extends Application {
    private static File selectedFile;
    private static Object sourceObject;
    private static Object temporarySourceObject;
    private Label gameLabel;

    @Override
    public void start(Stage primaryStage) {
        showMainMenu(primaryStage);
    }

    private void showMainMenu(Stage primaryStage) {
        Label title = new Label("Save Editor");
        title.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #333;");
        Label subtitle = new Label("The tool for managing backup files.");
        subtitle.setStyle("-fx-font-size: 14px; -fx-text-fill: #666;");

        gameLabel = new Label("Backup Not Selected"); // Default message
        gameLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #444;");

        ScaleTransition titleAnimation = new ScaleTransition(Duration.millis(800), title);
        titleAnimation.setFromX(0.8); // Start smaller
        titleAnimation.setFromY(0.8);
        titleAnimation.setToX(1.0); // Grow to normal size
        titleAnimation.setToY(1.0);
        titleAnimation.setCycleCount(1);
        titleAnimation.play();

        Button btnPrintData = createStyledButton("Print Data");
        Button btnChangeElement = createStyledButton("Change Element");
        Button btnSaveChanges = createStyledButton("Save Changes");
        Button btnExit = createStyledButton("Exit");

        btnPrintData.setOnAction(e -> printData());
        btnChangeElement.setOnAction(e -> openChangeElementMenu(primaryStage));
        btnSaveChanges.setOnAction(e -> saveChanges());
        btnExit.setOnAction(e -> Platform.exit());

        MenuBar menuBar = createMenuBar(primaryStage);

        VBox contentLayout = new VBox(20); // 20px spacing
        contentLayout.setPadding(new Insets(30));
        contentLayout.setAlignment(Pos.CENTER);
        contentLayout.getChildren().addAll(title, subtitle, gameLabel, btnPrintData, btnChangeElement, btnSaveChanges, btnExit);

        VBox rootLayout = new VBox(menuBar, contentLayout);
        rootLayout.setAlignment(Pos.TOP_CENTER);

        Scene scene = new Scene(rootLayout, 500, 500);
        scene.getStylesheets().add(getClass().getResource("/styles.css").toExternalForm());

        FadeTransition fadeTransition = new FadeTransition(Duration.millis(800), contentLayout);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();

        primaryStage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/app_icon.png"))));
        primaryStage.setScene(scene);
        primaryStage.setTitle("Papa Louie Save Editor");
        primaryStage.show();
    }

    private MenuBar createMenuBar(Stage primaryStage) {
        MenuBar menuBar = new MenuBar();

        Menu fileMenu = new Menu("File");
        MenuItem openFile = new MenuItem("Open...");
        MenuItem saveFile = new MenuItem("Save");
        MenuItem exitApp = new MenuItem("Exit");
        fileMenu.getItems().addAll(openFile, saveFile, new SeparatorMenuItem(), exitApp);

        Menu helpMenu = new Menu("Help");
        MenuItem aboutApp = new MenuItem("About");
        helpMenu.getItems().add(aboutApp);

        menuBar.getMenus().addAll(fileMenu, helpMenu);

        openFile.setOnAction(e -> handleOpenFile(primaryStage));
        saveFile.setOnAction(e -> handleSaveFile());
        exitApp.setOnAction(e -> Platform.exit());
        aboutApp.setOnAction(e -> showAboutDialog());

        // Add Icons to Menu Items
//        openFile.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/open.png")))));
//        saveFile.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/save.png")))));
//        exitApp.setGraphic(new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/icons/exit.png")))));

        return menuBar;
    }

    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-size: 16px; -fx-padding: 10 20; -fx-background-color: #6CA6CD; -fx-text-fill: white; -fx-background-radius: 5;");

        ScaleTransition hoverTransition = new ScaleTransition(Duration.millis(200), button);
        hoverTransition.setToX(1.1); // Scale to 110%
        hoverTransition.setToY(1.1);

        ScaleTransition exitTransition = new ScaleTransition(Duration.millis(200), button);
        exitTransition.setToX(1.0); // Back to original size
        exitTransition.setToY(1.0);

        button.setOnMouseEntered(e -> hoverTransition.playFromStart());
        button.setOnMouseExited(e -> exitTransition.playFromStart());

        return button;
    }

    private void printData() {
        if (temporarySourceObject instanceof Map<?, ?> map) {
            StringBuilder data = new StringBuilder("Current Data:\n");
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                data.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
            }
            showAlert(Alert.AlertType.INFORMATION, "Data", data.toString());
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Invalid data format.");
        }
    }


    private void saveChanges() {
        try {
            byte[] updatedData = serializeAmf(temporarySourceObject);
            byte[] compressedData = compressZlib(updatedData);

            File outputFile = new File("updated_backup.papa");
            Files.write(outputFile.toPath(), compressedData);
            showAlert(Alert.AlertType.INFORMATION, "Success", "Changes saved to " + outputFile.getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to save changes.");
        }
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    private static Object cloneObject(Object original) {
        try {
            byte[] serializedData = serializeAmf(original);
            return deserializeAmf(serializedData);
        } catch (Exception e) {
            throw new RuntimeException("Failed to clone object", e);
        }
    }

    public static void launchApp(String[] args) {
        launch(args);
    }

    private void handleOpenFile(Stage primaryStage) {
        File file = PapaFileChooser.promptForFile(primaryStage);
        if (file != null) {
            System.out.println("Opened file: " + file.getAbsolutePath());
            try {
                byte[] fileData = Files.readAllBytes(file.toPath());
                System.out.println("Read file data, size: " + fileData.length);

                byte[] decompressedData = decompressZlib(fileData);
                System.out.println("Decompressed data, size: " + decompressedData.length);

                sourceObject = deserializeAmf(decompressedData);
                System.out.println("Deserialized data: " + sourceObject);

                temporarySourceObject = cloneObject(sourceObject);

                if (!(sourceObject instanceof Map<?, ?> map)) {
                    System.out.println("Error: Data is not a Map.");
                    showAlert(Alert.AlertType.ERROR, "Error", "Not a valid map structure.");
                } else {
                    System.out.println("File data loaded successfully.");
                    String gameSKU = (String) map.get("gameSKU");
                    if (gameSKU != null) {
                        String gameName = getGameName(gameSKU);
                        gameLabel.setText("Game: " + gameName); // Update the label
                        System.out.println("Game identified: " + gameName);
                    } else {
                        System.out.println("gameSKU not found.");
                        gameLabel.setText("Game: Unknown");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to load file: " + e.getMessage());
            }
        } else {
            System.out.println("SOMETHING HAPPENNING");
        }
    }

    private String getGameName(String gameSKU) {
        return switch (gameSKU) {
            case "papasscooperiahd" -> "Papa's Scooperia";
            case "papasbakeria" -> "Papa's Bakeria";
            case "papascheeseria" -> "Papa's Cheeseria";
            case "papascupcakeria" -> "Papa's Cupcakeria";
            case "papasdonuteria" -> "Papa's Donuteria";
            case "papasfreezeria_" -> "Papa's Freezeria";
            case "papashotdoggeria" -> "Papa's Hot Doggeria";
            case "papaspancakeria_" -> "Papa's Pancakeria";
            case "papaspastaria" -> "Papa's Pastaria";
            case "papassushiria" -> "Papa's Sushiria";
            case "papastaqueria_" -> "Papa's Taco Mia";
            case "papaswingeria_" -> "Papa's Wingeria";
            default -> "Unknown Game";
        };

    }

    private void handleSaveFile() {
        saveChanges();
        System.out.println("File saved.");
    }

    private void showAboutDialog() {
        Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
        aboutDialog.setTitle("About");
        aboutDialog.setHeaderText("Papa's Save Editor");
        aboutDialog.setContentText("This is a save editor for Papa's Games, created by Adam Mawji.");
        aboutDialog.showAndWait();
    }

    private void openChangeElementMenu(Stage primaryStage) {
        if (temporarySourceObject instanceof Map<?, ?> map) {
            GameHandler gameHandler = selectGameHandler((String) map.get("gameSKU"), (Map<Object, Object>) map);

            if (gameHandler != null) {
                openGameHandlerWindow(primaryStage, gameHandler);
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Unsupported game data.");
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "No valid game data to edit.");
        }
    }

    private static GameHandler selectGameHandler(String gameSKU, Map<Object, Object> map) {
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

    private void openGameHandlerWindow(Stage parentStage, GameHandler gameHandler) {
        Stage changeElementStage = new Stage();
        changeElementStage.initOwner(parentStage);

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);

        Label title = new Label("Change Element Menu");
        title.setStyle("-fx-font-size: 20px; -fx-font-weight: bold;");
        Label subtitle = new Label("Edit your save data below:");
        subtitle.setStyle("-fx-font-size: 14px;");

        gameHandler.displayMenu(layout);

        Button backButton = new Button("Back");
        backButton.setOnAction(e -> changeElementStage.close());
        layout.getChildren().add(backButton);

        Scene scene = new Scene(layout, 400, 300);
        changeElementStage.setScene(scene);
        changeElementStage.setTitle("Edit Save Data");
        changeElementStage.show();
    }

}
