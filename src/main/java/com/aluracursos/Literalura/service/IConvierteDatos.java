package com.aluracursos.Literalura.service;

public interface IConvierteDatos {
    <T> T obtenerDatos(String json, Class<T> clase); //Convierte datos obtenidos de Json a String
}
