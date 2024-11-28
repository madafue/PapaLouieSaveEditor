package org.madafue.handlers;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.madafue.changeThings;

import java.util.Map;

public class BakeriaGameHandler implements GameHandler {
    private final Map<Object, Object> gameData;

    public BakeriaGameHandler(Map<Object, Object> gameData) {
        this.gameData = gameData;
    }

    @Override
    public void displayMenu(VBox layout) {
        // Facebook Button
        Label fbLabel = new Label("Change 'Clicked Facebook' State:");
        TextField fbInput = new TextField();
        fbInput.setPromptText("Enter true/false");
        Button fbButton = new Button("Update Facebook");

        fbButton.setOnAction(e -> {
            String input = fbInput.getText().trim();
            if (!input.equalsIgnoreCase("true") && !input.equalsIgnoreCase("false")) {
                showAlert("Invalid Input", "Please enter 'true' or 'false'.");
            } else {
                changeThings.changeClickFacebook(gameData, input);
            }
        });

        // Twitter Button
        Label twitterLabel = new Label("Change 'Clicked Twitter' State:");
        TextField twitterInput = new TextField();
        twitterInput.setPromptText("Enter true/false");
        Button twitterButton = new Button("Update Twitter");

        twitterButton.setOnAction(e -> {
            String input = twitterInput.getText().trim();
            if (!input.equalsIgnoreCase("true") && !input.equalsIgnoreCase("false")) {
                showAlert("Invalid Input", "Please enter 'true' or 'false'.");
            } else {
                changeThings.changeClickTwitter(gameData, input);
            }
        });

        // Tips Button
        Label tipsLabel = new Label("Change 'Tips' Value:");
        TextField tipsInput = new TextField();
        tipsInput.setPromptText("Enter a numeric value (at least 3 digits)");
        Button tipsButton = new Button("Update Tips");

        tipsButton.setOnAction(e -> {
            try {
                int input = Integer.parseInt(tipsInput.getText().trim());
                if (String.valueOf(input).length() >= 3) {
                    changeThings.changeTips(gameData, input);
                } else {
                    showAlert("Invalid Input", "The value must have at least 3 digits.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid numeric value.");
            }
        });

        // Add all UI elements to the layout
        layout.getChildren().addAll(
                fbLabel, fbInput, fbButton,
                twitterLabel, twitterInput, twitterButton,
                tipsLabel, tipsInput, tipsButton
        );
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
