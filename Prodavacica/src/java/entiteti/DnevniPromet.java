/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Minjoza
 */
@Entity
@Table(name = "dnevni_promet")
@NamedQueries({
    @NamedQuery(name = "DnevniPromet.findAll", query = "SELECT d FROM DnevniPromet d")
    , @NamedQuery(name = "DnevniPromet.findByIddnevniPromet", query = "SELECT d FROM DnevniPromet d WHERE d.iddnevniPromet = :iddnevniPromet")
    , @NamedQuery(name = "DnevniPromet.findByDatum", query = "SELECT d FROM DnevniPromet d WHERE d.datum = :datum")
    , @NamedQuery(name = "DnevniPromet.findByKolicina", query = "SELECT d FROM DnevniPromet d WHERE d.kolicina = :kolicina")
    , @NamedQuery(name = "DnevniPromet.findByIznos", query = "SELECT d FROM DnevniPromet d WHERE d.iznos = :iznos")})
public class DnevniPromet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "iddnevni_promet")
    private Integer iddnevniPromet;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datum")
    @Temporal(TemporalType.DATE)
    private Date datum;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina")
    private int kolicina;
    @Column(name = "iznos")
    private Integer iznos;
    @JoinColumn(name = "idprodavnica", referencedColumnName = "idprodavnica")
    @ManyToOne(optional = false)
    private Prodavnica idprodavnica;

    public DnevniPromet() {
    }

    public DnevniPromet(Integer iddnevniPromet) {
        this.iddnevniPromet = iddnevniPromet;
    }

    public DnevniPromet(Integer iddnevniPromet, Date datum, int kolicina) {
        this.iddnevniPromet = iddnevniPromet;
        this.datum = datum;
        this.kolicina = kolicina;
    }

    public Integer getIddnevniPromet() {
        return iddnevniPromet;
    }

    public void setIddnevniPromet(Integer iddnevniPromet) {
        this.iddnevniPromet = iddnevniPromet;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Integer getIznos() {
        return iznos;
    }

    public void setIznos(Integer iznos) {
        this.iznos = iznos;
    }

    public Prodavnica getIdprodavnica() {
        return idprodavnica;
    }

    public void setIdprodavnica(Prodavnica idprodavnica) {
        this.idprodavnica = idprodavnica;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (iddnevniPromet != null ? iddnevniPromet.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DnevniPromet)) {
            return false;
        }
        DnevniPromet other = (DnevniPromet) object;
        if ((this.iddnevniPromet == null && other.iddnevniPromet != null) || (this.iddnevniPromet != null && !this.iddnevniPromet.equals(other.iddnevniPromet))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.DnevniPromet[ iddnevniPromet=" + iddnevniPromet + " ]";
    }
    
}
