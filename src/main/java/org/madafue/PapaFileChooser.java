package org.madafue;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.prefs.Preferences;

public class PapaFileChooser {

    private static final String PREF_KEY = "lastBackupDirectory";
    private static File selectedFile;

    public static File getSelectedFile() {
        return selectedFile;
    }

    public static File promptForFile(Stage primaryStage) {
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

        if (selectedFile != null) {
            System.out.println("File selected: " + selectedFile.getAbsolutePath());
            // Update the last directory in preferences
            String selectedDirectory = selectedFile.getParent();
            prefs.put(PREF_KEY, selectedDirectory);
            try {
                prefs.flush();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("No file selected.");
        }


        return selectedFile;  // Will return null immediately if the user hasn't selected a file yet
    }
}
