package pl.polsl.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "autor")
public class Autor implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "klucz", nullable = false)
    private Integer klucz;

    @Column(name = "Imię")
    private String imię;

    @Column(name = "Nazwisko")
    private String nazwisko;

    public void setKlucz(Integer klucz) {
        this.klucz = klucz;
    }

    public Integer getKlucz() {
        return klucz;
    }

    public void setImię(String imię) {
        this.imię = imię;
    }

    public String getImię() {
        return imię;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    @Override
    public String toString() {
        return "Autor{" +
                "klucz=" + klucz + '\'' +
                "imię=" + imię + '\'' +
                "nazwisko=" + nazwisko + '\'' +
                '}';
    }
}
