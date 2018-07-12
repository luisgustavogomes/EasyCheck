/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.controller;

import br.edu.qi.entity.ContraCheque;
import br.edu.qi.entity.ItensFolha;
import br.edu.qi.util.EspelhoItensFolha;
import br.edu.qi.util.TratamentoImagem;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.print.JobSettings;
import javafx.print.PageLayout;
import javafx.print.PageOrientation;
import javafx.print.Paper;
import javafx.print.Printer;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Anderson Jorge
 */
public class ContraChequeController implements Initializable {

    @FXML
    private AnchorPane root;

    private Stage tela;
    private Scene scene;
    private List<EspelhoItensFolha> proventos;
    private List<EspelhoItensFolha> descontos;
    private List<ItensFolha> itensFolha;

    private ObservableList<String> dadosDescProv;
    private ObservableList<String> dadosRefProv;
    private ObservableList<String> dadosVlrProv;
    private ObservableList<String> dadosDescDesc;
    private ObservableList<String> dadosRefDesc;
    private ObservableList<String> dadosVlrDesc;

    public static ContraCheque cc;
    private JFXButton btPrint;
    @FXML
    private ListView<String> descProv;
    @FXML
    private ListView<String> refProv;
    @FXML
    private ListView<String> vlrProv;
    @FXML
    private Label totProv;
    @FXML
    private ListView<String> descDesc;
    @FXML
    private ListView<String> refDesc;
    @FXML
    private ListView<String> vlrDesc;
    @FXML
    private Label totDesc;
    @FXML
    private Label totGer;
    @FXML
    private ImageView imgLogo;
    @FXML
    private Label lbEmpregador;
    @FXML
    private Label lbUsuario;
    @FXML
    private Label lbCNPJ;
    @FXML
    private Label lbAtividade;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        imgLogo.setImage(TratamentoImagem.decodeToImage(LoginController.USER.getLogoEmpresa()));
        criarListView();
        proventos = new ArrayList<>();
        descontos = new ArrayList<>();
        itensFolha = new ArrayList<>();
        cc.getListaItens().forEach(l -> {
            itensFolha.add(l);
        });
        carregarDadosEmpresa();
        carregarLista(itensFolha);
        carregarListView();

    }

    private void carregarDadosEmpresa() {
        lbEmpregador.setText(cc.getDadosEmpresa().getRazaoSocial());
        lbCNPJ.setText(cc.getDadosEmpresa().getCnpj());
        lbAtividade.setText(cc.getFuncao());
        lbUsuario.setText(LoginController.USER.getNome());
    }

    private void handlerBtPrint(ActionEvent event) throws IOException {
        btPrint.setVisible(false);
        print();
        btPrint.setVisible(true);
    }

    public void print() {
        PrinterJob job = PrinterJob.createPrinterJob();
        Printer printer = Printer.getDefaultPrinter().getDefaultPrinter();
        PageLayout pageLayout = printer.createPageLayout(Paper.A4, PageOrientation.LANDSCAPE, Printer.MarginType.EQUAL);
        JobSettings jobSettings = job.getJobSettings();
        jobSettings.setPageLayout(pageLayout);
        boolean printed = job.printPage(root);

        if (printed) {
            job.endJob();
        }
    }

    public void Tela() {
        tela = new Stage();
        Parent content = root;
        scene = new Scene(content);
        tela.setScene(scene);
        //tela.show();
    }

    private void carregarLista(List<ItensFolha> itensFolha) {
        Map<String, List<ItensFolha>> lista = itensFolha.stream().collect(Collectors.groupingBy(a -> a.getAcao()));
        lista.entrySet().forEach((entry) -> {
            String key = entry.getKey();
            List<ItensFolha> value = entry.getValue();
            if (key.equals("Proventos")) {
                value.forEach((i) -> {
                    proventos.add(new EspelhoItensFolha(i.getCodItem(), i.getDescricao(), i.getReferencia(), Double.parseDouble(i.getValor())));
                });
            } else {
                value.forEach((i) -> {
                    descontos.add(new EspelhoItensFolha(i.getCodItem(), i.getDescricao(), i.getReferencia(), Double.parseDouble(i.getValor())));
                });
            }
        });
        proventos.parallelStream().sorted(Comparator.comparingDouble(EspelhoItensFolha::getValor).reversed());
        descontos.parallelStream().sorted(Comparator.comparingDouble(EspelhoItensFolha::getValor).reversed());
        Double p = proventos.parallelStream().mapToDouble(EspelhoItensFolha::getValor).sum();
        Double d = descontos.parallelStream().mapToDouble(EspelhoItensFolha::getValor).sum();

        totProv.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(p)));
        totDesc.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(d)));
        totGer.setText(String.valueOf(NumberFormat.getCurrencyInstance().format(p - d)));
    }

    private void criarListView() {
        this.dadosDescProv = FXCollections.observableArrayList();
        this.dadosRefProv = FXCollections.observableArrayList();
        this.dadosVlrProv = FXCollections.observableArrayList();
        this.dadosDescDesc = FXCollections.observableArrayList();
        this.dadosRefDesc = FXCollections.observableArrayList();
        this.dadosVlrDesc = FXCollections.observableArrayList();
    }

    private void carregarListView() {
        for (EspelhoItensFolha p : proventos) {
            dadosDescProv.add(p.getCodItem() + " - " + p.getDescricao());
            dadosRefProv.add(p.getReferencia());
            dadosVlrProv.add(String.valueOf(NumberFormat.getCurrencyInstance().format(p.getValor())));
        }
        for (EspelhoItensFolha d : descontos) {
            dadosDescDesc.add(d.getCodItem() + " - " + d.getDescricao());
            dadosRefDesc.add(d.getReferencia());
            dadosVlrDesc.add(String.valueOf(NumberFormat.getCurrencyInstance().format(d.getValor())));
        }
        this.descProv.setItems(dadosDescProv);
        this.refProv.setItems(dadosRefProv);
        this.vlrProv.setItems(dadosVlrProv);
        this.descDesc.setItems(dadosDescDesc);
        this.refDesc.setItems(dadosRefDesc);
        this.vlrDesc.setItems(dadosVlrDesc);

    }

}
