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
@Table(name = "tip_artikla")
@NamedQueries({
    @NamedQuery(name = "TipArtikla.findAll", query = "SELECT t FROM TipArtikla t")
    , @NamedQuery(name = "TipArtikla.findByIdtipArtikla", query = "SELECT t FROM TipArtikla t WHERE t.idtipArtikla = :idtipArtikla")
    , @NamedQuery(name = "TipArtikla.findByNaziv", query = "SELECT t FROM TipArtikla t WHERE t.naziv = :naziv")})
public class TipArtikla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtip_artikla")
    private Integer idtipArtikla;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tip")
    private Collection<Artikal> artikalCollection;

    public TipArtikla() {
    }

    public TipArtikla(Integer idtipArtikla) {
        this.idtipArtikla = idtipArtikla;
    }

    public TipArtikla(Integer idtipArtikla, String naziv) {
        this.idtipArtikla = idtipArtikla;
        this.naziv = naziv;
    }

    public Integer getIdtipArtikla() {
        return idtipArtikla;
    }

    public void setIdtipArtikla(Integer idtipArtikla) {
        this.idtipArtikla = idtipArtikla;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Collection<Artikal> getArtikalCollection() {
        return artikalCollection;
    }

    public void setArtikalCollection(Collection<Artikal> artikalCollection) {
        this.artikalCollection = artikalCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idtipArtikla != null ? idtipArtikla.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TipArtikla)) {
            return false;
        }
        TipArtikla other = (TipArtikla) object;
        if ((this.idtipArtikla == null && other.idtipArtikla != null) || (this.idtipArtikla != null && !this.idtipArtikla.equals(other.idtipArtikla))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.TipArtikla[ idtipArtikla=" + idtipArtikla + " ]";
    }
    
}
