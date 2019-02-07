package entiteti;

import entiteti.ProdRezervacije;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-02T22:26:06")
@StaticMetamodel(KupacRezervacija.class)
public class KupacRezervacija_ { 

    public static volatile SingularAttribute<KupacRezervacija, String> ime;
    public static volatile SingularAttribute<KupacRezervacija, String> prezime;
    public static volatile SingularAttribute<KupacRezervacija, Integer> telefon;
    public static volatile SingularAttribute<KupacRezervacija, Integer> idkupacRezervacija;
    public static volatile SingularAttribute<KupacRezervacija, ProdRezervacije> idrez;

}