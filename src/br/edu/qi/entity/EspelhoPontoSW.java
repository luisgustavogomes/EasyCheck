/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.entity;

import br.edu.qi.controller.LoginController;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
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
public class EspelhoPontoSW {

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static EspelhoPonto consultarEspelhoPonto(String user, String dataInicial, String dataFinal) throws IOException {

        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpPost request = new HttpPost("https://frozen-waters-52896.herokuapp.com/batidas/" + LoginController.USER.getId());
            StringEntity params = new StringEntity("{\"DataInicial\":\"" + dataInicial + "\",\"DataFinal\":\"" + dataFinal + "\"}");
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
                    //EspelhoPonto esp = new EspelhoPonto();
                    //return esp.parseEspelhoPonto(content);
                   //List<EspelhoPonto> arr = esp.parseStringToArray(content);
//                    esp.parseEspelhoPonto(arr);
                    return new EspelhoPonto(content);// esp.parseStringToArray(content);
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
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
