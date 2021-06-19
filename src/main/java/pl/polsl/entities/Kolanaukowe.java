package pl.polsl.entities;

import javax.persistence.*;
import java.io.Serializable;

@Entity

@NamedQueries({
        @NamedQuery(name = "kolanaukowe.findByTeacher",
                query = "SELECT k FROM Kolanaukowe k WHERE k.idNauczyciela = :id")})
@Table(name = "kolanaukowe")
public class Kolanaukowe implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID", nullable = false)
    private Integer ID;

    @Column(name = "idNauczyciela", nullable = false)
    private Integer idNauczyciela;

    @Column(name = "opis")
    private String opis;

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public Integer getID() {
        return ID;
    }

    public void setIdNauczyciela(Integer idNauczyciela) {
        this.idNauczyciela = idNauczyciela;
    }

    public Integer getIdNauczyciela() {
        return idNauczyciela;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getOpis() {
        return opis;
    }

    @Override
    public String toString() {
        return "Kolanaukowe{" +
                "ID=" + ID + '\'' +
                "idNauczyciela=" + idNauczyciela + '\'' +
                "opis=" + opis + '\'' +
                '}';
    }
}
