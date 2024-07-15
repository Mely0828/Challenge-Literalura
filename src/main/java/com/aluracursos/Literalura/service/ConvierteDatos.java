package com.aluracursos.Literalura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConvierteDatos implements IConvierteDatos {
    private ObjectMapper objectMapper = new ObjectMapper(); //Objeto de mapeo

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            return objectMapper.readValue(json, clase); //Mapea los valores del Json
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}