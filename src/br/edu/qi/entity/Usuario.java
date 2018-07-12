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
public class Usuario {

    private String DataAdmissao;
    private String Id;
    private String LogoEmpresa;
    private String Nome;
    private String Situacao;

    public String getDataAdmissao() {
        return DataAdmissao;
    }

    public void setDataAdmissao(String DataAdmissao) {
        this.DataAdmissao = DataAdmissao;
    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getLogoEmpresa() {
        return LogoEmpresa;
    }

    public void setLogoEmpresa(String LogoEmpresa) {
        this.LogoEmpresa = LogoEmpresa;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String Nome) {
        this.Nome = Nome;
    }

    public String getSituacao() {
        return Situacao;
    }

    public void setSituacao(String Situacao) {
        this.Situacao = Situacao;
    }

    public Usuario(String DataAdmissao, String Id, String LogoEmpresa, String Nome, String Situacao) {
        this.DataAdmissao = DataAdmissao;
        this.Id = Id;
        this.LogoEmpresa = LogoEmpresa;
        this.Nome = Nome;
        this.Situacao = Situacao;
    }

    public Usuario(String json) {
        this((JSONObject) JSONValue.parse(json));
    }

    public Usuario(JSONObject obj) {
        this.DataAdmissao = obj.get("DataAdmissao").toString();
        this.Id = obj.get("Id").toString();
        this.LogoEmpresa = obj.get("LogoEmpresa").toString();
        this.Nome = obj.get("Nome").toString();
        this.Situacao = obj.get("Situacao").toString();

    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("DataAdmissao", this.DataAdmissao);
        obj.put("Id", this.Id);
        obj.put("LogoEmpresa", this.LogoEmpresa);
        obj.put("Nome", this.Nome);
        obj.put("Situacao", this.Situacao);
        return obj;
    }

    public static List<Usuario> parseUsuario(String value) {
        return parseUsuario((JSONArray) JSONValue.parse(value));
    }

    public static List<Usuario> parseUsuario(JSONArray value) {
        List<Usuario> user = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            JSONObject obj = (JSONObject) value.get(i);
            user.add(new Usuario(obj));
        }
        return user;
    }

    @Override
    public String toString() {
        return this.toJSON().toString();
    }

}
