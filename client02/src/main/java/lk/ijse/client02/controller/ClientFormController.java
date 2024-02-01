package lk.ijse.client02.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import lk.ijse.client02.utill.DateTimeUtil;

import java.io.*;
import java.net.Socket;

public class ClientFormController extends Thread {
    public Label lblName;
    public Label lblDate;
    public Label lblTIme;
    public VBox vboxChat;
    public TextField txtMessageBox;
    public AnchorPane root;
    BufferedReader reader;
    PrintWriter writer;
    Socket socket;
    private FileChooser fileChooser;
    private File file;

    public void initialize(){
        String userName = LoginFormController.userName;
        lblName.setText(userName);
        try {
            socket = new Socket("localhost", 2000);
            System.out.println("Client Connected");
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(),true);
            this.start();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> updateClock()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        lblDate.setText(DateTimeUtil.dateNow());
    }
    private void updateClock(){lblTIme.setText(DateTimeUtil.timeNow());}

    @Override
    public void run(){
        try {
            while (true){
                String msg = reader.readLine();
                String[] tokens = msg.split(" ");
                String cmd = tokens[0];

                StringBuilder fullMsg = new StringBuilder();
                for (int i = 1; i < tokens.length; i++){
                    fullMsg.append(tokens[i] + " ");
                }

                String[] msgtoAr = msg.split(" ");
                String st = "";
                for (int i = 0; i < msgtoAr.length - 1; i++){
                    st += msgtoAr[i + 1] + " ";
                }

                Text text = new Text(st);
                String firstChar = "";
                if(st.length() > 3){
                    firstChar = st.substring(0, 3);
                }

                if(firstChar.equalsIgnoreCase("img")){
                    st = st.substring(3, st.length() - 1);

                    File file = new File(st);
                    Image image = new Image(file.toURI().toString());

                    ImageView imageView = new ImageView(image);

                    imageView.setFitHeight(150);
                    imageView.setFitWidth(200);

                    HBox hBox = new HBox(10);
                    hBox.setAlignment(Pos.BOTTOM_RIGHT);

                    if (!cmd.equalsIgnoreCase(lblName.getText())){
                        vboxChat.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.TOP_LEFT);

                        Text text1 = new Text(cmd + " :");
                        hBox.getChildren().add(text1);
                        hBox.getChildren().add(imageView);
                    }
                    else {
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(imageView);
                        Text text1 = new Text(": ME ");
                        hBox.getChildren().add(text1);
                    }

                    Platform.runLater(() -> vboxChat.getChildren().addAll(hBox));
                }
                else {
                    TextFlow textFlow = new TextFlow();

                    if (!cmd.equalsIgnoreCase(lblName.getText() + ":")){
                        Text txtName = new Text(cmd + " ");
                        txtName.getStyleClass().add("txtName");
                        textFlow.getChildren().add(txtName);
                    }

                    text.setFont(Font.font(16));
                    textFlow.getChildren().add(text);
                    textFlow.setMaxWidth(200);

                    TextFlow textFlow1 = new TextFlow(textFlow);

                    HBox hBox = new HBox(12);

                    if (!cmd.equalsIgnoreCase(lblName.getText() + ":")){
                        vboxChat.setAlignment(Pos.TOP_LEFT);
                        hBox.setAlignment(Pos.CENTER_LEFT);
                        hBox.getChildren().add(textFlow1);
                    }

                    else {
                        Text text1 = new Text(fullMsg + ": Me");
                        text1.setFont(Font.font(16));
                        TextFlow textFlow2 = new TextFlow(text1);
                        hBox.setAlignment(Pos.BOTTOM_RIGHT);
                        hBox.getChildren().add(textFlow2);
                    }
                    Text text1 = new Text(fullMsg + ": Me");
                    Platform.runLater(() -> vboxChat.getChildren().addAll(hBox));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void btnExitOnAction(ActionEvent actionEvent) {
        ((Stage)(((Button)actionEvent.getSource()).getScene().getWindow())).close();
    }

    public void btnSendOnAction(ActionEvent actionEvent) {
        String msg = txtMessageBox.getText();
        writer.println(lblName.getText() + ": " + msg);
        txtMessageBox.clear();

        if (msg.equalsIgnoreCase("Logout")){
            System.exit(0);
        }
    }

    public void btnLikeImojiOnAction(ActionEvent actionEvent) {
        txtMessageBox.appendText("\uD83D\uDC4D");
    }

    public void btnHeartImojiOnAction(ActionEvent actionEvent) {
        txtMessageBox.appendText("\u2764");
    }

    public void btnCareImojiOnAction(ActionEvent actionEvent) {
        txtMessageBox.appendText("\uD83E\uDD17");
    }

    public void btnWowImojiOnAction(ActionEvent actionEvent) {
        txtMessageBox.appendText("\uD83D\uDE32");
    }

    public void btnHahaImojiOnAction(ActionEvent actionEvent) {
        txtMessageBox.appendText("\uD83D\uDE02");
    }

    public void btnSadImojiOnAction(ActionEvent actionEvent) {
        txtMessageBox.appendText("\uD83D\uDE2D");
    }

    public void btnAngryImojiOnAction(ActionEvent actionEvent) {
        txtMessageBox.appendText("\uD83D\uDE20");
    }

    public void btnAddImageOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");
        this.file = fileChooser.showOpenDialog(stage);
        writer.println(lblName.getText() + " " + "img" + file.getPath());
    }
}
