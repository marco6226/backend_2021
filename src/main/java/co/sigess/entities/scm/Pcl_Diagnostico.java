package co.sigess.entities.scm;

import co.sigess.entities.emp.Empresa;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Luis
 */
@Entity
@Table(name = "pcl_diagnostico", schema = "scm")
public class Pcl_Diagnostico implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @SequenceGenerator(name = "pcl_diagnostico_id_sec_id", schema = "scm", sequenceName = "pcl_diagnostico_id_sec_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "pcl_diagnostico_id_sec_id")
    @Column(name = "id")
    private Long id;
    
    @JoinColumn(name = "id_pcl", referencedColumnName = "id")
    @ManyToOne
    private Pcl pcl;

    @JoinColumn(name = "id_diagnostico", referencedColumnName = "id")
    @ManyToOne
    private Diagnosticos diagnosticos;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Pcl getPcl() {
        return pcl;
    }

    public void setPcl(Pcl pcl) {
        this.pcl = pcl;
    }

    public Diagnosticos getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(Diagnosticos diagnosticos) {
        this.diagnosticos = diagnosticos;
    }
    

}