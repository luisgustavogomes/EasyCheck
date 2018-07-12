/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.entity;

import static br.edu.qi.entity.Batidas.parseBatidas;
import java.util.ArrayList;
import java.util.List;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Anderson Jorge
 */
public class EspelhoPonto {

    private Empresa dadosEmpresa;
    private List<Batidas> batidas;

    public EspelhoPonto() {
    }

    public EspelhoPonto(Empresa dadosEmpresa, List<Batidas> batidas) {
        this.dadosEmpresa = dadosEmpresa;
        this.batidas = batidas;
    }

    public Empresa getDadosEmpresa() {
        return dadosEmpresa;
    }

    public void setDadosEmpresa(Empresa dadosEmpresa) {
        this.dadosEmpresa = dadosEmpresa;
    }

    public List<Batidas> getBatidas() {
        return batidas;
    }

    public void setBatidas(List<Batidas> batidas) {
        this.batidas = batidas;
    }

    public EspelhoPonto(String json) {
        this((JSONObject) JSONValue.parse(json));
    }

    public EspelhoPonto(JSONObject obj) {
        this.dadosEmpresa = new Empresa(obj.get("Empresa").toString());
        this.batidas = parseBatidas(obj.get("Batidas").toString());
    }

    public JSONObject toJSON() {
        JSONObject obj = new JSONObject();
        obj.put("Empresa", this.dadosEmpresa);
        obj.put("Batidas", this.batidas);
        return obj;
    }

    public static List<EspelhoPonto> parseEspelhoPonto(String value) {
        return parseEspelhoPonto((JSONArray) JSONValue.parse(value));
    }

    public static List<EspelhoPonto> parseStringToArray(String value) throws ParseException {
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(value);
        JSONArray array = new JSONArray();
        array.add(obj);
        
        return array;
    }
    
    public static List<EspelhoPonto> parseEspelhoPonto(JSONArray value) {
        List<EspelhoPonto> espelho = new ArrayList<>();
        for (int i = 0; i < value.size(); i++) {
            JSONObject obj = (JSONObject) value.get(i);
            espelho.add(new EspelhoPonto(obj));
        }
        return espelho;
    }
    
//    public static Object parseStringToObject(String value) throws ParseException{
//        JSONParser parser = new JSONParser();
//        Object obj = parser.parse(value);
//        return obj;
//    }

    @Override
    public String toString() {
        return this.toJSON().toString();
    }

}
