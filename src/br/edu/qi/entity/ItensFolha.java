/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.entity;

import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * @author Anderson Jorge
 */
public class ItensFolha {

    private String codItem;
    private String acao;
    private String descricao;
    private String referencia;
    private String valor;

    public ItensFolha() {
    }

    public ItensFolha(String codItem, String acao, String descricao, String referencia, String valor) {
        this.codItem = codItem;
        this.acao = acao;
        this.descricao = descricao;
        this.referencia = referencia;
        this.valor = valor;
    }

    public String getCodItem() {
        return codItem;
    }

    public void setCodItem(String codItem) {
        this.codItem = codItem;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public ItensFolha(String json) {
        this((JSONObject) JSONValue.parse(json));
    }

    public ItensFolha(JSONObject obj) {
        this.codItem = obj.get("CodItem").toString();
        this.acao = obj.get("Acao").toString();
        this.descricao = obj.get("Descricao").toString();
        this.referencia = obj.get("Referencia").toString();
        this.valor = obj.get("Valor").toString();
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("CodItem", this.codItem);
        obj.put("Acao", this.acao);
        obj.put("Descricao", this.descricao);
        obj.put("Referencia", this.referencia);
        obj.put("Valor", this.valor);
        return obj;
    }

    public static List<ItensFolha> parseItensFolha(String value) {
        return parseItensFolha((JSONArray) JSONValue.parse(value));
    }

    public static List<ItensFolha> parseItensFolha(JSONArray value) {
        List<ItensFolha> user = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            JSONObject obj = (JSONObject) value.get(i);
            user.add(new ItensFolha(obj));
        }
        return user;
    }

    @Override
    public String toString() {
        return this.toJSON().toString();
    }

}
