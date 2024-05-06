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
public class CreatePclDTO {
    private Pcl pcl;
        private List<Long> diags;

    public CreatePclDTO(Pcl pcl, List<Long> diags) {
        this.pcl = pcl;
        this.diags = diags;
    }
     public CreatePclDTO() {
        
    }
    public Pcl getPcl() {
        return pcl;
    }

    public void setPcl(Pcl pcl) {
        this.pcl = pcl;
    }

    public List<Long> getDiags() {
        return diags;
    }

    public void setDiags(List<Long> diags) {
        this.diags = diags;
    }
    
}
