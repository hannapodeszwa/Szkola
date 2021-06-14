package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "przedmioty")
@NamedQueries({
        @NamedQuery(name = "przedmioty.findAll", query = "SELECT p FROM Przedmioty p"),
        @NamedQuery(name = "przedmioty.findById", query = "SELECT p FROM Przedmioty p WHERE p.ID = :id")
})
public class Przedmioty implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "nazwa", nullable = false)
    private String nazwa;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getNazwa() {
        return nazwa;
    }

    @Override
    public String toString() {
        return "Przedmioty{" +
                "ID=" + ID + '\'' +
                "nazwa=" + nazwa + '\'' +
                '}';
    }
}
