/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.qi.entity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

/**
 *
 * @author Anderson Jorge
 */
public class UsuarioWS {

    private static String convertStreamToString(java.io.InputStream is) {
        java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }

    public static Usuario consultarUsuario(String user, String pass) throws IOException {

        HttpClient httpClient = HttpClientBuilder.create().build();
        try {
            HttpEntity entity = teste(user, pass, httpClient);

            if (entity != null) {
                InputStream instream = entity.getContent();
                try {
                    String content = convertStreamToString(instream);
                    return new Usuario(content);
                } finally {
                    instream.close();
                }
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private static HttpEntity teste(String user, String pass, HttpClient httpClient) throws UnsupportedEncodingException, IOException {
        HttpPost request = new HttpPost("https://frozen-waters-52896.herokuapp.com/login");
        StringEntity params = new StringEntity("{\"Usuario\":\"" + user + "\",\"Senha\":\"" + pass + "\"}");
        request.addHeader("content-type", "application/json");
        request.addHeader("Accept", "application/json");
        request.setEntity(params);
        HttpResponse response = httpClient.execute(request);
        HttpEntity entity = response.getEntity();
        return entity;
    }
}
