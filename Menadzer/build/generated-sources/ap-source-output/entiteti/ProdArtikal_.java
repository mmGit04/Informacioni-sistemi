package entiteti;

import entiteti.Artikal;
import entiteti.Prodavnica;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-02T22:26:06")
@StaticMetamodel(ProdArtikal.class)
public class ProdArtikal_ { 

    public static volatile SingularAttribute<ProdArtikal, Prodavnica> idprod;
    public static volatile SingularAttribute<ProdArtikal, Integer> idprodArtikal;
    public static volatile SingularAttribute<ProdArtikal, Integer> kolicina;
    public static volatile SingularAttribute<ProdArtikal, Artikal> idartikal;
    public static volatile SingularAttribute<ProdArtikal, Double> cena;

}