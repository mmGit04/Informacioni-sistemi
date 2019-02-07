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
import javax.validation.constraints.Size;

/**
 *
 * @author Minjoza
 */
@Entity
@Table(name = "menadzer")
@NamedQueries({
    @NamedQuery(name = "Menadzer.findAll", query = "SELECT m FROM Menadzer m")
    , @NamedQuery(name = "Menadzer.findByIdmenadzer", query = "SELECT m FROM Menadzer m WHERE m.idmenadzer = :idmenadzer")
    , @NamedQuery(name = "Menadzer.findByIme", query = "SELECT m FROM Menadzer m WHERE m.ime = :ime")
    , @NamedQuery(name = "Menadzer.findByPrezime", query = "SELECT m FROM Menadzer m WHERE m.prezime = :prezime")})
public class Menadzer implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idmenadzer")
    private Integer idmenadzer;
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
    @JoinColumn(name = "prod", referencedColumnName = "idprodavnica")
    @ManyToOne(optional = false)
    private Prodavnica prod;

    public Menadzer() {
    }

    public Menadzer(Integer idmenadzer) {
        this.idmenadzer = idmenadzer;
    }

    public Menadzer(Integer idmenadzer, String ime, String prezime) {
        this.idmenadzer = idmenadzer;
        this.ime = ime;
        this.prezime = prezime;
    }

    public Integer getIdmenadzer() {
        return idmenadzer;
    }

    public void setIdmenadzer(Integer idmenadzer) {
        this.idmenadzer = idmenadzer;
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

    public Prodavnica getProd() {
        return prod;
    }

    public void setProd(Prodavnica prod) {
        this.prod = prod;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idmenadzer != null ? idmenadzer.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Menadzer)) {
            return false;
        }
        Menadzer other = (Menadzer) object;
        if ((this.idmenadzer == null && other.idmenadzer != null) || (this.idmenadzer != null && !this.idmenadzer.equals(other.idmenadzer))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.Menadzer[ idmenadzer=" + idmenadzer + " ]";
    }
    
}
