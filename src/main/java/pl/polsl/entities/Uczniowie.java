package pl.polsl.entities;

import pl.polsl.model.SchoolClass;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "uczniowie")
@NamedQueries({
        @NamedQuery(name = "uczniowie.getHighestID", query = "SELECT u.ID FROM Uczniowie u ORDER BY u.ID DESC"),
        @NamedQuery(name = "uczniowie.findAll", query = "SELECT u FROM Uczniowie u"),
        @NamedQuery(name = "uczniowie.findById", query = "SELECT u FROM Uczniowie u WHERE u.ID = :id"),
        @NamedQuery(name = "uczniowie.getStudentEmailById", query = "SELECT u.email FROM Uczniowie u WHERE u.ID = :ID")})
public class Uczniowie implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column(name = "ID", nullable = false)
    public Integer ID;

    @Column(name = "email", nullable = false)
    public String email;

    @Column(name = "idKlasy", nullable = false)
    public Integer idKlasy;

    @Column(name = "imie", nullable = false)
    public String imie;

    @Column(name = "drugieImie")
    public String drugieImie;

    @Column(name = "nazwisko", nullable = false)
    public String nazwisko;

    @Column(name = "adres")
    public String adres;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setIdKlasy(Integer idKlasy) {
        this.idKlasy = idKlasy;
    }

    public Integer getIdKlasy() {
        return idKlasy;
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

    public void setAdres(String adres) {
        this.adres = adres;
    }

    public String getAdres() {
        return adres;
    }

    @Override
    public String toString() {
        return "Uczniowie{" +
                "ID=" + ID + '\'' +
                "idKlasy=" + idKlasy + '\'' +
                "imie=" + imie + '\'' +
                "drugieImie=" + drugieImie + '\'' +
                "nazwisko=" + nazwisko + '\'' +
                "adres=" + adres + '\'' +
                '}';
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }
}
