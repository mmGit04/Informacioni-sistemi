package entiteti;

import entiteti.Prodavnica;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2018-06-02T22:26:06")
@StaticMetamodel(DnevniPromet.class)
public class DnevniPromet_ { 

    public static volatile SingularAttribute<DnevniPromet, Date> datum;
    public static volatile SingularAttribute<DnevniPromet, Integer> iznos;
    public static volatile SingularAttribute<DnevniPromet, Integer> iddnevniPromet;
    public static volatile SingularAttribute<DnevniPromet, Prodavnica> idprodavnica;
    public static volatile SingularAttribute<DnevniPromet, Integer> kolicina;

}