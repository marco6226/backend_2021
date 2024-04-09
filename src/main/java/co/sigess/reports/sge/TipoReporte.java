/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.reports.sge;

/**
 *
 * @author fmoreno
 */
public enum TipoReporte {
    DOCX("docx"),
    XLSX("xlsx"),
    PDF("pdf");
    
    private final String extension;

    private TipoReporte(String extension) {
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }
    
    
}
