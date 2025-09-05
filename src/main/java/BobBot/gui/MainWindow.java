package bobbot.gui;

import bobbot.core.BobBot;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main GUI window.
 * Handles user interactions and communication with the BobBot instance.
 */
public class MainWindow extends AnchorPane {
    private static final String USER_IMAGE_PATH = "/images/UserBob.jpg";
    private static final String BOT_IMAGE_PATH = "/images/KingBob.jpg";

    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private BobBot bobBot;
    private Image userImage;
    private Image botImage;

    /**
     * Initializes the controller after its root element has been completely processed.
     * Sets up auto-scrolling behavior and loads images.
     */
    @FXML
    private void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
        loadImages();
    }

    /**
     * Loads the user and bot images from resources.
     */
    private void loadImages() {
        try {
            userImage = new Image(this.getClass().getResourceAsStream(USER_IMAGE_PATH));
            botImage = new Image(this.getClass().getResourceAsStream(BOT_IMAGE_PATH));
        } catch (Exception exception) {
            System.err.println("Error loading images: " + exception.getMessage());
            // Create placeholder images if loading fails
            userImage = new Image("data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNkYPhfDwAChwGA60e6kgAAAABJRU5ErkJggg==");
            botImage = userImage;
        }
    }

    /**
     * Injects the BobBot instance and displays the welcome message.
     *
     * @param bobBot the BobBot instance to use for processing commands
     */
    public void setBobBot(BobBot bobBot) {
        this.bobBot = bobBot;
        showWelcomeMessage();
    }

    /**
     * Displays the welcome message from BobBot.
     */
    private void showWelcomeMessage() {
        String welcomeMessage = bobBot.getWelcomeMessage();
        DialogBox welcomeDialog = DialogBox.createBotDialog(welcomeMessage, botImage);
        dialogContainer.getChildren().add(welcomeDialog);
    }

    /**
     * Handles user input from the text field or send button.
     * Processes the command and displays both user input and bot response.
     */
    @FXML
    private void handleUserInput() {
        String userInputText = userInput.getText().trim();
        
        if (userInputText.isEmpty()) {
            return;
        }

        // Clear input field immediately
        userInput.clear();

        // Process the command and get response
        String botResponse = bobBot.getResponse(userInputText);

        // Add dialogs to the conversation
        DialogBox userDialog = DialogBox.createUserDialog(userInputText, userImage);
        DialogBox botDialog = DialogBox.createBotDialog(botResponse, botImage);
        
        dialogContainer.getChildren().addAll(userDialog, botDialog);

        // Handle exit command
        if (isExitCommand(userInputText)) {
            Platform.runLater(() -> {
                try {
                    Thread.sleep(1500); // Give user time to read goodbye message
                    Platform.exit();
                } catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
            });
        }
    }

    /**
     * Checks if the user input is an exit command.
     *
     * @param input the user input to check
     * @return true if the input is an exit command, false otherwise
     */
    private boolean isExitCommand(String input) {
        return "bye".equalsIgnoreCase(input.trim());
    }
}