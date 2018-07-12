/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.controller;

import br.edu.qi.entity.Usuario;
import br.edu.qi.entity.UsuarioWS;
import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javax.rmi.CORBA.Util;
import br.edu.qi.util.Tools;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;
import tray.notification.NotificationType;

/**
 * FXML Controller class
 *
 * @author Anderson Jorge
 */
public class LoginController implements Initializable {

    @FXML
    private JFXButton btEntrar;
    @FXML
    private FontAwesomeIconView btFechar;
    @FXML
    private JFXTextField txUsuario;
    @FXML
    private JFXPasswordField pwSenha;

    public static Usuario USER;
    public static String MATRICULA;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        txUsuario.setOnKeyPressed(event -> enterKeyPressed(event.getCode()));
        pwSenha.setOnKeyPressed(event -> enterKeyPressed(event.getCode()));
    }

    private void openWindow(String path, String controller) throws Exception {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Parent root = (Parent) loader.load();
        Scene scene = new Scene(root);
        HomeController hc = loader.<HomeController>getController();
        stage.initStyle(StageStyle.TRANSPARENT);
        hc.setStage(stage);
        stage.centerOnScreen();
        stage.setHeight(Screen.getPrimary().getVisualBounds().getHeight());
        stage.setWidth(Screen.getPrimary().getVisualBounds().getWidth());
        //stage.setTitle("EasyCheck");
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handlerBtEntrar(ActionEvent event) {
        entrar();
    }

    public void enterKeyPressed(KeyCode keyCode) {
        if (keyCode == KeyCode.ENTER) {
            entrar();
        }
    }

    public void entrar() {
        try {

            USER = UsuarioWS.consultarUsuario(txUsuario.getText(), pwSenha.getText());
            //System.out.println(UsuarioWS.consultarUsuario(txUsuario.getText(), pwSenha.getText()));

            //CONTROLE DE TENTATIVAS DE LOGIN
            if (USER != null) {
                openWindow("/br/edu/qi/view/Home.fxml", "HomeController");
                MATRICULA = txUsuario.getText();
                ((Stage) btFechar.getScene().getWindow()).close();
            } else {
                Tools.Notification("Login", "Usuario ou senha incorretos.", NotificationType.ERROR);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            Tools.Notification("Login", e.getMessage(), NotificationType.ERROR);
        } finally {

        }
    }

    @FXML
    private void handlerBtFechar(MouseEvent event) {
        ((Stage) btFechar.getScene().getWindow()).close();
    }

}
