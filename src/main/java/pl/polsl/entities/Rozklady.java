package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rozklady")

public class Rozklady implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Id
    @Column(name = "idKlasy", nullable = false)
    private Integer idKlasy;

    @Column(name = "dzien", nullable = false)
    private String dzien;

    @Column(name = "godzina", nullable = false)
    private Integer godzina;

    @Column(name = "idNauczyciela", nullable = false)
    private Integer idNauczyciela;

    @Column(name = "idPrzedmiotu", nullable = false)
    private Integer idPrzedmiotu;

    @Column(name = "idSali", nullable = false)
    private Integer idSali;

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

    public void setDzien(String dzien) {
        this.dzien = dzien;
    }

    public String getDzien() {
        return dzien;
    }

    public void setGodzina(Integer godzina) {
        this.godzina = godzina;
    }

    public Integer getGodzina() {
        return godzina;
    }

    public void setIdNauczyciela(Integer idNauczyciela) {
        this.idNauczyciela = idNauczyciela;
    }

    public Integer getIdNauczyciela() {
        return idNauczyciela;
    }

    public void setIdPrzedmiotu(Integer idPrzedmiotu) {
        this.idPrzedmiotu = idPrzedmiotu;
    }

    public Integer getIdPrzedmiotu() {
        return idPrzedmiotu;
    }

    public void setIdSali(Integer idSali) {
        this.idSali = idSali;
    }

    public Integer getIdSali() {
        return idSali;
    }

    @Override
    public String toString() {
        return "Rozklady{" +
                "ID=" + ID + '\'' +
                "idKlasy=" + idKlasy + '\'' +
                "dzien=" + dzien + '\'' +
                "godzina=" + godzina + '\'' +
                "idNauczyciela=" + idNauczyciela + '\'' +
                "idPrzedmiotu=" + idPrzedmiotu + '\'' +
                "idSali=" + idSali + '\'' +
                '}';
    }
}
