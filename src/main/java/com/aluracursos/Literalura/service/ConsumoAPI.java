package com.aluracursos.Literalura.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoAPI {
    public String obtenerDatos(String url) {
        //Levantamiento de la consulta HTTP
        HttpClient clienteHTTP = HttpClient.newHttpClient();
        HttpRequest solicitudHTTP = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();
        
        //Obtención de la respueta HTTP
        HttpResponse<String> response = null;
        try {
            response = clienteHTTP
                    .send(solicitudHTTP, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        
        //Respuesta en formato JSON
        String jsonResponse = response.body();
        return jsonResponse;
    }
}
