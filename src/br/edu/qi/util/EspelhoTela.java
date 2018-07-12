/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.util;

/**
 *
 * @author Anderson Jorge
 */
public class EspelhoTela {

    private String data;
    private String batidas;

    public EspelhoTela() {
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getBatidas() {
        return batidas;
    }

    public void setBatidas(String batidas) {
        this.batidas = batidas;
    }

    @Override
    public String toString() {
        return this.getData() + Tools.space(20) + this.getBatidas();
    }

}
