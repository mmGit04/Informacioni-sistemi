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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Minjoza
 */
@Entity
@Table(name = "prod_rezervacije")
@NamedQueries({
    @NamedQuery(name = "ProdRezervacije.findAll", query = "SELECT p FROM ProdRezervacije p")
    , @NamedQuery(name = "ProdRezervacije.findByIdprodRezervacije", query = "SELECT p FROM ProdRezervacije p WHERE p.idprodRezervacije = :idprodRezervacije")
    , @NamedQuery(name = "ProdRezervacije.findByKolicina", query = "SELECT p FROM ProdRezervacije p WHERE p.kolicina = :kolicina")})
public class ProdRezervacije implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprod_rezervacije")
    private Integer idprodRezervacije;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina")
    private int kolicina;
    @JoinColumn(name = "IdArtik", referencedColumnName = "idartikal")
    @ManyToOne
    private Artikal idArtik;
    @JoinColumn(name = "idprodav", referencedColumnName = "idprodavnica")
    @ManyToOne(optional = false)
    private Prodavnica idprodav;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idrez")
    private Collection<KupacRezervacija> kupacRezervacijaCollection;

    public ProdRezervacije() {
    }

    public ProdRezervacije(Integer idprodRezervacije) {
        this.idprodRezervacije = idprodRezervacije;
    }

    public ProdRezervacije(Integer idprodRezervacije, int kolicina) {
        this.idprodRezervacije = idprodRezervacije;
        this.kolicina = kolicina;
    }

    public Integer getIdprodRezervacije() {
        return idprodRezervacije;
    }

    public void setIdprodRezervacije(Integer idprodRezervacije) {
        this.idprodRezervacije = idprodRezervacije;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Artikal getIdArtik() {
        return idArtik;
    }

    public void setIdArtik(Artikal idArtik) {
        this.idArtik = idArtik;
    }

    public Prodavnica getIdprodav() {
        return idprodav;
    }

    public void setIdprodav(Prodavnica idprodav) {
        this.idprodav = idprodav;
    }

    public Collection<KupacRezervacija> getKupacRezervacijaCollection() {
        return kupacRezervacijaCollection;
    }

    public void setKupacRezervacijaCollection(Collection<KupacRezervacija> kupacRezervacijaCollection) {
        this.kupacRezervacijaCollection = kupacRezervacijaCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprodRezervacije != null ? idprodRezervacije.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProdRezervacije)) {
            return false;
        }
        ProdRezervacije other = (ProdRezervacije) object;
        if ((this.idprodRezervacije == null && other.idprodRezervacije != null) || (this.idprodRezervacije != null && !this.idprodRezervacije.equals(other.idprodRezervacije))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.ProdRezervacije[ idprodRezervacije=" + idprodRezervacije + " ]";
    }
    
}
