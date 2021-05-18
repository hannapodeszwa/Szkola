package pl.polsl.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "oceny")
public class Oceny implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "idUcznia", nullable = false)
    private Integer idUcznia;

    @Column(name = "idPrzedmiotu", nullable = false)
    private Integer idPrzedmiotu;

    @Column(name = "ocena", nullable = false)
    private Float ocena;

    @Column(name = "waga", nullable = false)
    private Float waga;

    @Column(name = "data", nullable = false)
    private Date data;

    @Column(name = "opis")
    private String opis;

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

    public void setIdPrzedmiotu(Integer idPrzedmiotu) {
        this.idPrzedmiotu = idPrzedmiotu;
    }

    public Integer getIdPrzedmiotu() {
        return idPrzedmiotu;
    }

    public void setOcena(Float ocena) {
        this.ocena = ocena;
    }

    public Float getOcena() {
        return ocena;
    }

    public void setWaga(Float waga) {
        this.waga = waga;
    }

    public Float getWaga() {
        return waga;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getData() {
        return data;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getOpis() {
        return opis;
    }

    @Override
    public String toString() {
        return "Oceny{" +
                "ID=" + ID + '\'' +
                "idUcznia=" + idUcznia + '\'' +
                "idPrzedmiotu=" + idPrzedmiotu + '\'' +
                "ocena=" + ocena + '\'' +
                "waga=" + waga + '\'' +
                "data=" + data + '\'' +
                "opis=" + opis + '\'' +
                '}';
    }
}
