/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.entity;

import static br.edu.qi.entity.Empresa.parseEmpresa;
import static br.edu.qi.entity.ItensFolha.parseItensFolha;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Anderson Jorge
 */
public class ContraCheque {

    private String mes;
    private String ano;
    private String tipo;
    private String funcao;
    private Double salarioHora;
    private Double salarioBase;
    private String departamento;
    private String centroDeCusto;
    private Double fgts;
    private Empresa dadosEmpresa;
    private List<ItensFolha> listaItens;

    public ContraCheque() {
    }

    public ContraCheque(String mes, String ano, String tipo, String funcao, Double salarioHora, Double salarioBase, String departamento, String centroDeCusto, Double fgts, Empresa dadosEmpresa, List<ItensFolha> listaItens) {
        this.mes = mes;
        this.ano = ano;
        this.tipo = tipo;
        this.funcao = funcao;
        this.salarioHora = salarioHora;
        this.salarioBase = salarioBase;
        this.departamento = departamento;
        this.centroDeCusto = centroDeCusto;
        this.fgts = fgts;
        this.dadosEmpresa = dadosEmpresa;
        this.listaItens = listaItens;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getFuncao() {
        return funcao;
    }

    public void setFuncao(String funcao) {
        this.funcao = funcao;
    }

    public Double getSalarioHora() {
        return salarioHora;
    }

    public void setSalarioHora(Double salarioHora) {
        this.salarioHora = salarioHora;
    }

    public Double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(Double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getCentroDeCusto() {
        return centroDeCusto;
    }

    public void setCentroDeCusto(String centroDeCusto) {
        this.centroDeCusto = centroDeCusto;
    }

    public Double getFgts() {
        return fgts;
    }

    public void setFgts(Double fgts) {
        this.fgts = fgts;
    }

    public List<ItensFolha> getListaItens() {
        return listaItens;
    }

    public void setListaItens(List<ItensFolha> listaItens) {
        this.listaItens = listaItens;
    }

    public Empresa getDadosEmpresa() {
        return dadosEmpresa;
    }

    public void setDadosEmpresa(Empresa dadosEmpresa) {
        this.dadosEmpresa = dadosEmpresa;
    }

  
    public ContraCheque(String json) {
        this((JSONObject) JSONValue.parse(json));
    }

    public ContraCheque(JSONObject obj) {
        this.dadosEmpresa = new Empresa(obj.get("Empresa").toString());
        this.salarioHora = Double.parseDouble(obj.get("SalarioHora").toString());
        this.salarioBase = Double.parseDouble(obj.get("SalarioBase").toString());
        this.mes = obj.get("MesPeriodo").toString();
        this.ano = obj.get("AnoPeriodo").toString();
        this.tipo = obj.get("TipoContracheque").toString();
        this.departamento = obj.get("Departamento").toString();
        this.centroDeCusto = obj.get("CentroDeCusto").toString();
        this.funcao = obj.get("Funcao").toString();
        this.fgts = Double.parseDouble(obj.get("FGTS").toString());
        this.listaItens = parseItensFolha(obj.get("ItensFolha").toString());
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("Empresa", this.dadosEmpresa);
        obj.put("SalarioHora", this.salarioHora);
        obj.put("SalarioBase", this.salarioBase);
        obj.put("MesPeriodo", this.mes);
        obj.put("AnoPeriodo", this.ano);
        obj.put("TipoContracheque", this.tipo);
        obj.put("Departamento", this.departamento);
        obj.put("CentroDeCusto", this.centroDeCusto);
        obj.put("Funcao", this.funcao);
        obj.put("FGTS", this.fgts);
        obj.put("ItensFolha", this.listaItens);
        return obj;
    }

    public static List<ContraCheque> parseContraCheque(String value) {
        return parseContraCheque((JSONArray) JSONValue.parse(value));
    }

    public static List<ContraCheque> parseContraCheque(JSONArray value) {
        List<ContraCheque> user = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            JSONObject obj = (JSONObject) value.get(i);
            user.add(new ContraCheque(obj));
        }
        return user;
    }

    @Override
    public String toString() {
        return this.toJSON().toString();
    }

}
