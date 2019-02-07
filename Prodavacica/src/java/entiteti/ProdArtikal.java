/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entiteti;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;

/**
 *
 * @author Minjoza
 */
@Entity
@Table(name = "prod_artikal")
@NamedQueries({
    @NamedQuery(name = "ProdArtikal.findAll", query = "SELECT p FROM ProdArtikal p")
    , @NamedQuery(name = "ProdArtikal.findByIdprodArtikal", query = "SELECT p FROM ProdArtikal p WHERE p.idprodArtikal = :idprodArtikal")
    , @NamedQuery(name = "ProdArtikal.findByKolicina", query = "SELECT p FROM ProdArtikal p WHERE p.kolicina = :kolicina")
    , @NamedQuery(name = "ProdArtikal.findByCena", query = "SELECT p FROM ProdArtikal p WHERE p.cena = :cena")})
public class ProdArtikal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idprod_artikal")
    private Integer idprodArtikal;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina")
    private int kolicina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private double cena;
    @JoinColumn(name = "idartikal", referencedColumnName = "idartikal")
    @ManyToOne(optional = false)
    private Artikal idartikal;
    @JoinColumn(name = "idprod", referencedColumnName = "idprodavnica")
    @ManyToOne(optional = false)
    private Prodavnica idprod;

    public ProdArtikal() {
    }

    public ProdArtikal(Integer idprodArtikal) {
        this.idprodArtikal = idprodArtikal;
    }

    public ProdArtikal(Integer idprodArtikal, int kolicina, double cena) {
        this.idprodArtikal = idprodArtikal;
        this.kolicina = kolicina;
        this.cena = cena;
    }

    public Integer getIdprodArtikal() {
        return idprodArtikal;
    }

    public void setIdprodArtikal(Integer idprodArtikal) {
        this.idprodArtikal = idprodArtikal;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public double getCena() {
        return cena;
    }

    public void setCena(double cena) {
        this.cena = cena;
    }

    public Artikal getIdartikal() {
        return idartikal;
    }

    public void setIdartikal(Artikal idartikal) {
        this.idartikal = idartikal;
    }

    public Prodavnica getIdprod() {
        return idprod;
    }

    public void setIdprod(Prodavnica idprod) {
        this.idprod = idprod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idprodArtikal != null ? idprodArtikal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProdArtikal)) {
            return false;
        }
        ProdArtikal other = (ProdArtikal) object;
        if ((this.idprodArtikal == null && other.idprodArtikal != null) || (this.idprodArtikal != null && !this.idprodArtikal.equals(other.idprodArtikal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.ProdArtikal[ idprodArtikal=" + idprodArtikal + " ]";
    }
    
}
