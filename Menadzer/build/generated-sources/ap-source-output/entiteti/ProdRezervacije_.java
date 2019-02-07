package entiteti;

import entiteti.Artikal;
import entiteti.KupacRezervacija;
import entiteti.Prodavnica;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-02T22:26:06")
@StaticMetamodel(ProdRezervacije.class)
public class ProdRezervacije_ { 

    public static volatile SingularAttribute<ProdRezervacije, Prodavnica> idprodav;
    public static volatile CollectionAttribute<ProdRezervacije, KupacRezervacija> kupacRezervacijaCollection;
    public static volatile SingularAttribute<ProdRezervacije, Artikal> idArtik;
    public static volatile SingularAttribute<ProdRezervacije, Integer> kolicina;
    public static volatile SingularAttribute<ProdRezervacije, Integer> idprodRezervacije;

}