/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.ind;

import java.math.BigInteger;
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
        name = "IndicadorAusentismoNativeQuery",
        query = "select "
        + "anio AS anio, "
        + "mes , "
        + "ind_frec , "
        + "ind_sev , "
        + "ind_ili,"
        + "num_casos,"
        + "num_dias "
        + "from emp.calcular_indicadores_ausentismo(?1, ?2, ?3);",
        resultSetMapping = "IndicadorAusentismoMapping"
)
@SqlResultSetMapping(
        name = "IndicadorAusentismoMapping",
        classes = @ConstructorResult(
                targetClass = IndicadorAusentismo.class,
                columns = {
                    @ColumnResult(name = "anio", type = Integer.class)
                    ,
            @ColumnResult(name = "mes", type = Integer.class)
                    ,
            @ColumnResult(name = "ind_frec", type = Double.class)
                    ,
            @ColumnResult(name = "ind_sev", type = Double.class)
                    ,
            @ColumnResult(name = "ind_ili", type = Double.class)
                    ,
            @ColumnResult(name = "num_casos", type = BigInteger.class)
                    ,
            @ColumnResult(name = "num_dias", type = BigInteger.class)}
        )
)
@Entity
public class IndicadorAusentismo {

    @Id
    private Integer anio;
    @Id
    private Integer mes;
    private Double indiceFrecuencia;
    private Double indiceSeveridad;
    private Double indiceLesionIncapacitante;
    private BigInteger numeroCasos;
    private BigInteger numeroDias;

    public IndicadorAusentismo() {
    }

    public IndicadorAusentismo(
            Integer anio,
            Integer mes,
            Double indiceFrecuencia,
            Double indiceSeveridad,
            Double indiceLesionIncapacitante,
            BigInteger numeroCasos,
            BigInteger numeroDias
    ) {
        this.anio = anio;
        this.mes = mes;
        this.indiceFrecuencia = indiceFrecuencia;
        this.indiceSeveridad = indiceSeveridad;
        this.indiceLesionIncapacitante = indiceLesionIncapacitante;
        this.numeroCasos = numeroCasos;
        this.numeroDias = numeroDias;
    }

    public Integer getAnio() {
        return anio;
    }

    public void setAnio(Integer anio) {
        this.anio = anio;
    }

    public Integer getMes() {
        return mes;
    }

    public void setMes(Integer mes) {
        this.mes = mes;
    }

    public Double getIndiceFrecuencia() {
        return indiceFrecuencia;
    }

    public void setIndiceFrecuencia(Double indiceFrecuencia) {
        this.indiceFrecuencia = indiceFrecuencia;
    }

    public Double getIndiceSeveridad() {
        return indiceSeveridad;
    }

    public void setIndiceSeveridad(Double indiceSeveridad) {
        this.indiceSeveridad = indiceSeveridad;
    }

    public Double getIndiceLesionIncapacitante() {
        return indiceLesionIncapacitante;
    }

    public void setIndiceLesionIncapacitante(Double indiceLesionIncapacitante) {
        this.indiceLesionIncapacitante = indiceLesionIncapacitante;
    }

    public BigInteger getNumeroCasos() {
        return numeroCasos;
    }

    public void setNumeroCasos(BigInteger numeroCasos) {
        this.numeroCasos = numeroCasos;
    }

    public BigInteger getNumeroDias() {
        return numeroDias;
    }

    public void setNumeroDias(BigInteger numeroDias) {
        this.numeroDias = numeroDias;
    }

}
