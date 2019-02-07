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
import javax.validation.constraints.Size;

/**
 *
 * @author Minjoza
 */
@Entity
@Table(name = "artikal")
@NamedQueries({
    @NamedQuery(name = "Artikal.findAll", query = "SELECT a FROM Artikal a")
    , @NamedQuery(name = "Artikal.findByIdartikal", query = "SELECT a FROM Artikal a WHERE a.idartikal = :idartikal")
    , @NamedQuery(name = "Artikal.findByNaziv", query = "SELECT a FROM Artikal a WHERE a.naziv = :naziv")
    , @NamedQuery(name = "Artikal.findByCena", query = "SELECT a FROM Artikal a WHERE a.cena = :cena")})
public class Artikal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idartikal")
    private Integer idartikal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "cena")
    private Double cena;
    @OneToMany(mappedBy = "idArtik")
    private Collection<ProdRezervacije> prodRezervacijeCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idartikal")
    private Collection<ProdArtikal> prodArtikalCollection;
    @JoinColumn(name = "idtip_artikla", referencedColumnName = "idtip_artikla")
    @ManyToOne
    private TipArtikla idtipArtikla;

    public Artikal() {
    }

    public Artikal(Integer idartikal) {
        this.idartikal = idartikal;
    }

    public Artikal(Integer idartikal, String naziv) {
        this.idartikal = idartikal;
        this.naziv = naziv;
    }

    public Integer getIdartikal() {
        return idartikal;
    }

    public void setIdartikal(Integer idartikal) {
        this.idartikal = idartikal;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Double getCena() {
        return cena;
    }

    public void setCena(Double cena) {
        this.cena = cena;
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

    public TipArtikla getIdtipArtikla() {
        return idtipArtikla;
    }

    public void setIdtipArtikla(TipArtikla idtipArtikla) {
        this.idtipArtikla = idtipArtikla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idartikal != null ? idartikal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Artikal)) {
            return false;
        }
        Artikal other = (Artikal) object;
        if ((this.idartikal == null && other.idartikal != null) || (this.idartikal != null && !this.idartikal.equals(other.idartikal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Artikal[ idartikal=" + idartikal + " ]";
    }
    
}
