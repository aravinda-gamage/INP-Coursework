package lk.ijse.client01.controller;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.client01.utill.Navigation;

import java.io.IOException;

public class LoginFormController {
    public TextField txtUserName;
    public AnchorPane root;
    static String userName;

    public void btnLoginOnAction(ActionEvent actionEvent) {
        userName = txtUserName.getText();
        txtUserName.clear();
        /*Stage stage=new Stage();
        try {
            stage.setScene(new Scene(FXMLLoader.load(LoginFormController.class.getResource("/view/clientForm.fxml"))));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage.close();
        stage.centerOnScreen();
        stage.show();*/
        try {
            Navigation.switchNavigation("clientForm", actionEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnExitOnAction(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }
}
