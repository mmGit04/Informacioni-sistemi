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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Minjoza
 */
@Entity
@Table(name = "prodavac")
@NamedQueries({
    @NamedQuery(name = "Prodavac.findAll", query = "SELECT p FROM Prodavac p")
    , @NamedQuery(name = "Prodavac.findByIdProdavac", query = "SELECT p FROM Prodavac p WHERE p.idProdavac = :idProdavac")
    , @NamedQuery(name = "Prodavac.findByIme", query = "SELECT p FROM Prodavac p WHERE p.ime = :ime")
    , @NamedQuery(name = "Prodavac.findByPrezime", query = "SELECT p FROM Prodavac p WHERE p.prezime = :prezime")})
public class Prodavac implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idProdavac")
    private Integer idProdavac;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Ime")
    private String ime;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Prezime")
    private String prezime;

    public Prodavac() {
    }

    public Prodavac(Integer idProdavac) {
        this.idProdavac = idProdavac;
    }

    public Prodavac(Integer idProdavac, String ime, String prezime) {
        this.idProdavac = idProdavac;
        this.ime = ime;
        this.prezime = prezime;
    }

    public Integer getIdProdavac() {
        return idProdavac;
    }

    public void setIdProdavac(Integer idProdavac) {
        this.idProdavac = idProdavac;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProdavac != null ? idProdavac.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prodavac)) {
            return false;
        }
        Prodavac other = (Prodavac) object;
        if ((this.idProdavac == null && other.idProdavac != null) || (this.idProdavac != null && !this.idProdavac.equals(other.idProdavac))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Prodavac[ idProdavac=" + idProdavac + " ]";
    }
    
}
