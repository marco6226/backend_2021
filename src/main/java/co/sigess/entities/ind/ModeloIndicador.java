/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.SqlResultSetMapping;

/**
 *
 * @author fmoreno
 */
@NamedNativeQueries({
    @NamedNativeQuery(
            name = "CaracterizacionAusentismoNativeQuery",
            query = "select "
            + "indicador, "
            + "datos::text "
            + "from com.indicadores_aus(?1, ?2);",
            resultSetMapping = "IndicadorMapping"
    )
    ,
     @NamedNativeQuery(
            name = "EvaluacionDesempenoNativeQuery",
            query = "select "
            + "indicador, "
            + "datos::text "
            + "from com.indicadores_evaluacion_comp(?1, ?2, ?3);",
            resultSetMapping = "IndicadorMapping"
    )
})
@SqlResultSetMapping(
        name = "IndicadorMapping",
        classes = @ConstructorResult(
                targetClass = ModeloIndicador.class,
                columns = {
                    @ColumnResult(name = "indicador", type = String.class)
                    ,
                    @ColumnResult(name = "datos", type = String.class)
                }
        )
)
@Entity
public class ModeloIndicador {

    @Id
    @Column(name = "indicador")
    private String indicador;

    @Column(name = "datos")
    private String datos;

    public ModeloIndicador() {
    }

    public ModeloIndicador(String indicador, String datos) {
        this.indicador = indicador;
        this.datos = datos;
    }

    public String getIndicador() {
        return indicador;
    }

    public void setIndicador(String indicador) {
        this.indicador = indicador;
    }

    public String getDatos() {
        return datos;
    }

    public void setDatos(String datos) {
        this.datos = datos;
    }

}
