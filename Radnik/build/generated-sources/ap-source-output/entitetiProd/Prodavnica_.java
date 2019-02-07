package entitetiProd;

import entitetiProd.Menadzer;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-05-26T15:42:07")
@StaticMetamodel(Prodavnica.class)
public class Prodavnica_ { 

    public static volatile SingularAttribute<Prodavnica, Integer> idprodavnica;
    public static volatile CollectionAttribute<Prodavnica, Menadzer> menadzerCollection;
    public static volatile SingularAttribute<Prodavnica, String> naziv;

}