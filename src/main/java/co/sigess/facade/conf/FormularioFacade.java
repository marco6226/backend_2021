/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.facade.conf;

import co.sigess.entities.conf.Campo;
import co.sigess.entities.conf.Formulario;
import co.sigess.facade.com.AbstractFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author fmoreno
 */
@Stateless
public class FormularioFacade extends AbstractFacade<Formulario> {

    @PersistenceContext(unitName = "SIGESS_PU")
    private EntityManager em;

    @EJB
    private CampoFacade campoFacade;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FormularioFacade() {
        super(Formulario.class);
    }

    @Override
    public Formulario create(Formulario formulario) throws Exception {
        if (formulario == null) {
            throw new IllegalArgumentException("No se ha especificado un formulario válido");
        }
        if (formulario.getCampoList() == null || formulario.getCampoList().isEmpty()) {
            throw new IllegalArgumentException("El formulario no contiene campos");
        }
        formulario = super.create(formulario);
        for (Campo campo : formulario.getCampoList()) {
            campo.setFormulario(formulario);
            campoFacade.create(campo);
        }
        return formulario;
    }

    @Override
    public Formulario edit(Formulario formulario) throws Exception {        
        campoFacade.removeByFormulario(formulario);
        for (Campo campo : formulario.getCampoList()) {
            campo.setFormulario(formulario);
            campoFacade.create(campo);
        }
        formulario = super.edit(formulario);
        return formulario;
    }
    
    

}
