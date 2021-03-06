package com.pickanis.api.dominio.excepcion;

public class ExcepcionMascotaNoEncontrada extends RuntimeException {
    public static final String MENSAJE = "No se encontrĂ³ la mascota con id %d en base de datos";

    public ExcepcionMascotaNoEncontrada(Long idMascota) {
        super(String.format(MENSAJE, idMascota));
    }
}
