package pl.polsl.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "wiadomosci")
public class Wiadomosci implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "typ", nullable = false)
    private Integer typ;

    @Column(name = "tresc")
    private String tresc;

    @Column(name = "idRodzica", nullable = false)
    private Integer idRodzica;

    @Column(name = "idUcznia", nullable = false)
    private Integer idUcznia;

    @Column(name = "idNauczyciela", nullable = false)
    private Integer idNauczyciela;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setTyp(Integer typ) {
        this.typ = typ;
    }

    public Integer getTyp() {
        return typ;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public String getTresc() {
        return tresc;
    }

    public void setIdRodzica(Integer idRodzica) {
        this.idRodzica = idRodzica;
    }

    public Integer getIdRodzica() {
        return idRodzica;
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

    @Override
    public String toString() {
        return "Wiadomosci{" +
                "ID=" + ID + '\'' +
                "typ=" + typ + '\'' +
                "tresc=" + tresc + '\'' +
                "idRodzica=" + idRodzica + '\'' +
                "idUcznia=" + idUcznia + '\'' +
                "idNauczyciela=" + idNauczyciela + '\'' +
                '}';
    }
}
