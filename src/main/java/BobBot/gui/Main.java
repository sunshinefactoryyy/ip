package bobbot.gui;

import bobbot.core.BobBot;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main application class for the JavaFX GUI version of BobBot.
 * Handles the initialization and setup of the primary stage and scene.
 */
public class Main extends Application {
    private static final String FXML_FILE_PATH = "/view/MainWindow.fxml";
    private static final String APPLICATION_TITLE = "BobBot";
    private static final String DATA_FILE_PATH = "data/bobbotTask.txt";
    private static final int MIN_WINDOW_HEIGHT = 220;
    private static final int MIN_WINDOW_WIDTH = 417;

    private BobBot BobBot;

    /**
     * Initializes the BobBot instance before the JavaFX application starts.
     */
    @Override
    public void init() {
        BobBot = new BobBot(DATA_FILE_PATH);
    }

    /**
     * Starts the JavaFX application by setting up the primary stage.
     *
     * @param primaryStage the primary stage for this application
     */
    @Override
    public void start(Stage primaryStage) {
        try {
            setupPrimaryStage(primaryStage);
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Sets up the primary stage with the main window.
     *
     * @param primaryStage the primary stage to configure
     * @throws Exception if there's an error loading the FXML file
     */
    private void setupPrimaryStage(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(FXML_FILE_PATH));
        AnchorPane mainWindow = fxmlLoader.load();
        
        Scene scene = new Scene(mainWindow);
        primaryStage.setScene(scene);
        primaryStage.setTitle(APPLICATION_TITLE);
        primaryStage.setMinHeight(MIN_WINDOW_HEIGHT);
        primaryStage.setMinWidth(MIN_WINDOW_WIDTH);

        MainWindow controller = fxmlLoader.getController();
        controller.setBobBot(BobBot);

        primaryStage.show();
    }

    /**
     * Main entry point for the GUI application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}