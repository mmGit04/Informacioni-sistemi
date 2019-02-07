package entiteti;

import entiteti.ProdArtikal;
import entiteti.ProdRezervacije;
import entiteti.TipArtikla;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-02T22:26:06")
@StaticMetamodel(Artikal.class)
public class Artikal_ { 

    public static volatile CollectionAttribute<Artikal, ProdRezervacije> prodRezervacijeCollection;
    public static volatile SingularAttribute<Artikal, String> naziv;
    public static volatile SingularAttribute<Artikal, Integer> idartikal;
    public static volatile SingularAttribute<Artikal, TipArtikla> idtipArtikla;
    public static volatile SingularAttribute<Artikal, Double> cena;
    public static volatile CollectionAttribute<Artikal, ProdArtikal> prodArtikalCollection;

}