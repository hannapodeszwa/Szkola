package pl.polsl.entities;

import java.sql.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.7.v20200504-rNA", date="2021-05-26T20:51:52")
@StaticMetamodel(Oceny.class)
public class Oceny_ { 

    public static volatile SingularAttribute<Oceny, Integer> idPrzedmiotu;
    public static volatile SingularAttribute<Oceny, Float> waga;
    public static volatile SingularAttribute<Oceny, Date> data;
    public static volatile SingularAttribute<Oceny, Integer> idUcznia;
    public static volatile SingularAttribute<Oceny, Integer> ID;
    public static volatile SingularAttribute<Oceny, Float> ocena;
    public static volatile SingularAttribute<Oceny, String> opis;

}