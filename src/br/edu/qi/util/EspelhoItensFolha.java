/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.util;

/**
 *
 * @author lg
 */
public class EspelhoItensFolha {

    private String codItem;
    private String descricao;
    private String referencia;
    private Double valor;

    public EspelhoItensFolha(String codItem, String descricao, String referencia, Double valor) {
        this.codItem = codItem;
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

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

}
