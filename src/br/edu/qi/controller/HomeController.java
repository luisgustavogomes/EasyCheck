/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.controller;

import br.edu.qi.entity.ContraCheque;
import br.edu.qi.entity.ContraChequeSW;
import br.edu.qi.entity.EspelhoPonto;
import br.edu.qi.entity.EspelhoPontoSW;
import br.edu.qi.util.Tools;
import br.edu.qi.util.TratamentoImagem;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTabPane;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import sun.misc.BASE64Decoder;
import tray.notification.NotificationType;

/**
 * FXML Controller class
 *
 * @author Anderson Jorge
 */
public class HomeController implements Initializable {

    private Stage stage;
    @FXML
    private ImageView imgLogo;

    /* ComboBox */
    @FXML
    private JFXComboBox cbMes;
    @FXML
    private JFXComboBox cbAno;
    @FXML
    private JFXComboBox cbTipo;
    @FXML
    private JFXDatePicker dtInicial;
    @FXML
    private JFXDatePicker dtFinal;

    /* TabePane */
    @FXML
    private JFXTabPane tabPane;
    @FXML
    private JFXButton btFechar;
    @FXML
    private BorderPane bt;
    @FXML
    private JFXButton btGerarCC;
    @FXML
    private JFXButton btGerarEP;    
    @FXML
    private JFXButton btPrintCC;
    @FXML
    private JFXButton btPrintEP;
    
    @FXML
    private Label textMenu;

    private Stage tela;
    private Node print;
    private Scene sceneTela;

    public void setStage(Stage stage) {
        this.stage = stage;
        stage.getIcons().add(Tools.iconImage);
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        imgLogo.setImage(TratamentoImagem.decodeToImage(LoginController.USER.getLogoEmpresa()));
        cbMes.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
        cbAno.getItems().addAll("2018");
        cbTipo.getItems().addAll("Mensal");
    }

    private void openTabPane(String url, String title, Object obj) {
        try {
            if (title.equalsIgnoreCase("Espelho Ponto")) {
//                if (EspelhoPontoController.arrList == null) {
//                    EspelhoPontoController.arrList = new ArrayList<>();
//                }
                //EspelhoPontoController.arrList.addAll((Collection<? extends EspelhoPonto>) obj);
                EspelhoPontoController.ep = (EspelhoPonto) obj;
            } else {
                ContraChequeController.cc = (ContraCheque) obj;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource(url));
            Parent content = loader.load();
            Tab tab = new Tab(title);
            tab.setContent(content);
            sceneTela = new Scene(content); //define somente o fxml
            //tela.setScene(sceneTela);
            //tabPane.setTabClosingPolicy(TabClosingPolicy.ALL_TABS);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);

            tabPane.getTabs().stream().forEach(t -> {
                ContextMenu contextMenu = new ContextMenu();
                MenuItem closeItem = new MenuItem();
                closeItem.setText("x");
                //closeItem.setId("btFechar");
                closeItem.setOnAction(actionEvent -> {
                    tabPane.getTabs().remove(t);
                });
                contextMenu.getItems().add(closeItem);
                t.setContextMenu(contextMenu);
            });
            // tela = stage;
            print = sceneTela.getRoot();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    @FXML
    private void handlerBtGerarCC(ActionEvent event) {
        try {
            ContraCheque cc = ContraChequeSW.consultarContraCheque(
                    cbMes.getSelectionModel().getSelectedItem().toString(),
                    cbAno.getSelectionModel().getSelectedItem().toString(),
                    cbTipo.getSelectionModel().getSelectedItem().toString()
            );
            System.out.println(cc);

            openTabPane("/br/edu/qi/view/ContraCheque.fxml", "Contra Cheque", cc);
        } catch (Exception e) {
            e.getMessage();
            Tools.Notification("Home", e.getMessage(), NotificationType.ERROR);
        }
    }

    @FXML
    private void handlerBtGerarEP(ActionEvent event) {
        try {
            String dInicial = dtInicial.getValue().atStartOfDay(ZoneId.systemDefault()).format(DateTimeFormatter.BASIC_ISO_DATE.ISO_LOCAL_DATE);
            String dFinal = dtFinal.getValue().atStartOfDay(ZoneId.systemDefault()).format(DateTimeFormatter.BASIC_ISO_DATE.ISO_LOCAL_DATE);
            
            validacaoEP(dInicial, dFinal);
            
            EspelhoPonto ep = EspelhoPontoSW.consultarEspelhoPonto(LoginController.MATRICULA, dInicial, dFinal);
            openTabPane("/br/edu/qi/view/EspelhoPonto.fxml", "Espelho Ponto", ep);
        } catch (Exception e) {
            e.getMessage();
            Tools.Notification("Home", e.getMessage(), NotificationType.ERROR);
        }
    }

    public void validacaoEP(String dInicial, String dFinal) throws Exception {
        if (Tools.calcularDiferencaDatas(Tools.stringToLocalDate(dInicial),(Tools.stringToLocalDate(dFinal))) > 31) {
            throw new Exception("Periodo máximo de dias permitido para o relatório é um mês!");
        }else if (Tools.stringToLocalDate(dInicial).isAfter(Tools.stringToLocalDate(dFinal))){
            throw new Exception("A data Inicial informada é maior ou igual a data Final!");
        }
    }
    
    @FXML
    private void handlerBtFechar(ActionEvent event) {
        ((Stage) btFechar.getScene().getWindow()).close();
    }

    @FXML
    private void handlerBtPrintCC(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/qi/view/ContraCheque.fxml"));
        Parent content = loader.load();
        print(content);

    }
    
    @FXML
    private void handlerBtPrintEP(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/br/edu/qi/view/EspelhoPonto.fxml"));
        Parent content = loader.load();
        print(content);

    }

    java.awt.print.Paper paper = new java.awt.print.Paper();

    public void print(final Node node) {
        PrinterJob job = PrinterJob.createPrinterJob();
        Printer printer = Printer.getDefaultPrinter().getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.EQUAL);
        JobSettings jobSettings = job.getJobSettings();
        jobSettings.setPageLayout(pageLayout);
        boolean printed = job.printPage(node);

        if (printed) {
            job.endJob();
        }
    }

}
