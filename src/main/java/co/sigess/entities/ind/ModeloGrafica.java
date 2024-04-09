/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import co.sigess.util.converter.DatasetListConverter;
import co.sigess.util.converter.StringListConverter;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

/**
 *
 * @author fmoreno
 */
@NamedNativeQuery(
        name = "IndicadorRaiNativeQuery",
        query = "select "
        + "title, "
        + "labels, "
        + "datasets "
        + "from com.indicadores_rai(?1, ?2, ?3);",
        resultSetMapping = "IndicadorRaiMapping"
)
@SqlResultSetMapping(
        name = "IndicadorRaiMapping",
        classes = @ConstructorResult(
                targetClass = ModeloGrafica.class,
                columns = {
                    @ColumnResult(name = "title", type = String.class)
                    ,
            @ColumnResult(name = "labels", type = String.class)
                    ,
            @ColumnResult(name = "datasets", type = String.class)}
        )
)
@Entity
public class ModeloGrafica  implements Serializable{

    @Id
    @Column(name = "title")
    private String title;
    
    @Column(name = "labels")
    private List<String> labels;
    
    @Column(name = "datasets")
    private List<Dataset> datasets;
    
    private FichaTecnicaIndicador fichaTecnicaIndicador;

    public ModeloGrafica() {
    }

    public ModeloGrafica(String title, String labels, String datasets) {
        this.title = title;
        this.labels = StringListConverter.convertToEntity(labels);
        this.datasets = DatasetListConverter.convertToEntity(datasets);
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getLabels() {
        return labels;
    }

    public void setLabels(List<String> labels) {
        this.labels = labels;
    }

    public List<Dataset> getDatasets() {
        return datasets;
    }

    public void setDatasets(List<Dataset> datasets) {
        this.datasets = datasets;
    }

    public FichaTecnicaIndicador getFichaTecnicaIndicador() {
        return fichaTecnicaIndicador;
    }

    public void setFichaTecnicaIndicador(FichaTecnicaIndicador fichaTecnicaIndicador) {
        this.fichaTecnicaIndicador = fichaTecnicaIndicador;
    }

}
