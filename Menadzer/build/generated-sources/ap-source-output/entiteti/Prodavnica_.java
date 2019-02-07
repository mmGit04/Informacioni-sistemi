package entiteti;

import entiteti.DnevniPromet;
import entiteti.Menadzer;
import entiteti.ProdArtikal;
import entiteti.ProdRezervacije;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-02T22:26:06")
@StaticMetamodel(Prodavnica.class)
public class Prodavnica_ { 

    public static volatile CollectionAttribute<Prodavnica, DnevniPromet> dnevniPrometCollection;
    public static volatile CollectionAttribute<Prodavnica, ProdRezervacije> prodRezervacijeCollection;
    public static volatile SingularAttribute<Prodavnica, Integer> idprodavnica;
    public static volatile CollectionAttribute<Prodavnica, Menadzer> menadzerCollection;
    public static volatile SingularAttribute<Prodavnica, String> naziv;
    public static volatile CollectionAttribute<Prodavnica, ProdArtikal> prodArtikalCollection;

}