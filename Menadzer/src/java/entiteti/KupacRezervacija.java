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
@Table(name = "kupac_rezervacija")
@NamedQueries({
    @NamedQuery(name = "KupacRezervacija.findAll", query = "SELECT k FROM KupacRezervacija k")
    , @NamedQuery(name = "KupacRezervacija.findByIdkupacRezervacija", query = "SELECT k FROM KupacRezervacija k WHERE k.idkupacRezervacija = :idkupacRezervacija")
    , @NamedQuery(name = "KupacRezervacija.findByTelefon", query = "SELECT k FROM KupacRezervacija k WHERE k.telefon = :telefon")
    , @NamedQuery(name = "KupacRezervacija.findByIme", query = "SELECT k FROM KupacRezervacija k WHERE k.ime = :ime")
    , @NamedQuery(name = "KupacRezervacija.findByPrezime", query = "SELECT k FROM KupacRezervacija k WHERE k.prezime = :prezime")})
public class KupacRezervacija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idkupac_rezervacija")
    private Integer idkupacRezervacija;
    @Basic(optional = false)
    @NotNull
    @Column(name = "telefon")
    private int telefon;
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
    @JoinColumn(name = "idrez", referencedColumnName = "idprod_rezervacije")
    @ManyToOne(optional = false)
    private ProdRezervacije idrez;

    public KupacRezervacija() {
    }

    public KupacRezervacija(Integer idkupacRezervacija) {
        this.idkupacRezervacija = idkupacRezervacija;
    }

    public KupacRezervacija(Integer idkupacRezervacija, int telefon, String ime, String prezime) {
        this.idkupacRezervacija = idkupacRezervacija;
        this.telefon = telefon;
        this.ime = ime;
        this.prezime = prezime;
    }

    public Integer getIdkupacRezervacija() {
        return idkupacRezervacija;
    }

    public void setIdkupacRezervacija(Integer idkupacRezervacija) {
        this.idkupacRezervacija = idkupacRezervacija;
    }

    public int getTelefon() {
        return telefon;
    }

    public void setTelefon(int telefon) {
        this.telefon = telefon;
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

    public ProdRezervacije getIdrez() {
        return idrez;
    }

    public void setIdrez(ProdRezervacije idrez) {
        this.idrez = idrez;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idkupacRezervacija != null ? idkupacRezervacija.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof KupacRezervacija)) {
            return false;
        }
        KupacRezervacija other = (KupacRezervacija) object;
        if ((this.idkupacRezervacija == null && other.idkupacRezervacija != null) || (this.idkupacRezervacija != null && !this.idkupacRezervacija.equals(other.idkupacRezervacija))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entiteti.KupacRezervacija[ idkupacRezervacija=" + idkupacRezervacija + " ]";
    }
    
}
