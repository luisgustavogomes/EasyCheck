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
public class Batidas {

    private String dataHora;
    private String tipo;

    public Batidas() {
    }

    public Batidas(String dataHora, String tipo) {
        this.dataHora = dataHora;
        this.tipo = tipo;
    }

    public String getDataHora() {
        return dataHora;
    }

    public void setDataHora(String dataHora) {
        this.dataHora = dataHora;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Batidas(String json) {
        this((JSONObject) JSONValue.parse(json));
    }

    public Batidas(JSONObject obj) {
        this.dataHora = obj.get("DataHora").toString();
        this.tipo = obj.get("Tipo").toString();
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("DataHora", this.dataHora);
        obj.put("Tipo", this.tipo);
        return obj;
    }

    public static List<Batidas> parseBatidas(String value) {
        return parseBatidas((JSONArray) JSONValue.parse(value));
    }

    public static List<Batidas> parseBatidas(JSONArray value) {
        List<Batidas> espelho = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            JSONObject obj = (JSONObject) value.get(i);
            espelho.add(new Batidas(obj));
        }
        return espelho;
    }

    @Override
    public String toString() {
        return this.toJSON().toString();
    }

}
