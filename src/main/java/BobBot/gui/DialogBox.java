package bobbot.gui;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents a dialog box consisting of an ImageView to represent the speaker's face 
 * and a label containing text from the speaker.
 */
public class DialogBox extends HBox {
    @FXML
    private Label dialogText;
    @FXML
    private ImageView speakerImage;

    /**
     * Creates a DialogBox with the specified text and image.
     *
     * @param text the text to display in the dialog
     * @param image the image to display for the speaker
     */
    private DialogBox(String text, Image image, boolean isUser) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException exception) {
            System.err.println("Error loading DialogBox FXML: " + exception.getMessage());
            exception.printStackTrace();
        }

        dialogText.setText(text);
        speakerImage.setImage(image);
        applyBubbleStyling(isUser);
    }

    private void applyBubbleStyling(boolean isUser) {
        if (isUser) {
            // User message - RIGHT side, GREEN bubble
            this.setAlignment(Pos.TOP_RIGHT);
            
            dialogText.setStyle(
                "-fx-background-color: #4CAF50; " +           // Solid green background
                "-fx-background-radius: 20 20 5 20; " +
                "-fx-padding: 12 16 12 16; " +
                "-fx-text-fill: WHITE; " +                    // WHITE text for visibility
                "-fx-font-size: 14px; " +
                "-fx-alignment: TOP_RIGHT; " +      // Align text to the right
                "-fx-font-weight: bold; " +                   // Bold for better visibility
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 8, 0.3, 2, 2);"
            );
            
        } else {
            // Bot message - LEFT side, WHITE bubble
            this.setAlignment(Pos.CENTER_LEFT);
            
            dialogText.setStyle(
                "-fx-background-color: WHITE; " +             // Solid white background
                "-fx-background-radius: 20 20 20 5; " +
                "-fx-padding: 12 16 12 16; " +
                "-fx-text-fill: BLACK; " +                    // BLACK text for visibility
                "-fx-font-size: 14px; " +
                "-fx-font-weight: normal; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 8, 0.3, 2, 2); " +
                "-fx-border-color: #CCCCCC; " +              // Light border for definition
                "-fx-border-width: 1; " +
                "-fx-border-radius: 20 20 20 5;"
            );
        }
    }

    /**
     * Flips the dialog box such that the ImageView is on the left and text on the right.
     */
    private void flipDialogToLeft() {
        ObservableList<Node> temporaryChildren = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(temporaryChildren);
        getChildren().setAll(temporaryChildren);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog box for the user's message.
     *
     * @param text the user's message
     * @param userImage the user's avatar image
     * @return a DialogBox positioned on the right side
     */
    public static DialogBox createUserDialog(String text, Image userImage) {
        return new DialogBox(text, userImage, true);
    }

    /**
     * Creates a dialog box for BobBot's message.
     *
     * @param text BobBot's response message
     * @param botImage BobBot's avatar image
     * @return a DialogBox positioned on the left side
     */
    public static DialogBox createBotDialog(String text, Image botImage) {
        DialogBox botDialog = new DialogBox(text, botImage, false);
        botDialog.flipDialogToLeft();
        return botDialog;
    }
}