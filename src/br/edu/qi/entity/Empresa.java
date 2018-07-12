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
public class Empresa {

    private String razaoSocial;
    private String cnpj;

    public Empresa() {
    }

    public Empresa(String razaoSocial, String cnpj) {
        this.razaoSocial = razaoSocial;
        this.cnpj = cnpj;
    }

    public String getRazaoSocial() {
        return razaoSocial;
    }

    public void setRazaoSocial(String razaoSocial) {
        this.razaoSocial = razaoSocial;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public Empresa(String json) {
        this((JSONObject) JSONValue.parse(json));
    }

    public Empresa(JSONObject obj) {
        this.razaoSocial = obj.get("RazaoSocial").toString();
        this.cnpj = obj.get("CNPJ").toString();
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("RazaoSocial", this.razaoSocial);
        obj.put("CNPJ", this.cnpj);
        return obj;
    }

    public static List<Empresa> parseEmpresa(String value) {
        return parseEmpresa((JSONArray) JSONValue.parse(value));
    }

    public static List<Empresa> parseEmpresa(JSONArray value) {
        List<Empresa> user = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            JSONObject obj = (JSONObject) value.get(i);
            user.add(new Empresa(obj));
        }
        return user;
    }

    @Override
    public String toString() {
        return this.toJSON().toString();
    }

}
