/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.sigess.entities.com;

import co.sigess.util.converter.JsonObjectConverter;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author fmoreno
 */
@Entity
@Table(name = "api_version", schema = "com")
public class ApiVersion implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    
    @Size(max = 20)
    @Column(name = "version_actual")
    private String versionActual;
    
    @Convert(converter = JsonObjectConverter.class)
    @Column(name = "soportado")
    private Map<String, Object> soportado;
    
    @Column(name = "playstore_url")
    private String playStoreUrl;
    
    @Column(name = "appstore_url")
    private String appStoreUrl;
    
    @Column(name = "ios_version")
    private String iosVersion;
    
    @Column(name = "android_version")
    private String androidVersion;

    public ApiVersion() {
    }

    public ApiVersion(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersionActual() {
        return versionActual;
    }

    public void setVersionActual(String versionActual) {
        this.versionActual = versionActual;
    }

    public Map<String, Object> getSoportado() {
        return soportado;
    }

    public void setSoportado(Map<String, Object> soportado) {
        this.soportado = soportado;
    }

    public String getPlayStoreUrl() {
        return playStoreUrl;
    }

    public void setPlayStoreUrl(String playStoreUrl) {
        this.playStoreUrl = playStoreUrl;
    }

    public String getAppStoreUrl() {
        return appStoreUrl;
    }

    public void setAppStoreUrl(String appStoreUrl) {
        this.appStoreUrl = appStoreUrl;
    }

    public String getIosVersion() {
        return iosVersion;
    }

    public void setIosVersion(String iosVersion) {
        this.iosVersion = iosVersion;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ApiVersion)) {
            return false;
        }
        ApiVersion other = (ApiVersion) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.sigess.entities.com.ApiVersion[ id=" + id + " ]";
    }
    
}
