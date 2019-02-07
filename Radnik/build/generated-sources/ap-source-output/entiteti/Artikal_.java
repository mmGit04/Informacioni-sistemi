package entiteti;

import entiteti.TipArtikla;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-26T15:42:07")
@StaticMetamodel(Artikal.class)
public class Artikal_ { 

    public static volatile SingularAttribute<Artikal, Integer> vreme;
    public static volatile SingularAttribute<Artikal, String> naziv;
    public static volatile SingularAttribute<Artikal, Integer> idartikal;
    public static volatile SingularAttribute<Artikal, TipArtikla> tip;
    public static volatile SingularAttribute<Artikal, Double> cena;

}