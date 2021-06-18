package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "uwagi")
@NamedQueries({
        @NamedQuery(name = "rozklady.findByTeacher",
                query = "SELECT r FROM Rozklady r WHERE r.idNauczyciela = :id"),

})
public class Uwagi implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "idUcznia", nullable = false)
    private Integer idUcznia;

    @Column(name = "idNauczyciela", nullable = false)
    private Integer idNauczyciela;

    @Column(name = "tresc")
    private String tresc;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setIdUcznia(Integer idUcznia) {
        this.idUcznia = idUcznia;
    }

    public Integer getIdUcznia() {
        return idUcznia;
    }

    public void setIdNauczyciela(Integer idNauczyciela) {
        this.idNauczyciela = idNauczyciela;
    }

    public Integer getIdNauczyciela() {
        return idNauczyciela;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getTresc() {
        return tresc;
    }

    @Override
    public String toString() {
        return "Uwagi{" +
                "ID=" + ID + '\'' +
                "idUcznia=" + idUcznia + '\'' +
                "idNauczyciela=" + idNauczyciela + '\'' +
                "tresc=" + tresc + '\'' +
                '}';
    }
}
