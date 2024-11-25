package org.example;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;

public class PapaFileChooser extends Application {

    private static final String PREF_KEY = "lastBackupDirectory";
    private static File selectedFile;

    public static File getSelectedFile() {
        return selectedFile;
    }

    @Override
    public void start(Stage primaryStage) throws BackingStoreException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Backup File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("PAPA Backup Files (*.papa)", "*.papa")
        );

        // Retrieve the last directory from preferences
        Preferences prefs = Preferences.userNodeForPackage(PapaFileChooser.class);
        String lastDirectory = prefs.get(PREF_KEY, System.getProperty("user.home"));
        fileChooser.setInitialDirectory(new File(lastDirectory));

        selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile == null) {
            System.out.println("No file selected. Exiting...");
        } else {
            System.out.println("File selected: " + selectedFile.getAbsolutePath());
            // Update the last directory in preferences
            String selectedDirectory = selectedFile.getParent();
            prefs.put(PREF_KEY, selectedDirectory);
            prefs.flush();
        }

        Platform.exit();
    }

    public static File promptForFile() {
        launch();
        return selectedFile;
    }
}