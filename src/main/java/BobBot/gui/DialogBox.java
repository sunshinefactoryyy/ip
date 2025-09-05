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
    private DialogBox(String text, Image image) {
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
        return new DialogBox(text, userImage);
    }

    /**
     * Creates a dialog box for BobBot's message.
     *
     * @param text BobBot's response message
     * @param botImage BobBot's avatar image
     * @return a DialogBox positioned on the left side
     */
    public static DialogBox createBotDialog(String text, Image botImage) {
        DialogBox botDialog = new DialogBox(text, botImage);
        botDialog.flipDialogToLeft();
        return botDialog;
    }
}