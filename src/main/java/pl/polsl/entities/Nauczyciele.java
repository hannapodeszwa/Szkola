package pl.polsl.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "nauczyciele")
public class Nauczyciele implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "imie", nullable = false)
    private String imie;

    @Column(name = "drugieImie")
    private String drugieImie;

    @Column(name = "Nazwisko", nullable = false)
    private String nazwisko;

    @Column(name = "nrKontaktowy")
    private String nrKontaktowy;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getImie() {
        return imie;
    }

    public void setDrugieImie(String drugieImie) {
        this.drugieImie = drugieImie;
    }

    public String getDrugieImie() {
        return drugieImie;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNrKontaktowy(String nrKontaktowy) {
        this.nrKontaktowy = nrKontaktowy;
    }

    public String getNrKontaktowy() {
        return nrKontaktowy;
    }

    @Override
    public String toString() {
        return "Nauczyciele{" +
                "ID=" + ID + '\'' +
                "imie=" + imie + '\'' +
                "drugieImie=" + drugieImie + '\'' +
                "nazwisko=" + nazwisko + '\'' +
                "nrKontaktowy=" + nrKontaktowy + '\'' +
                '}';
    }
}
