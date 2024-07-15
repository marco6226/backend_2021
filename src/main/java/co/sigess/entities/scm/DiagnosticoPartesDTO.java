/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.entities.scm;

import java.util.List;

/**
 *
 * @author Luis
 */
public class DiagnosticoPartesDTO {
    private Long idDiagnostico;
    private List<Long> idPartes;

    public Long getIdDiagnostico() {
        return idDiagnostico;
    }

    public void setIdDiagnostico(Long idDiagnostico) {
        this.idDiagnostico = idDiagnostico;
    }

    public List<Long> getIdPartes() {
        return idPartes;
    }

    public void setIdPartes(List<Long> idPartes) {
        this.idPartes = idPartes;
    }
    
    
}
