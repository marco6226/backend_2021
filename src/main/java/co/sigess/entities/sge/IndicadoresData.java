/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.sge;

import java.util.Collection;
import java.util.List;

/**
 *
 * @author fmoreno
 */
public class IndicadoresData {

    private Collection<String> labels;
    private List<DataSet> datasets;

    public Collection<String> getLabels() {
        return labels;
    }

    public void setLabels(Collection<String> labels) {
        this.labels = labels;
    }

    public List<DataSet> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<DataSet> datasets) {
        this.datasets = datasets;
    }

}
