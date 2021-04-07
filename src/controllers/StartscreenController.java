package controllers;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;


public class StartscreenController {
    @FXML
    Button exitButton;

    @FXML
    Button startButton;

    /**
     * Method used for closing the app when the exit button is clicked
     * @return null
     */
    public EventHandler<ActionEvent> closeApp()
    {
        Stage appStage = (Stage) exitButton.getScene().getWindow();
        appStage.close();
        return null;
    }

    /**
     * Method used for the start button, loads the first page
     * @throws IOException
     * @return null
     */
    public EventHandler<ActionEvent> startApp() throws IOException {
        Stage appStage = (Stage) startButton.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/mainapp.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        appStage.setScene(scene);
        appStage.show();
        return null;
    }

}
