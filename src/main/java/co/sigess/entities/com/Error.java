/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.com;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 *
 * @author fmoreno
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum Error {
    EMP_PERFIL_C("Error al intentar crear un nuevo perfil: ", "10-001-C"),
    EMP_PERFIL_R("Error al intentar consultar el perfil: ", "10-001-R"),
    EMP_PERFIL_U("Error al intentar actualizar el perfil: ", "10-001-U"),
    EMP_PERFIL_D("Error al intentar eliminar el perfil: ", "10-001-D"),
    // --
    EMP_PERMISO_C("Error al intentar crear un nuevo permiso: ", "10-001-C"),
    EMP_PERMISO_R("Error al intentar consultar el permiso: ", "10-001-R"),
    EMP_PERMISO_U("Error al intentar actualizar el permiso: ", "10-001-U"),
    EMP_PERMISO_D("Error al intentar eliminar el permiso: ", "10-001-D");

    private final String descripcion;
    private final String codigo;

    private Error(String descripcion, String codigo) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getId() {
        return this.name();
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCodigo() {
        return codigo;
    }

    @JsonCreator // This is the factory method and must be static
    public static Error fromString(String string) {
        return Error.valueOf(string);
    }

    @Override
    public String toString() {
        return name();
    }

}
