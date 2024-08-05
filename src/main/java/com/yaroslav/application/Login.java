package com.yaroslav.application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Login extends Application {

    @Override
    public void start(Stage stage) {
        LoginInterface clientLogin = new LoginInterface();

        clientLogin.setupSubmitButton(stage);

        Scene scene = new Scene(clientLogin.configureInterface(), 300, 300);
        stage.setTitle("Chat Login");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}