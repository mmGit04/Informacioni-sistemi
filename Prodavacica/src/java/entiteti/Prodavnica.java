/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Minjoza
 */
@Entity
@Table(name = "prodavnica")
@NamedQueries({
    @NamedQuery(name = "Prodavnica.findAll", query = "SELECT p FROM Prodavnica p")
    , @NamedQuery(name = "Prodavnica.findByIdprodavnica", query = "SELECT p FROM Prodavnica p WHERE p.idprodavnica = :idprodavnica")
    , @NamedQuery(name = "Prodavnica.findByNaziv", query = "SELECT p FROM Prodavnica p WHERE p.naziv = :naziv")})
public class Prodavnica implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprodavnica")
    private Integer idprodavnica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idprodav")
    private Collection<ProdRezervacije> prodRezervacijeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idprod")
    private Collection<ProdArtikal> prodArtikalCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idprodavnica")
    private Collection<DnevniPromet> dnevniPrometCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "prod")
    private Collection<Menadzer> menadzerCollection;

    public Prodavnica() {
    }

    public Prodavnica(Integer idprodavnica) {
        this.idprodavnica = idprodavnica;
    }

    public Prodavnica(Integer idprodavnica, String naziv) {
        this.idprodavnica = idprodavnica;
        this.naziv = naziv;
    }

    public Integer getIdprodavnica() {
        return idprodavnica;
    }

    public void setIdprodavnica(Integer idprodavnica) {
        this.idprodavnica = idprodavnica;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Collection<ProdRezervacije> getProdRezervacijeCollection() {
        return prodRezervacijeCollection;
    }

    public void setProdRezervacijeCollection(Collection<ProdRezervacije> prodRezervacijeCollection) {
        this.prodRezervacijeCollection = prodRezervacijeCollection;
    }

    public Collection<ProdArtikal> getProdArtikalCollection() {
        return prodArtikalCollection;
    }

    public void setProdArtikalCollection(Collection<ProdArtikal> prodArtikalCollection) {
        this.prodArtikalCollection = prodArtikalCollection;
    }

    public Collection<DnevniPromet> getDnevniPrometCollection() {
        return dnevniPrometCollection;
    }

    public void setDnevniPrometCollection(Collection<DnevniPromet> dnevniPrometCollection) {
        this.dnevniPrometCollection = dnevniPrometCollection;
    }

    public Collection<Menadzer> getMenadzerCollection() {
        return menadzerCollection;
    }

    public void setMenadzerCollection(Collection<Menadzer> menadzerCollection) {
        this.menadzerCollection = menadzerCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprodavnica != null ? idprodavnica.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prodavnica)) {
            return false;
        }
        Prodavnica other = (Prodavnica) object;
        if ((this.idprodavnica == null && other.idprodavnica != null) || (this.idprodavnica != null && !this.idprodavnica.equals(other.idprodavnica))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Prodavnica[ idprodavnica=" + idprodavnica + " ]";
    }
    
}
