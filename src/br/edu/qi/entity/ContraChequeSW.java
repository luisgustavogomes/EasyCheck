/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.entity;

import br.edu.qi.controller.LoginController;
import java.io.IOException;
import java.io.InputStream;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author Anderson Jorge
 */
public class ContraChequeSW {

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static ContraCheque consultarContraCheque(String mes, String ano, String tipo) throws IOException {

        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost("https://frozen-waters-52896.herokuapp.com/folhas/" + LoginController.USER.getId());
            StringEntity params = new StringEntity("{\"MesPeriodo\":\"" + mes + "\",\"AnoPeriodo\":\"" + ano + "\",\"Tipo\":\"" + tipo + "\"}");
            request.addHeader("content-type", "application/json");
            request.addHeader("Accept", "application/json");
            request.setEntity(params);
            HttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();

            System.out.println(request);
            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    String content = convertStreamToString(instream);
                    //content = content.substring(1);
                    //content = content.substring(0, content.length() - 1);
                    return new ContraCheque(content);
                } finally {
                    instream.close();
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
