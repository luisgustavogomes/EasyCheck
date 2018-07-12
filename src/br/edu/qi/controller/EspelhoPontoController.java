/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.controller;

import br.edu.qi.entity.Batidas;
import br.edu.qi.entity.EspelhoPonto;
import br.edu.qi.util.Espelho;
import br.edu.qi.util.EspelhoTela;
import br.edu.qi.util.Tools;
import br.edu.qi.util.TratamentoImagem;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author Anderson Jorge
 */
public class EspelhoPontoController implements Initializable {

    public static EspelhoPonto ep;
    private List<Batidas> arrList;
    private TextArea txaScroll;
    private ObservableList<EspelhoTela> dados;
    @FXML
    private ListView<EspelhoTela> listaRegistro;
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

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        arrList = new ArrayList<>();
        arrList.addAll(ep.getBatidas());
        imgLogo.setImage(TratamentoImagem.decodeToImage(LoginController.USER.getLogoEmpresa()));
        deserializarList();
        this.dados = FXCollections.observableArrayList();
        this.dados.addAll(deserializarList());
        carregarDadosEmpresa();
        listaRegistro.setItems(dados);
    }

    private void carregarDadosEmpresa() {
        lbEmpregador.setText(ep.getDadosEmpresa().getRazaoSocial());
        lbCNPJ.setText(ep.getDadosEmpresa().getCnpj());
        //lbAtividade.setText(ep.);
        lbUsuario.setText(LoginController.USER.getNome());
    }
    private List<EspelhoTela> deserializarList() {
        List<Espelho> arr = new ArrayList<>();
        List<EspelhoTela> et = new ArrayList<>();

        for (Batidas batida : arrList) {
            String data = batida.getDataHora();
            String[] str = new String[2];
            str = data.split("T");
            arr.add(new Espelho(str[0], str[1], batida.getTipo()));
        }
        Map<String, List<Espelho>> lista = arr.stream().collect(Collectors.groupingBy(a -> a.getData()));
        for (Map.Entry<String, List<Espelho>> entry : lista.entrySet()) {
            EspelhoTela espelhoTela = new EspelhoTela();
            String batidas = "";
            String dia = entry.getKey();
            espelhoTela.setData(dia);
            List<Espelho> value = entry.getValue();
            for (Espelho espelho : value) {
                batidas += espelho.getHora().substring(0, 5) + Tools.space(10);
            }
            batidas.substring(0, batidas.length() - 5); //remove os utimos 5 espaços da linha
            espelhoTela.setBatidas(batidas);
            et.add(espelhoTela);

        }
        Collections.sort(et, (o1, o2) -> {
            return o1.getData().compareTo(o2.getData());
        });

//        String sCurrentLine, script = "";
//        for (EspelhoTela espelhoTela : et) {
//            sCurrentLine = (espelhoTela.getData() + " " + espelhoTela.getBatidas());
//            //txaScroll.setText(espelhoTela.getData() + " " + espelhoTela.getBatidas());
//            script = script + "\r\n" + sCurrentLine;
//        }
//        txaScroll.setText(script);
        return et;
    }

    public List<Batidas> getArrList() {
        return arrList;
    }

    public void setArrList(List<Batidas> arrList) {
        this.arrList = arrList;
    }

}
