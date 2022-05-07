package com.clinicaodontologica.proyecto.exceptions;

public class BadRequestException extends Exception{
    public BadRequestException(String mensaje){
        super(mensaje);
    }
}
