/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.sigess.facade.scm.mailSLFacade;

import java.util.Map;

/**
 *
 * @author Luis
 */
public class EmailTask {
    private String email;
    private Map<String, String> parameters;

    public EmailTask(String email, Map<String, String> parameters) {
        this.email = email;
        this.parameters = parameters;
    }

    public String getEmail() {
        return email;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }
}

