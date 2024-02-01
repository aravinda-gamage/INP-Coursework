package lk.ijse.client01;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class AppInitializer extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(this.getClass().getResource("/view/loginForm.fxml"));
        Scene scene = new Scene(root);
        stage.getIcons().add(new Image("/icon/chat.png"));
        stage.setScene(scene);
        stage.setTitle("PLAY TECH");
        stage.centerOnScreen();
        stage.show();
    }
}
