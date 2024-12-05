package org.madafue.handlers;

import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.madafue.changeThings;

import java.util.Map;

public class ScooperiaGameHandler implements GameHandler {
    private final Map<Object, Object> gameData;

    public ScooperiaGameHandler(Map<Object, Object> gameData) {
        this.gameData = gameData;
    }

    @Override
    public void displayMenu(VBox layout) {
        // Facebook State
        boolean fbState = Boolean.parseBoolean(String.valueOf(gameData.getOrDefault("clickfacebook", "false")));
        Label fbLabel = new Label("Change 'Clicked Facebook' State:");
        Button fbTrueButton = new Button("True");
        Button fbFalseButton = new Button("False");

        // Style the buttons based on the initial state
        updateBooleanButtons(fbState, fbTrueButton, fbFalseButton);

        fbTrueButton.setOnAction(e -> {
            changeThings.changeClickFacebook(gameData, "true");
            updateBooleanButtons(true, fbTrueButton, fbFalseButton);
        });

        fbFalseButton.setOnAction(e -> {
            changeThings.changeClickFacebook(gameData, "false");
            updateBooleanButtons(false, fbTrueButton, fbFalseButton);
        });

        // Wrap the buttons in an HBox
        HBox fbButtonBox = new HBox(10, fbTrueButton, fbFalseButton); // Spacing of 10
        fbButtonBox.setStyle("-fx-alignment: center-left;"); // Align buttons to the left

        // Twitter State
        boolean twitterState = Boolean.parseBoolean(String.valueOf(gameData.getOrDefault("clicktwitter", "false")));
        Label twitterLabel = new Label("Change 'Clicked Twitter' State:");
        Button twitterTrueButton = new Button("True");
        Button twitterFalseButton = new Button("False");

        // Style the buttons based on the initial state
        updateBooleanButtons(twitterState, twitterTrueButton, twitterFalseButton);

        twitterTrueButton.setOnAction(e -> {
            changeThings.changeClickTwitter(gameData, "true");
            updateBooleanButtons(true, twitterTrueButton, twitterFalseButton);
        });

        twitterFalseButton.setOnAction(e -> {
            changeThings.changeClickTwitter(gameData, "false");
            updateBooleanButtons(false, twitterTrueButton, twitterFalseButton);
        });

        // Wrap the buttons in an HBox
        HBox twitterButtonBox = new HBox(10, twitterTrueButton, twitterFalseButton); // Spacing of 10
        twitterButtonBox.setStyle("-fx-alignment: center-left;");

        // Tips Value
        String tipsValue = String.valueOf(gameData.getOrDefault("tips", "0"));
        Label tipsLabel = new Label("Change 'Tips' Value (Current: " + tipsValue + "):");
        TextField tipsInput = new TextField();
        tipsInput.setPromptText("Enter a numeric value (at least 3 digits)");
        Button tipsButton = new Button("Update Tips");

        tipsButton.setOnAction(e -> {
            try {
                int input = Integer.parseInt(tipsInput.getText().trim());
                if (String.valueOf(input).length() >= 3) {
                    changeThings.changeTips(gameData, input);
                    tipsLabel.setText("Change 'Tips' Value (Current: " + input + "):");
                } else {
                    showAlert("Invalid Input", "The value must have at least 3 digits.");
                }
            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid numeric value.");
            }
        });

        String playerName = String.valueOf(gameData.getOrDefault("playername", "Unknown"));
        Label playerNameLabel = new Label("Change 'Player Name' (Current: " + playerName + "):");
        TextField playerNameInput = new TextField();
        playerNameInput.setPromptText("Enter a new player name");
        Button playerNameButton = new Button("Update Player Name");

        playerNameButton.setOnAction(e -> {
            String newName = playerNameInput.getText().trim();
            if (!newName.isEmpty()) {
                changeThings.changePlayerName(gameData, newName);
                playerNameLabel.setText("Change 'Player Name' (Current: " + newName + "):");
            } else {
                showAlert("Invalid Input", "Player name cannot be empty.");
            }
        });

        String dayValue = String.valueOf(gameData.getOrDefault("day", "0"));
        Label dayLabel = new Label("Change 'Day' Value (Current: " + dayValue + "):");
        TextField dayInput = new TextField();
        dayInput.setPromptText("Enter a numeric day value");
        Button dayButton = new Button("Update Day");

        // Negative days break the game
        //Figure out why completed savefiles are broken

        dayButton.setOnAction(e -> {
            try {
                int input = Integer.parseInt(dayInput.getText().trim());
                 // Ensure the day is non-negative?
                changeThings.changeDay(gameData, input);
                dayLabel.setText("Change 'Day' Value (Current: " + input + "):");

            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid numeric value.");
            }
        });

        String rankValue = String.valueOf(gameData.getOrDefault("rank", "1"));
        Label rankLabel = new Label("Change 'Rank' Value (Current: " + rankValue + "):");
        TextField rankInput = new TextField();
        rankInput.setPromptText("Enter a numeric rank value");
        Button rankButton = new Button("Update Rank");

        //Holiday is linked to your rank, for some reason?

        rankButton.setOnAction(e -> {
            try {
                int input = Integer.parseInt(rankInput.getText().trim());
                // Ensure the day is non-negative?
                changeThings.changeRank(gameData, input);
                rankLabel.setText("Change 'Rank' Value (Current: " + input + "):");

            } catch (NumberFormatException ex) {
                showAlert("Invalid Input", "Please enter a valid numeric value.");
            }
        });

        // Add all UI elements to the layout
        layout.getChildren().addAll(
                fbLabel, fbButtonBox,
                twitterLabel, twitterButtonBox,
                tipsLabel, tipsInput, tipsButton,
                playerNameLabel, playerNameInput, playerNameButton,
                dayLabel, dayInput, dayButton,
                rankLabel, rankInput, rankButton
        );
    }

    /**
     * Updates the styles of the true/false buttons based on the current value.
     */
    private void updateBooleanButtons(boolean state, Button trueButton, Button falseButton) {
        if (state) {
            trueButton.setStyle("-fx-background-color: green; -fx-text-fill: white;");
            falseButton.setStyle("-fx-background-color: lightgrey; -fx-text-fill: black;");
        } else {
            trueButton.setStyle("-fx-background-color: lightgrey; -fx-text-fill: black;");
            falseButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        }
    }



    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}